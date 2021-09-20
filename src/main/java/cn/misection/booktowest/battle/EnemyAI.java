package cn.misection.booktowest.battle;

/**
 * @author javaman
 * 怪物智能类;
 */
public class EnemyAI {

    /**
     * 战斗面板引用;
     */
    private BattlePanel battlePanel;

    public EnemyAI(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    /**
     * 选择攻击招数;
     * @param e
     */
    public void skillToUse(Enemy e) {
        battlePanel.setCurrentPattern((int) (Math.random() * e.getSkillNum()) + 1);
        if (battlePanel.getCurrentPattern() == 2) {
            battlePanel.setCurrentBeAttacked(4);
        }
    }

    /**
     * 选择攻击对象;
     */
    public void heroToAttack() {
        int i = 0;
        while (true) {
            //生成随机数
            i = (int) (Math.random() * 3) + 1;
            if (i == 1 && battlePanel.getZxf() != null && !battlePanel.getZxf().isDead) {
                battlePanel.setCurrentBeAttacked(i);
                break;
            }
            if (i == 2 && battlePanel.getYj() != null && !battlePanel.getYj().isDead) {
                battlePanel.setCurrentBeAttacked(i);
                break;
            }
            if (i == 3 && battlePanel.getLxq() != null && !battlePanel.getLxq().isDead()) {
                battlePanel.setCurrentBeAttacked(i);
                break;
            }
        }
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
