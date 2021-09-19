package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

/**
 * 背景动画类;
 * @author javaman
 */
public class BackgroundAnimation {

    private Image currentImage;

    private int x;

    private int y;

    private boolean drawn;

    private boolean stopped;

    private boolean over;

    private int code;

    private int length;

    private String name;

    private BattlePanel battlePanel;

    public BackgroundAnimation(BattlePanel battlePanel, String name, int length) {
        this.setBattlePanel(battlePanel);
        setDrawn(false);
        setStopped(true);
        this.setLength(length);
        this.setName(name);
        setX(0);
        setY(0);
    }

    public BackgroundAnimation(BattlePanel battlePanel) {
        this.setBattlePanel(battlePanel);
        setDrawn(false);
        setStopped(true);
        setX(0);
        setY(0);
    }

    public void set(String name, int length) {
        this.setLength(length);
        this.setName(name);
    }

    /**
     * 画出背景动画;
     * @param g
     */
    public void drawBackAnimation(Graphics g) {
        if (isDrawn()) {
            g.drawImage(getCurrentImage(), getX(), getY(), getBattlePanel());
        }
    }

    /**
     * 更新;
     */
    public void update() {
        if (!isStopped()) {
            if (getCode() < getLength()) {
                setCurrentImage(Reader.readImage("image/背景动画/" + getName() + "/" + (getCode() + 1) + ".jpg"));
                setCode(getCode() + 1);
            }
            if (getCode() == getLength()) {
                setCode(0);
                setCurrentImage(null);
                setDrawn(false);
                setStopped(true);
                setOver(true);
            }
        }
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * 当前图片
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    /**
     * 坐标
     */
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

    /**
     * 是否画出
     */
    public boolean isDrawn() {
        return drawn;
    }

    /**
     * 是否停止
     */
    public boolean isStopped() {
        return stopped;
    }

    /**
     * 是否完毕
     */
    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * 编号
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 长度
     */
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * 名称
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 战斗面板引用
     */
    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
