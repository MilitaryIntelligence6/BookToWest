package cn.misection.booktowest.battle;

import cn.misection.booktowest.media.MusicReader;

//攻击发动器
public class LaunchAttack {
    //战斗面板引用
    private BattlePanel bp;
    //计时器
    private int code;

    //构造方法
    public LaunchAttack(BattlePanel bp) {
        this.bp = bp;
    }

    //发动技能攻击
    public void skillAttack(int mpUse, int reminderCode, int skillCode, Hero hero) {
        if (hero.getMp() >= mpUse) {
            bp.getHurtValues().clear();
            hero.calDamage();
            bp.setCurrentPattern(0);
            //指示器停止
            bp.getInstruct().end();
            //显示提示
            bp.getReminder().show(reminderCode);
            hero.skill(skillCode);
        } else {
            bp.getReminder().show(20);
            bp.getCommand().setDraw(true);
            bp.setCurrentPattern(0);
            bp.setCurrentBeAttacked(0);
        }
    }

    //检验方法
    public void check() {
        //检验张小凡的回合
        if (bp.getCurrentRound() == 1 && bp.getCurrentBeAttacked() != 0) {
            checkZhang();
        }
        //检验文敏的回合
        if (bp.getCurrentRound() == 2 && bp.getCurrentBeAttacked() != 0) {
            checkWen();
        }
        //检验陆雪琪的回合
        if (bp.getCurrentRound() == 3 && bp.getCurrentBeAttacked() != 0) {
            checkLu();
        }

        //检验小精灵的回合
        if (bp.getCurrentRound() == 4 && bp.getCurrentBeAttacked() != 0) {
            checkPet();
        }

        //检验怪物1的回合
        if (bp.getCurrentRound() == 5 && bp.getCurrentBeAttacked() != 0) {
            checkEnemy1();
        }
        //检验怪物2的回合
        if (bp.getCurrentRound() == 6 && bp.getCurrentBeAttacked() != 0) {
            checkEnemy2();
        }
        //检验怪物3的回合
        if (bp.getCurrentRound() == 7 && bp.getCurrentBeAttacked() != 0) {
            checkEnemy3();
        }
    }

    //检验张小凡
    public void checkZhang() {

        //攻击
        if (bp.getCurrentPattern() == 1) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            //计算伤害值
            bp.getHurtValues().clear();
            bp.getZxf().calDamage();
            //指示器停止
            bp.getInstruct().end();
            bp.getZxf().attack();
            //为了使这一个判断只做一次
            bp.setCurrentPattern(0);
        }

        //横剑摆渡
        if (bp.getCurrentPattern() == 2) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(70, 0, 1, bp.getZxf());
        }
        if (bp.getCurrentPattern() == 3) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(120, 1, 2, bp.getZxf());
        }
        if (bp.getCurrentPattern() == 4) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(150, 2, 3, bp.getZxf());
        }
        if (bp.getCurrentPattern() == 5) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(160, 3, 4, bp.getZxf());
        }
        if (bp.getCurrentPattern() == 6) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            skillAttack(200, 4, 5, bp.getZxf());
        }

        //秘术
        if (bp.getCurrentPattern() == 7) {
            MusicReader.readMusic("张小凡攻击(2).wav");
            //全体获得金钟罩状态加成2回合
            for (Hero hero : bp.getHeroes()) {
                if (!hero.isDead()) {
                    hero.getBattleState().set(2, 11, 100, hero.getRoleCode(), hero.getShowX(), hero.getShowY());
                    hero.checkState();
                }
            }

            //开启动画
            bp.getZxf().isDraw = false;
            bp.getSkillAnimation().set("张小凡秘术", 18, 560, 190, 0, 0, 0, 0, 0, 0, 0, 0);
            bp.getSkillAnimation().setDrawn(true);
            bp.getSkillAnimation().setStopped(false);
            //指示器停止
            bp.getInstruct().end();
            //处理愤怒值
            ZhangXiaoFan.angryValue = 0;
            bp.getZxf().isAngry = false;
            //使判断只做一次
            bp.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);
            //人物重新出现
            bp.getZxf().isDraw = true;

            if (bp.getBackgroundAnimation() == null || !bp.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : bp.getHurtValues()) {
                    hurtValue.start();
                }
                bp.getCheck().checkEnemyDead();

                //进度条继续
                bp.getProgressBar().setZhangX(bp.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (bp.getBackgroundAnimation().isOvered()) {
            bp.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkEnemyDead();
            //进度条继续
            bp.getProgressBar().setZhangX(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    /**
     * 检验文敏;
     */
    public void checkWen() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            MusicReader.readMusic("文敏攻击(2).wav");
            bp.getHurtValues().clear();
            bp.getYj().calDamage();
            bp.setCurrentPattern(0);
            //指示器停止
            bp.getInstruct().end();
            bp.getYj().attack();
        }

        //技能1 伏虎冲天
        if (bp.getCurrentPattern() == 2) {
            MusicReader.readMusic("文敏攻击(2).wav");
            MusicReader.readMusic("伏虎冲天.wav");
            skillAttack(80, 5, 1, bp.getYj());
        }

        //技能2 追星破月
        if (bp.getCurrentPattern() == 3) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(120, 6, 2, bp.getYj());
        }

        //技能3 苍龙盖天
        if (bp.getCurrentPattern() == 4) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(150, 7, 3, bp.getYj());
        }

        //技能5 妙手回春
        if (bp.getCurrentPattern() == 5) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(120, 8, 4, bp.getYj());
        }

        //技能6 蝶影神灵
        if (bp.getCurrentPattern() == 6) {
            MusicReader.readMusic("文敏攻击(2).wav");
            skillAttack(200, 9, 5, bp.getYj());
        }

        //秘术
        if (bp.getCurrentPattern() == 7) {
            MusicReader.readMusic("文敏攻击(2).wav");
            //自身潜能爆发三个回合
            bp.getYj().battleState.set(3, 12, 100, 2, YuJie.showX, YuJie.showY);
            bp.getYj().checkState();
            //播放动画
            bp.getYj().isDraw = false;
            bp.getSkillAnimation().set("文敏秘术", 10, 650, 100, 0, 0, 0, 0, 0, 0, 0, 0);
            bp.getSkillAnimation().setDrawn(true);
            bp.getSkillAnimation().setStopped(false);
            //指示器停止
            bp.getInstruct().end();
            //处理愤怒值
            YuJie.angryValue = 0;
            bp.getYj().isAngry = false;
            //使判断只做一次
            bp.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);
            //人物重新出现
            bp.getYj().isDraw = true;

            if (bp.getBackgroundAnimation() == null || !bp.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : bp.getHurtValues()) {
                    hurtValue.start();
                }
                bp.getCheck().checkEnemyDead();

                //进度条继续
                bp.getProgressBar().setYuX(bp.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (bp.getBackgroundAnimation().isOvered()) {
            bp.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkEnemyDead();

            //进度条继续
            bp.getProgressBar().setYuX(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //检验陆雪琪
    public void checkLu() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            //计算伤害值
            bp.getHurtValues().clear();
            bp.getLxq().calDamage();
            //指示器停止
            bp.getInstruct().end();
            bp.getLxq().attack();
            //为了使这一个判断只做一次
            bp.setCurrentPattern(0);
        }

        if (bp.getCurrentPattern() == 2) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(80, 10, 1, bp.getLxq());
        }

        if (bp.getCurrentPattern() == 3) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack((int) (LuXueQi.mpMax * 0.6), 11, 2, bp.getLxq());
        }
        if (bp.getCurrentPattern() == 4) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(150, 12, 3, bp.getLxq());
        }
        if (bp.getCurrentPattern() == 5) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(160, 13, 4, bp.getLxq());
        }
        if (bp.getCurrentPattern() == 6) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            skillAttack(200, 14, 5, bp.getLxq());
        }

        //秘术
        if (bp.getCurrentPattern() == 7) {
            MusicReader.readMusic("陆雪琪攻击(2).wav");
            //召唤小精灵
            bp.setPet(new Pet(bp));

            //开启动画
            bp.getLxq().setDrawn(false);
            bp.getSkillAnimation().set("陆雪琪秘术", 8, 620, 300, 0, 0, 0, 0, 0, 0, 0, 0);
            bp.getSkillAnimation().setDrawn(true);
            bp.getSkillAnimation().setStopped(false);
            //指示器停止
            bp.getInstruct().end();
            //处理愤怒值
            LuXueQi.angryValue = 0;
            bp.getLxq().setAngry(false);
            //使判断只执行一次
            bp.setCurrentPattern(0);
        }

        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);
            //人物重新出现
            bp.getLxq().setDrawn(true);

            if (bp.getBackgroundAnimation() == null || !bp.getBackgroundAnimation().isDrawn()) {
                //显示伤害值
                for (HurtValue hurtValue : bp.getHurtValues()) {
                    hurtValue.start();
                }
                bp.getCheck().checkEnemyDead();

                //进度条继续
                bp.getProgressBar().setLuX(bp.getProgressBar().getBarX());
                //恢复
                resume();
            }
        }
        if (bp.getBackgroundAnimation().isOvered()) {
            bp.getBackgroundAnimation().setOvered(false);
            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkEnemyDead();

            //进度条继续
            bp.getProgressBar().setLuX(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //检验怪物1
    public void checkEnemy1() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm1().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm1().attack();
                code = 0;
            }
        }

        if (bp.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm1().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm1().attack();
                code = 0;
            }
        }
        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkHeroDead();
            //人物重新出现
            bp.getEm1().setDraw(true);
            //进度条继续
            bp.getProgressBar().setEnemy1X(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //检验怪物2
    public void checkEnemy2() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm2().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm2().attack();
                code = 0;
            }
        }

        if (bp.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm2().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm2().attack();
                code = 0;
            }
        }
        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkHeroDead();
            //人物重新出现
            bp.getEm2().setDraw(true);
            //进度条继续
            bp.getProgressBar().setEnemy2X(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //检验怪物3
    public void checkEnemy3() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm3().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm3().attack();
                code = 0;
            }
        }

        if (bp.getCurrentPattern() == 2) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getEm3().calDamage();
                bp.setCurrentPattern(0);
                bp.getEm3().attack();
                code = 0;
            }
        }

        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkHeroDead();
            //人物重新出现
            bp.getEm3().setDraw(true);
            //进度条继续
            bp.getProgressBar().setEnemy3X(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //检查小精灵
    public void checkPet() {
        //攻击
        if (bp.getCurrentPattern() == 1) {
            if (code < 5) {
                code++;
            } else {
                bp.getHurtValues().clear();
                bp.getPet().calDamage();
                bp.setCurrentPattern(0);
                bp.getPet().attack();
                code = 0;
            }
        }

        //技能发动结束后的恢复
        if (bp.getSkillAnimation().isOver()) {
            //为了使这个判断只做一次
            bp.getSkillAnimation().setOver(false);

            //显示伤害值
            for (HurtValue hurtValue : bp.getHurtValues()) {
                hurtValue.start();
            }
            bp.getCheck().checkEnemyDead();
            //人物重新出现
            bp.getPet().setDraw(true);
            //进度条继续
            bp.getProgressBar().setPetX(bp.getProgressBar().getBarX());
            //恢复
            resume();
        }
    }

    //恢复
    public void resume() {
        bp.setCurrentRound(0);
        bp.setCurrentBeAttacked(0);
        bp.setCurrentPattern(0);
        bp.getProgressBar().setStop(false);
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
}
