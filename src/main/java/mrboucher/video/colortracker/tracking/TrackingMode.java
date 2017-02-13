package mrboucher.video.colortracker.tracking;

public class TrackingMode
{
  private ModeType mode = ModeType.RETR_EXTERNAL;
  public enum ModeType
  {
    RETR_EXTERNAL(0),
    RETR_LIST(1),
    RETR_CCOMP(2),
    RETR_TREE(3),
    RETR_FLOODFILL(4);

    public int id;

    ModeType(int id)
    {
      this.id = id;
    }
  }

  public ModeType getMode()
  {
    return mode;
  }

  public void setMode(ModeType mode)
  {
    this.mode = mode;
  }
}
