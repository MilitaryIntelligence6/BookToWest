package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

/**
 * @author javaman
 * 战斗提示类;
 */
public class Reminder {

    /**
     * 图片引用
     */
    private Image currentImage;

    /**
     * 图片集合
     */
    private ArrayList<Image> images = new ArrayList<Image>();

    /**
     * 是否显示
     */
    private boolean isDraw;

    /**
     * 是否停止
     */
    private boolean isStop;

    /**
     * 中心
     */
    private int centreX;private int centreY;

    /**
     * 编号
     */
    private int code;

    /**
     * 目标矩形第一个角的坐标
     */
    private int dx1;private int dy1;

    /**
     * 目标矩形第二个角的坐标
     */
    private int dx2;private int dy2;

    /**
     * 源矩形第一个角的坐标
     */
    private int sx1;private int sy1;

    /**
     * 源矩形第二个角的坐标
     */
    private int sx2;private int sy2;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    public Reminder(BattlePanel battlePanel, int centreX, int centreY) {
        this.battlePanel = battlePanel;

        isDraw = false;
        isStop = true;

        sx1 = 0;
        sy1 = 0;
        sx2 = 128;
        sy2 = 24;

        this.dx1 = centreX;
        this.dx2 = centreX;
        this.dy1 = centreY;
        this.dy2 = centreY;
        this.centreX = centreX;
        this.centreY = centreY;

        loadImage();
    }

    //载入图片
    public void loadImage() {
        for (int i = 1; i <= 22; i++) {
            Image image = Reader.readImage("image/提示图/" + i + ".png");
            images.add(image);
        }
    }

    //画出
    public void drawReminder(Graphics g) {
        if (isDraw) {
            g.drawImage(currentImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, battlePanel);
        }
    }

    //显示提示
    public void show(int i) {
        currentImage = images.get(i);
        isDraw = true;
        isStop = false;
    }

    //更新
    public void update() {
        if (!isStop) {
            if (dx1 > centreX - 60) {
                dx1 -= 5;
                dx2 += 5;
                dy1 -= 1;
                dy2 += 1;
            } else {
                code++;
                if (code == 10) {
                    isDraw = false;
                    isStop = true;
                    code = 0;
                    dx1 = centreX;
                    dx2 = centreX;
                    dy1 = centreY;
                    dy2 = centreY;
                }
            }
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
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

    public int getCentreX() {
        return centreX;
    }

    public void setCentreX(int centreX) {
        this.centreX = centreX;
    }

    public int getCentreY() {
        return centreY;
    }

    public void setCentreY(int centreY) {
        this.centreY = centreY;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getDx1() {
        return dx1;
    }

    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }

    public int getDy1() {
        return dy1;
    }

    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }

    public int getDx2() {
        return dx2;
    }

    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }

    public int getDy2() {
        return dy2;
    }

    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }

    public int getSx1() {
        return sx1;
    }

    public void setSx1(int sx1) {
        this.sx1 = sx1;
    }

    public int getSy1() {
        return sy1;
    }

    public void setSy1(int sy1) {
        this.sy1 = sy1;
    }

    public int getSx2() {
        return sx2;
    }

    public void setSx2(int sx2) {
        this.sx2 = sx2;
    }

    public int getSy2() {
        return sy2;
    }

    public void setSy2(int sy2) {
        this.sy2 = sy2;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
