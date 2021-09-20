package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import cn.misection.booktowest.util.*;
import cn.misection.booktowest.shop.*;

/**
 * @author javaman
 * 使用药品的菜单;
 */
public class DrugMenu {

    /**
     * 背景图
     */
    private Image backImage;

    /**
     * 按钮
     */
    private GameButton drugButton;

    private List<GameButton> drugButtons = new ArrayList<>();

    /**
     * 按钮图片
     */
    private List<Image> buttonImages = new ArrayList<>();

    /**
     * 菜单出现的位置
     */
    private int x;

    private int y;

    /**
     * 是否画出
     */
    private boolean drawn;

    /**
     * 介绍性图片
     */
    private Image introduceImage;

    /**
     * 介绍图位置
     */
    private int introX;

    private int introY;

    /**
     * 介绍图是否画出
     */
    private boolean introDrawn;

    /**
     * 介绍文字
     */
    private String introString;

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    private DrugPack drugPack;

    /**
     * 当前英雄
     */
    private Hero currentHero;

    public DrugMenu(BattlePanel battlePanel) {
        this.x = 340;
        this.y = 200;

        this.introX = 220;

        backImage = Reader.readImage("image/药品菜单/药品显示框.png");
        this.battlePanel = battlePanel;
        drawn = false;

        if (DrugPack.drugList.size() == 0) {
            drugPack = new DrugPack();
        }
        loadImage();
        addButton();
    }

    /**
     * 读入图片;
     */
    public void loadImage() {
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 3; j++) {
                Image image = Reader.readImage("image/药品菜单/药品" + i + "按钮" + j + ".png");
                buttonImages.add(image);
            }
        }
        for (int i = 1; i <= 3; i++) {
            Image image = Reader.readImage("image/药品菜单/返回" + i + ".png");
            buttonImages.add(image);
        }
    }

    //添加按钮
    public void addButton() {
        for (int i = 0; i < buttonImages.size(); i = i + 3) {
            drugButton = new GameButton(395, 226 + i * 10, 215, 28, buttonImages.get(i), buttonImages.get(i + 1),
                    buttonImages.get(i + 2), battlePanel);
            drugButtons.add(drugButton);
        }
    }

    //检查是否移动鼠标进入菜单
    public void checkMoveIn() {
        for (GameButton button : drugButtons) {
            button.isMoveIn(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }

        for (int i = 0; i < 6; i++) {
            if (battlePanel.getCurrentX() > 395 && battlePanel.getCurrentX() < 610 && battlePanel.getCurrentY() > 226 + i * 30 && battlePanel.getCurrentY() < 226 + (i + 1) * 30) {
                introduceImage = DrugPack.drugList.get(i).getPicture();
                introString =
                        "hp " + DrugPack.drugList.get(i).getAddHp() + " mp " + DrugPack.drugList.get(i).getAddMp();
                this.introY = 226 + i * 30;
                introDrawn = true;
            }
        }
    }


    //检查鼠标是否点击菜单
    public void checkPressed() {
        for (GameButton button : drugButtons) {
            button.isPressedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
    }

    //检查是否松开鼠标
    public void checkReleased() {
        //按下金创药
        if (drugButtons.get(0).clicked) {
            checkDrugNumber(DrugPack.drugList.get(0), 1);
        }
        if (drugButtons.get(1).clicked) {
            checkDrugNumber(DrugPack.drugList.get(1), 2);
        }
        if (drugButtons.get(2).clicked) {
            checkDrugNumber(DrugPack.drugList.get(2), 1);
        }
        if (drugButtons.get(3).clicked) {
            checkDrugNumber(DrugPack.drugList.get(3), 2);
        }
        if (drugButtons.get(4).clicked) {
            checkDrugNumber(DrugPack.drugList.get(4), 1);
        }
        if (drugButtons.get(5).clicked) {
            checkDrugNumber(DrugPack.drugList.get(5), 2);
        }
        //按下返回按钮
        if (drugButtons.get(6).clicked) {
            drawn = false;
            battlePanel.getCommand().setDrawn(true);
        }
        for (GameButton button : drugButtons) {
            button.isRelesedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
    }

    /**
     * 检查药品数目;
     * @param drug
     * @param type
     */
    public void checkDrugNumber(Drug drug, int type) {
        if (drug.getNumberGOT() > 0) {
            battlePanel.getHurtValues().clear();
            HurtValue hurtValue = new HurtValue(battlePanel);
            if (type == 1) {
                hurtValue.show(drug.getAddHp(), 2, currentHero.getShowX(), currentHero.getShowY());
                if (drug.getAddHp() + currentHero.getHp() > currentHero.getHpMax()) {
                    currentHero.setHp(currentHero.getHpMax());
                } else {
                    currentHero.setHp(currentHero.getHp() + drug.getAddHp());
                }
            } else {
                hurtValue.show(drug.getAddMp(), 2, currentHero.getShowX(), currentHero.getShowY());
                if (drug.getAddMp() + currentHero.getMp() > currentHero.getMpMax()) {
                    currentHero.setMp(currentHero.getMpMax());
                } else {
                    currentHero.setMp(currentHero.getMp() + drug.getAddMp());
                }
            }

            battlePanel.getHurtValues().add(hurtValue);
            //显示伤害值
            for (HurtValue hurt : battlePanel.getHurtValues()) {
                hurt.start();
            }
            drug.setNumberGOT(drug.getNumberGOT() - 1);
            progressGo();
        } else {
            battlePanel.getReminder().show(19);
        }
    }

    /**
     * 进度继续;
     */
    public void progressGo() {
        switch (battlePanel.getCurrentRound()) {
            case 1:
                battlePanel.getProgressBar().setZhangX(battlePanel.getProgressBar().getBarX());
                break;
            case 2:
                battlePanel.getProgressBar().setYuX(battlePanel.getProgressBar().getBarX());
                break;
            case 3:
                battlePanel.getProgressBar().setLuX(battlePanel.getProgressBar().getBarX());
                break;
            default:
                break;
        }
        battlePanel.setCurrentRound(0);
        drawn = false;
        battlePanel.getProgressBar().setStopped(false);
    }

    /**
     * 确定当前英雄;
     */
    public void checkHero() {
        switch (battlePanel.getCurrentRound()) {
            case 1:
                currentHero = battlePanel.getZxf();
                break;
            case 2:
                currentHero = battlePanel.getYj();
                break;
            case 3:
                currentHero = battlePanel.getLxq();
                break;
        }
    }

    /**
     * 画出菜单;
     * @param g
     */
    public void drawDrugMenu(Graphics g) {
        if (drawn) {
            g.drawImage(backImage, x, y, battlePanel);
            for (GameButton button : drugButtons) {
                button.drawButton(g);
            }
            //画出剩余的药品数量
            for (int i = 0; i <= 5; i++) {
                g.drawString(DrugPack.drugList.get(i).getNumberGOT() + "", 575, 246 + i * 30);
            }

            if (introDrawn) {
                g.drawImage(introduceImage, introX, introY, battlePanel);
                g.drawString(introString, introX + 10, introY + 20);
            }
        }
    }

    public Image getBackImage() {
        return backImage;
    }

    public void setBackImage(Image backImage) {
        this.backImage = backImage;
    }

    public GameButton getDrugButton() {
        return drugButton;
    }

    public void setDrugButton(GameButton drugButton) {
        this.drugButton = drugButton;
    }

    public List<GameButton> getDrugButtons() {
        return drugButtons;
    }

    public void setDrugButtons(List<GameButton> drugButtons) {
        this.drugButtons = drugButtons;
    }

    public List<Image> getButtonImages() {
        return buttonImages;
    }

    public void setButtonImages(List<Image> buttonImages) {
        this.buttonImages = buttonImages;
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

    public Image getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(Image introduceImage) {
        this.introduceImage = introduceImage;
    }

    public int getIntroX() {
        return introX;
    }

    public void setIntroX(int introX) {
        this.introX = introX;
    }

    public int getIntroY() {
        return introY;
    }

    public void setIntroY(int introY) {
        this.introY = introY;
    }

    public boolean isIntroDrawn() {
        return introDrawn;
    }

    public void setIntroDrawn(boolean introDrawn) {
        this.introDrawn = introDrawn;
    }

    public String getIntroString() {
        return introString;
    }

    public void setIntroString(String introString) {
        this.introString = introString;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }

    public DrugPack getDrugPack() {
        return drugPack;
    }

    public void setDrugPack(DrugPack drugPack) {
        this.drugPack = drugPack;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
    }
}
