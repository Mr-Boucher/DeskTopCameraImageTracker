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
    //set up frame exit listeners
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

    //Create panel within the frame
    setBounds(100, 100, 650 * 2, 490 * 2);
    mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(mainPanel);
    mainPanel.setLayout(null);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //layout 2 panels on top, color correct and HSV
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    mainPanel.add( topPanel );
    cameraInputPanel = new JPanel();
    topPanel.add( cameraInputPanel );
    hsvPanel = new JPanel();
    topPanel.add(hsvPanel);

    //layout 2 panels on bottom Masked and Targeted
    JPanel bottomPanel = new JPanel();
    bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
    mainPanel.add( bottomPanel );
    maskedPanel = new JPanel();
    bottomPanel.add( maskedPanel );
    targetPanel = new JPanel();
    bottomPanel.add( targetPanel );

    //start the background thread to run the receive the camera data
    cameraThread.start();
  }

  /**
   * Paint all the images on the panels
   *
   * @param g graphics object
   */
  public void paint(Graphics g)
  {
    //display the processed captured image
    Tracked tracked = new Tracked();
    if( cap.read(tracked.getCameraInput()) )
    {
      //process the captured image
      tracker.track(tracked);

      //display the color correct original image
      Graphics cameraInputGraphics = cameraInputPanel.getGraphics();
      cameraInputGraphics.drawImage(new Mat2Image(tracked.getColorCorrected()).getImage(), 0, 0, this);

      //display the hsv image
      Graphics hsvGraphics = hsvPanel.getGraphics();
      hsvGraphics.drawImage(new Mat2Image(tracked.getHsv()).getImage(), 0, 0, this);

      //display the black and white masked image
      Graphics maskedGraphics = maskedPanel.getGraphics();
      maskedGraphics.drawImage(new Mat2Image(tracked.getMasked()).getImage(), 0, 0, this);

      //display the resulting image with target circle(s)
      Graphics targetGraphics = targetPanel.getGraphics();
      targetGraphics.drawImage(new Mat2Image(tracked.getTarget()).getImage(), 0, 0, this);
    }
  }

  /**
   * background thread for handling windowing events and displaying the processed images
   */
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