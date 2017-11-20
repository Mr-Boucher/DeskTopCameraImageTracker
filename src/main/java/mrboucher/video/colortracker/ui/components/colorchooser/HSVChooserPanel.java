package mrboucher.video.colortracker.ui.components.colorchooser;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class HSVChooserPanel extends JPanel implements ChangeListener
{
//    private final JColorChooser tcc;

    public HSVChooserPanel( ImagedComponent previewPanel )
    {
        super(new BorderLayout());

//        tcc = new JColorChooser();
//        tcc.getSelectionModel().addChangeListener(this);

        //remove all color panel
//        AbstractColorChooserPanel[] panels = tcc.getChooserPanels();
//        for (AbstractColorChooserPanel accp : panels)
        {
//            tcc.removeChooserPanel( accp );
        }

        //add image color chooser panel
//        tcc.addChooserPanel( new PreviewPanel( previewPanel ) );

        //
//        add(new PreviewPanel( previewPanel ), BorderLayout.PAGE_END);
    }

    public void stateChanged(ChangeEvent e)
    {
//        Color newColor = tcc.getColor();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("ColorChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new HSVChooserPanel( null );
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                createAndShowGUI();
            }
        });
    }
}
