package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

/**
 * 陆雪琪召唤出的小精灵类;
 * @author javaman
 */
public class Pet {

    private Image petImage;

    /**
     * 小头像
     */
    private Image headImage;

    /**
     * 攻击力
     */
    private int power;

    /**
     * 速度
     */
    private int speed;

    /**
     * 位置坐标
     */
    private int x;private int y;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    /**
     * 当前攻击对象
     */
    private Enemy currentEnemy;

    /**
     * 当前造成的伤害
     */
    private int currentDamage;

    /**
     * 当前造成的伤害类型
     */
    private int currentDamageType;

    /**
     * 是否画出
     */
    private boolean drawn;

    /**
     * 是否停止
     */
    private boolean stopped;

    /**
     * 计时器
     */
    private int code;

    public Pet(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
        x = 700;
        y = 400;
        loadImage();
        loadAnimation();
        drawn = true;
        stopped = false;

        speed = (int) ((ZhangXiaoFan.speed + LuXueQi.getSpeed() + YuJie.speed) / 3);
        power = (int) ((ZhangXiaoFan.hurt + LuXueQi.getHurt() + YuJie.hurt) / 3);
    }

    /**
     * 选择攻击对象;
     */
    public void enemyToAttack() {
        int i = 0;
        while (true) {
            i = (int) (Math.random() * 3) + 5;
            if (i == 5 && battlePanel.getEnemyOne() != null) {
                battlePanel.setCurrentBeAttacked(5);
                break;
            }
            if (i == 6 && battlePanel.getEnemyTwo() != null) {
                battlePanel.setCurrentBeAttacked(6);
                break;
            }
            if (i == 7 && battlePanel.getEnemyThree() != null) {
                battlePanel.setCurrentBeAttacked(7);
                break;
            }
        }
    }

    /**
     * 计算伤害;
     */
    public void calDamage() {
        switch (battlePanel.getCurrentBeAttacked()) {
            case 5:
                currentEnemy = battlePanel.getEnemyOne();
                break;
            case 6:
                currentEnemy = battlePanel.getEnemyTwo();
                break;
            case 7:
                currentEnemy = battlePanel.getEnemyThree();
                break;
            default:
                break;
        }

        currentDamage = power - currentEnemy.getDefense();
        if (currentDamage < 0) {
            currentDamage = 0;
        }
        currentDamageType = 1;
        currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
        currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
        HurtValue hurtValue = new HurtValue(battlePanel);
        hurtValue.show(currentDamage, currentDamageType, currentEnemy.getX(), currentEnemy.getY());
        battlePanel.getHurtValues().add(hurtValue);
    }

    /**
     * 攻击;
     */
    public void attack() {
        drawn = false;
        battlePanel.getSkillAnimation().set("小精灵攻击", 22, 120, 135, 8, 1, 6, 16, 22, 90, 210, 0);
        battlePanel.getSkillAnimation().setDraw(true);
        battlePanel.getSkillAnimation().setStop(false);
    }

    /**
     * 载入图片;
     */
    public void loadImage() {
        petImage = Reader.readImage("image/小精灵/小精灵.png");
        headImage = Reader.readImage("image/小精灵/头像.png");
    }

    /**
     * 载入动画;
     */
    public void loadAnimation() {
    }

    /**
     * 画出;
     * @param g
     */
    public void drawPet(Graphics g) {
        if (drawn) {
            g.drawImage(petImage, x, y, battlePanel);
        }
    }

    /**
     * 更新;
     */
    public void update() {
        if (!stopped) {
            if (code < 5) {
                y--;
                code++;
            }
            if (code >= 5 && code < 10) {
                y++;
                code++;
            }
            if (code == 10) {
                code = 0;
            }
        }
    }

    public Image getPetImage() {
        return petImage;
    }

    public void setPetImage(Image petImage) {
        this.petImage = petImage;
    }

    public Image getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Image headImage) {
        this.headImage = headImage;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }

    public void setCurrentEnemy(Enemy currentEnemy) {
        this.currentEnemy = currentEnemy;
    }

    public int getCurrentDamage() {
        return currentDamage;
    }

    public void setCurrentDamage(int currentDamage) {
        this.currentDamage = currentDamage;
    }

    public int getCurrentDamageType() {
        return currentDamageType;
    }

    public void setCurrentDamageType(int currentDamageType) {
        this.currentDamageType = currentDamageType;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
