package cn.misection.booktowest.battle;

import java.awt.Graphics;

/**
 * 我方战斗单位接口;
 * @author javaman
 */
public interface Hero {

    /**
     * 得到被击动画;
     *
     * @return
     */
    BeAttackedAnimation getBeAttackedAnimation();

    /**
     * 是否愤怒
     */
    boolean isAngry();

    /**
     * 设置是否愤怒
     */
    void setAngry(boolean isAngry);

    /**
     * 得到怒气值
     */
    int getAngryValue();

    /**
     * 设置怒气值
     */
    void setAngryValue(int a);

    /**
     * 得到胜利动画
     */
    VictoryAnimation getVictoryAnimation();

    /**
     * 得到死亡动画
     */
    DeadAnimation getDeadAnimation();

    /**
     * 更改英雄是否绘出
     */
    void setIsDraw(boolean isDraw);

    /**
     * 通过指定编号和长度来读入图片
     */
    void getImage();

    /**
     * 返回编号
     */
    int getRoleCode();

    /**
     * 自身动画
     */
    void doAction();

    /**
     * 画出自己
     */
    void drawHero(Graphics g);

    /**
     * 攻击
     */
    void attack();

    /**
     * 得到当前的技能动画
     */
    SkillAnimation getCurrentAnimation();

    /**
     * 得到物理防御
     */
    int getDefense();

    /**
     * 得到魔法防御
     */
    int getSkillDefense();

    /**
     * 得到hp
     */
    int getHp();

    /**
     * 设置hp
     */
    void setHp(int hp);

    /**
     * 得到mp
     */
    int getMp();

    /**
     * 设置mp
     */
    void setMp(int mp);

    /**
     * 得到经验
     */
    int getExp();

    /**
     * 设置经验
     */
    void setExp(int exp);

    /**
     * 得到升级所需的经验
     */
    int getExpToLevelUp();

    /**
     * 设置升级所需的经验
     */
    void setExpToLevelUp(int e);

    /**
     * 是否升级
     */
    boolean isLevelUp();

    /**
     * 设置是否升级
     */
    void setLevelUp(boolean isLevelUp);

    /**
     * 升级时调用的方法
     */
    void levelUp();

    /**
     * 计算伤害值
     */
    void calDamage();

    /**
     * 使用技能
     */
    void skill(int skillCode);

    /**
     * 得到体力
     */
    int getPhysicalPower();

    /**
     * 得到精气
     */
    int getSprit();

    /**
     * 得到敏捷
     */
    int getAgile();

    /**
     * 得到武力
     */
    int getStrength();

    /**
     * 得到最大hp
     */
    int getHpMax();

    /**
     * 得到最大mp
     */
    int getMpMax();

    /**
     * 得到x;
     */
    int getX();

    /**
     * 得到y
     */
    int getY();

    int getShowX();

    int getShowY();

    /**
     * 得到战斗状态
     */
    BattleState getBattleState();

    /**
     * 检查状态
     */
    void checkState();

    /**
     * 清空状态
     */
    void returnFromState();

    /**
     * 是否死亡
     */
    boolean isDead();

    /**
     * 设置死亡
     */
    void setDead(boolean isDead);

    void setPhysicalPower(int physicalPower);

    void setSprit(int sprit);

    void setAgile(int agile);

    void setStrength(int strength);

}
