package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.util.*;
import java.awt.*;

//游戏取得胜利之后的动画
public class VictoryAnimation {
    //当前图片
    private Image currentImage;
    //图片集合
    private ArrayList<Image> images = new ArrayList<Image>();
    //画出的坐标
    private int x;
    private int y;
    //长度
    private int length;
    //编号..计时器
    private int code;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;
    //战斗面板引用
    private BattlePanel bp;
    //英雄引用
    private Hero hero;

    //构造方法
    public VictoryAnimation(int x, int y, int length, String name, BattlePanel bp, Hero hero) {
        this.x = x;
        this.y = y;
        loadImage(length, name);
        this.bp = bp;
        this.hero = hero;
        this.length = length;
        this.currentImage = images.get(0);

        isDraw = false;
        isStop = true;
    }

    //载入图片
    public void loadImage(int length, String name) {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/胜利动画/" + name + "/" + i + ".png");
            images.add(image);
        }
    }

    //画出
    public void drawVictoryAnimation(Graphics g) {
        if (isDraw) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //开始
    public void start() {
        hero.setIsDraw(false);
        hero.getDeadAnimation().setDrawn(false);
        hero.getDeadAnimation().setStopped(true);
        isDraw = true;
        isStop = false;
    }

    //更新
    public void update() {
        if (!isStop) {
            if (code < length) {
                currentImage = images.get(code);
                code++;
            }
            if (code == length) {
                code = 0;
                currentImage = images.get(code);
                isStop = true;
                isDraw = false;
                hero.setIsDraw(true);
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

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
