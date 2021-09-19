package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

//状态栏类
public class StateBlank {
    //三主角状态栏背景图片
    private Image zhangBack;
    private Image wenBack;
    private Image luBack;
    //生命值和灵力
    private Image hpImage;
    private Image mpImage;
    //第一张背景坐标
    private int x;
    private int y;
    //第一张血条坐标
    private int hpX;
    private int hpY;
    //第一张精气坐标
    private int mpX;
    private int mpY;
    //血条与精气的宽度和高度
    private int zHpWidth;
    private int zMpWidth;
    private int wHpWidth;
    private int wMpWidth;
    private int lHpWidth;
    private int lMpWidth;
    private int height;

    //字体
    private Font font;

    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;
    //战斗面板引用
    private BattlePanel bp;

    public StateBlank(BattlePanel bp) {
        this.bp = bp;

        x = 0;
        y = 510;

        hpX = 90;
        hpY = 580;

        mpX = 90;
        mpY = 600;

        height = 8;
        zHpWidth = (int) (((double) ZhangXiaoFan.hp / ZhangXiaoFan.hpMax) * 140);
        zMpWidth = (int) (((double) ZhangXiaoFan.mp / ZhangXiaoFan.mpMax) * 140);
        wHpWidth = (int) (((double) YuJie.hp / YuJie.hpMax) * 140);
        wMpWidth = (int) (((double) YuJie.mp / YuJie.mpMax) * 140);
        lHpWidth = (int) (((double) LuXueQi.hp / LuXueQi.hpMax) * 140);
        lMpWidth = (int) (((double) LuXueQi.mp / LuXueQi.mpMax) * 140);

        getImage();
        isDraw = true;
        isStop = true;
    }

    public void getImage() {
        if (bp.getZxf() != null) {
            zhangBack = Reader.readImage("image/状态栏/张小凡.png");
        }
        if (bp.getYj() != null) {
            wenBack = Reader.readImage("image/状态栏/文敏.png");
        }
        if (bp.getLxq() != null) {
            luBack = Reader.readImage("image/状态栏/陆雪琪.png");
        }
        hpImage = Reader.readImage("image/状态栏/生命值.png");
        mpImage = Reader.readImage("image/状态栏/灵力.png");
    }

    //画出状态栏
    public void drawStateBlank(Graphics g) {
        if (isDraw) {
            g.drawImage(zhangBack, x, y, bp);
            g.drawImage(wenBack, x + 322, y, bp);
            g.drawImage(luBack, x + 322 * 2, y, bp);


            if (bp.getZxf() != null) {
                g.drawImage(hpImage, hpX, hpY, hpX + zHpWidth, hpY + height, 0, 0, zHpWidth, height, bp);
                g.drawImage(mpImage, mpX, mpY, mpX + zMpWidth, mpY + height, 0, 0, zMpWidth, height, bp);
                g.setColor(Color.black);
                g.drawString("当前等级 :" + ZhangXiaoFan.level, hpX, hpY - 20);
                g.drawString(ZhangXiaoFan.hp + "/" + ZhangXiaoFan.hpMax, hpX + 150, hpY + 10);
                g.drawString(ZhangXiaoFan.mp + "/" + ZhangXiaoFan.mpMax, mpX + 150, mpY + 10);
            }

            if (bp.getYj() != null) {
                g.drawImage(hpImage, hpX + 322, hpY, hpX + 322 + wHpWidth, hpY + height, 0, 0, wHpWidth, height, bp);
                g.drawImage(mpImage, mpX + 322, mpY, mpX + 322 + wMpWidth, mpY + height, 0, 0, wMpWidth, height, bp);
                g.setColor(Color.black);
                g.drawString("当前等级 :" + YuJie.level, hpX + 322, hpY - 20);
                g.drawString(YuJie.hp + "/" + YuJie.hpMax, hpX + 322 + 150, hpY + 10);
                g.drawString(YuJie.mp + "/" + YuJie.mpMax, mpX + 322 + 150, mpY + 10);
            }

            if (bp.getLxq() != null) {
                g.drawImage(hpImage, hpX + 322 * 2, hpY, hpX + 322 * 2 + lHpWidth, hpY + height, 0, 0, lHpWidth,
                        height, bp);
                g.drawImage(mpImage, mpX + 322 * 2, mpY, mpX + 322 * 2 + lMpWidth, mpY + height, 0, 0, lMpWidth,
                        height, bp);
                g.setColor(Color.black);
                g.drawString("当前等级 :" + LuXueQi.getLevel(), hpX + 322 * 2, hpY - 20);
                g.drawString(LuXueQi.hp + "/" + LuXueQi.hpMax, hpX + 322 * 2 + 150, hpY + 10);
                g.drawString(LuXueQi.mp + "/" + LuXueQi.mpMax, mpX + 322 * 2 + 150, mpY + 10);
            }
        }
    }

    public void update() {
        if (zHpWidth > (int) (((double) ZhangXiaoFan.hp / ZhangXiaoFan.hpMax) * 140)) {
            zHpWidth--;
        }
        if (zHpWidth < (int) (((double) ZhangXiaoFan.hp / ZhangXiaoFan.hpMax) * 140)) {
            zHpWidth++;
        }
        if (zMpWidth > (int) (((double) ZhangXiaoFan.mp / ZhangXiaoFan.mpMax) * 140)) {
            zMpWidth--;
        }
        if (zMpWidth < (int) (((double) ZhangXiaoFan.mp / ZhangXiaoFan.mpMax) * 140)) {
            zMpWidth++;
        }
        if (wHpWidth > (int) (((double) YuJie.hp / YuJie.hpMax) * 140)) {
            wHpWidth--;
        }
        if (wHpWidth < (int) (((double) YuJie.hp / YuJie.hpMax) * 140)) {
            wHpWidth++;
        }
        if (wMpWidth > (int) (((double) YuJie.mp / YuJie.mpMax) * 140)) {
            wMpWidth--;
        }
        if (wMpWidth < (int) (((double) YuJie.mp / YuJie.mpMax) * 140)) {
            wMpWidth++;
        }
        if (lHpWidth > (int) (((double) LuXueQi.hp / LuXueQi.hpMax) * 140)) {
            lHpWidth--;
        }
        if (lHpWidth < (int) (((double) LuXueQi.hp / LuXueQi.hpMax) * 140)) {
            lHpWidth++;
        }
        if (lMpWidth > (int) (((double) LuXueQi.mp / LuXueQi.mpMax) * 140)) {
            lMpWidth--;
        }
        if (lMpWidth < (int) (((double) LuXueQi.mp / LuXueQi.mpMax) * 140)) {
            lMpWidth++;
        }
    }

    public Image getZhangBack() {
        return zhangBack;
    }

    public void setZhangBack(Image zhangBack) {
        this.zhangBack = zhangBack;
    }

    public Image getWenBack() {
        return wenBack;
    }

    public void setWenBack(Image wenBack) {
        this.wenBack = wenBack;
    }

    public Image getLuBack() {
        return luBack;
    }

    public void setLuBack(Image luBack) {
        this.luBack = luBack;
    }

    public Image getHpImage() {
        return hpImage;
    }

    public void setHpImage(Image hpImage) {
        this.hpImage = hpImage;
    }

    public Image getMpImage() {
        return mpImage;
    }

    public void setMpImage(Image mpImage) {
        this.mpImage = mpImage;
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

    public int getHpX() {
        return hpX;
    }

    public void setHpX(int hpX) {
        this.hpX = hpX;
    }

    public int getHpY() {
        return hpY;
    }

    public void setHpY(int hpY) {
        this.hpY = hpY;
    }

    public int getMpX() {
        return mpX;
    }

    public void setMpX(int mpX) {
        this.mpX = mpX;
    }

    public int getMpY() {
        return mpY;
    }

    public void setMpY(int mpY) {
        this.mpY = mpY;
    }

    public int getzHpWidth() {
        return zHpWidth;
    }

    public void setzHpWidth(int zHpWidth) {
        this.zHpWidth = zHpWidth;
    }

    public int getzMpWidth() {
        return zMpWidth;
    }

    public void setzMpWidth(int zMpWidth) {
        this.zMpWidth = zMpWidth;
    }

    public int getwHpWidth() {
        return wHpWidth;
    }

    public void setwHpWidth(int wHpWidth) {
        this.wHpWidth = wHpWidth;
    }

    public int getwMpWidth() {
        return wMpWidth;
    }

    public void setwMpWidth(int wMpWidth) {
        this.wMpWidth = wMpWidth;
    }

    public int getlHpWidth() {
        return lHpWidth;
    }

    public void setlHpWidth(int lHpWidth) {
        this.lHpWidth = lHpWidth;
    }

    public int getlMpWidth() {
        return lMpWidth;
    }

    public void setlMpWidth(int lMpWidth) {
        this.lMpWidth = lMpWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
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
}
