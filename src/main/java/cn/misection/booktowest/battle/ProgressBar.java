package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.Reader;

/**
 * 进度条类;
 */
public class ProgressBar {

    /**
     * 进度条图片引用及其坐标;
     */
    private Image barImage;

    private int barX;

    private int barY;

    /**
     * 小头像图片引用及横坐标;
     */
    private Image zhangImage;

    private int zhangX;

    private Image yuJieImage;

    private int yuX;

    private Image luXueQiImage;

    private int luX;

    private Image pet;

    private int petX;

    private Image enemy1;

    private int enemy1X;

    private Image enemy2;

    private int enemy2X;

    private Image enemy3;

    private int enemy3X;

    /**
     * 是否画出;
     */
    private boolean drawn;

    /**
     * 是否停止;
     */
    private boolean stopped;

    private BattlePanel battlePanel;

    public ProgressBar(int x, int y, BattlePanel battlePanel) {
        this.barX = x;
        this.barY = y;
        this.battlePanel = battlePanel;
        zhangX = barX;
        yuX = barX;
        luX = barX;
        petX = barX;
        enemy1X = barX;
        enemy2X = barX;
        enemy3X = barX;
        drawn = true;
        stopped = false;
        getImage();
    }

    /**
     * 获取图片;
     */
    public void getImage() {
        barImage = Reader.readImage("image/进度条/进度条.png");
        if (battlePanel.getZxf() != null) {
            zhangImage = battlePanel.getZxf().headImage;
        }
        if (battlePanel.getYj() != null) {
            yuJieImage = battlePanel.getYj().headImage;
        }
        if (battlePanel.getLxq() != null) {
            luXueQiImage = battlePanel.getLxq().getHeadImage();
        }
        pet = Reader.readImage("image/小精灵/头像.png");
        if (battlePanel.getEnemyOne() != null) {
            enemy1 = battlePanel.getEnemyOne().getHeadImage();
        }
        if (battlePanel.getEnemyTwo() != null) {
            enemy2 = battlePanel.getEnemyTwo().getHeadImage();
        }
        if (battlePanel.getEnemyThree() != null) {
            enemy3 = battlePanel.getEnemyThree().getHeadImage();
        }
    }

    /**
     * 更新进度;
     */
    public void updateProgress() {
        if (!stopped && drawn) {
            if (zhangX - barX < 400 && yuX - barX < 400 && luX - barX < 400 && enemy1X - barX < 400 && enemy2X - barX < 400 && enemy3X - barX < 400 && petX - barX < 400) {
                if (battlePanel.getZxf() != null && !battlePanel.getZxf().isDead) {
                    zhangX += ZhangXiaoFan.speed;
                }
                if (battlePanel.getYj() != null && !battlePanel.getYj().isDead) {
                    yuX += YuJie.speed;
                }
                if (battlePanel.getLxq() != null && !battlePanel.getLxq().isDead()) {
                    luX += LuXueQi.getSpeed();
                }
                if (battlePanel.getPet() != null) {
                    petX += battlePanel.getPet().getSpeed();
                }
                if (battlePanel.getEnemyOne() != null) {
                    enemy1X += battlePanel.getEnemyOne().getSpeed();
                }
                if (battlePanel.getEnemyTwo() != null) {
                    enemy2X += battlePanel.getEnemyTwo().getSpeed();
                }
                if (battlePanel.getEnemyThree() != null) {
                    enemy3X += battlePanel.getEnemyThree().getSpeed();
                }
            } else if (zhangX - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(1);
                //将控制器绘制出来
                battlePanel.getCommand().setDrawn(true);

                //指示器画出;
                battlePanel.getInstruct().start();

            } else if (yuX - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(2);
                //将控制器绘制出来
                battlePanel.getCommand().setDrawn(true);

                //指示器画出;
                battlePanel.getInstruct().start();
            } else if (luX - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(3);
                //将控制器绘制出来
                battlePanel.getCommand().setDrawn(true);

                //指示器画出;
                battlePanel.getInstruct().start();
            } else if (petX - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(4);
                battlePanel.setCurrentPattern(1);

                //选择攻击对象
                battlePanel.getPet().enemyToAttack();
            } else if (battlePanel.getEnemyOne() != null && enemy1X - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(5);
                //暂时设定攻击模式为普通攻击
                battlePanel.getEnemyAI().skillToUse(battlePanel.getEnemyOne());
                //选择攻击对象
                if (battlePanel.getCurrentBeAttacked() != 4) {
                    battlePanel.getEnemyAI().heroToAttack();
                }
            } else if (battlePanel.getEnemyTwo() != null && enemy2X - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(6);
                //暂时设定攻击模式为普通攻击
                battlePanel.getEnemyAI().skillToUse(battlePanel.getEnemyTwo());
                //选择攻击对象
                if (battlePanel.getCurrentBeAttacked() != 4) {
                    battlePanel.getEnemyAI().heroToAttack();
                }
            } else if (battlePanel.getEnemyThree() != null && enemy3X - barX >= 400) {
                stopped = true;
                battlePanel.setCurrentRound(7);
                //暂时设定攻击模式为普通攻击
                battlePanel.getEnemyAI().skillToUse(battlePanel.getEnemyThree());
                //选择攻击对象
                if (battlePanel.getCurrentBeAttacked() != 4) {
                    battlePanel.getEnemyAI().heroToAttack();
                }
            }
        }
    }

    /**
     * 画出自己;
     * @param g
     */
    public void drawProgressBar(Graphics g) {
        if (drawn) {
            g.drawImage(barImage, barX, barY, battlePanel);
            if (battlePanel.getZxf() != null) {
                g.drawImage(zhangImage, zhangX, barY, battlePanel);
            }
            if (battlePanel.getYj() != null) {
                g.drawImage(yuJieImage, yuX, barY, battlePanel);
            }
            if (battlePanel.getLxq() != null) {
                g.drawImage(luXueQiImage, luX, barY, battlePanel);
            }
            if (battlePanel.getPet() != null) {
                g.drawImage(pet, petX, barY, battlePanel);
            }
            if (battlePanel.getEnemyOne() != null) {
                g.drawImage(enemy1, enemy1X, barY, battlePanel);
            }
            if (battlePanel.getEnemyTwo() != null) {
                g.drawImage(enemy2, enemy2X, barY, battlePanel);
            }
            if (battlePanel.getEnemyThree() != null) {
                g.drawImage(enemy3, enemy3X, barY, battlePanel);
            }
        }
    }

    public Image getBarImage() {
        return barImage;
    }

    public void setBarImage(Image barImage) {
        this.barImage = barImage;
    }

    public int getBarX() {
        return barX;
    }

    public void setBarX(int barX) {
        this.barX = barX;
    }

    public int getBarY() {
        return barY;
    }

    public void setBarY(int barY) {
        this.barY = barY;
    }

    public Image getZhangImage() {
        return zhangImage;
    }

    public void setZhangImage(Image zhangImage) {
        this.zhangImage = zhangImage;
    }

    public int getZhangX() {
        return zhangX;
    }

    public void setZhangX(int zhangX) {
        this.zhangX = zhangX;
    }

    public Image getYuJieImage() {
        return yuJieImage;
    }

    public void setYuJieImage(Image yuJieImage) {
        this.yuJieImage = yuJieImage;
    }

    public int getYuX() {
        return yuX;
    }

    public void setYuX(int yuX) {
        this.yuX = yuX;
    }

    public Image getLuXueQiImage() {
        return luXueQiImage;
    }

    public void setLuXueQiImage(Image luXueQiImage) {
        this.luXueQiImage = luXueQiImage;
    }

    public int getLuX() {
        return luX;
    }

    public void setLuX(int luX) {
        this.luX = luX;
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
        return enemy1X;
    }

    public void setEnemy1X(int enemy1X) {
        this.enemy1X = enemy1X;
    }

    public Image getEnemy2() {
        return enemy2;
    }

    public void setEnemy2(Image enemy2) {
        this.enemy2 = enemy2;
    }

    public int getEnemy2X() {
        return enemy2X;
    }

    public void setEnemy2X(int enemy2X) {
        this.enemy2X = enemy2X;
    }

    public Image getEnemy3() {
        return enemy3;
    }

    public void setEnemy3(Image enemy3) {
        this.enemy3 = enemy3;
    }

    public int getEnemy3X() {
        return enemy3X;
    }

    public void setEnemy3X(int enemy3X) {
        this.enemy3X = enemy3X;
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

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
