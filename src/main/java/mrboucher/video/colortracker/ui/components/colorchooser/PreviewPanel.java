package mrboucher.video.colortracker.ui.components.colorchooser;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviewPanel extends AbstractColorChooserPanel {
    private JComponent component;

    public PreviewPanel(JComponent component) {
        this.component = component;
        setPreferredSize(new Dimension(0, 100));
        setBorder(BorderFactory.createRaisedBevelBorder());
    }

    @Override
    public void updateChooser() {


        Color color = getColorFromModel();
        int r = color.getRed(), g = color.getGreen(),
                b = color.getBlue();

    }

    @Override
    protected void buildChooser() {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if( component instanceof ImagedComponent ) {
                    int x = e.getX();
                    int y = e.getY();
                    System.out.println("x: " + x + "y: " + y);
                    JOptionPane.showMessageDialog(null, x + "/" + y, "X/Y", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public Icon getSmallDisplayIcon() {
        return null;
    }

    @Override
    public Icon getLargeDisplayIcon() {
        return null;
    }

    public void paint(Graphics g) {
        super.paintComponent(g); // paint the background image and scale it to fill the entire space
        g.drawImage(component.createImage(component.getWidth(), component.getHeight()), component.getX(), component.getY(), null);
    }

    public void valueChanged(ListSelectionEvent e) {
        int r = 30;
        int b = 20;
        int g = 10;

        if (r != -1 && g != -1 && b != -1)
            getColorSelectionModel().setSelectedColor(new Color(r, g, b));
    }
}
