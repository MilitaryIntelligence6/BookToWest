package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

//陆雪琪召唤出的小精灵类
public class Pet {
    Image petImage;
    //小头像
    Image headImage;
    //攻击力
    int power;
    //速度
    int speed;
    //位置坐标
    int x;
    int y;
    //战斗面板引用
    BattlePanel bp;
    //当前攻击对象
    Enemy currentEnemy;
    //当前造成的伤害
    int currentDamage;
    //当前造成的伤害类型
    int currentDamageType;

    //是否画出
    boolean isDraw;
    //是否停止
    boolean isStop;
    //计时器
    int code;

    //构造方法
    public Pet(BattlePanel bp) {
        this.bp = bp;
        x = 700;
        y = 400;
        loadImage();
        loadAnimation();
        isDraw = true;
        isStop = false;

        speed = (int) ((ZhangXiaoFan.speed + LuXueQi.speed + YuJie.speed) / 3);
        power = (int) ((ZhangXiaoFan.hurt + LuXueQi.hurt + YuJie.hurt) / 3);
    }

    //选择攻击对象
    public void enemyToAttack() {
        int i = 0;
        while (true) {
            i = (int) (Math.random() * 3) + 5;
            if (i == 5 && bp.getEm1() != null) {
                bp.setCurrentBeAttacked(5);
                break;
            }
            if (i == 6 && bp.getEm2() != null) {
                bp.setCurrentBeAttacked(6);
                break;
            }
            if (i == 7 && bp.getEm3() != null) {
                bp.setCurrentBeAttacked(7);
                break;
            }
        }
    }

    //计算伤害
    public void calDamage() {
        switch (bp.getCurrentBeAttacked()) {
            case 5:
                currentEnemy = bp.getEm1();
                break;
            case 6:
                currentEnemy = bp.getEm2();
                break;
            case 7:
                currentEnemy = bp.getEm3();
                break;
        }

        currentDamage = power - currentEnemy.defense;
        if (currentDamage < 0) {
            currentDamage = 0;
        }
        currentDamageType = 1;
        currentEnemy.hp -= currentDamage;
        currentEnemy.hp -= currentDamage;
        HurtValue hurtValue = new HurtValue(bp);
        hurtValue.show(currentDamage, currentDamageType, currentEnemy.x, currentEnemy.y);
        bp.getHurtValues().add(hurtValue);
    }

    //攻击
    public void attack() {
        isDraw = false;
        bp.getSkillAnimation().set("小精灵攻击", 22, 120, 135, 8, 1, 6, 16, 22, 90, 210, 0);
        bp.getSkillAnimation().setDrawn(true);
        bp.getSkillAnimation().setStopped(false);
    }

    //载入图片
    public void loadImage() {
        petImage = Reader.readImage("image/小精灵/小精灵.png");
        headImage = Reader.readImage("image/小精灵/头像.png");
    }

    //载入动画
    public void loadAnimation() {
    }

    //画出
    public void drawPet(Graphics g) {
        if (isDraw) {
            g.drawImage(petImage, x, y, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop) {
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

}
