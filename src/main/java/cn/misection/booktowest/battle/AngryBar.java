package cn.misection.booktowest.battle;


import cn.misection.booktowest.util.Reader;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * @author javaman
 * 怒气槽类;
 */
public class AngryBar {
    /**
     * 背景层
     */
    private Image back;

    /**
     * 当前图片
     */
    private Image currentImage;

    /**
     * 图片集合
     */
    private List<Image> images = new ArrayList<Image>();

    /**
     * 计时用编号
     */
    private int code;

    /**
     * 是否画出
     */
    private boolean isDraw;

    /**
     * 是否停止
     */
    private boolean isStop;

    /**
     * 底图的坐标
     */
    private int backX;

    private int backY;

    /**
     * 里部图的坐标
     */
    private int dx1;

    private int dy1;

    private int dx2;

    private int dy2;

    private int sx1;

    private int sy1;

    private int sx2;

    private int sy2;

    /**
     * 战斗面板引用
     */
    private BattlePanel bp;

    /**
     * 当前英雄
     */
    private Hero hero;

    public AngryBar(BattlePanel bp, Hero hero) {
        this.bp = bp;
        this.hero = hero;

        switch (hero.getRoleCode()) {
            //张小凡
            case 1:
                backX = 170;
                backY = 510 - 96 / 2 + 20;
                dx1 = backX + 8;
                dy1 = backY + 8 + 80;
                dx2 = dx1 + 80;
                dy2 = dy1;
                break;
            //文敏
            case 2:
                backX = 170 + 322;
                backY = 510 - 96 / 2 + 20;
                dx1 = backX + 8;
                dy1 = backY + 8 + 80;
                dx2 = dx1 + 80;
                dy2 = dy1;
                break;
            //陆雪琪
            case 3:
                backX = 170 + 322 * 2;
                backY = 510 - 96 / 2 + 20;
                dx1 = backX + 8;
                dy1 = backY + 8 + 80;
                dx2 = dx1 + 80;
                dy2 = dy1;
                break;
            default:
                break;
        }

        sx1 = 0;
        sy1 = 80;
        sx2 = 80;
        sy2 = 80;

        loadImage();
        currentImage = images.get(0);

        isDraw = true;
        isStop = false;

    }

    //载入图片
    public void loadImage() {
        back = Reader.readImage("image/怒气槽/底.png");
        for (int i = 1; i <= 4; i++) {
            Image image = Reader.readImage("image/怒气槽/" + i + ".png");
            images.add(image);
        }
    }

    //画出
    public void drawAngryBar(Graphics g) {
        if (isDraw) {
            g.drawImage(back, backX, backY, bp);
            g.drawImage(currentImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop) {
            int height = (int) (((double) hero.getAngryValue() / hero.getHpMax()) * 100);
            dy1 = backY + 8 + 80 - height;
            sy1 = 80 - height;
            if (hero.isAngry()) {
                if (code < 4) {
                    currentImage = images.get(code);
                    code++;
                }
                if (code == 4) {
                    code = 0;
                    currentImage = images.get(code);
                }
            }
        }
    }

    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
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

    public int getBackX() {
        return backX;
    }

    public void setBackX(int backX) {
        this.backX = backX;
    }

    public int getBackY() {
        return backY;
    }

    public void setBackY(int backY) {
        this.backY = backY;
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
