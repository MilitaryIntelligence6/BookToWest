package cn.misection.booktowest.util;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * @author javaman
 */
public class GameButton {
    private int currentX;
    private int currentY;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image normalImage;
    protected Image waitclickImage;
    protected Image pressedImage;
    public Image buttonImage;
    protected JPanel mp;
    public boolean clicked = false;
    public boolean moveIn = false;


    public GameButton(int x, int y, int width, int height, Image normalImage, Image waitclickImage, Image pressedImage,
                      JPanel mp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalImage = normalImage;
        this.waitclickImage = waitclickImage;
        this.pressedImage = pressedImage;
        this.buttonImage = normalImage;
        this.mp = mp;

    }

    public void isMoveIn(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = waitclickImage;
            moveIn = true;
        } else {
            buttonImage = normalImage;
        }
        moveIn = false;
    }

    public void isPressedButton(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = pressedImage;

            clicked = true;
        } else {
            buttonImage = normalImage;
        }
    }

    public void isRelesedButton(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {

            buttonImage = waitclickImage;
            clicked = false;
        } else {
            buttonImage = normalImage;
        }
    }

    public void drawButton(Graphics g) {
        g.drawImage(buttonImage, x, y, mp);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
}
