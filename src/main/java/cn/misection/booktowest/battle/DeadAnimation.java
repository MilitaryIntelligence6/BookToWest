package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.util.*;
import java.awt.*;
import java.util.List;

/**
 * @author javaman
 * 我方战斗单位死亡之后播放的动画;
 */
public class DeadAnimation {

    /**
     * 当前图片
     */
    private Image currentImage;

    /**
     * 图片集合
     */
    private List<Image> images = new ArrayList<>();

    /**
     * 画出的坐标
     */
    private int x;

    private int y;

    /**
     * 长度
     */
    private int length;

    /**
     * 编号..计时器
     */
    private int code;

    /**
     * 是否画出
     */
    private boolean drawn;

    /**
     * 是否停止
     */
    private boolean stopped;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    /**
     * 英雄引用
     */
    private Hero hero;

    public DeadAnimation(int x, int y, int length, String name, BattlePanel battlePanel, Hero hero) {
        this.x = x;
        this.y = y;
        loadImage(length, name);
        this.battlePanel = battlePanel;
        this.hero = hero;
        this.length = length;
        this.currentImage = images.get(0);

        this.drawn = false;
        this.stopped = true;
    }

    //载入图片
    public void loadImage(int length, String name) {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/死亡动画/" + name + "/" + i + ".png");
            images.add(image);
        }
    }

    //画出
    public void drawDeadAniamtion(Graphics g) {
        if (drawn) {
            g.drawImage(currentImage, x, y, battlePanel);
        }
    }

    //开始
    public void start() {
        hero.setIsDraw(false);
        drawn = true;
        stopped = false;
    }

    //更新
    public void update() {
        if (!stopped) {
            if (code < length) {
                currentImage = images.get(code);
                code++;
            }
            if (code == length) {
                code = 0;
                currentImage = images.get(code);
            }
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
