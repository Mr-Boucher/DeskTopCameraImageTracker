package mrboucher.video.colortracker.ui.configuration;

import mrboucher.video.colortracker.tracking.TrackerContext;
import mrboucher.video.colortracker.tracking.TrackingMode;
import mrboucher.video.colortracker.ui.components.RangeSlider.RangeSlider;
import mrboucher.video.colortracker.ui.components.RangeSlider.RangeSliderPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class ConfigurationFrame extends JFrame implements ChangeListener, ActionListener
{
  private final JComboBox<TrackingMode.ModeType> rectangleModeChooser;
  private Rectangle frameDimension = new Rectangle(100, 100, 300, 400);
  private final TrackerContext trackerContext;

  private RangeSliderPanel objectHueSlider;
  private RangeSliderPanel objectSaturationSlider;
  private RangeSliderPanel objectValueSlider;

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
          ConfigurationFrame frame = new ConfigurationFrame( new TrackerContext( ) );
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  public ConfigurationFrame( TrackerContext trackerContext)
  {
    this(trackerContext, null );
  }

  public Rectangle getFrameDimension() {
    return frameDimension;
  }

  /**
   * Create the frame.
   */
  public ConfigurationFrame(final TrackerContext trackerContext, Component component )
  {
    this.trackerContext = trackerContext;
    //set up frame exit listeners
    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        e.getWindow().dispose();
      }
    });

    // Set window location and display.
    //frame setup
    setBounds(frameDimension);

    // Set window content and validate.
    setLayout(new BorderLayout());
    setLocationRelativeTo(component);
    setVisible(true);

    rectangleModeChooser = new JComboBox<>(TrackingMode.ModeType.values());
    rectangleModeChooser.setSelectedIndex(0);
    rectangleModeChooser.addActionListener(this);

    //Create control Panel
    objectHueSlider = new RangeSliderPanel( "Hue", trackerContext.getHueRange(), this );
    objectHueSlider.initialize();

    objectSaturationSlider = new RangeSliderPanel( "Saturation", trackerContext.getSaturationRange(), this );
    objectSaturationSlider.initialize();

    objectValueSlider = new RangeSliderPanel( "Value", trackerContext.getValueRange(), this );
    objectValueSlider.initialize();

    JPanel mainPanel = new JPanel();
    setContentPane( mainPanel );
    mainPanel.add( rectangleModeChooser );
    mainPanel.add( objectHueSlider );
    mainPanel.add( objectSaturationSlider );
    mainPanel.add( objectValueSlider );

    // Create window frame.
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("Configuration Frame");

    //
    component.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        updateColorSelector( e );
      }
    });
  }

  /**
   *
   * @param e
   */
  private void updateColorSelector( MouseEvent e ) {
    int packedInt = trackerContext.getImage().getRGB(e.getX(), e.getY());
    Color color = new Color(packedInt, true);
    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();
    float[] hsv = new float[3];
    Color.RGBtoHSB(r,g,b,hsv);

    int separationValue = 20;

    float hue = hsv[0] * 180;
    objectHueSlider.getRangeSlider().setValue( (int)hue - separationValue );
    objectHueSlider.getRangeSlider().setUpperValue( (int)hue + separationValue );

    float sat = hsv[1] * 255;
    objectSaturationSlider.getRangeSlider().setValue( (int)sat - separationValue );
    objectSaturationSlider.getRangeSlider().setUpperValue( (int)sat + separationValue );

    float value = hsv[2] * 255;
    objectValueSlider.getRangeSlider().setValue( (int)value - separationValue );
    objectValueSlider.getRangeSlider().setUpperValue( (int)value + separationValue );
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    if (e.getSource() == objectHueSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerContext.getHueRange().setLowerRange( slider.getValue() );
      trackerContext.getHueRange().setUpperRange( slider.getUpperValue() );
    }
    else if (e.getSource() == objectSaturationSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerContext.getSaturationRange().setLowerRange( slider.getValue() );
      trackerContext.getSaturationRange().setUpperRange( slider.getUpperValue() );
    }
    else if (e.getSource() == objectValueSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerContext.getValueRange().setLowerRange( slider.getValue() );
      trackerContext.getValueRange().setUpperRange( slider.getUpperValue() );
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    trackerContext.getTrackingMode().setMode( (TrackingMode.ModeType)rectangleModeChooser.getSelectedItem() );
  }
}
