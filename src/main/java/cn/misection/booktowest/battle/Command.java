package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.awt.*;
import java.util.*;

//控制台类
public class Command {
    //按钮引用
    GameButton attack;
    GameButton skill;
    GameButton defend;
    GameButton thing;
    //按钮集合
    ArrayList<GameButton> gameButtons = new ArrayList<GameButton>();
    //图片引用
    Image normalImage;
    Image waitclickImage;
    Image pressedImage;
    //击按钮的位置
    int x;
    int y;
    //是否被画出
    boolean isDraw;
    //战斗面板引用
    BattlePanel bp;

    //构造方法
    public Command(BattlePanel bp) {

        //创建击按钮
        normalImage = Reader.readImage("image/按钮图/击1.png");
        waitclickImage = Reader.readImage("image/按钮图/击2.png");
        pressedImage = Reader.readImage("image/按钮图/击3.png");
        this.x = 500;
        this.y = 300;
        this.bp = bp;
        attack = new GameButton(x, y, 58, 62, normalImage, waitclickImage, pressedImage, bp);
        gameButtons.add(attack);

        //创建技按钮
        normalImage = Reader.readImage("image/按钮图/技1.png");
        waitclickImage = Reader.readImage("image/按钮图/技2.png");
        pressedImage = Reader.readImage("image/按钮图/技3.png");
        skill = new GameButton(x, y - 62, 58, 62, normalImage, waitclickImage, pressedImage, bp);
        gameButtons.add(skill);

        //创建防按钮
        normalImage = Reader.readImage("image/按钮图/防1.png");
        waitclickImage = Reader.readImage("image/按钮图/防2.png");
        pressedImage = Reader.readImage("image/按钮图/防3.png");
        defend = new GameButton(x - 58, y + 40, 58, 62, normalImage, waitclickImage, pressedImage, bp);
        gameButtons.add(defend);

        //创建物按钮
        normalImage = Reader.readImage("image/按钮图/物1.png");
        waitclickImage = Reader.readImage("image/按钮图/物2.png");
        pressedImage = Reader.readImage("image/按钮图/物3.png");
        thing = new GameButton(x + 58, y + 40, 58, 62, normalImage, waitclickImage, pressedImage, bp);
        gameButtons.add(thing);

        isDraw = false;
    }

    //检查是否移动鼠标进入控制台
    public void checkMoveIn() {
        for (GameButton button : gameButtons) {
            button.isMoveIn(bp.getCurrentX(), bp.getCurrentY());
        }
    }

    //检查鼠标是否点击控制台
    public void checkPressed() {
        for (GameButton button : gameButtons) {
            button.isPressedButton(bp.getCurrentX(), bp.getCurrentY());
        }
    }

    //检查是否松开鼠标
    public void checkReleased() {
        //检验 击 按钮是否被按下
        if (attack.clicked == true) {
            //把控制台隐藏掉
            isDraw = false;
            bp.setCurrentPattern(1);
            //打开怪物选择
            bp.getEnemySlector().isSlectable = true;

        }

        if (skill.clicked == true) {
            //把控制台隐藏掉
            isDraw = false;
            bp.getSkillMenu().checkRound();
            bp.getSkillMenu().isDraw = true;
        }

        if (thing.clicked == true) {
            //把控制台隐藏掉
            isDraw = false;
            bp.getDrugMenu().checkHero();
            bp.getDrugMenu().isDraw = true;
        }

        if (defend.clicked == true) {
            switch (bp.getCurrentRound()) {
                case 1:
                    if (bp.getZxf().isAngry) {
                        //把控制台隐藏掉
                        isDraw = false;
                        //选定攻击对象
                        bp.setCurrentBeAttacked(8);
                        //攻击模式
                        bp.setCurrentPattern(7);
                    } else {
                        bp.getReminder().show(21);
                    }
                    break;
                case 2:
                    if (bp.getYj().isAngry) {
                        //把控制台隐藏掉
                        isDraw = false;
                        //选定攻击对象
                        bp.setCurrentBeAttacked(8);
                        //攻击模式
                        bp.setCurrentPattern(7);
                    } else {
                        bp.getReminder().show(21);
                    }
                    break;
                case 3:
                    if (bp.getLxq().isAngry) {
                        //把控制台隐藏掉
                        isDraw = false;
                        //选定攻击对象
                        bp.setCurrentBeAttacked(8);
                        //攻击模式
                        bp.setCurrentPattern(7);
                    } else {
                        bp.getReminder().show(21);
                    }
                    break;
            }

        }


        for (GameButton button : gameButtons) {
            button.isRelesedButton(bp.getCurrentX(), bp.getCurrentY());
        }
    }

    //画出控制台
    public void drawCommand(Graphics g) {
        if (isDraw) {
            for (GameButton button : gameButtons) {
                button.drawButton(g);
            }
        }
    }
}
