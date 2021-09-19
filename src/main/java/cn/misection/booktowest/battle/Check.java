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

        if (bp.getEm1() != null && bp.getEm1().getHp() <= 0) {
            bp.getEnemies().remove(bp.getEnemies().indexOf(bp.getEm1()));
            bp.setEm1(null);
            bp.getProgressBar().Enemy1X = 0;
        }

        if (bp.getEm2() != null && bp.getEm2().getHp() <= 0) {
            bp.getEnemies().remove(bp.getEnemies().indexOf(bp.getEm2()));
            bp.setEm2(null);
            bp.getProgressBar().Enemy2X = 0;
        }

        if (bp.getEm3() != null && bp.getEm3().getHp() <= 0) {
            bp.getEnemies().remove(bp.getEnemies().indexOf(bp.getEm3()));
            bp.setEm3(null);
            bp.getProgressBar().Enemy3X = 0;
        }

        //如果全部怪物被杀死
        if (bp.getEm1() == null && bp.getEm2() == null && bp.getEm3() == null) {

            //我方获得所有经验
            for (Hero hero : bp.getHeroes()) {
                hero.setExp(hero.getExp() + bp.getVictoryReminder().expToGet);
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
            bp.getProgressBar().isDraw = false;

            bp.getVictoryReminder().isDraw = true;
            bp.getVictoryReminder().isStop = false;
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
            bp.getProgressBar().isDraw = false;
            bp.getGameOver().isDraw = true;
            bp.getGameOver().isStop = false;
        }
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
