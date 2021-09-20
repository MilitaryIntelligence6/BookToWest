package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 控制台类;
 */
public class Command {

    /**
     * 按钮引用
     */
    private GameButton attack;

    private GameButton skill;

    private GameButton defend;

    private GameButton thing;

    /**
     * 按钮集合
     */
    private List<GameButton> gameButtons = new ArrayList<>();

    /**
     * 图片引用
     */
    private Image normalImage;

    private Image waitClickImage;

    private Image pressedImage;

    /**
     * 击按钮的位置
     */
    private int x;

    private int y;

    /**
     * 是否被画出
     */
    private boolean drawn;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    public Command(BattlePanel battlePanel) {
        //创建击按钮
        normalImage = Reader.readImage("image/按钮图/击1.png");
        waitClickImage = Reader.readImage("image/按钮图/击2.png");
        pressedImage = Reader.readImage("image/按钮图/击3.png");
        this.x = 500;
        this.y = 300;
        this.battlePanel = battlePanel;
        attack = new GameButton(x, y, 58, 62, normalImage, waitClickImage, pressedImage, battlePanel);
        gameButtons.add(attack);

        //创建技按钮
        normalImage = Reader.readImage("image/按钮图/技1.png");
        waitClickImage = Reader.readImage("image/按钮图/技2.png");
        pressedImage = Reader.readImage("image/按钮图/技3.png");
        skill = new GameButton(x, y - 62, 58, 62, normalImage, waitClickImage, pressedImage, battlePanel);
        gameButtons.add(skill);

        //创建防按钮
        normalImage = Reader.readImage("image/按钮图/防1.png");
        waitClickImage = Reader.readImage("image/按钮图/防2.png");
        pressedImage = Reader.readImage("image/按钮图/防3.png");
        defend = new GameButton(x - 58, y + 40, 58, 62, normalImage, waitClickImage, pressedImage, battlePanel);
        gameButtons.add(defend);

        //创建物按钮
        normalImage = Reader.readImage("image/按钮图/物1.png");
        waitClickImage = Reader.readImage("image/按钮图/物2.png");
        pressedImage = Reader.readImage("image/按钮图/物3.png");
        thing = new GameButton(x + 58, y + 40, 58, 62, normalImage, waitClickImage, pressedImage, battlePanel);
        gameButtons.add(thing);

        drawn = false;
    }

    //检查是否移动鼠标进入控制台
    public void checkMoveIn() {
        for (GameButton button : gameButtons) {
            button.isMoveIn(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
    }

    //检查鼠标是否点击控制台
    public void checkPressed() {
        for (GameButton button : gameButtons) {
            button.isPressedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
    }

    //检查是否松开鼠标
    public void checkReleased() {
        //检验 击 按钮是否被按下
        if (attack.isClicked() == true) {
            //把控制台隐藏掉
            drawn = false;
            battlePanel.setCurrentPattern(1);
            //打开怪物选择
            battlePanel.getEnemySlector().setSelectable(true);

        }

        if (skill.isClicked() == true) {
            //把控制台隐藏掉
            drawn = false;
            battlePanel.getSkillMenu().checkRound();
            battlePanel.getSkillMenu().setDraw(true);
        }

        if (thing.isClicked() == true) {
            //把控制台隐藏掉
            drawn = false;
            battlePanel.getDrugMenu().checkHero();
            battlePanel.getDrugMenu().setDrawn(true);
        }

        if (defend.isClicked() == true) {
            switch (battlePanel.getCurrentRound()) {
                case 1:
                    if (battlePanel.getZxf().isAngry) {
                        //把控制台隐藏掉
                        drawn = false;
                        //选定攻击对象
                        battlePanel.setCurrentBeAttacked(8);
                        //攻击模式
                        battlePanel.setCurrentPattern(7);
                    } else {
                        battlePanel.getReminder().show(21);
                    }
                    break;
                case 2:
                    if (battlePanel.getYj().isAngry) {
                        //把控制台隐藏掉
                        drawn = false;
                        //选定攻击对象
                        battlePanel.setCurrentBeAttacked(8);
                        //攻击模式
                        battlePanel.setCurrentPattern(7);
                    } else {
                        battlePanel.getReminder().show(21);
                    }
                    break;
                case 3:
                    if (battlePanel.getLxq().isAngry()) {
                        //把控制台隐藏掉
                        drawn = false;
                        //选定攻击对象
                        battlePanel.setCurrentBeAttacked(8);
                        //攻击模式
                        battlePanel.setCurrentPattern(7);
                    } else {
                        battlePanel.getReminder().show(21);
                    }
                    break;
            }

        }


        for (GameButton button : gameButtons) {
            button.isRelesedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
    }

    //画出控制台
    public void drawCommand(Graphics g) {
        if (drawn) {
            for (GameButton button : gameButtons) {
                button.drawButton(g);
            }
        }
    }

    public GameButton getAttack() {
        return attack;
    }

    public void setAttack(GameButton attack) {
        this.attack = attack;
    }

    public GameButton getSkill() {
        return skill;
    }

    public void setSkill(GameButton skill) {
        this.skill = skill;
    }

    public GameButton getDefend() {
        return defend;
    }

    public void setDefend(GameButton defend) {
        this.defend = defend;
    }

    public GameButton getThing() {
        return thing;
    }

    public void setThing(GameButton thing) {
        this.thing = thing;
    }

    public List<GameButton> getGameButtons() {
        return gameButtons;
    }

    public void setGameButtons(List<GameButton> gameButtons) {
        this.gameButtons = gameButtons;
    }

    public Image getNormalImage() {
        return normalImage;
    }

    public void setNormalImage(Image normalImage) {
        this.normalImage = normalImage;
    }

    public Image getWaitClickImage() {
        return waitClickImage;
    }

    public void setWaitClickImage(Image waitClickImage) {
        this.waitClickImage = waitClickImage;
    }

    public Image getPressedImage() {
        return pressedImage;
    }

    public void setPressedImage(Image pressedImage) {
        this.pressedImage = pressedImage;
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

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
