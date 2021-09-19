package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.ArrayList;

import cn.misection.booktowest.util.*;
import cn.misection.booktowest.shop.*;

//使用药品的菜单
public class DrugMenu {
    //背景图
    private Image backImage;

    //按钮
    private GameButton drugButton;
    private ArrayList<GameButton> drugButtons = new ArrayList<GameButton>();
    //按钮图片
    private ArrayList<Image> buttonImages = new ArrayList<Image>();
    //菜单出现的位置
    private int x;
    private int y;
    //是否画出
    private boolean isDraw;

    //介绍性图片
    private Image introduceImage;
    //介绍图位置
    private int introX;
    private int introY;
    //介绍图是否画出
    private boolean isDrawIntro;
    //介绍文字
    private String introString;

    //战斗面板引用
    private BattlePanel bp;

    private DrugPack drugPack;
    //当前英雄
    private Hero currentHero;

    public DrugMenu(BattlePanel bp) {
        this.x = 340;
        this.y = 200;

        this.introX = 220;

        backImage = Reader.readImage("image/药品菜单/药品显示框.png");
        this.bp = bp;
        isDraw = false;

        if (DrugPack.drugList.size() == 0) {
            drugPack = new DrugPack();
        }

        getImage();
        addButton();
    }

    //读入图片
    public void getImage() {
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
                    buttonImages.get(i + 2), bp);
            drugButtons.add(drugButton);
        }
    }

    //检查是否移动鼠标进入菜单
    public void checkMoveIn() {
        for (GameButton button : drugButtons) {
            button.isMoveIn(bp.getCurrentX(), bp.getCurrentY());
        }

        for (int i = 0; i < 6; i++) {
            if (bp.getCurrentX() > 395 && bp.getCurrentX() < 610 && bp.getCurrentY() > 226 + i * 30 && bp.getCurrentY() < 226 + (i + 1) * 30) {
                introduceImage = DrugPack.drugList.get(i).getPicture();
                introString =
                        "hp " + DrugPack.drugList.get(i).getAddHp() + " mp " + DrugPack.drugList.get(i).getAddMp();
                this.introY = 226 + i * 30;
                isDrawIntro = true;
            }
        }
    }


    //检查鼠标是否点击菜单
    public void checkPressed() {
        for (GameButton button : drugButtons) {
            button.isPressedButton(bp.getCurrentX(), bp.getCurrentY());
        }
    }

    //检查是否松开鼠标
    public void checkReleased() {
        //按下金创药
        if (drugButtons.get(0).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(0), 1);
        }

        if (drugButtons.get(1).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(1), 2);
        }

        if (drugButtons.get(2).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(2), 1);
        }

        if (drugButtons.get(3).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(3), 2);
        }

        if (drugButtons.get(4).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(4), 1);
        }

        if (drugButtons.get(5).clicked == true) {
            checkDrugNumber(DrugPack.drugList.get(5), 2);
        }

        //按下返回按钮
        if (drugButtons.get(6).clicked == true) {
            isDraw = false;
            bp.getCommand().setDraw(true);
        }

        for (GameButton button : drugButtons) {
            button.isRelesedButton(bp.getCurrentX(), bp.getCurrentY());
        }
    }

    //检查药品数目
    public void checkDrugNumber(Drug drug, int type) {
        if (drug.getNumberGOT() > 0) {
            bp.getHurtValues().clear();
            HurtValue hurtValue = new HurtValue(bp);
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

            bp.getHurtValues().add(hurtValue);
            //显示伤害值
            for (HurtValue hurt : bp.getHurtValues()) {
                hurt.start();
            }
            drug.setNumberGOT(drug.getNumberGOT() - 1);
            progressGo();
        } else {
            bp.getReminder().show(19);
        }
    }

    //进度继续
    public void progressGo() {
        switch (bp.getCurrentRound()) {
            case 1:
                bp.getProgressBar().setZhangX(bp.getProgressBar().getBarX());
                break;
            case 2:
                bp.getProgressBar().setYuX(bp.getProgressBar().getBarX());
                break;
            case 3:
                bp.getProgressBar().setLuX(bp.getProgressBar().getBarX());
                break;
        }
        bp.setCurrentRound(0);
        isDraw = false;
        bp.getProgressBar().setStop(false);
    }


    //确定当前英雄
    public void checkHero() {
        switch (bp.getCurrentRound()) {
            case 1:
                currentHero = bp.getZxf();
                break;
            case 2:
                currentHero = bp.getYj();
                break;
            case 3:
                currentHero = bp.getLxq();
                break;
        }
    }

    //画出菜单
    public void drawDrugMenu(Graphics g) {
        if (isDraw) {
            g.drawImage(backImage, x, y, bp);
            for (GameButton button : drugButtons) {
                button.drawButton(g);
            }
            //画出剩余的药品数量
            for (int i = 0; i <= 5; i++) {
                g.drawString(DrugPack.drugList.get(i).getNumberGOT() + "", 575, 246 + i * 30);
            }

            if (isDrawIntro) {
                g.drawImage(introduceImage, introX, introY, bp);
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

    public ArrayList<GameButton> getDrugButtons() {
        return drugButtons;
    }

    public void setDrugButtons(ArrayList<GameButton> drugButtons) {
        this.drugButtons = drugButtons;
    }

    public ArrayList<Image> getButtonImages() {
        return buttonImages;
    }

    public void setButtonImages(ArrayList<Image> buttonImages) {
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

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
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

    public boolean isDrawIntro() {
        return isDrawIntro;
    }

    public void setDrawIntro(boolean drawIntro) {
        isDrawIntro = drawIntro;
    }

    public String getIntroString() {
        return introString;
    }

    public void setIntroString(String introString) {
        this.introString = introString;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
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
