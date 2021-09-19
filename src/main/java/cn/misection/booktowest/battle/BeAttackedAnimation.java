package cn.misection.booktowest.battle;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import cn.misection.booktowest.media.MusicReader;

import cn.misection.booktowest.util.Reader;

//受到攻击的动画
public class BeAttackedAnimation {
    //当前图片引用
    private Image currentImage;
    //图片集合
    private ArrayList<Image> Images = new ArrayList<Image>();
    //编号
    private int code;
    //当前次数
    private int currentTime;
    //次数
    private int times;
    //长度
    private int length;
    //出现位置
    private int x;
    private int y;
    //战斗面板引用
    private BattlePanel bp;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;

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
                    bp.getEm1().setDraw(true);
                }
                if (bp.getCurrentBeAttacked() == 6) {
                    bp.getEm2().setDraw(true);
                }
                if (bp.getCurrentBeAttacked() == 7) {
                    bp.getEm3().setDraw(true);
                }

                if (bp.getCurrentBeAttacked() == 8) {
                    for (Enemy e : bp.getEnemies()) {
                        e.setDraw(true);
                    }
                }
            } else {
                currentTime++;
            }
            MusicReader.readMusic("刀声.wav");
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public ArrayList<Image> getImages() {
        return Images;
    }

    public void setImages(ArrayList<Image> images) {
        Images = images;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
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
}

