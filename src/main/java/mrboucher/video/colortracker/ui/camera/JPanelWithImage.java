package mrboucher.video.colortracker.ui.camera;

import mrboucher.video.colortracker.ui.components.colorchooser.ImagedComponent;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class JPanelWithImage extends JPanel implements ImagedComponent{

    private BufferedImage image;

    @Override
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
