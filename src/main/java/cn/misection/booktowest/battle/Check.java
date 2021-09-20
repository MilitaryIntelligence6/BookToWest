package cn.misection.booktowest.battle;

import cn.misection.booktowest.media.MusicReader;

//检查类
public class Check {

    /**
     * 战斗面板引用;
     */
    private BattlePanel battlePanel;

    public Check(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * 检查怪物是否死亡 注意要在所有的技能动画发动结束以后;
     */
    public void checkEnemyDead() {

        if (battlePanel.getEnemyOne() != null && battlePanel.getEnemyOne().getHp() <= 0) {
            battlePanel.getEnemyList().remove(battlePanel.getEnemyList().indexOf(battlePanel.getEnemyOne()));
            battlePanel.setEnemyOne(null);
            battlePanel.getProgressBar().setEnemy1X(0);
        }

        if (battlePanel.getEnemyTwo() != null && battlePanel.getEnemyTwo().getHp() <= 0) {
            battlePanel.getEnemyList().remove(battlePanel.getEnemyList().indexOf(battlePanel.getEnemyTwo()));
            battlePanel.setEnemyTwo(null);
            battlePanel.getProgressBar().setEnemy2X(0);
        }

        if (battlePanel.getEnemyThree() != null && battlePanel.getEnemyThree().getHp() <= 0) {
            battlePanel.getEnemyList().remove(battlePanel.getEnemyList().indexOf(battlePanel.getEnemyThree()));
            battlePanel.setEnemyThree(null);
            battlePanel.getProgressBar().setEnemy3X(0);
        }

        //如果全部怪物被杀死
        if (battlePanel.getEnemyOne() == null && battlePanel.getEnemyTwo() == null && battlePanel.getEnemyThree() == null) {

            //我方获得所有经验
            for (Hero hero : battlePanel.getHeroes()) {
                hero.setExp(hero.getExp() + battlePanel.getVictoryReminder().getExpToGet());
            }
            //我方所有状态清空
            for (Hero hero : battlePanel.getHeroes()) {
                if (hero.getBattleState().isUsable()) {
                    hero.returnFromState();
                    hero.getBattleState().clear();
                }
            }
            //检查是否升级
            for (Hero hero : battlePanel.getHeroes()) {
                if (hero.getExp() >= hero.getExpToLevelUp()) {
                    hero.levelUp();
                }
            }
            //播放胜利动画
            for (Hero hero : battlePanel.getHeroes()) {
                MusicReader.readMusic("战斗胜利.MP3");
                hero.getVictoryAnimation().start();
            }

            //进度条停止
            battlePanel.getProgressBar().setDraw(false);

            battlePanel.getVictoryReminder().setDraw(true);
            battlePanel.getVictoryReminder().setStop(false);
        }
    }

    //检查我方英雄是否死亡,在怪物的攻击技能播放之后检查
    public void checkHeroDead() {
        for (Hero hero : battlePanel.getHeroes()) {
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
        for (Hero hero : battlePanel.getHeroes()) {
            if (!hero.isDead()) {
                isAllDead = false;
                break;
            }
        }
        if (isAllDead) {
            MusicReader.readMusic("战斗失败.wav");
            battlePanel.getProgressBar().setDraw(false);
            battlePanel.getGameOver().setDrawn(true);
            battlePanel.getGameOver().setStopped(false);
        }
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
