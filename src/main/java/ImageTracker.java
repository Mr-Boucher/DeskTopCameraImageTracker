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
  //	these are the threshold values in order
  public static final Scalar REFLECTIVE_TAPE_LOWER_COLOR_BOUNDS = new Scalar(58,0,109);
  public static final Scalar REFLECTIVE_TAPE_UPPER_COLOR_BOUNDS = new Scalar(93,255,240);

  //Just testing for blue objects
  public static final Scalar TEST_LOWER = new Scalar(150/2, 100,20); //Blue objects
  public static final Scalar TEST_UPPER = new Scalar(260/2,255,255); //Blue objects

  public static final int BOUNDING_SIZE = 5;

  public void track( Tracked tracked )
  {
    Mat matHierarchy = new Mat();

    //Flip because of my camera
    Imgproc.cvtColor(tracked.getCameraInput(),tracked.getColorCorrected(),Imgproc.COLOR_BGR2RGB);

    //convert to HSV for better color processing
    Imgproc.cvtColor(tracked.getColorCorrected(),tracked.getHsv(),Imgproc.COLOR_RGB2HSV);

    //Make copy of off original/color corrected in this case
    tracked.getColorCorrected().copyTo( tracked.getTarget() );

    //mask out all colors not in range
    Core.inRange(tracked.getHsv(), TEST_LOWER, TEST_UPPER, tracked.getMasked());

    //Clean up noise
    Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
    Imgproc.morphologyEx(tracked.getMasked(),tracked.getMasked(),Imgproc.MORPH_OPEN,structuringElement);
    Imgproc.morphologyEx(tracked.getMasked(),tracked.getMasked(),Imgproc.MORPH_CLOSE,structuringElement);

    //find the edges/contours of object that meet the inRange color specifications.
    ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(tracked.getMasked().clone(), contours, matHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
    for( MatOfPoint contour : contours )
    {
      //Get the edge rectangle for all contour objects
      Rect boundingRect = Imgproc.boundingRect(contour);

      //if the width of the rect is less then 25 pixels, don't display it because they are just to small to be useful
      if( boundingRect.width > 25)
      {
        //create a circle that is surrounds the object
//        Point center = new Point(rec.br().x - rec.width / 2 , rec.br().y - rec.height / 2);
//        Imgproc.circle(tracked.getTarget(), center, rec.width / 2, new Scalar(255, 0, 0), 5);

        //Create a square around the object
        Rect rect = new Rect( boundingRect.x-BOUNDING_SIZE, boundingRect.y-BOUNDING_SIZE, boundingRect.width + (BOUNDING_SIZE * 2), boundingRect.height + (BOUNDING_SIZE * 2) );
        Imgproc.rectangle( tracked.getTarget(), rect.tl(), rect.br(), new Scalar(255, 0, 0), BOUNDING_SIZE);
      }
    }
  }
}
