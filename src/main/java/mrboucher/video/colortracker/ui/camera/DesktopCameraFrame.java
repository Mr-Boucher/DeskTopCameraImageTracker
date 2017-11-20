package mrboucher.video.colortracker.ui.camera;

import mrboucher.video.colortracker.tracking.ImageTracker;
import mrboucher.video.colortracker.tracking.Target;
import mrboucher.video.colortracker.tracking.Tracked;
import mrboucher.video.colortracker.tracking.TrackerContext;
import mrboucher.video.colortracker.ui.configuration.ConfigurationFrame;
import org.opencv.core.Core;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class DesktopCameraFrame extends JFrame implements ChangeListener
{
  static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

  private CameraThread cameraThread = new CameraThread();

  private Rectangle frameDimension = new Rectangle(100, 100, 650 * 2, 490 * 2);
  private JPanel hsvPanel = null;
  private JPanel maskedPanel = null;
  private JPanelWithImage cameraInputPanel = null;
  private JPanel targetPanel = null;
  private JPanel mainPanel = null;

  private ConfigurationFrame configurationFrame;
  private final VideoCapture cap = new VideoCapture(0);

  private TrackerContext trackerContext;

  private ImageTracker tracker;

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
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        e.getWindow().dispose();
        cameraThread.setRunning(false);
      }
    });

    //frame setup
    setBounds(frameDimension);

    // Set window content and validate.
    setLayout(new BorderLayout());

    //Create panel within the frame
    mainPanel = new JPanel();
    mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(mainPanel);
    mainPanel.setLayout(null);
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    //layout 2 panels on top, color correct and HSV
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
    mainPanel.add( topPanel );
    cameraInputPanel = new JPanelWithImage();
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

    //
    trackerContext = new TrackerContext( );
    tracker = new ImageTracker(trackerContext);
    configurationFrame = new ConfigurationFrame(trackerContext, this );

    //start the background thread to run the receive the camera data
    cameraThread.start();
  }

  /**
   *
   * @param e
   */
  public void stateChanged(ChangeEvent e)
  {
  }

  /**
   * Paint all the images on the panels
   *
   * @param g graphics object
   */
  public void paint(Graphics g)
  {
    //display the processed captured image
    Tracked tracked = new Tracked( new Target( 200, 200, (int)frameDimension.getWidth()/2, (int)frameDimension.getHeight()/2 ) );
    if( cap.read(tracked.getCameraInput()) )
    {
      //process the captured image
      tracker.track(tracked);

      //display the color correct original image
      Graphics cameraInputGraphics = cameraInputPanel.getGraphics();
      BufferedImage image = Tracked.getImage(tracked.getColorCorrected());
      trackerContext.setImage( image );
      cameraInputGraphics.drawImage(image, 0, 0, this);

      //display the hsv image
      Graphics hsvGraphics = hsvPanel.getGraphics();
      hsvGraphics.drawImage(Tracked.getImage(tracked.getHsv()), 0, 0, this);

      //display the black and white masked image
      Graphics maskedGraphics = maskedPanel.getGraphics();
      maskedGraphics.drawImage(Tracked.getImage(tracked.getMasked()), 0, 0, this);

      //display the resulting image with target circle(s)
      Graphics targetGraphics = targetPanel.getGraphics();
      targetGraphics.drawImage(Tracked.getImage(tracked.getTarget().getMat()), 0, 0, this);
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
          //ignore
        }
      }
    }
  }
}