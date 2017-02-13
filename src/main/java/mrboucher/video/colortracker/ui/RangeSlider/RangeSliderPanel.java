package mrboucher.video.colortracker.ui.RangeSlider;

import mrboucher.video.colortracker.tracking.RangeFinder;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demo application panel to display a range slider.
 */
public class RangeSliderPanel extends JPanel {

  private final RangeFinder rangeFinder;
  private final ChangeListener changeListener;
  private JLabel rangeSliderLabel1 = new JLabel();
  private JLabel rangeSliderValue1 = new JLabel();
  private JLabel rangeSliderLabel2 = new JLabel();
  private JLabel rangeSliderValue2 = new JLabel();
  private RangeSlider rangeSlider;

  public RangeSliderPanel( RangeFinder rangeFinder, ChangeListener changeListener  ) {
    this.rangeFinder = rangeFinder;
    this.changeListener = changeListener;
    createRangeSlider(rangeFinder, changeListener);
  }

  private RangeSlider createRangeSlider(RangeFinder rangeFinder, ChangeListener changeListener)
  {
    //
    setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    setLayout(new GridBagLayout());

    //
    rangeSlider = new RangeSlider();
    rangeSliderLabel1.setText("Lower value:");
    rangeSliderLabel2.setText("Upper value:");
    rangeSliderValue1.setHorizontalAlignment(JLabel.LEFT);
    rangeSliderValue2.setHorizontalAlignment(JLabel.LEFT);

    rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
    rangeSlider.setMinimum(rangeFinder.getMinRange());
    rangeSlider.setMaximum(rangeFinder.getMaxRange());

    // Add listener to update display.
    if( changeListener != null )
      rangeSlider.addChangeListener( changeListener );

    //add UI listener
    rangeSlider.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        RangeSlider slider = (RangeSlider) e.getSource();
        rangeSliderValue1.setText(String.valueOf(slider.getValue()));
        rangeSliderValue2.setText(String.valueOf(slider.getUpperValue()));
      }
    });

    //add layout
    add(rangeSliderLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    add(rangeSliderValue1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));
    add(rangeSliderLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    add(rangeSliderValue2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));
    add(rangeSlider      , new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    return rangeSlider;
  }

  public void initialize() {
    // Initialize values.
    rangeSlider.setValue((int)rangeFinder.getLowerRange() );
    rangeSlider.setUpperValue((int)rangeFinder.getUpperRange());

    // Initialize value display.
    rangeSliderValue1.setText(String.valueOf(rangeSlider.getValue()));
    rangeSliderValue2.setText(String.valueOf(rangeSlider.getUpperValue()));


  }

  public RangeSlider getRangeSlider()
  {
    return rangeSlider;
  }
}
