package cn.misection.booktowest.battle;

import cn.misection.booktowest.media.MusicReader;

/**
 * @author javaman
 * 攻击发动器;
 */
public class LaunchAttack {

    /**
     * 战斗面板引用;
     */
    private BattlePanel battlePanel;

    /**
     * 计时器;
     */
    private int code;

    public LaunchAttack(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * 发动技能攻击;
     * @param mpUse
     * @param reminderCode
     * @param skillCode
     * @param hero
     */
    public void skillAttack(int mpUse, int reminderCode, int skillCode, Hero hero) {
        if (hero.getMp() >= mpUse) {
            battlePanel.getHurtValues().clear();
            hero.calDamage();
            battlePanel.setCurrentPattern(0);
            //指示器停止
            battlePanel.getInstruct().end();
            //显示提示
            battlePanel.getReminder().show(reminderCode);
            hero.skill(skillCode);
        } else {
            battlePanel.getReminder().show(20);
            battlePanel.getCommand().setDrawn(true);
            battlePanel.setCurrentPattern(0);
            battlePanel.setCurrentBeAttacked(0);
        }
    }

    /**
     * 检验方法;
     */
    public void check() {
        //检验张小凡的回合
        if (battlePanel.getCurrentRound() == 1 && battlePanel.getCurrentBeAttacked() != 0) {
            checkZhang();
        }
        //检验文敏的回合
        if (battlePanel.getCurrentRound() == 2 && battlePanel.getCurrentBeAttacked() != 0) {
            checkWen();
        }
        //检验陆雪琪的回合
        if (battlePanel.getCurrentRound() == 3 && battlePanel.getCurrentBeAttacked() != 0) {
            checkLu();
        }

        //检验小精灵的回合
        if (battlePanel.getCurrentRound() == 4 && battlePanel.getCurrentBeAttacked() != 0) {
            checkPet();
        }

        //检验怪物1的回合
        if (battlePanel.getCurrentRound() == 5 && battlePanel.getCurrentBeAttacked() != 0) {
            checkEnemy1();
        }
        //检验怪物2的回合
        if (battlePanel.getCurrentRound() == 6 && battlePanel.getCurrentBeAttacked() != 0) {
            checkEnemy2();
        }
        //检验怪物3的回合
        if (battlePanel.getCurrentRound() == 7 && battlePanel.getCurrentBeAttacked() != 0) {
            checkEnemy3();
        }
    }

    /**
     * 检验张小凡;
     */
    public void checkZhang() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            //计算伤害值
            battlePanel.getHurtValues().clear();
            battlePanel.getZxf().calDamage();
            //指示器停止
            battlePanel.getInstruct().end();
            battlePanel.getZxf().attack();
            //为了使这一个判断只做一次
            battlePanel.setCurrentPattern(0);
        }

        //横剑摆渡
        if (battlePanel.getCurrentPattern() == 2) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(70, 0, 1, battlePanel.getZxf());
        }
        if (battlePanel.getCurrentPattern() == 3) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(120, 1, 2, battlePanel.getZxf());
        }
        if (battlePanel.getCurrentPattern() == 4) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(150, 2, 3, battlePanel.getZxf());
        }
        if (battlePanel.getCurrentPattern() == 5) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(160, 3, 4, battlePanel.getZxf());
        }
        if (battlePanel.getCurrentPattern() == 6) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(200, 4, 5, battlePanel.getZxf());
        }

        //秘术
        if (battlePanel.getCurrentPattern() == 7) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            //全体获得金钟罩状态加成2回合
            for (Hero hero : battlePanel.getHeroes()) {
                if (!hero.isDead()) {
                    hero.getBattleState().set(2, 11, 100, hero.getRoleCode(), hero.getShowX(), hero.getShowY());
                    hero.checkState();
                }
            }

            //开启动画
            battlePanel.getZxf().isDraw = false;
            battlePanel.getSkillAnimation().set("张小凡秘术", 18, 560, 190, 0, 0, 0, 0, 0, 0, 0, 0);
            battlePanel.getSkillAnimation().setDraw(true);
            battlePanel.getSkillAnimation().setStop(false);
            //指示器停止
            battlePanel.getInstruct().end();
            //处理愤怒值
            ZhangXiaoFan.angryValue = 0;
            battlePanel.getZxf().isAngry = false;
            //使判断只做一次
            battlePanel.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);
            //人物重新出现
            battlePanel.getZxf().isDraw = true;

            if (battlePanel.getBackgroundAnimation() == null || !battlePanel.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                    hurtValue.start();
                }
                battlePanel.getCheck().checkEnemyDead();

                //进度条继续
                battlePanel.getProgressBar().setZhangX(battlePanel.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (battlePanel.getBackgroundAnimation().isOvered()) {
            battlePanel.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkEnemyDead();
            //进度条继续
            battlePanel.getProgressBar().setZhangX(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验文敏;
     */
    public void checkWen() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            MusicReader.readMusic("文敏攻击(2).wav");
            battlePanel.getHurtValues().clear();
            battlePanel.getYj().calDamage();
            battlePanel.setCurrentPattern(0);
            //指示器停止
            battlePanel.getInstruct().end();
            battlePanel.getYj().attack();
        }

        //技能1 伏虎冲天
        if (battlePanel.getCurrentPattern() == 2) {
            MusicReader.readMusic("文敏攻击(2).wav");
            MusicReader.readMusic("伏虎冲天.wav");
            skillAttack(80, 5, 1, battlePanel.getYj());
        }

        //技能2 追星破月
        if (battlePanel.getCurrentPattern() == 3) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(120, 6, 2, battlePanel.getYj());
        }

        //技能3 苍龙盖天
        if (battlePanel.getCurrentPattern() == 4) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(150, 7, 3, battlePanel.getYj());
        }

        //技能5 妙手回春
        if (battlePanel.getCurrentPattern() == 5) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(120, 8, 4, battlePanel.getYj());
        }

        //技能6 蝶影神灵
        if (battlePanel.getCurrentPattern() == 6) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(200, 9, 5, battlePanel.getYj());
        }

        //秘术
        if (battlePanel.getCurrentPattern() == 7) {
            MusicReader.readMusic("文敏攻击(2).wav");
            //自身潜能爆发三个回合
            battlePanel.getYj().battleState.set(3, 12, 100, 2, YuJie.showX, YuJie.showY);
            battlePanel.getYj().checkState();
            //播放动画
            battlePanel.getYj().isDraw = false;
            battlePanel.getSkillAnimation().set("文敏秘术", 10, 650, 100, 0, 0, 0, 0, 0, 0, 0, 0);
            battlePanel.getSkillAnimation().setDraw(true);
            battlePanel.getSkillAnimation().setStop(false);
            //指示器停止
            battlePanel.getInstruct().end();
            //处理愤怒值
            YuJie.angryValue = 0;
            battlePanel.getYj().isAngry = false;
            //使判断只做一次
            battlePanel.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);
            //人物重新出现
            battlePanel.getYj().isDraw = true;

            if (battlePanel.getBackgroundAnimation() == null || !battlePanel.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                    hurtValue.start();
                }
                battlePanel.getCheck().checkEnemyDead();

                //进度条继续
                battlePanel.getProgressBar().setYuX(battlePanel.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (battlePanel.getBackgroundAnimation().isOvered()) {
            battlePanel.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkEnemyDead();

            //进度条继续
            battlePanel.getProgressBar().setYuX(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验陆雪琪;
     */
    public void checkLu() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            //计算伤害值
            battlePanel.getHurtValues().clear();
            battlePanel.getLxq().calDamage();
            //指示器停止
            battlePanel.getInstruct().end();
            battlePanel.getLxq().attack();
            //为了使这一个判断只做一次
            battlePanel.setCurrentPattern(0);
        }

        if (battlePanel.getCurrentPattern() == 2) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(80, 10, 1, battlePanel.getLxq());
        }

        if (battlePanel.getCurrentPattern() == 3) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack((int) (LuXueQi.mpMax * 0.6), 11, 2, battlePanel.getLxq());
        }
        if (battlePanel.getCurrentPattern() == 4) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(150, 12, 3, battlePanel.getLxq());
        }
        if (battlePanel.getCurrentPattern() == 5) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(160, 13, 4, battlePanel.getLxq());
        }
        if (battlePanel.getCurrentPattern() == 6) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(200, 14, 5, battlePanel.getLxq());
        }

        //秘术
        if (battlePanel.getCurrentPattern() == 7) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            //召唤小精灵
            battlePanel.setPet(new Pet(battlePanel));

            //开启动画
            battlePanel.getLxq().setDrawn(false);
            battlePanel.getSkillAnimation().set("陆雪琪秘术", 8, 620, 300, 0, 0, 0, 0, 0, 0, 0, 0);
            battlePanel.getSkillAnimation().setDraw(true);
            battlePanel.getSkillAnimation().setStop(false);
            //指示器停止
            battlePanel.getInstruct().end();
            //处理愤怒值
            LuXueQi.angryValue = 0;
            battlePanel.getLxq().setAngry(false);
            //使判断只执行一次
            battlePanel.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);
            //人物重新出现
            battlePanel.getLxq().setDrawn(true);

            if (battlePanel.getBackgroundAnimation() == null || !battlePanel.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                    hurtValue.start();
                }
                battlePanel.getCheck().checkEnemyDead();

                //进度条继续
                battlePanel.getProgressBar().setLuX(battlePanel.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (battlePanel.getBackgroundAnimation().isOvered()) {
            battlePanel.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkEnemyDead();

            //进度条继续
            battlePanel.getProgressBar().setLuX(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验怪物1;
     */
    public void checkEnemy1() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyOne().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyOne().attack();
                code = 0;
            }
        }

        if (battlePanel.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyOne().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyOne().attack();
                code = 0;
            }
        }
        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkHeroDead();
            //人物重新出现
            battlePanel.getEnemyOne().setDraw(true);
            //进度条继续
            battlePanel.getProgressBar().setEnemy1X(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验怪物2;
     */
    public void checkEnemy2() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyTwo().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyTwo().attack();
                code = 0;
            }
        }

        if (battlePanel.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyTwo().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyTwo().attack();
                code = 0;
            }
        }
        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkHeroDead();
            //人物重新出现
            battlePanel.getEnemyTwo().setDraw(true);
            //进度条继续
            battlePanel.getProgressBar().setEnemy2X(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验怪物 3;
     */
    public void checkEnemy3() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyThree().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyThree().attack();
                code = 0;
            }
        }

        if (battlePanel.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getEnemyThree().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getEnemyThree().attack();
                code = 0;
            }
        }

        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkHeroDead();
            //人物重新出现
            battlePanel.getEnemyThree().setDraw(true);
            //进度条继续
            battlePanel.getProgressBar().setEnemy3X(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检查小精灵;
     */
    public void checkPet() {
        //攻击
        if (battlePanel.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                battlePanel.getHurtValues().clear();
                battlePanel.getPet().calDamage();
                battlePanel.setCurrentPattern(0);
                battlePanel.getPet().attack();
                code = 0;
            }
        }

        //技能发动结束后的恢复
        if (battlePanel.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            battlePanel.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : battlePanel.getHurtValues()) {
                hurtValue.start();
            }
            battlePanel.getCheck().checkEnemyDead();
            //人物重新出现
            battlePanel.getPet().setDraw(true);
            //进度条继续
            battlePanel.getProgressBar().setPetX(battlePanel.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 恢复;
     */
    public void resume() {
        battlePanel.setCurrentRound(0);
        battlePanel.setCurrentBeAttacked(0);
        battlePanel.setCurrentPattern(0);
        battlePanel.getProgressBar().setStop(false);
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
