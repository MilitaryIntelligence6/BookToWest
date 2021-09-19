package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.Reader;

//进度条类
public class ProgressBar {
    //进度条图片引用及其坐标
    Image Bar;
    int BarX;
    int BarY;
    //小头像图片引用及横坐标
    Image zhang;
    int ZhangX;
    Image yujie;
    int YuX;
    Image luxueqi;
    int LuX;
    Image pet;
    int petX;
    Image enemy1;
    int Enemy1X;
    Image enemy2;
    int Enemy2X;
    Image enemy3;
    int Enemy3X;
    //是否画出
    boolean isDraw;
    //是否停止
    boolean isStop;

    BattlePanel bp;

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
            luxueqi = bp.getLxq().headImage;
        }
        pet = Reader.readImage("image/小精灵/头像.png");
        if (bp.getEm1() != null) {
            enemy1 = bp.getEm1().headImage;
        }
        if (bp.getEm2() != null) {
            enemy2 = bp.getEm2().headImage;
        }
        if (bp.getEm3() != null) {
            enemy3 = bp.getEm3().headImage;
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
                if (bp.getLxq() != null && !bp.getLxq().dead) {
                    LuX += LuXueQi.speed;
                }
                if (bp.getPet() != null) {
                    petX += bp.getPet().speed;
                }
                if (bp.getEm1() != null) {
                    Enemy1X += bp.getEm1().speed;
                }
                if (bp.getEm2() != null) {
                    Enemy2X += bp.getEm2().speed;
                }
                if (bp.getEm3() != null) {
                    Enemy3X += bp.getEm3().speed;
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
            } else if (bp.getEm1() != null && Enemy1X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(5);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEm1());
                //选择攻击对象
                if (bp.getCurrentBeAttacked() != 4) {
                    bp.getEnemyAI().heroToAttack();
                }
            } else if (bp.getEm2() != null && Enemy2X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(6);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEm2());
                //选择攻击对象
                if (bp.getCurrentBeAttacked() != 4) {
                    bp.getEnemyAI().heroToAttack();
                }
            } else if (bp.getEm3() != null && Enemy3X - BarX >= 400) {
                isStop = true;
                bp.setCurrentRound(7);
                //暂时设定攻击模式为普通攻击
                bp.getEnemyAI().skillToUse(bp.getEm3());
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
            if (bp.getEm1() != null) {
                g.drawImage(enemy1, Enemy1X, BarY, bp);
            }
            if (bp.getEm2() != null) {
                g.drawImage(enemy2, Enemy2X, BarY, bp);
            }
            if (bp.getEm3() != null) {
                g.drawImage(enemy3, Enemy3X, BarY, bp);
            }
        }
    }
}
