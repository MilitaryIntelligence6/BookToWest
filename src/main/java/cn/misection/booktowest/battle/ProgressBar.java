package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.Reader;

//进度条类
public class ProgressBar {
    //进度条图片引用及其坐标
    private Image Bar;
    private int BarX;
    private int BarY;
    //小头像图片引用及横坐标
    private Image zhang;
    private int ZhangX;
    private Image yujie;
    private int YuX;
    private Image luxueqi;
    private int LuX;
    private Image pet;
    private int petX;
    private Image enemy1;
    private int Enemy1X;
    private Image enemy2;
    private int Enemy2X;
    private Image enemy3;
    private int Enemy3X;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;

    private BattlePanel bp;

    //构造方法
    public ProgressBar(int x, int y, BattlePanel bp) {
        this.BarX = x;
        this.BarY = y;

        this.bp = bp;
        ZhangX = BarX;
        YuX = BarX;
        LuX = BarX;
        petX = BarX;
        Enemy1X = BarX;
        Enemy2X = BarX;
        Enemy3X = BarX;

        isDraw = true;
        isStop = false;

        getImage();

    }

    //获取图片
    public void getImage() {
        Bar = Reader.readImage("image/进度条/进度条.png");
        if (bp.getZxf() != null) {
            zhang = bp.getZxf().headImage;
        }
        if (bp.getYj() != null) {
            yujie = bp.getYj().headImage;
        }
        if (bp.getLxq() != null) {
            luxueqi = bp.getLxq().getHeadImage();
        }
        pet = Reader.readImage("image/小精灵/头像.png");
        if (bp.getEnemyOne() != null) {
            enemy1 = bp.getEnemyOne().getHeadImage();
        }
        if (bp.getEnemyTwo() != null) {
            enemy2 = bp.getEnemyTwo().getHeadImage();
        }
        if (bp.getEnemyThree() != null) {
            enemy3 = bp.getEnemyThree().getHeadImage();
        }
    }

    //更新进度
    public void updateProgress() {
        if (!isStop && isDraw) {
            if (ZhangX - BarX < 400 && YuX - BarX < 400 && LuX - BarX < 400 && Enemy1X - BarX < 400 && Enemy2X - BarX < 400 && Enemy3X - BarX < 400 && petX - BarX < 400) {
                if (bp.getZxf() != null && !bp.getZxf().isDead) {
                    ZhangX += ZhangXiaoFan.speed;
                }
                if (bp.getYj() != null && !bp.getYj().isDead) {
                    YuX += YuJie.speed;
                }
                if (bp.getLxq() != null && !bp.getLxq().isDead()) {
                    LuX += LuXueQi.getSpeed();
                }
                if (bp.getPet() != null) {
                    petX += bp.getPet().getSpeed();
                }
                if (bp.getEnemyOne() != null) {
                    Enemy1X += bp.getEnemyOne().getSpeed();
                }
                if (bp.getEnemyTwo() != null) {
                    Enemy2X += bp.getEnemyTwo().getSpeed();
                }
                if (bp.getEnemyThree() != null) {
                    Enemy3X += bp.getEnemyThree().getSpeed();
                }
            } else if (ZhangX - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(1);
                //将控制器绘制出来
                bp.getCommand().setDraw(true);

                //指示器画出;
                bp.getInstruct().start();

            } else if (YuX - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(2);
                //将控制器绘制出来
                bp.getCommand().setDraw(true);

                //指示器画出;
                bp.getInstruct().start();
            } else if (LuX - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(3);
                //将控制器绘制出来
                bp.getCommand().setDraw(true);

                //指示器画出;
                bp.getInstruct().start();
            } else if (petX - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(4);
                bp.setCurrentPattern(1);

                //选择攻击对象
                bp.getPet().enemyToAttack();
            } else if (bp.getEnemyOne() != null && Enemy1X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(5);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEnemyOne());
                //选择攻击对象
                if (bp.getCurrentBeAttacked() != 4) {
                    bp.getEnemyAI().heroToAttack();
                }
            } else if (bp.getEnemyTwo() != null && Enemy2X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(6);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEnemyTwo());
                //选择攻击对象
                if (bp.getCurrentBeAttacked() != 4) {
                    bp.getEnemyAI().heroToAttack();
                }
            } else if (bp.getEnemyThree() != null && Enemy3X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(7);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEnemyThree());
                //选择攻击对象
                if (bp.getCurrentBeAttacked() != 4) {
                    bp.getEnemyAI().heroToAttack();
                }
            }
        }
    }

    //画出自己
    public void drawProgressBar(Graphics g) {
        if (isDraw) {
            g.drawImage(Bar, BarX, BarY, bp);
            if (bp.getZxf() != null) {
                g.drawImage(zhang, ZhangX, BarY, bp);
            }
            if (bp.getYj() != null) {
                g.drawImage(yujie, YuX, BarY, bp);
            }
            if (bp.getLxq() != null) {
                g.drawImage(luxueqi, LuX, BarY, bp);
            }
            if (bp.getPet() != null) {
                g.drawImage(pet, petX, BarY, bp);
            }
            if (bp.getEnemyOne() != null) {
                g.drawImage(enemy1, Enemy1X, BarY, bp);
            }
            if (bp.getEnemyTwo() != null) {
                g.drawImage(enemy2, Enemy2X, BarY, bp);
            }
            if (bp.getEnemyThree() != null) {
                g.drawImage(enemy3, Enemy3X, BarY, bp);
            }
        }
    }

    public Image getBar() {
        return Bar;
    }

    public void setBar(Image bar) {
        Bar = bar;
    }

    public int getBarX() {
        return BarX;
    }

    public void setBarX(int barX) {
        BarX = barX;
    }

    public int getBarY() {
        return BarY;
    }

    public void setBarY(int barY) {
        BarY = barY;
    }

    public Image getZhang() {
        return zhang;
    }

    public void setZhang(Image zhang) {
        this.zhang = zhang;
    }

    public int getZhangX() {
        return ZhangX;
    }

    public void setZhangX(int zhangX) {
        ZhangX = zhangX;
    }

    public Image getYujie() {
        return yujie;
    }

    public void setYujie(Image yujie) {
        this.yujie = yujie;
    }

    public int getYuX() {
        return YuX;
    }

    public void setYuX(int yuX) {
        YuX = yuX;
    }

    public Image getLuxueqi() {
        return luxueqi;
    }

    public void setLuxueqi(Image luxueqi) {
        this.luxueqi = luxueqi;
    }

    public int getLuX() {
        return LuX;
    }

    public void setLuX(int luX) {
        LuX = luX;
    }

    public Image getPet() {
        return pet;
    }

    public void setPet(Image pet) {
        this.pet = pet;
    }

    public int getPetX() {
        return petX;
    }

    public void setPetX(int petX) {
        this.petX = petX;
    }

    public Image getEnemy1() {
        return enemy1;
    }

    public void setEnemy1(Image enemy1) {
        this.enemy1 = enemy1;
    }

    public int getEnemy1X() {
        return Enemy1X;
    }

    public void setEnemy1X(int enemy1X) {
        Enemy1X = enemy1X;
    }

    public Image getEnemy2() {
        return enemy2;
    }

    public void setEnemy2(Image enemy2) {
        this.enemy2 = enemy2;
    }

    public int getEnemy2X() {
        return Enemy2X;
    }

    public void setEnemy2X(int enemy2X) {
        Enemy2X = enemy2X;
    }

    public Image getEnemy3() {
        return enemy3;
    }

    public void setEnemy3(Image enemy3) {
        this.enemy3 = enemy3;
    }

    public int getEnemy3X() {
        return Enemy3X;
    }

    public void setEnemy3X(int enemy3X) {
        Enemy3X = enemy3X;
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
