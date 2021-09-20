package cn.misection.booktowest.start;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

import cn.misection.booktowest.util.Reader;


public class StartAnimation {
    //动画的图片集合
    private Image[] array;
    //当前显示的动画图片
    private Image currentImage;
    //记录当前播放包第几张图片
    private int i;
    //设置是否允许播放动画的信号
    private boolean isStop;
    //设置判断动画是否播放一个循环的判断
    private boolean isLoop;
    //设置动画出现的位置
    private int x;
    private int y;
    //设置动画出现的面板
    private JPanel panel;

    public StartAnimation(int length, String s, JPanel panel, int x, int y) {
        array = new Image[length];
        for (int i = 0; i < length; i++) {
            array[i] = Reader.readImage("sources/StartPanel/" + s + "/" + (i + 1) + ".png");
        }
        currentImage = array[0];
        this.panel = panel;
        this.x = x;
        this.y = y;
        //将不可播放设为true
        isStop = true;
        //将是否为一循环设为false
        isLoop = false;
    }

    public void updateImage() {
        if (i == array.length - 1) {
            isLoop = true;
        }
        if (!isStop) {
            currentImage = array[i];
            if (i < array.length - 1) {
                i++;
            } else {
                i = 0;
            }
        }
    }

    public void drawAnimation(Graphics g) {
        g.drawImage(currentImage, x, y, panel);
    }

    public void startAnimation() {
        isStop = false;
    }

    public void stopButtonAnimation() {
        isStop = true;
        currentImage = array[0];
    }

    public void stopScorllAnimation() {
        isStop = true;
        isLoop = false;
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

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public Image[] getArray() {
        return array;
    }

    public void setArray(Image[] array) {
        this.array = array;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}
