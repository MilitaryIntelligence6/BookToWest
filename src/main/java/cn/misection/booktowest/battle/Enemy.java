package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

//敌人类
public class Enemy {
    //角色编号
    private int roleCode;
    //当前图片引用
    private Image currentImage;
    //头像图片
    private Image headImage;
    //姓名
    private String name;
    //图片集合
    private ArrayList<Image> Images = new ArrayList<Image>();
    //出现坐标
    private int x;
    private int y;
    //当前编号
    private int code;
    //图片长度
    private int length;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;
    //战斗面板引用
    private BattlePanel bp;

    //是否死亡
    private boolean isDead;

    //速度
    private int speed;

    //战斗状态
    private BattleState battleState;

    //被击动画引用
    private BeAttackedAnimation beAttackedAnimation;
    //被击动画长度
    private int beAttackedLength;
    //被击动画出现位置
    private int beAttackedX;
    private int beAttackedY;

    //被选中后的图片
    private Image selectedImage;

//敌人的战斗数据

    //hp和mp
    private int hp;
    private int mp;

    //攻击力
    private int hurt;
    //魔法攻击
    private int skillHurt;
    //防御力
    private int defense;
    private int hurtMax;
    private int skillHurtMax;
    private int defenseMax;

    //招数(默认为1)
    private int skillNum = 1;

    //当前造成的伤害
    private int currentDamage;
    //当前造成的伤害类型
    private int currentDamageType;
    //当前敌人
    private ArrayList<Hero> currentEnemies = new ArrayList<Hero>();

//关于等级的数据

    //战胜后可以获得的经验
    private int exp;
    //战胜后可以获得的物品
    private String thing;
    //战胜后可以获得的金钱
    private int money;

    //存储技能信息
    private String skillName;
    private int skillLength;
    private int skillX;
    private int skillY;
    private int beAttackedCode;
    private int beAttackedTimes;
    private int runCode;
    private int attackCode;
    private int withdrawCode;
    private int offsetTo1;
    private int offsetTo2;
    private int offsetTo3;

    public void setSkill(String skillName, int skillLength, int skillX, int skillY, int beAttackedCode,
                         int beAttackedTimes, int runCode, int attackCode, int withdrawCode, int offsetTo1,
                         int offsetTo2, int offsetTo3) {
        this.skillName = skillName;
        this.skillLength = skillLength;
        this.skillX = skillX;
        this.skillY = skillY;
        this.beAttackedCode = beAttackedCode;
        this.beAttackedTimes = beAttackedTimes;
        this.runCode = runCode;
        this.attackCode = attackCode;
        this.withdrawCode = withdrawCode;
        this.offsetTo1 = offsetTo1;
        this.offsetTo2 = offsetTo2;
        this.offsetTo3 = offsetTo3;
    }

    public void calDamage() {
        switch (bp.getCurrentBeAttacked()) {
            case 1:
                currentEnemies.clear();
                currentEnemies.add(bp.getZxf());
                break;
            case 2:
                currentEnemies.clear();
                currentEnemies.add(bp.getYj());
                break;
            case 3:
                currentEnemies.clear();
                currentEnemies.add(bp.getLxq());
                break;
            //我方全体受到攻击
            case 4:
                currentEnemies.clear();
                if (bp.getZxf() != null && !bp.getZxf().isDead) {
                    currentEnemies.add(bp.getZxf());
                }
                if (bp.getYj() != null && !bp.getYj().isDead) {
                    currentEnemies.add(bp.getYj());
                }
                if (bp.getLxq() != null && !bp.getLxq().dead) {
                    currentEnemies.add(bp.getLxq());
                }
        }

        switch (bp.getCurrentPattern()) {
            //普通攻击
            case 1:
                for (Hero currentEnemy : currentEnemies) {
                    currentDamage = hurt - currentEnemy.getDefense() + (int) (Math.random() * 5);
                    currentDamageType = 1;
                    if (currentDamage < 0) {
                        currentDamage = 0;
                    }

                    //给我方单位加上愤怒值
                    if (!currentEnemy.isAngry()) {
                        currentEnemy.setAngryValue(currentEnemy.getAngryValue() + currentDamage);
                    }
                    //检查我方单位是否达到愤怒
                    if (currentEnemy.getAngryValue() >= (int) (currentEnemy.getHpMax() * 0.8)) {
                        currentEnemy.setAngry(true);
                    }

                    currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
                    //hp低于0时变成0
                    if (currentEnemy.getHp() < 0) {
                        currentEnemy.setHp(0);
                    }
                    HurtValue hurtValue = new HurtValue(bp);
                    hurtValue.show(currentDamage, currentDamageType, currentEnemy.getShowX(), currentEnemy.getShowY());
                    bp.getHurtValues().add(hurtValue);
                }
                break;
            //群体攻击
            case 2:
                for (Hero currentEnemy : currentEnemies) {
                    currentDamage = (int) (hurt * 0.6) - currentEnemy.getDefense() + (int) (Math.random() * 5);
                    currentDamageType = 1;
                    if (currentDamage < 0) {
                        currentDamage = 0;
                    }

                    //给我方单位加上愤怒值
                    if (!currentEnemy.isAngry()) {
                        currentEnemy.setAngryValue(currentEnemy.getAngryValue() + currentDamage);
                    }
                    //检查我方单位是否达到愤怒
                    if (currentEnemy.getAngryValue() >= (int) (currentEnemy.getHpMax() * 0.8)) {
                        currentEnemy.setAngry(true);
                    }

                    currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
                    //hp低于0时变成0
                    if (currentEnemy.getHp() < 0) {
                        currentEnemy.setHp(0);
                    }
                    HurtValue hurtValue = new HurtValue(bp);
                    hurtValue.show(currentDamage, currentDamageType, currentEnemy.getShowX(), currentEnemy.getShowY());
                    bp.getHurtValues().add(hurtValue);
                }
                break;
        }
    }

    public Enemy(String name, int roleCode, BattlePanel bp) {
        this.roleCode = roleCode;
        initial(name, roleCode);
        this.hurtMax = hurt;
        this.skillHurtMax = skillHurt;
        this.defenseMax = defense;
        this.name = name;
        getImage(name, this.length);
        isDraw = true;
        isStop = false;
        this.bp = bp;
        battleState = new BattleState(bp);
        loadAnimation(name, this.beAttackedLength);
    }

    //初始化怪物的数据
    public void initial(String name, int roleCode) {
        switch (roleCode) {
            case 5:
                this.x = 100;
                this.y = 220;
                break;
            case 6:
                this.x = 60;
                this.y = 90;
                break;
            case 7:
                this.x = 60;
                this.y = 330;
                break;
        }

        switch (name) {
            case "怪物1":
                this.beAttackedX = x;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 11;
                this.hurt = 250;
                this.skillHurt = hurt;
                this.defense = 60;
                this.hp = 250;
                this.exp = 200;
                skillNum = 2;
                thing = "金创药/1";
                money = 1000;
                break;
            case "怪物2":
                this.beAttackedX = x - 125;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 10;
                this.hurt = 260;
                this.skillHurt = hurt;
                this.defense = 60;
                this.hp = 300;
                this.exp = 200;
                thing = "姜黄粉/1";
                money = 1200;
                break;
            case "大刀":
                this.beAttackedX = x - 70;
                this.beAttackedY = y - 60;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 4;
                this.hurt = 200;
                this.skillHurt = hurt;
                this.defense = 50;
                this.hp = 600;
                this.exp = 400;
                skillNum = 2;
                thing = "蓝格怪衣/2";
                money = 2000;
                break;
            case "武林高手1":
                this.beAttackedX = x - 50;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 5;
                this.hurt = 150;
                this.skillHurt = hurt;
                this.defense = 55;
                this.hp = 200;
                this.exp = 150;
                thing = "金创药/1";
                money = 1200;
                break;
            case "武林高手2":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 5;
                this.beAttackedLength = 6;
                this.speed = 12;
                this.hurt = 440;
                this.skillHurt = hurt;
                this.defense = 130;
                this.hp = 500;
                this.exp = 500;
                thing = "神农药方/1";
                money = 3800;
                break;
            case "舞剑者":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 6;
                this.hurt = 160;
                this.skillHurt = hurt;
                this.defense = 40;
                this.hp = 280;
                this.exp = 160;
                skillNum = 2;
                thing = "姜黄粉/1";
                money = 1000;
                break;
            case "商塔弟子":
                this.beAttackedX = x - 10;
                this.beAttackedY = y;
                this.length = 9;
                this.beAttackedLength = 6;
                this.speed = 13;
                this.hurt = 450;
                this.skillHurt = hurt;
                this.defense = 220;
                this.hp = 460;
                this.exp = 330;
                thing = "还魄丹/1";
                money = 1200;
                break;
            case "商塔护法":
                this.beAttackedX = x - 30;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 14;
                this.hurt = 450;
                this.skillHurt = hurt;
                this.defense = 150;
                this.hp = 520;
                this.exp = 360;
                thing = "还灵丹/1";
                money = 1200;
                break;
            case "商塔堂主":
                this.beAttackedX = x - 30;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 14;
                this.hurt = 500;
                this.skillHurt = hurt;
                this.defense = 200;
                this.hp = 3500;
                this.exp = 3500;
                skillNum = 2;
                thing = "工布/2";
                money = 2200;
                break;
            case "文学谷大弟子":
                this.beAttackedX = x - 10;
                this.beAttackedY = y - 10;
                this.length = 8;
                this.beAttackedLength = 6;
                this.speed = 12;
                this.hurt = 280;
                this.skillHurt = hurt;
                this.defense = 100;
                this.hp = 300;
                this.exp = 300;
                thing = "还魄丹/1";
                money = 1300;
                break;
            case "文学谷弟子":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 15;
                this.hurt = 300;
                this.skillHurt = hurt;
                this.defense = 100;
                this.hp = 400;
                this.exp = 350;
                thing = "还灵丹/1";
                money = 1300;
                break;
            case "文学谷护法":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 3;
                this.beAttackedLength = 6;
                this.speed = 13;
                this.hurt = 300;
                this.skillHurt = hurt;
                this.defense = 140;
                this.hp = 500;
                this.exp = 400;
                thing = "还魄丹/1";
                money = 1300;
                break;
            case "文学谷堂主":
                this.beAttackedX = x - 20;
                this.beAttackedY = y - 10;
                this.length = 8;
                this.beAttackedLength = 6;
                this.speed = 16;
                this.hurt = 330;
                this.skillHurt = hurt;
                this.defense = 160;
                this.hp = 2800;
                this.exp = 2800;
                skillNum = 2;
                thing = "龙鳞盔/2";
                money = 2300;
                break;
            case "文学谷小boss":
                this.beAttackedX = x - 20;
                this.beAttackedY = y - 10;
                this.length = 6;
                this.beAttackedLength = 6;
                this.speed = 16;
                this.hurt = 480;
                this.skillHurt = hurt;
                this.defense = 120;
                this.hp = 2800;
                this.exp = 2500;
                skillNum = 2;
                thing = "龙泉/2";
                money = 2300;
                break;
            case "罹年居士":
                this.beAttackedX = x - 60;
                this.beAttackedY = y;
                this.length = 6;
                this.beAttackedLength = 6;
                this.speed = ZhangXiaoFan.speed + 6;
                this.hurt = 9999;
                this.skillHurt = 9999;
                this.defense = 9999;
                this.hp = 9999;
                this.exp = 9999;
                skillNum = 2;
                thing = "御衡镇日刀/2";
                money = 9999;
                break;
            case "风易岚":
                this.beAttackedX = x - 125;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 23;
                this.hurt = 700;
                this.skillHurt = hurt;
                this.defense = 300;
                this.hp = 6000;
                this.exp = 2000;
                skillNum = 2;
                thing = "泰阿/2";
                money = 5000;
                break;
            case "缘铭道者":
                this.beAttackedX = x - 30;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 11;
                this.hurt = 480;
                this.skillHurt = hurt;
                this.defense = 140;
                this.hp = 4500;
                this.exp = 2500;
                skillNum = 2;
                thing = "颀��巨环/2";
                money = 3000;
                break;
            case "蒙面怪人":
                this.beAttackedX = x - 50;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 9;
                this.hurt = 350;
                this.skillHurt = hurt;
                this.defense = 100;
                this.hp = 2500;
                this.exp = 1500;
                skillNum = 2;
                thing = "青钢剑/2";
                money = 2000;
                break;
            case "李洵":
                this.beAttackedX = x - 5;
                this.beAttackedY = y - 5;
                this.length = 8;
                this.beAttackedLength = 6;
                this.speed = 17;
                this.hurt = 620;
                this.skillHurt = hurt;
                this.defense = 200;
                this.hp = 6000;
                this.exp = 4500;
                skillNum = 2;
                thing = "千月星痕/2";
                money = 5000;
                break;
            case "最终李洵":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 25;
                this.hurt = 800;
                this.skillHurt = hurt;
                this.defense = 350;
                this.hp = 8000;
                this.exp = 4000;
                skillNum = 2;
                thing = "赤龙牙/2";
                money = 8000;
                break;
            case "红叶":
                this.beAttackedX = x - 20;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 13;
                this.hurt = 500;
                this.skillHurt = hurt;
                this.defense = 140;
                this.hp = 3200;
                this.exp = 3000;
                skillNum = 2;
                thing = "湛卢/2";
                money = 4000;
                break;
            case "物理阁大弟子":
                this.beAttackedX = x - 125;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 16;
                this.hurt = 600;
                this.skillHurt = hurt;
                this.defense = 180;
                this.hp = 4500;
                this.exp = 3500;
                skillNum = 1;
                thing = "玄雪丝衣/2";
                money = 4000;
                break;
            case "物理阁护法":
                this.beAttackedX = x - 125;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 18;
                this.hurt = 600;
                this.skillHurt = hurt;
                this.defense = 190;
                this.hp = 4800;
                this.exp = 4500;
                skillNum = 2;
                thing = "紫金冠/2";
                money = 4000;
                break;
            case "物理阁堂主":
                this.beAttackedX = x - 125;
                this.beAttackedY = y;
                this.length = 4;
                this.beAttackedLength = 6;
                this.speed = 19;
                this.hurt = 620;
                this.skillHurt = hurt;
                this.defense = 200;
                this.hp = 5000;
                this.exp = 5500;
                skillNum = 2;
                thing = "天仙神衣/2";
                money = 4000;
                break;
            case "罹年居士分身":
                this.beAttackedX = x - 60;
                this.beAttackedY = y;
                this.length = 6;
                this.beAttackedLength = 6;
                this.speed = 18;
                this.hurt = 600;
                this.skillHurt = 600;
                this.defense = 190;
                this.hp = 2000;
                this.exp = 2000;
                skillNum = 2;
                thing = "神农药方/1";
                money = 3000;
                break;
        }
    }

    //载入图片
    public void getImage(String name, int length) {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/怪物/" + name + "/" + i + ".png");
            Images.add(image);
        }

        headImage = Reader.readImage("image/怪物/" + name + "/小头.png");
        selectedImage = Reader.readImage("image/怪物/" + name + "/选中.png");
    }

    public void loadAnimation(String name, int beAttackedLength) {
        //载入被击动画
        beAttackedAnimation = new BeAttackedAnimation(name + "被击", beAttackedLength, this.beAttackedX,
                this.beAttackedY, bp);

        //载入攻击动画
        switch (name) {
            case "怪物1":
                setSkill("怪物/怪物1攻击", 16, this.x - 50, this.y - 235, 6, 1, 6, 10, 16, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY, this.y - LuXueQi.showY - 30);
                break;
            case "怪物2":
                setSkill("怪物/怪物2攻击", 16, this.x - 50, this.y - 200, 6, 1, 6, 10, 16, this.y - ZhangXiaoFan.showY + 20
                        , this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "大刀":
                setSkill("怪物/大刀攻击", 26, this.x - 70, this.y - 160, 9, 3, 9, 19, 26, this.y - ZhangXiaoFan.showY - 20,
                        this.y - YuJie.showY, this.y - LuXueQi.showY - 30);
                break;
            case "武林高手1":
                setSkill("怪物/武林高手1攻击", 26, this.x - 10, this.y - 170, 9, 3, 10, 18, 26,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 30, this.y - LuXueQi.showY + 10);
                break;
            case "武林高手2":
                setSkill("怪物/武林高手2攻击", 26, this.x - 10, this.y - 170, 17, 1, 9, 20, 26,
                        this.y - ZhangXiaoFan.showY + 40, this.y - YuJie.showY + 50, this.y - LuXueQi.showY);
                break;
            case "舞剑者":
                setSkill("怪物/舞剑者攻击", 25, this.x - 10, this.y - 170, 6, 3, 6, 16, 25, this.y - ZhangXiaoFan.showY + 30
                        , this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "商塔弟子":
                setSkill("怪物/商塔弟子攻击", 29, this.x - 10, this.y - 170, 7, 3, 8, 20, 29,
                        this.y - ZhangXiaoFan.showY + 30, this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "商塔护法":
                setSkill("怪物/商塔护法攻击", 28, this.x - 10, this.y - 170, 7, 3, 8, 17, 28, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "商塔堂主":
                setSkill("怪物/商塔堂主攻击", 23, this.x - 10, this.y - 170, 5, 3, 6, 14, 23,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 20, this.y - LuXueQi.showY);
                break;
            case "文学谷大弟子":
                setSkill("怪物/文学谷大弟子攻击", 15, this.x - 10, this.y - 170, 6, 1, 5, 11, 15,
                        this.y - ZhangXiaoFan.showY - 30, this.y - YuJie.showY, this.y - LuXueQi.showY - 80);
                break;
            case "文学谷弟子":
                setSkill("怪物/文学谷弟子攻击", 11, this.x - 20, this.y - 150, 4, 1, 4, 8, 11,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 30, this.y - LuXueQi.showY - 60);
                break;
            case "文学谷护法":
                setSkill("怪物/文学谷护法攻击", 9, this.x - 20, this.y - 150, 5, 1, 4, 6, 9, this.y - ZhangXiaoFan.showY + 10,
                        this.y - YuJie.showY + 30, this.y - LuXueQi.showY - 40);
                break;
            case "文学谷堂主":
                setSkill("怪物/文学谷堂主攻击", 18, this.x - 20, this.y - 150, 6, 3, 5, 15, 18, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY + 10, this.y - LuXueQi.showY - 10);
                break;
            case "文学谷小boss":
                setSkill("怪物/文学谷小boss攻击", 14, this.x - 20, this.y - 150, 10, 1, 6, 11, 14,
                        this.y - ZhangXiaoFan.showY, this.y - YuJie.showY, this.y - LuXueQi.showY - 40);
                break;
            case "罹年居士":
                setSkill("怪物/罹年居士攻击", 10, this.x - 20, this.y - 130, 4, 1, 4, 7, 10, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY + 20, this.y - LuXueQi.showY);
                break;
            case "风易岚":
                setSkill("怪物/风易岚攻击", 11, this.x - 50, this.y - 170, 5, 1, 4, 7, 11, this.y - ZhangXiaoFan.showY - 40,
                        this.y - YuJie.showY, this.y - LuXueQi.showY - 50);
                break;
            case "缘铭道者":
                setSkill("怪物/缘铭道者攻击", 9, this.x - 20, this.y - 130, 5, 1, 4, 7, 9, this.y - ZhangXiaoFan.showY + 20,
                        this.y - YuJie.showY + 40, this.y - LuXueQi.showY);
                break;
            case "蒙面怪人":
                setSkill("怪物/蒙面怪人攻击", 11, this.x - 20, this.y - 120, 6, 1, 4, 8, 11, this.y - ZhangXiaoFan.showY + 20
                        , this.y - YuJie.showY + 40, this.y - LuXueQi.showY - 30);
                break;
            case "李洵":
                setSkill("怪物/李洵攻击", 17, this.x - 20, this.y - 120, 6, 1, 5, 14, 17, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY, this.y - LuXueQi.showY);
                break;
            case "最终李洵":
                setSkill("怪物/最终李洵攻击", 11, this.x - 50, this.y - 140, 6, 1, 4, 8, 11, this.y - ZhangXiaoFan.showY + 30
                        , this.y - YuJie.showY + 40, this.y - LuXueQi.showY);
                break;
            case "红叶":
                setSkill("怪物/红叶攻击", 11, this.x - 50, this.y - 140, 3, 2, 3, 8, 11, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY, this.y - LuXueQi.showY);
                break;
            case "物理阁大弟子":
                setSkill("怪物/物理阁大弟子攻击", 11, this.x - 50, this.y - 150, 6, 1, 6, 10, 16,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "物理阁护法":
                setSkill("怪物/物理阁护法攻击", 12, this.x - 50, this.y - 150, 6, 1, 6, 10, 16,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "物理阁堂主":
                setSkill("怪物/物理阁堂主攻击", 9, this.x - 50, this.y - 150, 6, 1, 6, 10, 16,
                        this.y - ZhangXiaoFan.showY + 20, this.y - YuJie.showY + 30, this.y - LuXueQi.showY);
                break;
            case "罹年居士分身":
                setSkill("怪物/罹年居士分身攻击", 10, this.x - 20, this.y - 130, 4, 1, 4, 7, 10, this.y - ZhangXiaoFan.showY,
                        this.y - YuJie.showY + 20, this.y - LuXueQi.showY);
                break;
        }

    }

    //做出动作
    public void doAction() {
        if (!isStop && code < length) {
            currentImage = Images.get(code);
            code++;
        } else if (code == length) {
            code = 0;
        }
    }

    //画出敌人
    public void drawEnemy(Graphics g) {
        if (isDraw) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //检查战斗的状态
    public void checkState() {
        switch (battleState.getType()) {
            //增加敏捷度
            case 1:
                speed += 1;
                break;
            case 2:
                hurt += 40;
                break;
            case 3:
                skillHurt += 30;
                break;
            case 4:
                defense += 40;
                break;
            //降低敏捷度
            case 5:
                speed -= 1;
                break;
            case 6:
                hurt -= 40;
                break;
            case 7:
                skillHurt -= 30;
                break;
            case 8:
                defense -= 40;
                break;
            //麻痹状态
            case 10:
                hurt = 0;
                skillHurt = 0;
                break;
        }
    }

    //执行状态
    public void excuteState() {
        switch (battleState.getType()) {
            //中毒状态
            case 9:
                bp.getHurtValues().clear();
                int damage = (int) (hp * 0.05);
                hp -= damage;
                HurtValue hurtValue = new HurtValue(bp);
                hurtValue.show(damage, 1, x, y);
                bp.getHurtValues().add(hurtValue);
                for (HurtValue h : bp.getHurtValues()) {
                    h.start();
                }
                break;
        }
    }

    //从状态中恢复
    public void returnFromState() {
        switch (battleState.getType()) {
            case 1:
                speed -= 1;
                break;
            case 2:
                hurt -= 40;
                break;
            case 3:
                skillHurt -= 30;
                break;
            case 4:
                defense -= 40;
                break;
            case 5:
                speed += 1;
                break;
            case 6:
                hurt += 40;
                break;
            case 7:
                skillHurt += 30;
                break;
            case 8:
                defense += 40;
                break;
            case 10:
                hurt = hurtMax;
                skillHurt = skillHurtMax;
                break;
        }
    }

    //攻击
    public void attack() {
        isDraw = false;
        bp.getSkillAnimation().set(skillName, skillLength, skillX, skillY, beAttackedCode, beAttackedTimes, runCode,
                attackCode, withdrawCode, offsetTo1, offsetTo2, offsetTo3);
        bp.getSkillAnimation().setDrawn(true);
        bp.getSkillAnimation().setStopped(false);
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public Image getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Image headImage) {
        this.headImage = headImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getImages() {
        return Images;
    }

    public void setImages(ArrayList<Image> images) {
        Images = images;
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }

    public BeAttackedAnimation getBeAttackedAnimation() {
        return beAttackedAnimation;
    }

    public void setBeAttackedAnimation(BeAttackedAnimation beAttackedAnimation) {
        this.beAttackedAnimation = beAttackedAnimation;
    }

    public int getBeAttackedLength() {
        return beAttackedLength;
    }

    public void setBeAttackedLength(int beAttackedLength) {
        this.beAttackedLength = beAttackedLength;
    }

    public int getBeAttackedX() {
        return beAttackedX;
    }

    public void setBeAttackedX(int beAttackedX) {
        this.beAttackedX = beAttackedX;
    }

    public int getBeAttackedY() {
        return beAttackedY;
    }

    public void setBeAttackedY(int beAttackedY) {
        this.beAttackedY = beAttackedY;
    }

    public Image getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Image selectedImage) {
        this.selectedImage = selectedImage;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    public int getSkillHurt() {
        return skillHurt;
    }

    public void setSkillHurt(int skillHurt) {
        this.skillHurt = skillHurt;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getHurtMax() {
        return hurtMax;
    }

    public void setHurtMax(int hurtMax) {
        this.hurtMax = hurtMax;
    }

    public int getSkillHurtMax() {
        return skillHurtMax;
    }

    public void setSkillHurtMax(int skillHurtMax) {
        this.skillHurtMax = skillHurtMax;
    }

    public int getDefenseMax() {
        return defenseMax;
    }

    public void setDefenseMax(int defenseMax) {
        this.defenseMax = defenseMax;
    }

    public int getSkillNum() {
        return skillNum;
    }

    public void setSkillNum(int skillNum) {
        this.skillNum = skillNum;
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

    public ArrayList<Hero> getCurrentEnemies() {
        return currentEnemies;
    }

    public void setCurrentEnemies(ArrayList<Hero> currentEnemies) {
        this.currentEnemies = currentEnemies;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillLength() {
        return skillLength;
    }

    public void setSkillLength(int skillLength) {
        this.skillLength = skillLength;
    }

    public int getSkillX() {
        return skillX;
    }

    public void setSkillX(int skillX) {
        this.skillX = skillX;
    }

    public int getSkillY() {
        return skillY;
    }

    public void setSkillY(int skillY) {
        this.skillY = skillY;
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
}
