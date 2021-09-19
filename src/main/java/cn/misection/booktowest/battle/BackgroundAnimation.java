package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.Reader;

import java.awt.*;

/**
 * 背景动画类;
 * @author javaman
 */
public class BackgroundAnimation {

    //当前图片
    private Image currentImage;
    //坐标
    private int x;
    private int y;
    //是否画出
    private boolean drawn;
    //是否停止
    private boolean stopped;
    //是否完毕
    private boolean overed;
    //编号
    private int code;
    //长度
    private int length;
    //名称
    private String name;
    //战斗面板引用
    private BattlePanel bp;

    public BackgroundAnimation(BattlePanel bp, String name, int length) {
        this.bp = bp;
        drawn = false;
        stopped = true;
        this.length = length;
        this.name = name;
        x = 0;
        y = 0;
    }

    public BackgroundAnimation(BattlePanel bp) {
        this.bp = bp;
        drawn = false;
        stopped = true;
        x = 0;
        y = 0;
    }

    public void set(String name, int length) {
        this.length = length;
        this.name = name;
    }

    //画出背景动画
    public void drawBackAnimation(Graphics g) {
        if (drawn) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //更新
    public void update() {
        if (!stopped) {
            if (code < length) {
                currentImage = Reader.readImage("image/背景动画/" + name + "/" + (code + 1) + ".jpg");
                code++;
            }
            if (code == length) {
                code = 0;
                currentImage = null;
                drawn = false;
                stopped = true;
                overed = true;
            }
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
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

    public boolean isOvered() {
        return overed;
    }

    public void setOvered(boolean overed) {
        this.overed = overed;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
