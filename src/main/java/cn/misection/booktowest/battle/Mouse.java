package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author javaman
 * 游标类;
 */
public class Mouse {

    /**
     * 图片引用
     */
    private Image mouseImage;

    /**
     * 当前的图片
     */
    private Image currentImage;

    /**
     * 图片集合
     */
    private List<Image> images = new ArrayList<>();

    /**
     * 坐标
     */
    private int x;

    private int y;

    /**
     * 是否画出
     */
    private boolean isDraw;

    /**
     * 是否停止
     */
    private boolean isStop;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    /**
     * 编号
     */
    private int code;

    public Mouse(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
        x = battlePanel.getCurrentX();
        y = battlePanel.getCurrentY();
        getImage();
        isDraw = true;
        isStop = false;
    }

    //读取图片
    public void getImage() {
        for (int i = 1; i <= 8; i++) {
            mouseImage = Reader.readImage("image/鼠标图/" + i + ".png");
            images.add(mouseImage);
        }
    }

    //画出游标
    public void drawMouse(Graphics g) {
        if (isDraw) {
            g.drawImage(currentImage, x, y, battlePanel);
        }
    }

    /**
     * 更新方法;
     */
    public void update() {
        x = battlePanel.getCurrentX();
        y = battlePanel.getCurrentY();
        if (!isStop && code < 8) {
            currentImage = images.get(code);
            code++;
        } else if (code == 8) {
            code = 0;
        }
    }

    public Image getMouseImage() {
        return mouseImage;
    }

    public void setMouseImage(Image mouseImage) {
        this.mouseImage = mouseImage;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
