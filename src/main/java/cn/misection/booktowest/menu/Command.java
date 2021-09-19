package cn.misection.booktowest.menu;

import cn.misection.booktowest.media.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import cn.misection.booktowest.util.*;

import javax.swing.ImageIcon;


/**
 * @author Administrator
 */

/**
 * @author Administrator
 */
public class Command {
    /**
     * 各种panel的引用;
     */
    private FatherPanel fatherPanel;

    /**
     * 各种GameButton;
     */
    private int widthOfGameButton = 52;

    private int heightOfGameButton = 37;

    private int xOfGameButton = 400;

    private int yOfGameButton = 50;

    private MenuButton thingButton;

    private MenuButton equipButton;

    private MenuButton magicButton;

    private MenuButton funcButton;

    private List<MenuButton> buttonList = new ArrayList<MenuButton>();

    private MenuPanel menupanel;

    public Command(MenuPanel menuPanel) {
        this.menupanel = menuPanel;
        addGameButton();
    }

    public Command(FatherPanel a) {
        this.fatherPanel = a;
        addGameButton();
    }

    private void addGameButton() {
        // TODO Auto-generated method stub

        Image image1 = new ImageIcon("sources/菜单/菜单/标题物品1.png").getImage();
        Image image2 = new ImageIcon("sources/菜单/菜单/标题物品2.png").getImage();
        Image image3 = new ImageIcon("sources/菜单/菜单/标题物品3.png").getImage();


        thingButton = new MenuButton(xOfGameButton, yOfGameButton, widthOfGameButton, heightOfGameButton,
                image1, image2, image3, menupanel);
        buttonList.add(thingButton);

        image1 = new ImageIcon("sources/菜单/菜单/标题装备1.png").getImage();
        image2 = new ImageIcon("sources/菜单/菜单/标题装备2.png").getImage();
        image3 = new ImageIcon("sources/菜单/菜单/标题装备3.png").getImage();


        equipButton = new MenuButton(xOfGameButton + 2 * widthOfGameButton, yOfGameButton, widthOfGameButton,
                heightOfGameButton, image1, image2, image3, menupanel);
        buttonList.add(equipButton);

        image1 = new ImageIcon("sources/菜单/菜单/标题奇术1.png").getImage();
        image2 = new ImageIcon("sources/菜单/菜单/标题奇术2.png").getImage();
        image3 = new ImageIcon("sources/菜单/菜单/标题奇术3.png").getImage();


        magicButton = new MenuButton(xOfGameButton + 4 * widthOfGameButton, yOfGameButton, widthOfGameButton,
                heightOfGameButton, image1, image2, image3, menupanel);
        buttonList.add(magicButton);

        image1 = new ImageIcon("sources/菜单/菜单/标题天书1.png").getImage();
        image2 = new ImageIcon("sources/菜单/菜单/标题天书2.png").getImage();
        image3 = new ImageIcon("sources/菜单/菜单/标题天书3.png").getImage();


        funcButton = new MenuButton(xOfGameButton + 6 * widthOfGameButton, yOfGameButton, widthOfGameButton,
                heightOfGameButton, image1, image2, image3, menupanel);
        buttonList.add(funcButton);
    }

    /**
     * 检查是否移动鼠标进入控制台;
     */
    public void checkMoveIn() {
        for (MenuButton button : buttonList) {
            button.isMoveIn(menupanel.currentX, menupanel.currentY);
        }
    }

    /**
     * 检查鼠标是否点击控制台;
     */
    public void checkPressed() {
        for (MenuButton button : buttonList) {
            button.isPressedButton(menupanel.currentX, menupanel.currentY);
        }

        if (thingButton.isClicked()) {
            MusicReader.readMusic("换list.wav");
            menupanel.switcher.show(menupanel, menupanel.thingPanel.getName());
            menupanel.currentPanel = menupanel.thingPanel;
        } else if (magicButton.isClicked()) {

            MusicReader.readMusic("换list.wav");
            menupanel.switcher.show(menupanel, menupanel.magicPanel.getName());
            menupanel.currentPanel = menupanel.magicPanel;
        } else if (funcButton.isClicked()) {

            MusicReader.readMusic("换list.wav");
            menupanel.switcher.show(menupanel, menupanel.funcPanel.getName());
            menupanel.currentPanel = menupanel.funcPanel;
        } else if (equipButton.isClicked()) {

            MusicReader.readMusic("换list.wav");
            menupanel.switcher.show(menupanel, menupanel.equipPanel.getName());
            menupanel.currentPanel = menupanel.equipPanel;
        }
    }

    /**
     * 检查是否松开鼠标;
     */
    public void checkReleased() {
        //检验 击 按钮是否被按下
        for (MenuButton button : buttonList) {
            button.isRelesedButton(menupanel.currentX, menupanel.currentY);
        }
    }

    /**
     * 画出控制台;
     * @param g
     */
    public void drawCommand(Graphics g) {
        String s = "";
        if (Reader.task != null) {
            s = Reader.task;
        } else {
            s = "无";
        }
        Image image = new ImageIcon("sources/菜单/菜单/标题栏.png").getImage();
        g.drawImage(image, 0, yOfGameButton, menupanel);
        g.setColor(Color.white);
        g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 25));
        g.drawString("当前任务:" + s, 10, yOfGameButton - 13);
        for (MenuButton button : buttonList) {
            button.drawButton(g);
        }
    }

    public List<MenuButton> getButtonList() {
        return buttonList;
    }
}
