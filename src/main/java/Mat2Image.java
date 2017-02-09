import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class Mat2Image {
  private Mat mat;
  private BufferedImage img;
  private byte[] dat;

  public Mat2Image(Mat mat) {
    this.mat = mat;
  }

  public BufferedImage getImage(){
    int w = mat.cols();
    int h = mat.rows();
    if( w > 0 && h > 0 )
    {
      if (dat == null || dat.length != w * h * 3)
        dat = new byte[w * h * 3];

      if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != BufferedImage.TYPE_3BYTE_BGR)
        img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

      mat.get(0, 0, dat);
      img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
    }

    return img;
  }
  static{
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
  }
}
