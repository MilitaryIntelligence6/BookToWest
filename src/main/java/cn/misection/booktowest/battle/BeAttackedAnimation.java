package cn.misection.booktowest.battle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import cn.misection.booktowest.media.MusicReader;

import cn.misection.booktowest.util.Reader;

//受到攻击的动画
public class BeAttackedAnimation {
    //当前图片引用
    Image currentImage;
    //图片集合
    ArrayList<Image> Images = new ArrayList<Image>();
    //编号
    int code;
    //当前次数
    int currentTime;
    //次数
    int times;
    //长度
    int length;
    //出现位置
    int x;
    int y;
    //战斗面板引用
    BattlePanel bp;
    //是否画出
    boolean isDraw;
    //是否停止
    boolean isStop;

    //获得图片
    public void getImage(String name, int length) {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/被击动画/" + name + "/" + i + ".png");
            Images.add(image);
        }
        currentImage = Images.get(0);
    }

    //构造方法
    public BeAttackedAnimation(String name, int length, int x, int y, BattlePanel bp) {
        this.bp = bp;
        getImage(name, length);

        this.x = x;
        this.y = y;
        this.length = length;
        this.currentTime = 1;

        isDraw = false;
        isStop = true;
    }

    //得到播放次数
    public void getTimes(int times) {
        this.times = times;
    }

    //画出动画
    public void drawAnimation(Graphics g) {
        if (isDraw && currentImage != null) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop && code < length) {
            currentImage = Images.get(code);
            code++;
        } else if (code == length) {
            code = 0;
            currentImage = Images.get(code);

            if (currentTime == times) {
                //停止动画
                isStop = true;
                //不再画出
                isDraw = false;
                currentTime = 1;

                //重新出现
                if (bp.getCurrentBeAttacked() == 1) {
                    bp.getZxf().isDraw = true;
                }
                if (bp.getCurrentBeAttacked() == 2) {
                    bp.getYj().isDraw = true;
                }
                if (bp.getCurrentBeAttacked() == 3) {
                    bp.getLxq().drawn = true;
                }
                if (bp.getCurrentBeAttacked() == 4) {
                    for (Hero h : bp.getHeroes()) {
                        if (!h.isDead()) {
                            h.setIsDraw(true);
                        }
                    }
                }
                if (bp.getCurrentBeAttacked() == 5) {
                    bp.getEm1().isDraw = true;
                }
                if (bp.getCurrentBeAttacked() == 6) {
                    bp.getEm2().isDraw = true;
                }
                if (bp.getCurrentBeAttacked() == 7) {
                    bp.getEm3().isDraw = true;
                }

                if (bp.getCurrentBeAttacked() == 8) {
                    for (Enemy e : bp.getEnemies()) {
                        e.isDraw = true;
                    }
                }
            } else {
                currentTime++;
            }
            MusicReader.readMusic("刀声.wav");
        }
    }
}

