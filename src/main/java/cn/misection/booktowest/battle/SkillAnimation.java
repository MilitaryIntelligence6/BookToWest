package cn.misection.booktowest.battle;


import cn.misection.booktowest.util.Reader;

import java.awt.*;
import java.util.ArrayList;

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
    private boolean isDraw;
    //是否停止
    private boolean isStop;
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
        this.bp = bp;

        this.name = name;
        this.x = x;
        this.y = y;

        this.initialX = x;
        this.initialY = y;

        this.length = length;
        this.beAttackedCode = beAttackedCode;
        this.beAttackedTimes = beAttackedTimes;
        this.runCode = runCode;
        this.attackCode = attackCode;
        this.withdrawCode = withdrawCode;
        this.offsetTo1 = offsetTo1;
        this.offsetTo2 = offsetTo2;
        this.offsetTo3 = offsetTo3;

        isDraw = false;
        isStop = true;
        isOver = false;
    }

    public SkillAnimation(BattlePanel bp) {
        this.bp = bp;
        isDraw = false;
        isStop = true;
        isOver = false;
    }

    //设置方法
    public void set(String name, int length, int x, int y, int beAttackedCode, int beAttackedTimes, int runCode,
                    int attackCode, int withdrawCode, int offsetTo1, int offsetTo2, int offsetTo3) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.initialX = x;
        this.initialY = y;

        this.length = length;
        this.beAttackedCode = beAttackedCode;
        this.beAttackedTimes = beAttackedTimes;
        this.runCode = runCode;
        this.attackCode = attackCode;
        this.withdrawCode = withdrawCode;
        this.offsetTo1 = offsetTo1;
        this.offsetTo2 = offsetTo2;
        this.offsetTo3 = offsetTo3;

        currentImage = Reader.readImage("image/技能动画/" + name + "/1.png");
    }

    //画出动画
    public void drawAnimation(Graphics g) {
        if (isDraw && currentImage != null) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop) {
            //对象为敌人一
            if (bp.getCurrentBeAttacked() == 5 || bp.getCurrentBeAttacked() == 1) {
                if (code < length) {
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 5) {
                    //让怪物显示被击效果
                    bp.getEnemyOne().setDraw(false);
                    bp.getEnemyOne().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyOne().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyOne().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 1) {
                    //让张小凡显示被击效果
                    bp.getZxf().isDraw = false;
                    bp.getZxf().beAttackedAnimation.getTimes(beAttackedTimes);
                    bp.getZxf().beAttackedAnimation.setDraw(true);
                    bp.getZxf().beAttackedAnimation.setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //停止动画
                    isStop = true;
                    //不再画出
                    isDraw = false;
                    //发出动画结束信号
                    isOver = true;
                }
            }//攻击敌人一结束

            //对象为敌人二
            if (bp.getCurrentBeAttacked() == 6 || bp.getCurrentBeAttacked() == 2) {
                if (code < length) {
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 6) {
                    //让怪物显示被击效果
                    bp.getEnemyTwo().setDraw(false);
                    bp.getEnemyTwo().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyTwo().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyTwo().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 2) {
                    //让文敏显示被击效果
                    bp.getYj().isDraw = false;
                    bp.getYj().beAttackedAnimation.getTimes(beAttackedTimes);
                    bp.getYj().beAttackedAnimation.setDraw(true);
                    bp.getYj().beAttackedAnimation.setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo2 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo2 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //停止动画
                    isStop = true;
                    //不再画出
                    isDraw = false;
                    //发出动画结束信号
                    isOver = true;
                }
            }//攻击敌人2结束

            //对象为敌人三
            if (bp.getCurrentBeAttacked() == 7 || bp.getCurrentBeAttacked() == 3) {
                if (code < length) {
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 7) {
                    //MusicReader.readmusic("刀声.wav");
                    //让怪物显示被击效果
                    bp.getEnemyThree().setDraw(false);
                    bp.getEnemyThree().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyThree().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyThree().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 3) {
                    //让陆雪琪显示被击效果
                    bp.getLxq().setIsDraw(false);
                    bp.getLxq().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getLxq().getBeAttackedAnimation().setDraw(true);
                    bp.getLxq().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo3 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo3 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //停止动画
                    isStop = true;
                    //不再画出
                    isDraw = false;
                    //发出动画结束信号
                    isOver = true;
                }
            }//攻击敌人3结束

            //对象为敌人的全体
            if (bp.getCurrentBeAttacked() == 8) {
                if (code < length) {
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode) {
                    //MusicReader.readmusic("刀声.wav");
                    for (Enemy enemy : bp.getEnemyList()) {
                        enemy.setDraw(false);
                        enemy.getBeAttackedAnimation().getTimes(beAttackedTimes);
                        enemy.getBeAttackedAnimation().setDraw(true);
                        enemy.getBeAttackedAnimation().setStop(false);
                    }
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //停止动画
                    isStop = true;
                    //不再画出
                    isDraw = false;
                    //发出动画结束信号
                    isOver = true;
                }
            }

            //对象为我方全体
            if (bp.getCurrentBeAttacked() == 4) {
                if (code < length) {
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode) {
                    for (Hero hero : bp.getHeroes()) {
                        if (!hero.isDead()) {
                            hero.setIsDraw(false);
                            hero.getBeAttackedAnimation().getTimes(beAttackedTimes);
                            hero.getBeAttackedAnimation().setDraw(true);
                            hero.getBeAttackedAnimation().setStop(false);
                        }
                    }
                    currentImage = Reader.readImage("image/技能动画/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //停止动画
                    isStop = true;
                    //不再画出
                    isDraw = false;
                    //发出动画结束信号
                    isOver = true;
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


