package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;
import java.util.List;

import cn.misection.booktowest.util.*;

/**
 * @author javaman
 * 战斗标记指示类;
 */
public class Instruct {

    /**
     * 当前图片
     */
    private Image currentImage;

    private List<Image> images = new ArrayList<>();

    /**
     * 编号
     */
    private int code;

    /**
     * 长度
     */
    private int length;

    /**
     * 出现位置
     */
    private int x;

    private int y;

    /**
     * 战斗面板
     */
    private BattlePanel battlePanel;

    /**
     * 是否画出
     */
    private boolean drawn;

    /**
     * 是否停止
     */
    private boolean stopped;

    public void getImage() {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/指示图/" + i + ".png");
            images.add(image);
        }

        currentImage = images.get(0);
    }

    //构造方法
    public Instruct(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
        this.length = 5;
        drawn = false;
        stopped = true;

        getImage();
    }

    //开始出现
    public void start() {
        int i = battlePanel.getCurrentRound();
        switch (i) {
            //张小凡
            case 1:
                x = battlePanel.getZxf().x + 210;
                y = battlePanel.getZxf().y + 75;
                break;
            //文敏
            case 2:
                x = battlePanel.getYj().x + 85;
                y = battlePanel.getYj().y - 20;
                break;
            //陆雪琪
            case 3:
                x = battlePanel.getLxq().getX() + 45;
                y = battlePanel.getLxq().getY() - 20;
                break;
        }
        drawn = true;
        stopped = false;
    }

    //结束
    public void end() {
        drawn = false;
        stopped = true;
    }

    //画出
    public void drawInstruct(Graphics g) {

        if (drawn) {
            g.drawImage(currentImage, x, y, battlePanel);
        }
    }

    //更新
    public void update() {
        if (!stopped && code < 5) {
            currentImage = images.get(code);
            code++;
        } else if (code == 5) {
            code = 0;
        }
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
