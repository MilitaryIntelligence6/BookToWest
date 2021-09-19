package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

public class YuJie implements Hero {
    //角色编号
    int roleCode = 2;
    //角色当前的图片引用
    Image currentImage;
    //角色的图片集
    ArrayList<Image> Images = new ArrayList<Image>();
    //角色的小头像
    Image headImage;
    //是否被画出
    boolean isDraw;
    //自身动作是否停止
    boolean isStop;
    //是否死亡
    boolean isDead;
    //被击动画
    BeAttackedAnimation beAttackedAnimation;
    //胜利动画
    VictoryAnimation victoryAnimation;
    //死亡动画
    DeadAnimation deadAnimation;

    //位置坐标
    int x;
    int y;
    //伤害值显示位置
    public static int showX;
    public static int showY;
    //战斗面板
    BattlePanel bp;
    //编号
    int code = 0;
    //当前技能数量
    public static int skillNumber = 3;
    //战斗状态
    BattleState battleState;

    //人物战斗数据

    //hp和mp
    public static int hp;
    public static int mp;

    //怒气值
    public static int angryValue = 0;
    //是否愤怒
    boolean isAngry = false;

    //hp和mp上限
    public static int hpMax;
    public static int mpMax;

    //体力
    public static int physicalPower = 14;
    //精气
    public static int sprit = 12;
    //敏捷
    public static int agile = 17;
    //武力
    public static int strength = 18;

    //速度
    public static int speed;
    //攻击力
    public static int hurt;
    //技能攻击力
    public static int skillHurt;
    //防御力
    public static int defense;
    //技能防御
    public static int skillDefense;

    //当前造成的伤害值
    int currentDamage;
    //当前造成的伤害类型 1.伤害 2.回复
    int currentDamageType;
    //当前攻击的对象集合
    ArrayList<Enemy> currentEnemies = new ArrayList<>();


    //有关于人物级别的数据

    //等级
    public static int level = 3;
    //距离升下级还需要的经验
    public static int expToLevelUp;
    //当前经验
    public static int exp = 0;
    //是否升级
    boolean isLevelUp;
    //是否习得技能
    boolean isGetSkill;

    ArrayList<String> roleInfo = new ArrayList<String>();


    //更新人物的属性值
    public void refreshValue() {
        hpMax = physicalPower * 70;
        mpMax = sprit * 30;
        hurt = strength * 10;
        skillHurt = sprit * 8;
        speed = (int) agile / 2;
        defense = physicalPower * 5;
        skillDefense = physicalPower * 2 + sprit * 3;

        if (hp >= hpMax) {
            hp = hpMax;
        }
        if (mp >= mpMax) {
            mp = mpMax;
        }
    }

    //计算伤害
    @Override
    public void calDamage() {
        switch (bp.getCurrentBeAttacked()) {
            case 5:
                currentEnemies.clear();
                currentEnemies.add(bp.getEm1());
                break;
            case 6:
                currentEnemies.clear();
                currentEnemies.add(bp.getEm2());
                break;
            case 7:
                currentEnemies.clear();
                currentEnemies.add(bp.getEm3());
                break;
            case 8:
                currentEnemies.clear();
                if (bp.getEm1() != null) {
                    currentEnemies.add(bp.getEm1());
                }
                if (bp.getEm2() != null) {
                    currentEnemies.add(bp.getEm2());
                }
                if (bp.getEm3() != null) {
                    currentEnemies.add(bp.getEm3());
                }
                break;
        }
        switch (bp.getCurrentPattern()) {
            //普通攻击
            case 1:
                for (Enemy currentEnemy : currentEnemies) {
                    currentDamage = hurt - currentEnemy.getDefense() + (int) (Math.random() * 15);
                    currentDamageType = 1;
                    if (currentDamage < 0) {
                        currentDamage = 0;
                    }
                    currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
                    HurtValue hurtValue = new HurtValue(bp);
                    hurtValue.show(currentDamage, currentDamageType, currentEnemy.getX(), currentEnemy.getY());
                    bp.getHurtValues().add(hurtValue);
                }
                break;
            //技能1 伏虎冲天
            case 2:
                attackSkill(135, 15, 80);
                //使被攻击对象80几率进入中毒状态
                for (Enemy e : currentEnemies) {
                    e.getBattleState().set(2, 9, 80, e.getRoleCode(), e.getX(), e.getY());
                    e.checkState();
                }
                break;
            //技能2 追星破月
            case 3:
                attackSkill(250, 15, 120);
                //自身敏捷提升
                battleState.set(2, 1, 100, 2, showX, showY);
                bp.getYj().checkState();
                break;
            //技能3 苍龙盖天
            case 4:
                attackSkill(100, 15, 150);
                break;
            //技能4 妙手回春
            case 5:
                for (Hero hero : bp.getHeroes()) {
                    currentDamage = (int) (hero.getHpMax() * 0.4) + (int) (Math.random() * 20);
                    currentDamageType = 2;
                    hero.setHp(hero.getHp() + currentDamage);
                    if (hero.getHp() >= hero.getHpMax()) {
                        hero.setHp(hero.getHpMax());
                    }

                    //使死亡的人物复活
                    hero.setDead(false);
                    hero.getDeadAnimation().setDraw(false);
                    hero.getDeadAnimation().setStop(true);
                    hero.setIsDraw(true);


                    HurtValue hurtValue = new HurtValue(bp);
                    hurtValue.show(currentDamage, currentDamageType, hero.getShowX(), hero.getShowY());
                    bp.getHurtValues().add(hurtValue);
                }
                mp -= 120;
                break;
            //技能5 蝶影神灵
            case 6:
                attackSkill(600, 50, 200);
                //自身武力提升
                battleState.set(3, 2, 100, 2, showX, showY);
                bp.getYj().checkState();
                break;
        }
    }

    //攻击型技能
    public void attackSkill(int baseHurt, int offsetHurt, int mpUse) {
        for (Enemy currentEnemy : currentEnemies) {
            currentDamage = skillHurt - currentEnemy.getDefense() + (int) (Math.random() * offsetHurt) + baseHurt;
            currentDamageType = 1;
            if (currentDamage < 0) {
                currentDamage = 0;
            }
            currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
            HurtValue hurtValue = new HurtValue(bp);
            hurtValue.show(currentDamage, currentDamageType, currentEnemy.getX(), currentEnemy.getY());
            bp.getHurtValues().add(hurtValue);
        }
        mp -= mpUse;
    }

    //升级时调用的方法
    @Override
    public void levelUp() {
        isLevelUp = true;
        //级别提升
        level++;
        //属性提升
        physicalPower += 2;
        sprit += 1;
        agile += 2;
        strength += 3;

        //在这些级别时获得新的技能
        if (level == 2 || level == 5 || level == 10) {
            skillNumber++;
            isGetSkill = true;
        }

        //经验计算
        exp = exp - expToLevelUp;
        //刷新属性值
        refreshValue();

        hp = hpMax;
        mp = mpMax;
        expToLevelUp = (int) (500 * ((Math.pow(1.4, level))));
    }

    //构造方法
    public YuJie() {

    }

    public YuJie(int x, int y, BattlePanel bp) {
        this.x = x;
        this.y = y;
        YuJie.showX = x + 50;
        YuJie.showY = y;
        this.bp = bp;

        getImage();
        isDraw = true;
        isStop = false;


        physicalPower = 14 + (level - 3) * 2;
        sprit = 12 + (level - 3) * 1;
        agile = 17 + (level - 3) * 2;
        strength = 18 + (level - 3) * 3;

        loadAnimation();
        refreshValue();
        hp = hpMax;
        mp = mpMax;
        expToLevelUp = (int) (500 * ((Math.pow(1.4, level))));
        battleState = new BattleState(bp);
    }

    //检查战斗的状态
    @Override
    public void checkState() {
        switch (battleState.getType()) {
            //增加敏捷度
            case 1:
                agile += 2;
                speed = (int) agile / 2;
                break;
            case 2:
                strength += 4;
                hurt = strength * 10;
                break;
            case 3:
                sprit += 4;
                skillHurt = sprit * 8;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 4:
                physicalPower += 4;
                defense = physicalPower * 5;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 5:
                agile -= 2;
                speed = (int) agile / 2;
                break;
            case 6:
                strength -= 4;
                hurt = strength * 10;
                break;
            case 7:
                sprit -= 4;
                skillHurt = sprit * 8;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 8:
                physicalPower -= 4;
                defense = physicalPower * 5;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 10:
                hurt = 0;
                skillHurt = 0;
                break;
            //金钟罩
            case 11:
                defense = 9999;
                skillDefense = 9999;
                break;
            //潜能爆发
            case 12:
                physicalPower *= 2;
                sprit *= 2;
                agile *= 2;
                strength *= 2;
                refreshValue();
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
                hurtValue.show(damage, 1, showX, showY);
                bp.getHurtValues().add(hurtValue);
                for (HurtValue h : bp.getHurtValues()) {
                    h.start();
                }
        }
    }

    //从状态中恢复
    @Override
    public void returnFromState() {
        switch (battleState.getType()) {
            case 1:
                agile -= 2;
                speed = (int) agile / 2;
                break;
            case 2:
                strength -= 4;
                hurt = strength * 10;
                break;
            case 3:
                sprit -= 4;
                skillHurt = sprit * 8;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 4:
                physicalPower -= 4;
                defense = physicalPower * 5;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 5:
                agile += 2;
                speed = (int) agile / 2;
                break;
            case 6:
                strength += 4;
                hurt = strength * 10;
                break;
            case 7:
                sprit += 4;
                skillHurt = sprit * 8;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 8:
                physicalPower += 4;
                defense = physicalPower * 5;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            case 10:
                hurt = strength * 10;
                skillHurt = sprit * 8;
                break;
            //金钟罩
            case 11:
                defense = physicalPower * 5;
                skillDefense = physicalPower * 2 + sprit * 3;
                break;
            //潜能爆发
            case 12:
                physicalPower /= 2;
                sprit /= 2;
                agile /= 2;
                strength /= 2;
                refreshValue();
                break;
        }
    }

    //载入动画
    public void loadAnimation() {
        //被击动画
        beAttackedAnimation = new BeAttackedAnimation("文敏被击", 2, YuJie.showX, YuJie.showY, bp);
        //胜利动画
        victoryAnimation = new VictoryAnimation(120, -80, 12, "文敏", bp, this);
        //死亡动画
        deadAnimation = new DeadAnimation(400, -70, 4, "文敏", bp, this);
    }

    @Override
    public void getImage() {

        for (int i = 1; i <= 8; i++) {
            Image image = Reader.readImage("image/主角2/" + i + ".png");
            Images.add(image);
        }

        headImage = Reader.readImage("image/主角2/小头.png");
    }

    @Override
    public void doAction() {

        if (!isStop && code < 8) {
            currentImage = Images.get(code);
            code++;

        } else if (code == 8) {
            code = 0;
        }
    }

    @Override
    public void drawHero(Graphics g) {

        if (isDraw) {
            g.drawImage(currentImage, x, y, bp);
        }

    }


    @Override
    public void attack() {
        //人物不再画出
        isDraw = false;
        bp.getSkillAnimation().set("文敏攻击", 21, 120, -80, 8, 1, 8, 15, 21, -150, 0, -260);
        bp.getSkillAnimation().setDraw(true);
        bp.getSkillAnimation().setStop(false);
    }

    //使用技能
    @Override
    public void skill(int i) {
        //人物不再画出
        isDraw = false;

        switch (i) {
            case 1:
                bp.getBackgroundAnimation().set("伏虎冲天", 74);
                bp.getSkillAnimation().set("文敏技能1", 28, 120, -80, 8, 6, 9, 22, 28, -150, 0, -260);
                break;
            case 2:
                bp.getBackgroundAnimation().set("追星破月", 41);
                bp.getSkillAnimation().set("文敏技能2", 32, 120, -80, 8, 3, 10, 26, 32, -150, 0, -260);
                break;
            case 3:
                bp.getBackgroundAnimation().set("苍龙盖天", 51);
                bp.getSkillAnimation().set("文敏技能3", 28, 120, -80, 8, 4, 9, 23, 28, -150, 0, -260);
                break;
            case 4:
                bp.getBackgroundAnimation().set("妙手回春", 23);
                bp.getSkillAnimation().set("文敏技能4", 16, 120, -80, 0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case 5:
                bp.getBackgroundAnimation().set("蝶影神灵", 41);
                bp.getSkillAnimation().set("文敏技能5", 41, 120, -80, 8, 4, 9, 36, 41, -150, 0, -260);
                break;
        }

        bp.getBackgroundAnimation().setDrawn(true);
        bp.getBackgroundAnimation().setStopped(false);
        bp.getSkillAnimation().setDraw(true);
        bp.getSkillAnimation().setStop(false);
    }

    @Override
    public SkillAnimation getCurrentAnimation() {
        return null;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSkillDefense() {
        return skillDefense;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        YuJie.hp = hp;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public void setExp(int exp) {
        YuJie.exp = exp;
    }

    @Override
    public int getExpToLevelUp() {
        return expToLevelUp;
    }

    @Override
    public boolean isLevelUp() {
        return isLevelUp;
    }

    @Override
    public int getPhysicalPower() {
        return physicalPower;
    }

    @Override
    public int getSprit() {
        return sprit;
    }

    @Override
    public int getAgile() {
        return agile;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setLevelUp(boolean isLevelUp) {
        this.isLevelUp = isLevelUp;
    }

    @Override
    public int getHpMax() {
        return hpMax;
    }

    @Override
    public int getMpMax() {
        return mpMax;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getShowX() {
        return showX;
    }

    @Override
    public int getShowY() {
        return showY;
    }

    @Override
    public int getMp() {

        return mp;
    }

    @Override
    public void setMp(int mp) {

        YuJie.mp = mp;
    }

    @Override
    public void setDead(boolean isDead) {

        this.isDead = isDead;
    }

    @Override
    public BattleState getBattleState() {

        return battleState;
    }

    @Override
    public int getRoleCode() {

        return roleCode;
    }

    @Override
    public void setExpToLevelUp(int e) {

        expToLevelUp = e;
    }

    @Override
    public void setIsDraw(boolean isDraw) {

        this.isDraw = isDraw;
    }

    @Override
    public VictoryAnimation getVictoryAnimation() {

        return victoryAnimation;
    }

    @Override
    public DeadAnimation getDeadAnimation() {

        return deadAnimation;
    }

    @Override
    public int getAngryValue() {

        return angryValue;
    }

    @Override
    public void setAngryValue(int a) {

        angryValue = a;
    }

    @Override
    public boolean isAngry() {

        return isAngry;
    }

    @Override
    public void setAngry(boolean isAngry) {

        this.isAngry = isAngry;
    }

    @Override
    public BeAttackedAnimation getBeAttackedAnimation() {

        return beAttackedAnimation;
    }

    //存档方法
    public ArrayList<String> saveRoleInfo() {
        roleInfo.clear();
        //存入当前的等级
        roleInfo.add(level + "");
        //存入当前的hp
        roleInfo.add(hp + "");
        //存入当前的mp
        roleInfo.add(mp + "");
        //存入当前怒气值
        roleInfo.add(angryValue + "");
        //存入当前是否达到愤怒
        roleInfo.add(isAngry + "");
        //存入当前是否死亡
        roleInfo.add(isDead + "");
        //存入当前经验
        roleInfo.add(exp + "");

        return roleInfo;
    }

    //读档方法
    public void loadRoleInfo(ArrayList<String> roleInfo) {
        this.roleInfo = roleInfo;
        intialFromInfo();
    }

    //根据信息初始化人物属性
    public void intialFromInfo() {
        YuJie.level = Integer.parseInt(roleInfo.get(0));
        if (level >= 2) {
            skillNumber = 3;
        }
        if (level >= 5) {
            skillNumber = 4;
        }
        if (level >= 10) {
            skillNumber = 5;
        }
        physicalPower = 14 + (level - 3) * 2;
        sprit = 12 + (level - 3) * 1;
        agile = 17 + (level - 3) * 2;
        strength = 18 + (level - 3) * 3;
        refreshValue();
        expToLevelUp = (int) (500 * ((Math.pow(1.4, level))));
        YuJie.hp = Integer.parseInt(roleInfo.get(1));
        YuJie.mp = Integer.parseInt(roleInfo.get(2));
        YuJie.angryValue = Integer.parseInt(roleInfo.get(3));
        this.isAngry = Boolean.parseBoolean(roleInfo.get(4));
        this.isDead = Boolean.parseBoolean(roleInfo.get(5));
        YuJie.exp = Integer.parseInt(roleInfo.get(6));
    }

    @Override
    public void setPhysicalPower(int physicalPower) {

        YuJie.physicalPower = physicalPower;
    }

    @Override
    public void setSprit(int sprit) {

        YuJie.sprit = sprit;
    }

    @Override
    public void setAgile(int agile) {

        YuJie.agile = agile;
    }

    @Override
    public void setStrength(int strength) {

        YuJie.strength = strength;
    }


}
