package cn.misection.booktowest.menu;

import cn.misection.booktowest.media.*;
import cn.misection.booktowest.app.*;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import cn.misection.booktowest.start.LoadAndSavePanel;

public class FuncButtons {
    private FatherPanel fatherPanel;
    //for saveButton
    private MenuButton saveButton;

    //for readButton
    private MenuButton readButton;

    //for setButton
    private MenuButton setButton;
    private MenuButton setBGM;
    private MenuButton setClick;
    private MenuButton setKey;

    private MenuButton on_BGM;
    private MenuButton off_BGM;
    private MenuButton on_click;
    private MenuButton off_click;

    //for returnButton
    private MenuButton returnButton;

    //for exitButon
    private MenuButton exitButton;
    private MenuButton exitForSure;
    private MenuButton restart;

    private MenuButton[][] subButtonList = new MenuButton[5][];
    private MenuButton[] buttonList = new MenuButton[5];


    public FuncButtons(FatherPanel a) {
        fatherPanel = a;
        addButton();
    }

    private void addButton() {
        // TODO Auto-generated method stub

        int x_GameButton = 400;
        int y_GameButton = 150;
        int width_GameButton = 88;
        int height_GameButton = 50;
        int y_move = 10;//偏移量
        int x_move = 0;


        Image image1 = new ImageIcon("sources/菜单/天书/存档1.png").getImage();
        Image image2 = new ImageIcon("sources/菜单/天书/存档2.png").getImage();
        Image image3 = new ImageIcon("sources/菜单/天书/存档3.png").getImage();

        saveButton = new MenuButton(x_GameButton, y_GameButton, width_GameButton, height_GameButton, image1, image2,
                image3, fatherPanel.getMenuPanel());

        image1 = new ImageIcon("sources/菜单/天书/提取1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/提取2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/提取3.png").getImage();

        readButton = new MenuButton(x_GameButton + 1 * width_GameButton + x_move, y_GameButton, width_GameButton,
                height_GameButton, image1, image2, image3, fatherPanel.getMenuPanel());

        image1 = new ImageIcon("sources/菜单/天书/设定1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/设定2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/设定3.png").getImage();
        setButton = new MenuButton(x_GameButton + 2 * width_GameButton + 2 * x_move, y_GameButton, width_GameButton,
                height_GameButton, image1, image2, image3, fatherPanel.getMenuPanel());


        image1 = new ImageIcon("sources/菜单/天书/返回1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/返回2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/返回3.png").getImage();
        returnButton = new MenuButton(x_GameButton + 3 * width_GameButton + 2 * x_move, y_GameButton,
                width_GameButton, height_GameButton, image1, image2, image3, fatherPanel.getMenuPanel());

        image1 = new ImageIcon("sources/菜单/天书/退出1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/退出2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/退出3.png").getImage();
        exitButton = new MenuButton(x_GameButton + 4 * width_GameButton + 3 * x_move, y_GameButton, width_GameButton,
                height_GameButton, image1, image2, image3, fatherPanel.getMenuPanel());

        buttonList[0] = saveButton;
        buttonList[1] = readButton;
        buttonList[2] = setButton;
        buttonList[3] = returnButton;
        buttonList[4] = exitButton;


        int y_MenuButton = 230;
        int width_MenuButton = 145;
        int height_MenuButton = 40;

        int width_on = 40;
        int height_on = 40;


        image1 = new ImageIcon("sources/菜单/天书/背景音乐1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/背景音乐2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/背景音乐3.png").getImage();
        setBGM = new MenuButton(x_GameButton + 2 * width_GameButton + 2 * x_move, y_MenuButton, width_MenuButton,
                height_MenuButton, image1, image2, image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/特殊音效1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/特殊音效2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/特殊音效3.png").getImage();
        setClick = new MenuButton(x_GameButton + 2 * width_GameButton + 2 * x_move,
                y_MenuButton + 1 * (y_move + height_MenuButton), width_MenuButton, height_MenuButton, image1, image2,
                image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/键盘设定1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/键盘设定2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/键盘设定3.png").getImage();
        setKey = new MenuButton(x_GameButton + 2 * width_GameButton + 2 * x_move,
                y_MenuButton + 2 * (y_move + height_MenuButton), width_MenuButton, height_MenuButton, image1, image2,
                image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/开1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/开2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/开3.png").getImage();
        on_BGM = new MenuButton(x_GameButton + 4 * width_GameButton + 2 * x_move, y_MenuButton - y_move, width_on,
                height_on, image1, image2, image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/关1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/关2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/关3.png").getImage();
        off_BGM = new MenuButton(x_GameButton + 4 * width_GameButton + 2 * x_move, y_MenuButton + 2 * y_move + 10,
                width_on, height_on, image1, image2, image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/开1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/开2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/开3.png").getImage();
        on_click = new MenuButton(x_GameButton + 4 * width_GameButton + 3 * x_move,
                y_MenuButton - y_move + height_MenuButton, width_on, height_on, image1, image2, image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/关1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/关2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/关3.png").getImage();
        off_click = new MenuButton(x_GameButton + 4 * width_GameButton + 3 * x_move,
                y_MenuButton + 2 * y_move + 10 + height_MenuButton, width_on, height_on, image1, image2, image3, fatherPanel);


        image1 = new ImageIcon("sources/菜单/天书/确认离开1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/确认离开2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/确认离开3.png").getImage();
        exitForSure = new MenuButton(x_GameButton + 4 * width_GameButton + 4 * x_move, y_MenuButton, width_MenuButton
                , height_MenuButton, image1, image2, image3, fatherPanel);

        image1 = new ImageIcon("sources/菜单/天书/重新开始1.png").getImage();
        image2 = new ImageIcon("sources/菜单/天书/重新开始2.png").getImage();
        image3 = new ImageIcon("sources/菜单/天书/重新开始3.png").getImage();
        restart = new MenuButton(x_GameButton + 4 * width_GameButton + 4 * x_move,
                y_MenuButton + 1 * (y_move + height_MenuButton), width_MenuButton, height_MenuButton, image1, image2,
                image3, fatherPanel);


        subButtonList[1] = new MenuButton[2];//for set
        subButtonList[2] = new MenuButton[2];//for BGM
        subButtonList[3] = new MenuButton[2];//for Click
        subButtonList[4] = new MenuButton[2];//for exit


        subButtonList[1][0] = setBGM;
        subButtonList[1][1] = setClick;

        subButtonList[2][0] = on_BGM;
        subButtonList[2][1] = off_BGM;

        subButtonList[3][0] = on_click;
        subButtonList[3][1] = off_click;

        subButtonList[4][0] = exitForSure;
        subButtonList[4][1] = restart;
        for (int i = 1; i < 5; i++) {
            for (MenuButton b : subButtonList[i]) {
                b.setIsDraw(MenuButton.getNo());
            }

        }

    }


    /**
     *
     */
    public void checkPressed() {

        for (MenuButton button : this.fatherPanel.getMenuPanel().getCommand().getButtonList()) {
            if (button.isClicked()) {
                for (int i = 1; i < 5; i++) {
                    for (MenuButton b : subButtonList[i]) {
                        b.setIsDraw(MenuButton.getNo());
                    }

                }
            }
        }

        for (MenuButton button : buttonList) {
            button.isPressedButton(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
        }
        if (saveButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }

            }

            GameApplication.lsPanel.setLastPanel("menu");
            GameApplication.lsPanel.changeStateTo(LoadAndSavePanel.isSAVE());
            GameApplication.switchTo("ls");

        } else if (readButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }

            }
            GameApplication.lsPanel.setLastPanel("menu");
            GameApplication.lsPanel.changeStateTo(LoadAndSavePanel.isLOAD());
            GameApplication.switchTo("ls");
        } else if (setButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
            }

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }

        } else if (returnButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
                GameApplication.switchTo("scene");

            }

        } else if (exitButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getYes());
            }
        }


        //////子button
        for (int i = 1; i < 5; i++) {
            for (MenuButton button : subButtonList[i]) {
                button.isPressedButton(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
            }
        }


        //3
        if (setBGM.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
            }
            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getYes());
            }
        }
        //4
        if (setClick.isClicked()) {
            MusicReader.readMusic("换list.wav");

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getNo());
            }
        }


        //5
        if (setKey.isClicked()) {
            MusicReader.readMusic("换list.wav");
            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
            }
            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            //重新设置键盘

        }
        //6
        if (on_BGM.isClicked()) {
            MusicReader.readMusic("换list.wav");

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getNo());
            }
            //开背景音乐
            MusicReader.openBgm();


        }
        //7
        if (off_BGM.isClicked()) {
            MusicReader.readMusic("换list.wav");

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getNo());
            }
            //关背景音乐

            MusicReader.closeBgm();

        }
        //8
        if (on_click.isClicked()) {

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getNo());
            }

            //开音乐
            MusicReader.openMusic();
            MusicReader.readMusic("换list.wav");
        }
        //9
        if (off_click.isClicked()) {

            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getYes());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getNo());
            }
            //关音乐
            MusicReader.closeMusic();

        }

        //10
        if (restart.isClicked()) {
            MusicReader.readMusic("换list.wav");


            for (MenuButton button : subButtonList[1]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[2]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[3]) {
                button.setIsDraw(MenuButton.getNo());
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getYes());
            }
            //重新开始

            GameApplication.switchTo("start");
        }
        //11
        if (exitForSure.isClicked()) {
            MusicReader.readMusic("换list.wav");

            for (int i = 1; i < 5; i++) {
                for (MenuButton b : subButtonList[i]) {
                    b.setIsDraw(MenuButton.getNo());
                }
            }
            for (MenuButton button : subButtonList[4]) {
                button.setIsDraw(MenuButton.getYes());
            }
            System.exit(0);
        }
    }


    //检查是否松开鼠标
    public void checkReleased() {
        //检验 击 按钮是否被按下
        for (MenuButton button : buttonList) {
            button.isRelesedButton(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
        }
        for (int i = 1; i < 5; i++) {
            for (MenuButton button : subButtonList[i]) {
                button.isRelesedButton(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
            }
        }
    }

    public void checkMoveIn() {
        for (MenuButton button : buttonList) {
            button.isMoveIn(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
        }
        for (int i = 1; i < 5; i++) {
            for (MenuButton button : subButtonList[i]) {
                button.isMoveIn(fatherPanel.getCurrentX(), fatherPanel.getCurrentY());
            }
        }
    }

    public void drawFuncButtons(Graphics g) {
        for (MenuButton button : buttonList) {
            button.drawButton(g);
        }
        for (int i = 1; i < 5; i++) {
            for (MenuButton button : subButtonList[i]) {
                button.drawButton(g);
            }
        }
    }

    public FatherPanel getFatherPanel() {
        return fatherPanel;
    }

    public void setFatherPanel(FatherPanel fatherPanel) {
        this.fatherPanel = fatherPanel;
    }

    public MenuButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(MenuButton saveButton) {
        this.saveButton = saveButton;
    }

    public MenuButton getReadButton() {
        return readButton;
    }

    public void setReadButton(MenuButton readButton) {
        this.readButton = readButton;
    }

    public MenuButton getSetButton() {
        return setButton;
    }

    public void setSetButton(MenuButton setButton) {
        this.setButton = setButton;
    }

    public MenuButton getSetBGM() {
        return setBGM;
    }

    public void setSetBGM(MenuButton setBGM) {
        this.setBGM = setBGM;
    }

    public MenuButton getSetClick() {
        return setClick;
    }

    public void setSetClick(MenuButton setClick) {
        this.setClick = setClick;
    }

    public MenuButton getSetKey() {
        return setKey;
    }

    public void setSetKey(MenuButton setKey) {
        this.setKey = setKey;
    }

    public MenuButton getOn_BGM() {
        return on_BGM;
    }

    public void setOn_BGM(MenuButton on_BGM) {
        this.on_BGM = on_BGM;
    }

    public MenuButton getOff_BGM() {
        return off_BGM;
    }

    public void setOff_BGM(MenuButton off_BGM) {
        this.off_BGM = off_BGM;
    }

    public MenuButton getOn_click() {
        return on_click;
    }

    public void setOn_click(MenuButton on_click) {
        this.on_click = on_click;
    }

    public MenuButton getOff_click() {
        return off_click;
    }

    public void setOff_click(MenuButton off_click) {
        this.off_click = off_click;
    }

    public MenuButton getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(MenuButton returnButton) {
        this.returnButton = returnButton;
    }

    public MenuButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(MenuButton exitButton) {
        this.exitButton = exitButton;
    }

    public MenuButton getExitForSure() {
        return exitForSure;
    }

    public void setExitForSure(MenuButton exitForSure) {
        this.exitForSure = exitForSure;
    }

    public MenuButton getRestart() {
        return restart;
    }

    public void setRestart(MenuButton restart) {
        this.restart = restart;
    }

    public MenuButton[][] getSubButtonList() {
        return subButtonList;
    }

    public void setSubButtonList(MenuButton[][] subButtonList) {
        this.subButtonList = subButtonList;
    }

    public MenuButton[] getButtonList() {
        return buttonList;
    }

    public void setButtonList(MenuButton[] buttonList) {
        this.buttonList = buttonList;
    }
}
