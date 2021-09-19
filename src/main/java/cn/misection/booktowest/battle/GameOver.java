package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.app.GameApplication;
import cn.misection.booktowest.util.*;

//我方全灭之后的图案
public class GameOver {
    private Image image1;
    private Image image2;

    //左图的八个坐标
    private int ldx1;
    private int ldy1;
    private int ldx2;
    private int ldy2;
    private int lsx1;
    private int lsy1;
    private int lsx2;
    private int lsy2;

    //右图的八个坐标
    private int rdx1;
    private int rdy1;
    private int rdx2;
    private int rdy2;
    private int rsx1;
    private int rsy1;
    private int rsx2;
    private int rsy2;

    //计数器
    private int code;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;
    //战斗面板引用
    private BattlePanel bp;

    public GameOver(BattlePanel bp) {
        this.bp = bp;
        loadImage();
        this.isDraw = false;
        this.isStop = true;
        ldx1 = 0;
        ldy1 = 0;
        ldx2 = 0;
        ldy2 = 640;
        lsx1 = 0;
        lsy1 = 0;
        lsx2 = 0;
        lsy2 = 640;

        rdx1 = 1024;
        rdy1 = 0;
        rdx2 = 1024;
        rdy2 = 640;
        rsx1 = 512;
        rsy1 = 0;
        rsx2 = 512;
        rsy2 = 640;
    }

    //载入图片
    public void loadImage() {
        image1 = Reader.readImage("image/全灭图/全灭图1.png");
        image2 = Reader.readImage("image/全灭图/全灭图2.png");
    }

    //画出
    public void drawGameOver(Graphics g) {
        if (isDraw) {
            g.drawImage(image1, ldx1, ldy1, ldx2, ldy2, lsx1, lsy1, lsx2, lsy2, bp);
            g.drawImage(image2, rdx1, rdy1, rdx2, rdy2, rsx1, rsy1, rsx2, rsy2, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop) {
            if (lsx2 < 512) {
                lsx2 += 8;
                ldx2 += 8;
                rsx1 -= 8;
                rdx1 -= 8;
            }
            if (lsx2 == 512) {
                if (code < 10) {
                    code++;
                }
                if (code == 10) {
                    //跳转回开始界面
                    if (bp.getEm1().getName().equals("罹年居士")) {
                        GameApplication.switchTo("scene");
                        bp.setEm1(null);
                        bp.getEnemies().clear();
                        bp.getZxf().deadAnimation.setDraw(false);
                        bp.getZxf().isDraw = true;
                        ZhangXiaoFan.hp = ZhangXiaoFan.hpMax / 2;
                        bp.getYj().deadAnimation.setDraw(false);
                        bp.getYj().isDraw = true;
                        YuJie.hp = YuJie.hpMax / 2;
                        bp.getHeroes().clear();
                    } else {
                        GameApplication.switchTo("start");
                        bp.setEm1(null);
                        bp.setEm2(null);
                        bp.setEm3(null);
                        bp.getEnemies().clear();
                        if (bp.getZxf() != null) {
                            bp.getZxf().deadAnimation.setDraw(false);
                            bp.getZxf().isDraw = true;
                        }
                        if (bp.getYj() != null) {
                            bp.getYj().deadAnimation.setDraw(false);
                            bp.getYj().isDraw = true;
                        }
                        if (bp.getLxq() != null) {
                            bp.getLxq().setDrawn(true);
                            bp.getLxq().getDeadAnimation().setDraw(false);
                        }
                        bp.getHeroes().clear();
                    }
                    isStop = true;
                }
            }
        }
    }

    public Image getImage1() {
        return image1;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public Image getImage2() {
        return image2;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }

    public int getLdx1() {
        return ldx1;
    }

    public void setLdx1(int ldx1) {
        this.ldx1 = ldx1;
    }

    public int getLdy1() {
        return ldy1;
    }

    public void setLdy1(int ldy1) {
        this.ldy1 = ldy1;
    }

    public int getLdx2() {
        return ldx2;
    }

    public void setLdx2(int ldx2) {
        this.ldx2 = ldx2;
    }

    public int getLdy2() {
        return ldy2;
    }

    public void setLdy2(int ldy2) {
        this.ldy2 = ldy2;
    }

    public int getLsx1() {
        return lsx1;
    }

    public void setLsx1(int lsx1) {
        this.lsx1 = lsx1;
    }

    public int getLsy1() {
        return lsy1;
    }

    public void setLsy1(int lsy1) {
        this.lsy1 = lsy1;
    }

    public int getLsx2() {
        return lsx2;
    }

    public void setLsx2(int lsx2) {
        this.lsx2 = lsx2;
    }

    public int getLsy2() {
        return lsy2;
    }

    public void setLsy2(int lsy2) {
        this.lsy2 = lsy2;
    }

    public int getRdx1() {
        return rdx1;
    }

    public void setRdx1(int rdx1) {
        this.rdx1 = rdx1;
    }

    public int getRdy1() {
        return rdy1;
    }

    public void setRdy1(int rdy1) {
        this.rdy1 = rdy1;
    }

    public int getRdx2() {
        return rdx2;
    }

    public void setRdx2(int rdx2) {
        this.rdx2 = rdx2;
    }

    public int getRdy2() {
        return rdy2;
    }

    public void setRdy2(int rdy2) {
        this.rdy2 = rdy2;
    }

    public int getRsx1() {
        return rsx1;
    }

    public void setRsx1(int rsx1) {
        this.rsx1 = rsx1;
    }

    public int getRsy1() {
        return rsy1;
    }

    public void setRsy1(int rsy1) {
        this.rsy1 = rsy1;
    }

    public int getRsx2() {
        return rsx2;
    }

    public void setRsx2(int rsx2) {
        this.rsx2 = rsx2;
    }

    public int getRsy2() {
        return rsy2;
    }

    public void setRsy2(int rsy2) {
        this.rsy2 = rsy2;
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
}
