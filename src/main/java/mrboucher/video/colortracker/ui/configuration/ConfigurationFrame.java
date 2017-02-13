package mrboucher.video.colortracker.ui.configuration;

import mrboucher.video.colortracker.tracking.TrackerConfiguration;
import mrboucher.video.colortracker.tracking.TrackingMode;
import mrboucher.video.colortracker.ui.RangeSlider.RangeSlider;
import mrboucher.video.colortracker.ui.RangeSlider.RangeSliderPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfigurationFrame extends JFrame implements ChangeListener, ActionListener
{
  private final JComboBox rectangleModeChooser;
  private Rectangle frameDimension = new Rectangle(100, 100, 300, 300);
  private final TrackerConfiguration trackerConfiguration;

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
          ConfigurationFrame frame = new ConfigurationFrame( new TrackerConfiguration() );
          frame.setVisible(true);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    });
  }

  public ConfigurationFrame( TrackerConfiguration trackerConfiguration )
  {
    this( trackerConfiguration, null );
  }

  /**
   * Create the frame.
   */
  public ConfigurationFrame( TrackerConfiguration trackerConfiguration, Component component )
  {
    this.trackerConfiguration = trackerConfiguration;
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

    rectangleModeChooser = new JComboBox(TrackingMode.ModeType.values());
    rectangleModeChooser.setSelectedIndex(0);
    rectangleModeChooser.addActionListener(this);

    //Create control Panel
    objectHueSlider = new RangeSliderPanel( trackerConfiguration.getHueRange(), this );
    objectHueSlider.initialize();

    objectSaturationSlider = new RangeSliderPanel( trackerConfiguration.getSaturationRange(), this );
    objectSaturationSlider.initialize();

    objectValueSlider = new RangeSliderPanel( trackerConfiguration.getValueRange(), this );
    objectValueSlider.initialize();

    JPanel mainPanel = new JPanel();
    setContentPane( mainPanel );
    mainPanel.add( rectangleModeChooser );
    mainPanel.add( objectHueSlider );
    mainPanel.add( objectSaturationSlider );
    mainPanel.add( objectValueSlider );

    // Create window frame.
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setTitle("Configuration Frame");
  }

  @Override
  public void stateChanged(ChangeEvent e)
  {
    if (e.getSource() == objectHueSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerConfiguration.getHueRange().setLowerRange( slider.getValue() );
      trackerConfiguration.getHueRange().setUpperRange( slider.getUpperValue() );
    }
    else if (e.getSource() == objectSaturationSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerConfiguration.getSaturationRange().setLowerRange( slider.getValue() );
      trackerConfiguration.getSaturationRange().setUpperRange( slider.getUpperValue() );
    }
    else if (e.getSource() == objectValueSlider.getRangeSlider())
    {
      RangeSlider slider = (RangeSlider)e.getSource();
      trackerConfiguration.getValueRange().setLowerRange( slider.getValue() );
      trackerConfiguration.getValueRange().setUpperRange( slider.getUpperValue() );
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    trackerConfiguration.getTrackingMode().setMode( (TrackingMode.ModeType)rectangleModeChooser.getSelectedItem() );
  }
}
