package cn.misection.booktowest.battle;

import cn.misection.booktowest.media.MusicReader;

//检查类
public class Check {

    //战斗面板引用
    private BattlePanel bp;

    //构造方法
    public Check(BattlePanel bp) {
        this.bp = bp;
    }

    //检查怪物是否死亡 注意要在所有的技能动画发动结束以后
    public void checkEnemyDead() {

        if (bp.getEnemyOne() != null && bp.getEnemyOne().getHp() <= 0) {
            bp.getEnemyList().remove(bp.getEnemyList().indexOf(bp.getEnemyOne()));
            bp.setEnemyOne(null);
            bp.getProgressBar().setEnemy1X(0);
        }

        if (bp.getEnemyTwo() != null && bp.getEnemyTwo().getHp() <= 0) {
            bp.getEnemyList().remove(bp.getEnemyList().indexOf(bp.getEnemyTwo()));
            bp.setEnemyTwo(null);
            bp.getProgressBar().setEnemy2X(0);
        }

        if (bp.getEnemyThree() != null && bp.getEnemyThree().getHp() <= 0) {
            bp.getEnemyList().remove(bp.getEnemyList().indexOf(bp.getEnemyThree()));
            bp.setEnemyThree(null);
            bp.getProgressBar().setEnemy3X(0);
        }

        //如果全部怪物被杀死
        if (bp.getEnemyOne() == null && bp.getEnemyTwo() == null && bp.getEnemyThree() == null) {

            //我方获得所有经验
            for (Hero hero : bp.getHeroes()) {
                hero.setExp(hero.getExp() + bp.getVictoryReminder().getExpToGet());
            }
            //我方所有状态清空
            for (Hero hero : bp.getHeroes()) {
                if (hero.getBattleState().isUsable()) {
                    hero.returnFromState();
                    hero.getBattleState().clear();
                }
            }
            //检查是否升级
            for (Hero hero : bp.getHeroes()) {
                if (hero.getExp() >= hero.getExpToLevelUp()) {
                    hero.levelUp();
                }
            }
            //播放胜利动画
            for (Hero hero : bp.getHeroes()) {
                MusicReader.readMusic("战斗胜利.MP3");
                hero.getVictoryAnimation().start();
            }

            //进度条停止
            bp.getProgressBar().setDraw(false);

            bp.getVictoryReminder().setDraw(true);
            bp.getVictoryReminder().setStop(false);
        }
    }

    //检查我方英雄是否死亡,在怪物的攻击技能播放之后检查
    public void checkHeroDead() {
        for (Hero hero : bp.getHeroes()) {
            if (hero.getHp() <= 0) {
                hero.setDead(true);
                //清空状态
                if (hero.getBattleState().isUsable()) {
                    hero.returnFromState();
                    hero.getBattleState().clear();
                }
                //播放死亡动画
                hero.getDeadAnimation().start();
            }
        }


        //检查我方是否全灭
        boolean isAllDead = true;
        for (Hero hero : bp.getHeroes()) {
            if (!hero.isDead()) {
                isAllDead = false;
                break;
            }
        }
        if (isAllDead) {
            MusicReader.readMusic("战斗失败.wav");
            bp.getProgressBar().setDraw(false);
            bp.getGameOver().setDraw(true);
            bp.getGameOver().setStop(false);
        }
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
