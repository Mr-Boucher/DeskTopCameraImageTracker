package mrboucher.video.colortracker.tracking;

import org.opencv.core.Scalar;

import java.awt.image.BufferedImage;

public class TrackerContext
{
  private RangeFinder hueRange  = new RangeFinder( 0, 180, 50, 130);
  private RangeFinder saturationRange  = new RangeFinder(0, 255, 100, 200);
  private RangeFinder valueRange  = new RangeFinder( 0, 255, 0, 255 );
  private TrackingMode trackingMode = new TrackingMode();
  private BufferedImage image;
  private final Object syncObject = new Object();

  public BufferedImage getImage() {
    BufferedImage result;
    synchronized ( syncObject ) {
      result = image;
    }
    return result;
  }

  public void setImage(BufferedImage image) {
    synchronized ( syncObject ) {
      this.image = image;
    }
  }

  public RangeFinder getHueRange()
  {
    return hueRange;
  }

  public RangeFinder getSaturationRange()
  {
    return saturationRange;
  }

  public RangeFinder getValueRange()
  {
    return valueRange;
  }

  public Scalar getLowerHSVScalar()
  {
    return new Scalar(hueRange.getLowerRange(), saturationRange.getLowerRange(), valueRange.getLowerRange());
  }

  public Scalar getUpperHSVScalar()
  {
    return new Scalar( hueRange.getUpperRange(), saturationRange.getUpperRange(), valueRange.getUpperRange() );
  }

  public TrackingMode getTrackingMode()
  {
    return trackingMode;
  }
}
