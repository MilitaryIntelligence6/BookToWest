package cn.misection.booktowest.start;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import javax.swing.JPanel;

/**
 * @author javaman
 */
public class Mouse {
    //鼠标的动画
    private StartAnimation mouseAnimation;
    private int[] pixels;
    private Image image;
    private Cursor transparentCursor;
    private JPanel panel;


    public Mouse(StartAnimation animation, JPanel panel) {
        this.mouseAnimation = animation;
        pixels = new int[256];
        image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        // 制作一个透明的游标
        transparentCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(image, new Point(0, 0), "hidden");
        // 插入透明游标，以此模拟无游标状态
        panel.setCursor(transparentCursor);
    }

    public void startMouseAnimation() {
        mouseAnimation.startAnimation();
    }

    public StartAnimation getMouseAnimation() {
        return mouseAnimation;
    }

    public void setMouseAnimation(StartAnimation mouseAnimation) {
        this.mouseAnimation = mouseAnimation;
    }

    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Cursor getTransparentCursor() {
        return transparentCursor;
    }

    public void setTransparentCursor(Cursor transparentCursor) {
        this.transparentCursor = transparentCursor;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
