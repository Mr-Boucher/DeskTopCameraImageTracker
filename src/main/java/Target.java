import org.opencv.core.Mat;
import org.opencv.core.Rect;

public class Target
{
  private final int targetWidth;
  private final int targetHeight;
  private final int frameWidth;
  private final int frameHeight;
  private Mat mat = new Mat();

  /**
   *
   */
  public Target(int targetWidth, int targetHeight, int frameWidth, int frameHeight )
  {
    this.targetWidth = targetWidth;
    this.targetHeight = targetHeight;
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;
  }

  public Mat getMat()
  {
    return mat;
  }

  public Rect createRectangle( int targetBoarderSize )
  {
    int targetX = frameWidth/2 - targetWidth/2 - targetBoarderSize;
    int targetY = frameHeight/2 - targetHeight/2 - targetBoarderSize;
    Rect targetRectangle = new Rect( targetX, targetY, targetWidth, targetHeight );
    return targetRectangle;
  }
}
