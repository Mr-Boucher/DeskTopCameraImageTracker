import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

/**
 * https://solderspot.wordpress.com/2014/10/18/using-opencv-for-simple-object-detection/
 *
 * Currently will find blue lego blocks
 */
public class ImageTracker
{
  //These are the threshold values in order
  public static final Scalar REFLECTIVE_TAPE_LOWER_COLOR_BOUNDS = new Scalar(58,0,109);
  public static final Scalar REFLECTIVE_TAPE_UPPER_COLOR_BOUNDS = new Scalar(93,255,240);

  //Testing for blue objects
  public static final Scalar TEST_LOWER = new Scalar(150/2, 100, 20); //Blue objects
  public static final Scalar TEST_UPPER = new Scalar(260/2, 255, 255); //Blue objects

  //Thickness of the boarder around objects
  public static final int OBJECT_BOARDER_SIZE = 2;
  public static final int TARGET_BOARDER_SIZE = 2;

  //Minimum object size to target, otherwise my have lots of very small targets
  private static final int MIN_OBJECT_SIZE = 25;

  //Default colors
  private static final Scalar OBJECT_BOARDER_COLOR = new Scalar(255, 0, 0);
  private static final Scalar TARGET_BOARDER_COLOR = new Scalar(0, 0, 255);

  /**
   * Updates the Tracked object with all the images to needed to find the object
   *
   * @param tracked all images to find object
   */
  public void track( Tracked tracked )
  {
    //Flip from BGR to RGB because of my camera colors are inverted
    Imgproc.cvtColor(tracked.getCameraInput(),tracked.getColorCorrected(),Imgproc.COLOR_BGR2RGB);

    //convert to HSV for better color processing
    Imgproc.cvtColor(tracked.getColorCorrected(),tracked.getHsv(),Imgproc.COLOR_RGB2HSV);

    //Make copy of off original/color corrected in this case
    tracked.getColorCorrected().copyTo( tracked.getTarget().getMat() );

    //mask out all colors not in range
    Core.inRange(tracked.getHsv(), TEST_LOWER, TEST_UPPER, tracked.getMasked());

    //Clean up noise
    Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
    Imgproc.morphologyEx(tracked.getMasked(),tracked.getMasked(),Imgproc.MORPH_OPEN,structuringElement);
    Imgproc.morphologyEx(tracked.getMasked(),tracked.getMasked(),Imgproc.MORPH_CLOSE,structuringElement);

    //Add target square in center
    Rect targetRectangle = tracked.getTarget().createRectangle( TARGET_BOARDER_SIZE );
    Imgproc.rectangle( tracked.getTarget().getMat(), targetRectangle.tl(), targetRectangle.br(), TARGET_BOARDER_COLOR, TARGET_BOARDER_SIZE);

    //find the edges/contours of object that meet the inRange color specifications.
    ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(tracked.getMasked().clone(), contours, tracked.getHierarchy(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
    for( MatOfPoint contour : contours )
    {
      //Get the edge rectangle for all contour objects
      Rect boundingRect = Imgproc.boundingRect(contour);

      //if the width of the rect is less then 25 pixels, don't display it because they are just to small to be useful
      if( boundingRect.width > MIN_OBJECT_SIZE && boundingRect.height > MIN_OBJECT_SIZE)
      {
        //Create a square around the object
        Rect objectRectangle = new Rect( boundingRect.x- OBJECT_BOARDER_SIZE, boundingRect.y- OBJECT_BOARDER_SIZE, boundingRect.width + (OBJECT_BOARDER_SIZE * 2), boundingRect.height + (OBJECT_BOARDER_SIZE * 2) );
        Imgproc.rectangle( tracked.getTarget().getMat(), objectRectangle.tl(), objectRectangle.br(), OBJECT_BOARDER_COLOR, OBJECT_BOARDER_SIZE);
      }
    }
  }
}
