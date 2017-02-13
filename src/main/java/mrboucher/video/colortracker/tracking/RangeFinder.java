package mrboucher.video.colortracker.tracking;

public class RangeFinder
{
  private int minRange;
  private int maxRange;
  private double lowerRange;
  private double upperRange;

  public RangeFinder(int minRange, int maxRange, double lowerRange, double upperRange)
  {
    this.minRange = minRange;
    this.maxRange = maxRange;
    this.lowerRange = lowerRange;
    this.upperRange = upperRange;
  }

  public int getMinRange()
  {
    return minRange;
  }

  public void setMinRange(int minRange)
  {
    this.minRange = minRange;
  }

  public int getMaxRange()
  {
    return maxRange;
  }

  public void setMaxRange(int maxRange)
  {
    this.maxRange = maxRange;
  }

  public double getLowerRange()
  {
    return lowerRange;
  }

  public void setLowerRange(double lowerRange)
  {
    this.lowerRange = lowerRange;
  }

  public double getUpperRange()
  {
    return upperRange;
  }

  public void setUpperRange(double upperRange)
  {
    this.upperRange = upperRange;
  }
}
