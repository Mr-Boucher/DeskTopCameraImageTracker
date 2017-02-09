import org.opencv.core.Mat;

/**
 * Created by Chad on 2/9/2017.
 */
public class Tracked
{
  private Mat cameraInput = new Mat();
  private Mat colorCorrected = new Mat();
  private Mat hsv = new Mat();
  private Mat masked = new Mat();
  private Mat target = new Mat();

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
}
