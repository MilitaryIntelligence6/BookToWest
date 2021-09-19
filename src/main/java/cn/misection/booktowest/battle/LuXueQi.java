package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

/**
 * @author javaman
 */
public class LuXueQi implements Hero {
    //角色编号
    private int roleCode = 3;
    //角色当前的图片引用
    private Image currentImage;
    //角色的图片集
    private ArrayList<Image> Images = new ArrayList<Image>();
    //角色的小头像
    private Image headImage;
    //是否被画出
    private boolean drawn;
    //自身动作是否停止
    private boolean stopped;
    //是否死亡
    private boolean dead;

    //被击动画
    private BeAttackedAnimation beAttackedAnimation;
    //胜利动画
    private VictoryAnimation victoryAnimation;
    //死亡动画
    private DeadAnimation deadAnimation;

    //位置坐标
    private int x;
    private int y;
    //伤害值显示坐标
    public static int showX;
    public static int showY;
    //战斗面板
    private BattlePanel bp;
    //编号
    private int code = 0;
    //当前技能数量
    private static int skillNumber = 2;
    private BattleState battleState;

    //人物战斗数据

    //hp和mp
    public static int hp;
    public static int mp;

    //怒气值
    public static int angryValue = 0;
    //是否愤怒
    private boolean isAngry = false;

    //hp和mp上限
    public static int hpMax;
    public static int mpMax;

    //体力
    public static int physicalPower = 10;
    //精气
    public static int sprit = 12;
    //敏捷
    public static int agile = 14;
    //武力
    public static int strength = 8;

    //速度
    private static int speed;
    //攻击力
    private static int hurt;
    //技能攻击力
    private static int skillHurt;
    //防御力
    private static int defense;
    //技能防御
    private static int skillDefense;

    //当前造成的伤害值
    private int currentDamage;
    //当前造成的伤害类型 1.伤害 2.回复
    private int currentDamageType;
    //当前攻击的对象集合
    private ArrayList<Enemy> currentEnemies = new ArrayList<>();


    //有关于人物级别的数据

    //等级
    private static int level = 1;
    //距离升下级还需要的经验
    public static int expToLevelUp;
    //当前经验
    public static int exp = 0;
    //是否升级
    private boolean isLevelUp;
    //是否习得技能
    private boolean isGetSkill;

    private ArrayList<String> roleInfo = new ArrayList<String>();

    public static void setShowX(int showX) {
        LuXueQi.showX = showX;
    }

    public static void setShowY(int showY) {
        LuXueQi.showY = showY;
    }

    public static int getSkillNumber() {
        return skillNumber;
    }

    public static void setSkillNumber(int skillNumber) {
        LuXueQi.skillNumber = skillNumber;
    }

    public static void setHpMax(int hpMax) {
        LuXueQi.hpMax = hpMax;
    }

    public static void setMpMax(int mpMax) {
        LuXueQi.mpMax = mpMax;
    }

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int speed) {
        LuXueQi.speed = speed;
    }

    public static int getHurt() {
        return hurt;
    }

    public static void setHurt(int hurt) {
        LuXueQi.hurt = hurt;
    }

    public static int getSkillHurt() {
        return skillHurt;
    }

    public static void setSkillHurt(int skillHurt) {
        LuXueQi.skillHurt = skillHurt;
    }

    public static void setDefense(int defense) {
        LuXueQi.defense = defense;
    }

    public static void setSkillDefense(int skillDefense) {
        LuXueQi.skillDefense = skillDefense;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        LuXueQi.level = level;
    }


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
                    if (currentDamage < 0) {
                        currentDamage = 0;
                    }
                    currentDamageType = 1;
                    currentEnemy.setHp(currentEnemy.getHp() - currentDamage);
                    HurtValue hurtValue = new HurtValue(bp);
                    hurtValue.show(currentDamage, currentDamageType, currentEnemy.getX(), currentEnemy.getY());
                    bp.getHurtValues().add(hurtValue);
                }
                break;
            case 2:
                for (Hero hero : bp.getHeroes()) {
                    if (!hero.isDead()) {
                        hero.getBattleState().set(2, 1, 100, hero.getRoleCode(), hero.getShowX(), hero.getShowY());
                        hero.checkState();
                    }
                }
                mp -= 80;
                break;
            case 3:
                if (bp.getZxf() != null && !bp.getZxf().isDead) {
                    bp.getProgressBar().setZhangX(bp.getProgressBar().getBarX() + 400);
                }
                if (bp.getYj() != null && !bp.getYj().isDead) {
                    bp.getProgressBar().setYuX(bp.getProgressBar().getBarX() + 400);
                }
                if (bp.getEm1() != null) {
                    bp.getProgressBar().setEnemy1X(bp.getProgressBar().getBarX());
                }
                if (bp.getEm2() != null) {
                    bp.getProgressBar().setEnemy2X(bp.getProgressBar().getBarX());
                }
                if (bp.getEm3() != null) {
                    bp.getProgressBar().setEnemy3X(bp.getProgressBar().getBarX());
                }
                for (Enemy enemy : bp.getEnemies()) {
                    enemy.getBattleState().set(1, 5, 70, enemy.getRoleCode(), enemy.getX(), enemy.getY());
                    enemy.checkState();
                }
                mp -= (int) (mpMax * 0.6);
                break;
            case 4:
                attackSkill(80, 30, 150);
                for (Enemy enemy : bp.getEnemies()) {
                    enemy.getBattleState().set(2, 9, 80, enemy.getRoleCode(), enemy.getX(), enemy.getY());
                    enemy.checkState();
                }
                break;
            case 5:
                attackSkill(200, 30, 160);
                for (Enemy enemy : currentEnemies) {
                    enemy.getBattleState().set(2, 10, 40, enemy.getRoleCode(), enemy.getX(), enemy.getY());
                    enemy.checkState();
                }
                break;
            case 6:
                attackSkill(180, 30, 200);
                for (Enemy enemy : bp.getEnemies()) {
                    int type = (int) (Math.random() * 6) + 5;
                    enemy.getBattleState().set(2, type, 100, enemy.getRoleCode(), enemy.getX(), enemy.getY());
                    enemy.checkState();
                }
                for (Hero hero : bp.getHeroes()) {
                    if (!hero.isDead()) {
                        int type = (int) (Math.random() * 4) + 1;
                        hero.getBattleState().set(2, type, 100, hero.getRoleCode(), hero.getShowX(), hero.getShowY());
                        hero.checkState();
                    }
                }
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
        physicalPower += 1;
        sprit += 3;
        agile += 3;
        strength += 1;

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
    public LuXueQi() {

    }

    public LuXueQi(int x, int y, BattlePanel bp) {
        this.x = x;
        this.y = y;
        LuXueQi.showX = x;
        LuXueQi.showY = y;
        this.bp = bp;

        getImage();
        drawn = true;
        stopped = false;

        battleState = new BattleState(bp);


        physicalPower = 10 + (level - 1) * 1;
        sprit = 12 + (level - 1) * 3;
        agile = 14 + (level - 1) * 3;
        strength = 8 + (level - 1) * 1;

        loadAnimation();
        refreshValue();
        hp = hpMax;
        mp = mpMax;
        expToLevelUp = (int) (500 * ((Math.pow(1.4, level))));
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
        }
    }


    public void loadAnimation() {
        //被击动画
        beAttackedAnimation = new BeAttackedAnimation("陆雪琪被击", 2, LuXueQi.showX, LuXueQi.showY, bp);
        //胜利动画
        victoryAnimation = new VictoryAnimation(120, 135, 8, "陆雪琪", bp, this);
        //死亡动画
        deadAnimation = new DeadAnimation(720, 360, 4, "陆雪琪", bp, this);
    }

    @Override
    public void getImage() {
        // TODO Auto-generated method stub
        for (int i = 1; i <= 6; i++) {
            Image image = Reader.readImage("image/主角3/" + i + ".png");
            Images.add(image);
        }

        headImage = Reader.readImage("image/主角3/小头.png");
    }

    @Override
    public void doAction() {
        // TODO Auto-generated method stub
        if (!stopped && code < 6) {
            currentImage = Images.get(code);
            code++;

        } else if (code == 6) {
            code = 0;
        }
    }

    @Override
    public void drawHero(Graphics g) {
        // TODO Auto-generated method stub
        if (drawn) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    @Override
    public void attack() {
        // TODO Auto-generated method stub
        //人物不再画出
        drawn = false;
        bp.getSkillAnimation().set("陆雪琪攻击", 24, 120, 135, 8, 1, 7, 13, 24, 90, 210, -20);
        bp.getSkillAnimation().setDrawn(true);
        bp.getSkillAnimation().setStopped(false);
    }

    @Override
    public SkillAnimation getCurrentAnimation() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int getDefense() {
        // TODO Auto-generated method stub
        return defense;
    }

    @Override
    public int getSkillDefense() {
        // TODO Auto-generated method stub
        return skillDefense;
    }

    @Override
    public int getHp() {
        // TODO Auto-generated method stub
        return hp;
    }

    @Override
    public void setHp(int hp) {
        // TODO Auto-generated method stub
        LuXueQi.hp = hp;
    }

    @Override
    public int getExp() {
        // TODO Auto-generated method stub
        return exp;
    }

    @Override
    public void setExp(int exp) {
        // TODO Auto-generated method stub
        LuXueQi.exp = exp;
    }

    @Override
    public int getExpToLevelUp() {
        // TODO Auto-generated method stub
        return expToLevelUp;
    }

    @Override
    public boolean isLevelUp() {
        // TODO Auto-generated method stub
        return isLevelUp;
    }

    @Override
    public void setLevelUp(boolean isLevelUp) {
        // TODO Auto-generated method stub
        this.isLevelUp = isLevelUp;
    }

    @Override
    public int getPhysicalPower() {
        // TODO Auto-generated method stub
        return physicalPower;
    }

    @Override
    public int getSprit() {
        // TODO Auto-generated method stub
        return sprit;
    }

    @Override
    public int getAgile() {
        // TODO Auto-generated method stub
        return agile;
    }

    @Override
    public int getStrength() {
        // TODO Auto-generated method stub
        return strength;
    }

    @Override
    public boolean isDead() {
        // TODO Auto-generated method stub
        return dead;
    }

    @Override
    public int getHpMax() {
        // TODO Auto-generated method stub
        return hpMax;
    }

    @Override
    public int getMpMax() {
        // TODO Auto-generated method stub
        return mpMax;
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return x;
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return y;
    }

    @Override
    public int getShowX() {
        // TODO Auto-generated method stub
        return showX;
    }

    @Override
    public int getShowY() {
        // TODO Auto-generated method stub
        return showY;
    }

    @Override
    public void skill(int skillCode) {
        // TODO Auto-generated method stub
        //人物不再画出
        drawn = false;
//				animation=animations.get(skillCode);
//				animation.isDraw=true;
//				animation.isStop=false;

        switch (skillCode) {
            case 1:
                bp.getBackgroundAnimation().set("灵凤吐珠", 30);
                bp.getSkillAnimation().set("陆雪琪技能1", 17, 120, 135, 0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case 2:
                bp.getBackgroundAnimation().set("踏月无痕", 68);
                bp.getSkillAnimation().set("陆雪琪技能2", 23, 120, 135, 0, 0, 0, 0, 0, 0, 0, 0);
                break;
            case 3:
                bp.getBackgroundAnimation().set("星火乾坤圈", 38);
                bp.getSkillAnimation().set("陆雪琪技能3", 21, 120, 135, 8, 1, 0, 0, 0, 0, 0, 0);
                break;
            case 4:
                bp.getBackgroundAnimation().set("亟电崩离", 30);
                bp.getSkillAnimation().set("陆雪琪技能4", 29, 120, 135, 8, 2, 9, 18, 29, 90, 210, -20);
                break;
            case 5:
                bp.getBackgroundAnimation().set("劈风追月", 90);
                bp.getSkillAnimation().set("陆雪琪技能5", 29, 120, 135, 8, 11, 9, 22, 29, 90, 210, -20);
                break;
        }
        bp.getBackgroundAnimation().setDrawn(true);
        bp.getBackgroundAnimation().setStopped(false);
        bp.getSkillAnimation().setDrawn(true);
        bp.getSkillAnimation().setStopped(false);
    }

    @Override
    public int getMp() {
        // TODO Auto-generated method stub
        return mp;
    }

    @Override
    public void setMp(int mp) {
        // TODO Auto-generated method stub
        LuXueQi.mp = mp;
    }

    @Override
    public void setDead(boolean isDead) {
        // TODO Auto-generated method stub
        this.dead = isDead;
    }

    @Override
    public BattleState getBattleState() {
        // TODO Auto-generated method stub
        return battleState;
    }

    @Override
    public int getRoleCode() {
        // TODO Auto-generated method stub
        return roleCode;
    }

    @Override
    public void setExpToLevelUp(int e) {
        // TODO Auto-generated method stub
        expToLevelUp = e;
    }

    @Override
    public void setIsDraw(boolean isDraw) {
        // TODO Auto-generated method stub
        this.drawn = isDraw;
    }

    @Override
    public VictoryAnimation getVictoryAnimation() {
        // TODO Auto-generated method stub
        return victoryAnimation;
    }

    @Override
    public DeadAnimation getDeadAnimation() {
        // TODO Auto-generated method stub
        return deadAnimation;
    }

    @Override
    public int getAngryValue() {
        // TODO Auto-generated method stub
        return angryValue;
    }

    @Override
    public void setAngryValue(int a) {
        // TODO Auto-generated method stub
        angryValue = a;
    }

    @Override
    public boolean isAngry() {
        // TODO Auto-generated method stub
        return isAngry;
    }

    @Override
    public void setAngry(boolean isAngry) {
        // TODO Auto-generated method stub
        this.isAngry = isAngry;
    }

    @Override
    public BeAttackedAnimation getBeAttackedAnimation() {
        // TODO Auto-generated method stub
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
        roleInfo.add(dead + "");
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
        LuXueQi.level = Integer.parseInt(roleInfo.get(0));
        if (level >= 2) {
            skillNumber = 3;
        }
        if (level >= 5) {
            skillNumber = 4;
        }
        if (level >= 10) {
            skillNumber = 5;
        }
        physicalPower = 10 + (level - 1) * 1;
        sprit = 12 + (level - 1) * 3;
        agile = 14 + (level - 1) * 3;
        strength = 8 + (level - 1) * 1;
        refreshValue();
        expToLevelUp = (int) (500 * ((Math.pow(1.4, level))));
        LuXueQi.hp = Integer.parseInt(roleInfo.get(1));
        LuXueQi.mp = Integer.parseInt(roleInfo.get(2));
        LuXueQi.angryValue = Integer.parseInt(roleInfo.get(3));
        this.isAngry = Boolean.parseBoolean(roleInfo.get(4));
        this.dead = Boolean.parseBoolean(roleInfo.get(5));
        LuXueQi.exp = Integer.parseInt(roleInfo.get(6));
    }

    @Override
    public void setPhysicalPower(int physicalPower) {
        // TODO Auto-generated method stub
        LuXueQi.physicalPower = physicalPower;
    }

    @Override
    public void setSprit(int sprit) {
        // TODO Auto-generated method stub
        LuXueQi.sprit = sprit;
    }

    @Override
    public void setAgile(int agile) {
        // TODO Auto-generated method stub
        LuXueQi.agile = agile;
    }

    @Override
    public void setStrength(int strength) {
        // TODO Auto-generated method stub
        LuXueQi.strength = strength;
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

    public ArrayList<Image> getImages() {
        return Images;
    }

    public void setImages(ArrayList<Image> images) {
        Images = images;
    }

    public Image getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Image headImage) {
        this.headImage = headImage;
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

    public void setBeAttackedAnimation(BeAttackedAnimation beAttackedAnimation) {
        this.beAttackedAnimation = beAttackedAnimation;
    }

    public void setVictoryAnimation(VictoryAnimation victoryAnimation) {
        this.victoryAnimation = victoryAnimation;
    }

    public void setDeadAnimation(DeadAnimation deadAnimation) {
        this.deadAnimation = deadAnimation;
    }

    public void setX(int x) {
        this.x = x;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
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

    public ArrayList<Enemy> getCurrentEnemies() {
        return currentEnemies;
    }

    public void setCurrentEnemies(ArrayList<Enemy> currentEnemies) {
        this.currentEnemies = currentEnemies;
    }

    public boolean isGetSkill() {
        return isGetSkill;
    }

    public void setGetSkill(boolean getSkill) {
        isGetSkill = getSkill;
    }

    public ArrayList<String> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(ArrayList<String> roleInfo) {
        this.roleInfo = roleInfo;
    }
}
