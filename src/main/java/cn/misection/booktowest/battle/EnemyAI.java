package cn.misection.booktowest.battle;

//怪物智能类
public class EnemyAI {

    //战斗面板引用
    private BattlePanel bp;

    //构造方法
    public EnemyAI(BattlePanel bp) {
        this.bp = bp;
    }

    //选择攻击招数
    public void skillToUse(Enemy e) {
        bp.setCurrentPattern((int) (Math.random() * e.getSkillNum()) + 1);
        if (bp.getCurrentPattern() == 2) {
            bp.setCurrentBeAttacked(4);
        }
    }


    //选择攻击对象
    public void heroToAttack() {
        int i = 0;
        while (true) {
            //生成随机数
            i = (int) (Math.random() * 3) + 1;
            if (i == 1 && bp.getZxf() != null && !bp.getZxf().isDead) {
                bp.setCurrentBeAttacked(i);
                break;
            }
            if (i == 2 && bp.getYj() != null && !bp.getYj().isDead) {
                bp.setCurrentBeAttacked(i);
                break;
            }
            if (i == 3 && bp.getLxq() != null && !bp.getLxq().dead) {
                bp.setCurrentBeAttacked(i);
                break;
            }
        }
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
