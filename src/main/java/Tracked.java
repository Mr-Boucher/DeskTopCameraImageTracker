import org.opencv.core.Mat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Container for all mats need to performance a single image tracking pass
 */
public class Tracked
{
  private Mat cameraInput = new Mat();
  private Mat colorCorrected = new Mat();
  private Mat hsv = new Mat();
  private Mat masked = new Mat();
  private Mat target = new Mat();
  private Mat hierarchy = new Mat();

  public Mat getHierarchy()
  {
    return hierarchy;
  }

  public Mat getColorCorrected()
  {
    return colorCorrected;
  }

  public Mat getCameraInput()
  {
    return cameraInput;
  }

  public Mat getHsv()
  {
    return hsv;
  }

  public Mat getMasked()
  {
    return masked;
  }

  public Mat getTarget()
  {
    return target;
  }

  /**
   * Creates images for the Mat
   *
   * @param mat the Mat
   *
   * @return the Image
   */
  public static Image getImage( Mat mat ){
    BufferedImage img = null;
    int w = mat.cols();
    int h = mat.rows();
    if( w > 0 && h > 0 )
    {
      byte[] dat = new byte[w * h * 3];
      mat.get(0, 0, dat);
      img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
      img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
    }

    return img;
  }
}
