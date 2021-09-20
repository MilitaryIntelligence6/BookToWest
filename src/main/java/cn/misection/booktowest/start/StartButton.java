package cn.misection.booktowest.start;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class StartButton {
    private int currentX;
    private int currentY;
    private int x;
    private int y;
    private int width;
    private int height;
    private Image normalImage;
    private Image waitclickImage;
    private Image pressedImage;
    private Image buttonImage;
    private JPanel mp;
    private boolean isclicked = false;
    //属于此按钮的动画
    private StartAnimation animation;

    public StartButton(int x, int y, int width, int height, Image normalImage, Image waitclickImage, Image pressedImage,
                       JPanel mp, StartAnimation animation) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalImage = normalImage;
        this.waitclickImage = waitclickImage;
        this.pressedImage = pressedImage;
        this.buttonImage = normalImage;
        this.mp = mp;
        this.animation = animation;
        this.isclicked = false;
    }

    public void isMoveIn(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = waitclickImage;
            animation.startAnimation();
        } else {
            buttonImage = normalImage;
            animation.stopButtonAnimation();
        }
    }

    public void isPressedButton(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = pressedImage;
            isclicked = true;
            animation.stopButtonAnimation();
        } else {
            buttonImage = normalImage;
        }
    }

    public void isRelesedButton(int currentX, int currentY) {
        if (currentX > x - 15 && currentX < (x + width - 15) && currentY > (y - 6) && currentY < (y + height - 6)) {
            buttonImage = waitclickImage;
            isclicked = false;

        } else {
            buttonImage = normalImage;
        }
    }

    public void drawButton(Graphics g) {
        g.drawImage(buttonImage, x, y, mp);
        animation.drawAnimation(g);
    }


    public boolean isIsclicked() {
        return isclicked;
    }

    public void setIsclicked(boolean isclicked) {
        this.isclicked = isclicked;
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

    public Image getWaitclickImage() {
        return waitclickImage;
    }

    public void setWaitclickImage(Image waitclickImage) {
        this.waitclickImage = waitclickImage;
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

    public StartAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(StartAnimation animation) {
        this.animation = animation;
    }
}


