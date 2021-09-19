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
    private List<Image> images = new ArrayList<>();

    /**
     * 计时用编号
     */
    private int code;

    /**
     * 是否画出
     */
    private boolean drawed;

    /**
     * 是否停止
     */
    private boolean stoped;

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
    private BattlePanel battlePanel;

    /**
     * 当前英雄
     */
    private Hero hero;

    public AngryBar(BattlePanel battlePanel, Hero hero) {
        this.battlePanel = battlePanel;
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

        drawed = true;
        stoped = false;

    }

    /**
     * 载入图片;
     */
    public void loadImage() {
        back = Reader.readImage("image/怒气槽/底.png");
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
        if (drawed) {
            g.drawImage(back, backX, backY, battlePanel);
            g.drawImage(currentImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, battlePanel);
        }
    }

    /**
     * 更新;
     */
    public void update() {
        if (!stoped) {
            int height = (int) (((double) hero.getAngryValue() / hero.getHpMax()) * 100);
            dy1 = backY + 8 + 80 - height;
            sy1 = 80 - height;
            if (hero.wheatherAngry()) {
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

}
