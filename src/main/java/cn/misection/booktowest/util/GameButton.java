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
    private int x;
    private int y;
    private int width;
    private int height;
    private Image normalImage;
    private Image waitClickImage;
    private Image pressedImage;
    private Image buttonImage;
    private JPanel mp;
    private boolean clicked = false;
    private boolean moveIn = false;


    public GameButton(int x, int y, int width, int height, Image normalImage, Image waitClickImage, Image pressedImage,
                      JPanel mp) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalImage = normalImage;
        this.waitClickImage = waitClickImage;
        this.pressedImage = pressedImage;
        this.buttonImage = normalImage;
        this.mp = mp;

    }

    public void isMoveIn(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = waitClickImage;
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
            buttonImage = waitClickImage;
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

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getNormalImage() {
        return normalImage;
    }

    public void setNormalImage(Image normalImage) {
        this.normalImage = normalImage;
    }

    public Image getWaitClickImage() {
        return waitClickImage;
    }

    public void setWaitClickImage(Image waitClickImage) {
        this.waitClickImage = waitClickImage;
    }

    public Image getPressedImage() {
        return pressedImage;
    }

    public void setPressedImage(Image pressedImage) {
        this.pressedImage = pressedImage;
    }

    public Image getButtonImage() {
        return buttonImage;
    }

    public void setButtonImage(Image buttonImage) {
        this.buttonImage = buttonImage;
    }

    public JPanel getMp() {
        return mp;
    }

    public void setMp(JPanel mp) {
        this.mp = mp;
    }

    public boolean isMoveIn() {
        return moveIn;
    }

    public void setMoveIn(boolean moveIn) {
        this.moveIn = moveIn;
    }
}
