package cn.misection.booktowest.scene;

import java.util.List;

import cn.misection.booktowest.app.GameApplication;
import cn.misection.booktowest.battle.Enemy;
import cn.misection.booktowest.battle.LuXueQi;
import cn.misection.booktowest.battle.YuJie;
import cn.misection.booktowest.battle.ZhangXiaoFan;

public class FightEvent {

    private static boolean fight;
    private ScenePanel scene;
    private List<String[]> battle0;
    private List<String[]> battle1;
    private int countOfBattle1;
    private int count;
    private int x;
    private int y;
    private int count_battle0;
    private boolean battle1Over;

    public FightEvent(ScenePanel scene, List<String[]> battle0,
                      List<String[]> battle1) {
        this.scene = scene;
        this.battle0 = battle0;
        if (battle0 != null) {
            if (scene.getMapSet().length > 20) {
                count_battle0 = 50;
            } else {
                count_battle0 = 30;
            }
        }
        // 与对话剧情相对应的战斗需要保存-----------
        this.battle1 = battle1;
    }

    public static boolean isFight() {
        return fight;
    }

    public static void setFight(boolean fight) {
        FightEvent.fight = fight;
    }

    public void startBattle1() {
        if (!battle1Over) {
            fight(battle1.get(countOfBattle1));
        }
        if (countOfBattle1 >= battle1.size() - 1) {
            battle1Over = true;
        } else {
            countOfBattle1++;
        }
    }

    public void startBattle0() {
        int i = (int) (Math.random() * battle0.size());
        fight(battle0.get(i));
    }

    public void fight(String[] battleInfo) {
        String fileName = battleInfo[0];
        String zhang = battleInfo[1];
        String yu = battleInfo[2];
        String lu = battleInfo[3];
        String enemy1 = battleInfo[4];
        String enemy2 = battleInfo[5];
        String enemy3 = battleInfo[6];
        ZhangXiaoFan zxf;
        YuJie yj;
        LuXueQi lxq;
        Enemy en1;
        Enemy en2;
        Enemy en3;
        if (zhang.equals("zhang")) {
            zxf = GameApplication.zhangXiaoFan;
        } else {
            zxf = null;
        }
        if (yu.equals("yu")) {
            yj = GameApplication.yuJie;
        } else {
            yj = null;
        }
        if (lu.equals("lu")) {
            lxq = GameApplication.luXueQi;
        } else {
            lxq = null;
        }
        if (!enemy1.equals("null")) {
            en1 = new Enemy(enemy1.split("/")[0], Integer.parseInt(enemy1
                    .split("/")[1]), GameApplication.battlePanel);
        } else {
            en1 = null;
        }
        if (!enemy2.equals("null")) {
            en2 = new Enemy(enemy2.split("/")[0], Integer.parseInt(enemy2
                    .split("/")[1]), GameApplication.battlePanel);
        } else {
            en2 = null;
        }
        if (!enemy3.equals("null")) {
            en3 = new Enemy(enemy3.split("/")[0], Integer.parseInt(enemy3
                    .split("/")[1]), GameApplication.battlePanel);
        } else {
            en3 = null;
        }
        if (enemy1.equals("物理阁堂主/5") || enemy1.equals("罹年居士/5")
                || enemy1.equals("缘铭道者/5") || enemy1.equals("大刀/5")
                || enemy1.equals("蒙面怪人/5") || enemy1.equals("商塔堂主/5")
                || enemy1.equals("最终李洵/5") || enemy1.equals("李洵/5")) {
            scene.getExitEvent().nextScript();
        }
        GameApplication.battlePanel.initial(fileName, zxf, yj, lxq, en1, en2, en3);
        scene.getRole().setEvent(true);
        GameApplication.switchTo("battle");
    }

    public void checkBattle0() {
        if (scene.getRole().getX() != x || scene.getRole().getY() != y) {
            count++;
            x = scene.getRole().getX();
            y = scene.getRole().getY();
        }
        if (count == count_battle0) {
            // 开始战斗
            startBattle0();
            count = 0;
        }
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public List<String[]> getBattle0() {
        return battle0;
    }

    public void setBattle0(List<String[]> battle0) {
        this.battle0 = battle0;
    }

    public List<String[]> getBattle1() {
        return battle1;
    }

    public void setBattle1(List<String[]> battle1) {
        this.battle1 = battle1;
    }

    public int getCountOfBattle1() {
        return countOfBattle1;
    }

    public void setCountOfBattle1(int countOfBattle1) {
        this.countOfBattle1 = countOfBattle1;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCount_battle0() {
        return count_battle0;
    }

    public void setCount_battle0(int count_battle0) {
        this.count_battle0 = count_battle0;
    }

    public boolean isBattle1Over() {
        return battle1Over;
    }

    public void setBattle1Over(boolean battle1Over) {
        this.battle1Over = battle1Over;
    }
}
