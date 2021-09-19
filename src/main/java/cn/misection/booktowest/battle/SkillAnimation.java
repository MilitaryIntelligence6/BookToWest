package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

public class SkillAnimation {
    //当前图片引用
    private Image currentImage;
    //图片集合
    private ArrayList<Image> Images = new ArrayList<Image>();
    //编号
    private int code;
    //长度
    private int length;
    //出现位置
    private int x;
    private int y;
    //初始位置
    private int initialX;
    private int initialY;
    //战斗面板引用
    private BattlePanel bp;
    //是否画出
    private boolean drawn;
    //是否停止
    private boolean stopped;
    //是否发动结束的信号
    private boolean isOver;

    //被攻击方开始被击动画的编号
    private int beAttackedCode;
    //被击动画播放的次数
    private int beAttackedTimes;
    //技能动画三个阶段的信号
//跑位图
    private int runCode;
    //攻击图
    private int attackCode;
    //撤回图
    private int withdrawCode;

    //相对于怪物1(中间)的偏移量
    private int offsetTo1;
    //相对于怪物2(上方)的偏移量
    private int offsetTo2;
    //相对于怪物3(下方)的偏移量
    private int offsetTo3;

    private String name;
    ;

    //构造方法
    public SkillAnimation(String name, int length, int x, int y, BattlePanel bp, int beAttackedCode,
                          int beAttackedTimes, int runCode, int attackCode, int withdrawCode, int offsetTo1,
                          int offsetTo2, int offsetTo3) {
        this.setBp(bp);

        this.setName(name);
        this.setX(x);
        this.setY(y);

        this.setInitialX(x);
        this.setInitialY(y);

        this.setLength(length);
        this.setBeAttackedCode(beAttackedCode);
        this.setBeAttackedTimes(beAttackedTimes);
        this.setRunCode(runCode);
        this.setAttackCode(attackCode);
        this.setWithdrawCode(withdrawCode);
        this.setOffsetTo1(offsetTo1);
        this.setOffsetTo2(offsetTo2);
        this.setOffsetTo3(offsetTo3);

        setDrawn(false);
        setStopped(true);
        setOver(false);
    }

    public SkillAnimation(BattlePanel bp) {
        this.setBp(bp);
        setDrawn(false);
        setStopped(true);
        setOver(false);
    }

    //设置方法
    public void set(String name, int length, int x, int y, int beAttackedCode, int beAttackedTimes, int runCode,
                    int attackCode, int withdrawCode, int offsetTo1, int offsetTo2, int offsetTo3) {
        this.setName(name);
        this.setX(x);
        this.setY(y);

        this.setInitialX(x);
        this.setInitialY(y);

        this.setLength(length);
        this.setBeAttackedCode(beAttackedCode);
        this.setBeAttackedTimes(beAttackedTimes);
        this.setRunCode(runCode);
        this.setAttackCode(attackCode);
        this.setWithdrawCode(withdrawCode);
        this.setOffsetTo1(offsetTo1);
        this.setOffsetTo2(offsetTo2);
        this.setOffsetTo3(offsetTo3);

        setCurrentImage(Reader.readImage("image/技能动画/" + name + "/1.png"));
    }

    //画出动画
    public void drawAnimation(Graphics g) {
        if (isDrawn() && getCurrentImage() != null) {
            g.drawImage(getCurrentImage(), getX(), getY(), getBp());
        }
    }

    //更新
    public void update() {
        if (!isStopped()) {
            //对象为敌人一
            if (getBp().getCurrentBeAttacked() == 5 || getBp().getCurrentBeAttacked() == 1) {
                if (getCode() < getLength()) {
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 5) {
                    //让怪物显示被击效果
                    getBp().getEm1().isDraw = false;
                    getBp().getEm1().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getEm1().beAttackedAnimation.setDraw(true);
                    getBp().getEm1().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 1) {
                    //让张小凡显示被击效果
                    getBp().getZxf().isDraw = false;
                    getBp().getZxf().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getZxf().beAttackedAnimation.setDraw(true);
                    getBp().getZxf().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() < getRunCode()) {
                    setY(getY() - Math.round(getOffsetTo1() / getRunCode()));
                }
                if (getAttackCode() <= getCode() && getCode() < getWithdrawCode()) {
                    setY(getY() + Math.round(getOffsetTo1() / (getWithdrawCode() - getAttackCode())));
                }
                if (getCode() == getLength()) {
                    setCode(0);
                    setCurrentImage(null);
                    setX(getInitialX());
                    setY(getInitialY());
                    //停止动画
                    setStopped(true);
                    //不再画出
                    setDrawn(false);
                    //发出动画结束信号
                    setOver(true);
                }
            }//攻击敌人一结束

            //对象为敌人二
            if (getBp().getCurrentBeAttacked() == 6 || getBp().getCurrentBeAttacked() == 2) {
                if (getCode() < getLength()) {
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 6) {
                    //让怪物显示被击效果
                    getBp().getEm2().isDraw = false;
                    getBp().getEm2().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getEm2().beAttackedAnimation.setDraw(true);
                    getBp().getEm2().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 2) {
                    //让文敏显示被击效果
                    getBp().getYj().isDraw = false;
                    getBp().getYj().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getYj().beAttackedAnimation.setDraw(true);
                    getBp().getYj().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() < getRunCode()) {
                    setY(getY() - Math.round(getOffsetTo2() / getRunCode()));
                }
                if (getAttackCode() <= getCode() && getCode() < getWithdrawCode()) {
                    setY(getY() + Math.round(getOffsetTo2() / (getWithdrawCode() - getAttackCode())));
                }
                if (getCode() == getLength()) {
                    setCode(0);
                    setCurrentImage(null);
                    setX(getInitialX());
                    setY(getInitialY());
                    //停止动画
                    setStopped(true);
                    //不再画出
                    setDrawn(false);
                    //发出动画结束信号
                    setOver(true);
                }
            }//攻击敌人2结束

            //对象为敌人三
            if (getBp().getCurrentBeAttacked() == 7 || getBp().getCurrentBeAttacked() == 3) {
                if (getCode() < getLength()) {
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 7) {
                    //MusicReader.readmusic("刀声.wav");
                    //让怪物显示被击效果
                    getBp().getEm3().isDraw = false;
                    getBp().getEm3().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getEm3().beAttackedAnimation.setDraw(true);
                    getBp().getEm3().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode() && getBp().getCurrentBeAttacked() == 3) {
                    //让陆雪琪显示被击效果
                    getBp().getLxq().drawn = false;
                    getBp().getLxq().beAttackedAnimation.getTimes(getBeAttackedTimes());
                    getBp().getLxq().beAttackedAnimation.setDraw(true);
                    getBp().getLxq().beAttackedAnimation.setStop(false);
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() < getRunCode()) {
                    setY(getY() - Math.round(getOffsetTo3() / getRunCode()));
                }
                if (getAttackCode() <= getCode() && getCode() < getWithdrawCode()) {
                    setY(getY() + Math.round(getOffsetTo3() / (getWithdrawCode() - getAttackCode())));
                }
                if (getCode() == getLength()) {
                    setCode(0);
                    setCurrentImage(null);
                    setX(getInitialX());
                    setY(getInitialY());
                    //停止动画
                    setStopped(true);
                    //不再画出
                    setDrawn(false);
                    //发出动画结束信号
                    setOver(true);
                }
            }//攻击敌人3结束

            //对象为敌人的全体
            if (getBp().getCurrentBeAttacked() == 8) {
                if (getCode() < getLength()) {
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode()) {
                    //MusicReader.readmusic("刀声.wav");
                    for (Enemy enemy : getBp().getEnemies()) {
                        enemy.isDraw = false;
                        enemy.beAttackedAnimation.getTimes(getBeAttackedTimes());
                        enemy.beAttackedAnimation.setDraw(true);
                        enemy.beAttackedAnimation.setStop(false);
                    }
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() < getRunCode()) {
                    setY(getY() - Math.round(getOffsetTo1() / getRunCode()));
                }
                if (getAttackCode() <= getCode() && getCode() < getWithdrawCode()) {
                    setY(getY() + Math.round(getOffsetTo1() / (getWithdrawCode() - getAttackCode())));
                }
                if (getCode() == getLength()) {
                    setCode(0);
                    setCurrentImage(null);
                    setX(getInitialX());
                    setY(getInitialY());
                    //停止动画
                    setStopped(true);
                    //不再画出
                    setDrawn(false);
                    //发出动画结束信号
                    setOver(true);
                }
            }

            //对象为我方全体
            if (getBp().getCurrentBeAttacked() == 4) {
                if (getCode() < getLength()) {
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() == getBeAttackedCode()) {
                    for (Hero hero : getBp().getHeroes()) {
                        if (!hero.isDead()) {
                            hero.setIsDraw(false);
                            hero.getBeAttackedAnimation().getTimes(getBeAttackedTimes());
                            hero.getBeAttackedAnimation().setDraw(true);
                            hero.getBeAttackedAnimation().setStop(false);
                        }
                    }
                    setCurrentImage(Reader.readImage("image/技能动画/" + getName() + "/" + (getCode() + 1) + ".png"));
                    setCode(getCode() + 1);
                }
                if (getCode() < getRunCode()) {
                    setY(getY() - Math.round(getOffsetTo1() / getRunCode()));
                }
                if (getAttackCode() <= getCode() && getCode() < getWithdrawCode()) {
                    setY(getY() + Math.round(getOffsetTo1() / (getWithdrawCode() - getAttackCode())));
                }
                if (getCode() == getLength()) {
                    setCode(0);
                    setCurrentImage(null);
                    setX(getInitialX());
                    setY(getInitialY());
                    //停止动画
                    setStopped(true);
                    //不再画出
                    setDrawn(false);
                    //发出动画结束信号
                    setOver(true);
                }
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

    public int getInitialX() {
        return initialX;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
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

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getBeAttackedCode() {
        return beAttackedCode;
    }

    public void setBeAttackedCode(int beAttackedCode) {
        this.beAttackedCode = beAttackedCode;
    }

    public int getBeAttackedTimes() {
        return beAttackedTimes;
    }

    public void setBeAttackedTimes(int beAttackedTimes) {
        this.beAttackedTimes = beAttackedTimes;
    }

    public int getRunCode() {
        return runCode;
    }

    public void setRunCode(int runCode) {
        this.runCode = runCode;
    }

    public int getAttackCode() {
        return attackCode;
    }

    public void setAttackCode(int attackCode) {
        this.attackCode = attackCode;
    }

    public int getWithdrawCode() {
        return withdrawCode;
    }

    public void setWithdrawCode(int withdrawCode) {
        this.withdrawCode = withdrawCode;
    }

    public int getOffsetTo1() {
        return offsetTo1;
    }

    public void setOffsetTo1(int offsetTo1) {
        this.offsetTo1 = offsetTo1;
    }

    public int getOffsetTo2() {
        return offsetTo2;
    }

    public void setOffsetTo2(int offsetTo2) {
        this.offsetTo2 = offsetTo2;
    }

    public int getOffsetTo3() {
        return offsetTo3;
    }

    public void setOffsetTo3(int offsetTo3) {
        this.offsetTo3 = offsetTo3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


