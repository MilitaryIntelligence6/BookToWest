package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.awt.*;

public class StartAnimation {

    private BattlePanel bp;
    private int code;
    //两侧图片
    private Image left;
    private Image right;
    //左侧坐标
    private int leftX;
    private int leftY;
    //右侧坐标
    private int rightX;
    private int rightY;

    private boolean isDraw;
    private boolean isStop;

    //构造方法
    public StartAnimation(BattlePanel bp) {
        this.bp = bp;
        leftX = 0;
        leftY = 0;
        rightX = 0;
        rightY = 0;
        //读入图片 右侧图片与左侧相同
        left = Reader.readImage("image/其他/云雾.png");
        right = left;

        isDraw = true;
        isStop = false;
    }

    //画出
    public void drawStartAnimation(Graphics g) {
        if (isDraw) {
            g.drawImage(left, leftX, leftY, bp);
            g.drawImage(right, rightX, rightY, bp);
        }
    }

    public void update() {
        if (!isStop) {
            if (leftX < 1024) {
                leftX += 30;
                rightX -= 30;
            } else {
                left = null;
                right = null;
                isDraw = false;
                isStop = true;
            }
        }
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Image getLeft() {
        return left;
    }

    public void setLeft(Image left) {
        this.left = left;
    }

    public Image getRight() {
        return right;
    }

    public void setRight(Image right) {
        this.right = right;
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getLeftY() {
        return leftY;
    }

    public void setLeftY(int leftY) {
        this.leftY = leftY;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getRightY() {
        return rightY;
    }

    public void setRightY(int rightY) {
        this.rightY = rightY;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
