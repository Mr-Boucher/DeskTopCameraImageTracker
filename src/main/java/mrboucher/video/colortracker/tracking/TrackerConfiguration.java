package mrboucher.video.colortracker.tracking;

import com.sun.management.jmx.TraceFilter;
import org.opencv.core.Scalar;

public class TrackerConfiguration
{
  private RangeFinder hueRange  = new RangeFinder( 0, 180, 50, 130);
  private RangeFinder saturationRange  = new RangeFinder(0, 255, 100, 200);
  private RangeFinder valueRange  = new RangeFinder( 0, 255, 0, 255 );
  private TrackingMode trackingMode = new TrackingMode();

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
