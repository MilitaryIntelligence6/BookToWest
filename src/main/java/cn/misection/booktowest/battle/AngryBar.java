package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;
import java.util.List;

import cn.misection.booktowest.util.*;

/**
 * 怒气槽类;
 * @author javaman
 */
public class AngryBar {

    private Image back;

    private Image currentImage;

    private List<Image> images = new ArrayList<>();

    private int code;

    private boolean drawn;

    private boolean stopped;

    private int backX;

    private int backY;

    private int dx1;

    private int dy1;

    private int dx2;

    private int dy2;

    private int sx1;

    private int sy1;

    private int sx2;

    private int sy2;

    private BattlePanel battlePanel;

    private Hero hero;

    public AngryBar(BattlePanel battlePanel, Hero hero) {
        this.battlePanel = battlePanel;
        this.hero = hero;

        switch (hero.getRoleCode()) {
            //张小凡
            case 1:
                backX = 170;
                backY = (510 - (96 / 2)) + 20;
                dx1 = backX + 8;
                dy1 = backY + 8 + 80;
                dx2 = dx1 + 80;
                dy2 = dy1;
                break;
            //文敏
            case 2:
                backX = 170 + 322;
                backY = ((510 - (96 / 2)) + 20);
                dx1 = backX + 8;
                dy1 = backY + 8 + 80;
                dx2 = dx1 + 80;
                dy2 = dy1;
                break;
            //陆雪琪
            case 3:
                backX = (170 + (322 * 2));
                backY = ((510 - (96 / 2)) + 20);
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
        this.drawn = true;
        this.stopped = false;
    }

    /**
     * 载入图片;
     */
    public void loadImage() {
        back = (Reader.readImage("image/怒气槽/底.png"));
        for (int i = 1; i <= 4; i++) {
            Image image = Reader.readImage("image/怒气槽/" + i + ".png");
            images.add(image);
        }
    }

    /**
     * 画出;
     * @param g
     */
    public void drawAngryBar(Graphics g) {
        if (drawn) {
            g.drawImage(getBack(), getBackX(), getBackY(), getBattlePanel());
            g.drawImage(getCurrentImage(), getDx1(), getDy1(), getDx2(), getDy2(), getSx1(), getSy1(), getSx2(), getSy2(), getBattlePanel());
        }
    }

    /**
     * 更新;
     */
    public void update() {
        if (!stopped) {
            int height = (int) (((double) getHero().getAngryValue() / getHero().getHpMax()) * 100);
            setDy1(getBackY() + 8 + 80 - height);
            setSy1(80 - height);
            if (getHero().isAngry()) {
                if (getCode() < 4) {
                    setCurrentImage(getImages().get(getCode()));
                    setCode(getCode() + 1);
                }
                if (getCode() == 4) {
                    setCode(0);
                    setCurrentImage(getImages().get(getCode()));
                }
            }
        }
    }

    /**
     * 背景层
     */
    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
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
     * 图片集合
     */
    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * 计时用编号
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 是否画出
     */
    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    /**
     * 是否停止
     */
    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * 底图的坐标
     */
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

    /**
     * 里部图的坐标
     */
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

    /**
     * 战斗面板引用
     */
    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * 当前英雄
     */
    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
