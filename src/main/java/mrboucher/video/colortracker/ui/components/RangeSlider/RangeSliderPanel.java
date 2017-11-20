package mrboucher.video.colortracker.ui.components.RangeSlider;

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
  private JLabel titleJLabel = new JLabel();
  private JLabel lowerBoundLabel = new JLabel();
  private JLabel lowerBoundValue = new JLabel();
  private JLabel upperBoundLabel = new JLabel();
  private JLabel upperBoundValue = new JLabel();
  private RangeSlider rangeSlider;

  public RangeSliderPanel( String title, RangeFinder rangeFinder, ChangeListener changeListener  ) {
    this.rangeFinder = rangeFinder;
    this.changeListener = changeListener;
    createRangeSlider(title, rangeFinder, changeListener);
  }

  private RangeSlider createRangeSlider( String title, RangeFinder rangeFinder, ChangeListener changeListener)
  {
    //
    setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    setLayout(new GridBagLayout());

    //
    rangeSlider = new RangeSlider( title );
    this.titleJLabel.setText( title + ":" );
    lowerBoundLabel.setText("Lower value:");
    upperBoundLabel.setText("Upper value:");
    lowerBoundValue.setHorizontalAlignment(JLabel.LEFT);
    upperBoundValue.setHorizontalAlignment(JLabel.LEFT);

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
        lowerBoundValue.setText(String.valueOf(slider.getValue()));
        upperBoundValue.setText(String.valueOf(slider.getUpperValue()));
      }
    });

    //add layout
    add(titleJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));

    //
    add(lowerBoundLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    add(lowerBoundValue, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 0), 0, 0));

    //
    add(upperBoundLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 3, 3), 0, 0));
    add(upperBoundValue, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 6, 0), 0, 0));

    add(rangeSlider, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    return rangeSlider;
  }

  public void initialize() {
    // Initialize values.
    rangeSlider.setValue((int)rangeFinder.getLowerRange() );
    rangeSlider.setUpperValue((int)rangeFinder.getUpperRange());

    // Initialize value display.
    lowerBoundValue.setText(String.valueOf(rangeSlider.getValue()));
    upperBoundValue.setText(String.valueOf(rangeSlider.getUpperValue()));


  }

  public RangeSlider getRangeSlider()
  {
    return rangeSlider;
  }
}
