import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DesktopCameraFrame extends JFrame
{
  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

  private CameraThread cameraThread = new CameraThread();

  private final JPanel hsvPanel;
  private final JPanel maskedPanel;
  private final JPanel cameraInputPanel;
  private final JPanel targetPanel;
  private final JPanel mainPanel;

  private final VideoCapture cap = new VideoCapture(0);
  private ImageTracker tracker = new ImageTracker();

  /**
   * Launch the application.
   */
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          DesktopCameraFrame frame = new DesktopCameraFrame();
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public DesktopCameraFrame()
  {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        e.getWindow().dispose();
        cameraThread.setRunning(false);
      }
    });

    setBounds(100, 100, 650 * 2, 490 * 2);
    mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(mainPanel);
    mainPanel.setLayout(null);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    mainPanel.add( topPanel );

    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    mainPanel.add( bottomPanel );

    cameraInputPanel = new JPanel();
    topPanel.add( cameraInputPanel );
    hsvPanel = new JPanel();
    topPanel.add(hsvPanel);

    maskedPanel = new JPanel();
    bottomPanel.add( maskedPanel );
    targetPanel = new JPanel();
    bottomPanel.add( targetPanel );

    cameraThread.start();
  }

  public void paint(Graphics g)
  {

    Tracked tracked = new Tracked();
    if( cap.read(tracked.getCameraInput()) )
    {
      tracker.track(tracked);
      Graphics cameraInputGraphics = cameraInputPanel.getGraphics();
      cameraInputGraphics.drawImage(new Mat2Image(tracked.getColorCorrected()).getImage(), 0, 0, this);

      Graphics hsvGraphics = hsvPanel.getGraphics();
      hsvGraphics.drawImage(new Mat2Image(tracked.getHsv()).getImage(), 0, 0, this);

      Graphics maskedGraphics = maskedPanel.getGraphics();
      maskedGraphics.drawImage(new Mat2Image(tracked.getMasked()).getImage(), 0, 0, this);

      Graphics targetGraphics = targetPanel.getGraphics();
      targetGraphics.drawImage(new Mat2Image(tracked.getTarget()).getImage(), 0, 0, this);

    }
  }

  private class CameraThread extends Thread
  {
    private boolean running = false;

    public boolean isRunning()
    {
      return running;
    }

    public void setRunning(boolean running)
    {
      this.running = running;
    }

    @Override
    public void run()
    {
      running = true;
      while( running )
      {
        repaint();
        try
        {
          Thread.sleep(30);
        }
        catch (InterruptedException e)
        {
        }
      }
    }
  }
}