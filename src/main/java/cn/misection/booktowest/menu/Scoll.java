package cn.misection.booktowest.menu;

import cn.misection.booktowest.media.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import cn.misection.booktowest.scene.*;

import javax.swing.ImageIcon;

import cn.misection.booktowest.battle.*;

import cn.misection.booktowest.util.*;

public class Scoll {
    //各种引用
    private FatherPanel fp;


    private int x_scoll = 60 + 32;
    private int y_scoll = 70;
    private Image scollImage;

    private MenuButton hero1;
    private MenuButton hero2;

    private MenuButton hero4;
    private int x_head = x_scoll + 14;
    private int y_head = y_scoll + 20;
    private int width_head = 40;
    private int height_head = 40;
    private int hgap = 10;

    private Image currentHeroImage;
    private int whichHero = 1;
    private static int zhangxiaofan = 1;
    private static int luxueqi = 2;
    private static int songdaren = 3;
    private static int yujie = 4;

    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private int x_hero = x_scoll + 38 + 50;
    private int y_hero = y_scoll + 41;
    private int width_hero = 176;
    private int height_hero = 131;

    private ArrayList<GameButton> buttonList = new ArrayList<GameButton>();


    // 显示当前的级别 经验等数据

    private String level = "" + ZhangXiaoFan.level;

    private int x_level = x_head + 90;
    private int y_level = y_scoll + 70;
    private Image level_ima;

    private int x_num = x_level + 30;
    private int y_num = y_level + 62;

    public Scoll(FatherPanel a) {
        fp = a;
        addImage();
        initial();
    }

    public static int getZhangxiaofan() {
        return zhangxiaofan;
    }

    public static void setZhangxiaofan(int zhangxiaofan) {
        Scoll.zhangxiaofan = zhangxiaofan;
    }

    public static int getLuxueqi() {
        return luxueqi;
    }

    public static void setLuxueqi(int luxueqi) {
        Scoll.luxueqi = luxueqi;
    }

    public static int getSongdaren() {
        return songdaren;
    }

    public static void setSongdaren(int songdaren) {
        Scoll.songdaren = songdaren;
    }

    public static int getYujie() {
        return yujie;
    }

    public static void setYujie(int yujie) {
        Scoll.yujie = yujie;
    }

    private void addImage() {
        level_ima = new ImageIcon("sources/菜单/scoll/等级.png").getImage();
    }

    private void initial() {

        image1 = new ImageIcon("sources/菜单/scoll/hero1.png").getImage();
        image2 = new ImageIcon("sources/菜单/scoll/hero12.png").getImage();
        image3 = new ImageIcon("sources/菜单/scoll/hero13.png").getImage();


        hero1 = new MenuButton(x_head, y_head, width_head, height_head, image1, image2, image3, fp);
        hero1.setIsDraw(MenuButton.getYes());
        buttonList.add(hero1);

        image1 = new ImageIcon("sources/菜单/scoll/hero2.png").getImage();
        image2 = new ImageIcon("sources/菜单/scoll/hero22.png").getImage();
        image3 = new ImageIcon("sources/菜单/scoll/hero23.png").getImage();
        hero2 = new MenuButton(x_head + width_head + hgap, y_head + 6, width_head, height_head, image1, image2,
                image3, fp);
        hero2.setIsDraw(MenuButton.getNo());
        buttonList.add(hero2);

        image1 = new ImageIcon("sources/菜单/scoll/hero4.png").getImage();
        image2 = new ImageIcon("sources/菜单/scoll/hero42.png").getImage();
        image3 = new ImageIcon("sources/菜单/scoll/hero43.png").getImage();
        hero4 = new MenuButton(x_head + 2 * (width_head + hgap), y_head, width_head, height_head, image1, image2,
                image3, fp);
        hero4.setIsDraw(MenuButton.getNo());
        buttonList.add(hero4);


        image1 = new ImageIcon("sources/菜单/scoll/卷轴1.png").getImage();
        image2 = new ImageIcon("sources/菜单/scoll/卷轴2.png").getImage();
        image3 = new ImageIcon("sources/菜单/scoll/卷轴3.png").getImage();
        image4 = new ImageIcon("sources/菜单/scoll/卷轴4.png").getImage();
        scollImage = image1;
        level = "" + ZhangXiaoFan.level;
    }

    //检查是否移动鼠标进入
    public void checkMoveIn() {

        if (SaveAndLoad.lu) {
            hero2.setIsDraw(MenuButton.getYes());
        }
        if (SaveAndLoad.wen) {
            hero4.setIsDraw(MenuButton.getYes());
        }
        for (GameButton button : buttonList) {
            button.isMoveIn(fp.getCurrentX(), fp.getCurrentY());
        }
    }

    //检查鼠标是否点击
    public void checkPressed() {
        for (GameButton button : buttonList) {
            button.isPressedButton(fp.getCurrentX(), fp.getCurrentY());
        }

        if (hero1.isClicked()) {
            scollImage = image1;
            whichHero = zhangxiaofan;
            level = "" + ZhangXiaoFan.level;

            MusicReader.readMusic("换头像.wav");
        }

        if (SaveAndLoad.lu) {

            if (hero2.isClicked()) {
                scollImage = image2;
                whichHero = luxueqi;
                level = "" + LuXueQi.getLevel();

                MusicReader.readMusic("换头像.wav");
            }
        }


        if (SaveAndLoad.wen) {

            if (hero4.isClicked()) {
                scollImage = image4;
                whichHero = yujie;
                level = "" + YuJie.level;
                MusicReader.readMusic("换头像.wav");
            }
        }

    }

    //检查是否松开鼠标
    public void checkReleased() {
        //检验 击 按钮是否被按下
        for (GameButton button : buttonList) {
            button.isRelesedButton(fp.getCurrentX(), fp.getCurrentY());
        }
    }

    //画出控制台
    public void drawScoll(Graphics g) {
        if (SaveAndLoad.zhang) {
            hero1.setIsDraw(MenuButton.getYes());
            level = "" + ZhangXiaoFan.level;
        }
        if (SaveAndLoad.lu) {
            hero2.setIsDraw(MenuButton.getYes());
        }
        if (SaveAndLoad.wen) {
            hero4.setIsDraw(MenuButton.getYes());
        }


        g.drawImage(scollImage, x_scoll, y_scoll, fp);
        g.drawImage(level_ima, x_level, y_level, fp);
        g.setColor(Color.red);
        g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 30));
        g.drawString(level, x_num, y_num);

        for (GameButton button : buttonList) {
            button.drawButton(g);
        }


    }

    public FatherPanel getFp() {
        return fp;
    }

    public void setFp(FatherPanel fp) {
        this.fp = fp;
    }

    public int getX_scoll() {
        return x_scoll;
    }

    public void setX_scoll(int x_scoll) {
        this.x_scoll = x_scoll;
    }

    public int getY_scoll() {
        return y_scoll;
    }

    public void setY_scoll(int y_scoll) {
        this.y_scoll = y_scoll;
    }

    public Image getScollImage() {
        return scollImage;
    }

    public void setScollImage(Image scollImage) {
        this.scollImage = scollImage;
    }

    public MenuButton getHero1() {
        return hero1;
    }

    public void setHero1(MenuButton hero1) {
        this.hero1 = hero1;
    }

    public MenuButton getHero2() {
        return hero2;
    }

    public void setHero2(MenuButton hero2) {
        this.hero2 = hero2;
    }

    public MenuButton getHero4() {
        return hero4;
    }

    public void setHero4(MenuButton hero4) {
        this.hero4 = hero4;
    }

    public int getX_head() {
        return x_head;
    }

    public void setX_head(int x_head) {
        this.x_head = x_head;
    }

    public int getY_head() {
        return y_head;
    }

    public void setY_head(int y_head) {
        this.y_head = y_head;
    }

    public int getWidth_head() {
        return width_head;
    }

    public void setWidth_head(int width_head) {
        this.width_head = width_head;
    }

    public int getHeight_head() {
        return height_head;
    }

    public void setHeight_head(int height_head) {
        this.height_head = height_head;
    }

    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public Image getCurrentHeroImage() {
        return currentHeroImage;
    }

    public void setCurrentHeroImage(Image currentHeroImage) {
        this.currentHeroImage = currentHeroImage;
    }

    public int getWhichHero() {
        return whichHero;
    }

    public void setWhichHero(int whichHero) {
        this.whichHero = whichHero;
    }

    public Image getImage1() {
        return image1;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public Image getImage2() {
        return image2;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }

    public Image getImage3() {
        return image3;
    }

    public void setImage3(Image image3) {
        this.image3 = image3;
    }

    public Image getImage4() {
        return image4;
    }

    public void setImage4(Image image4) {
        this.image4 = image4;
    }

    public int getX_hero() {
        return x_hero;
    }

    public void setX_hero(int x_hero) {
        this.x_hero = x_hero;
    }

    public int getY_hero() {
        return y_hero;
    }

    public void setY_hero(int y_hero) {
        this.y_hero = y_hero;
    }

    public int getWidth_hero() {
        return width_hero;
    }

    public void setWidth_hero(int width_hero) {
        this.width_hero = width_hero;
    }

    public int getHeight_hero() {
        return height_hero;
    }

    public void setHeight_hero(int height_hero) {
        this.height_hero = height_hero;
    }

    public ArrayList<GameButton> getButtonList() {
        return buttonList;
    }

    public void setButtonList(ArrayList<GameButton> buttonList) {
        this.buttonList = buttonList;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getX_level() {
        return x_level;
    }

    public void setX_level(int x_level) {
        this.x_level = x_level;
    }

    public int getY_level() {
        return y_level;
    }

    public void setY_level(int y_level) {
        this.y_level = y_level;
    }

    public Image getLevel_ima() {
        return level_ima;
    }

    public void setLevel_ima(Image level_ima) {
        this.level_ima = level_ima;
    }

    public int getX_num() {
        return x_num;
    }

    public void setX_num(int x_num) {
        this.x_num = x_num;
    }

    public int getY_num() {
        return y_num;
    }

    public void setY_num(int y_num) {
        this.y_num = y_num;
    }
}
