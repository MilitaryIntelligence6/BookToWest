package cn.misection.booktowest.menu;

import cn.misection.booktowest.media.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import cn.misection.booktowest.battle.*;
import cn.misection.booktowest.shop.*;
import cn.misection.booktowest.util.*;

public class DrugPanel extends FatherPanel {

    private static final long serialVersionUID = -1886842159260678221L;
    private DrugPack drugPack;
    private Drug currentDrug;
    private Hero hero;
    private MenuButton use_button;


    private int width_button = 120;
    private int height_button = 40;
    private Image druaImage;
    private int x_picture = 820;
    private int y_picture = 178;

    private int x_start_point = 448;
    private int y_start_point = 190;

    private int x_button = x_picture;
    private int y_button = y_picture + 240;
    private String message1 = "";
    private String message2 = "";
    private String message3 = "";
    private int x_message = 510;
    private int y_message = 586;
    //draw value bar

    private String s1;
    private String s2;
    private String s3;
    private String s4;
    private int x_value = 107;
    private int y_value = 320;
    private int signal = 0;//一些标志 此为 物品包是否为空;
    private ArrayList<Drug> list = new ArrayList<Drug>();
    private int hp;
    private int mp;

    public DrugPanel(MenuPanel a, ZhangXiaoFan h1, LuXueQi h2, YuJie h4) {
        super(a, h1, h2, h4);

        this.setName("thingPanel");
        scoll = new Scoll(this);
        drugPack = new DrugPack();

        addButton();
        upDateDrugList();
        hero1.refreshValue();
        hero2.refreshValue();
        hero4.refreshValue();
        hp = hero1.getHp();
        mp = hero1.getMp();


    }

    private void addButton() {
        // TODO Auto-generated method stub
        Image image1 = new ImageIcon("sources/菜单/物品/使用1.png").getImage();
        Image image2 = new ImageIcon("sources/菜单/物品/使用2.png").getImage();
        Image image3 = new ImageIcon("sources/菜单/物品/使用3.png").getImage();
        use_button = new MenuButton(x_button, y_button, width_button,
                height_button, image1, image2, image3, this);
        use_button.isDraw = MenuButton.No;


    }

    @Override
    public void readBackgroundImage() {
        // TODO Auto-generated method stub
        backgroundImage = Reader.readImage("sources/菜单/物品/物品3.png");
    }

    @Override
    public void drawThisPanel(Graphics g) {
        use_button.drawButton(g);
        drawEquipment(g);
        drawValueBar(g);
    }


    private void drawEquipment(Graphics g) {
        // TODO Auto-generated method stub

        // 为画图而给出的坐标
        int x = x_start_point, y = y_start_point;
        g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 26));
        g.setColor(Color.white);


        for (Drug e : DrugPack.drugList) {
            if (e.getNumberGOT() > 0) {
                g.drawString(e.getName(), x, y);
                g.drawString("" + e.getNumberGOT(), x + 180, y);
                y += 32;
            }
        }

        if (currentDrug != null) {

            g.drawImage(currentDrug.getPicture(), x_picture, y_picture, this);
            g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 24));

            //物品说明
            message1 = currentDrug.getName();
            message2 = ": 生命 +" + currentDrug.getAddHp();
            message3 = "魔法 +" + currentDrug.getAddMp();
        } else {
            message1 = "没药了...";
            message2 = "快去药店买点吧~";
            message3 = "";
        }

        g.drawString(message1, x_message, y_message);
        g.drawString(message2, x_message + 105, y_message);
        g.drawString(message3, x_message + 265, y_message);

    }

    private void isMoveIn() {
        int originalY = y_start_point - 32;
        //边框大小;

        upDateDrugList();
        if (list.size() != 0) {

            for (int i = 0; i < list.size(); i++) {
                if (currentX > x_start_point
                        && currentX < x_start_point + 130
                        && currentY > originalY
                        && currentY < (originalY + 32)) {
                    currentDrug = list.get(i);
                    y_button = originalY + 5;
                    use_button.isDraw = MenuButton.Yes;
                }
                originalY += 32;
            }

        } else {
            use_button.isDraw = MenuButton.No;
        }
    }

    private void drawValueBar(Graphics g) {
        // TODO Auto-generated method stub
        hero1.refreshValue();
        hero2.refreshValue();
        hero4.refreshValue();
        g.setColor(Color.blue);
        g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 20));
        switch (scoll.whichHero) {
            case 1:
                hero = hero1;
                break;
            case 2:
                hero = hero2;
                break;
            case 3:
                hero = hero3;
                break;
            case 4:
                hero = hero4;
                break;
            default:
                break;
        }
        s1 = "生命值:" + hero.getHp() + "/" + hero.getHpMax();
        s2 = "魔法值:" + hero.getMp() + "/" + hero.getMpMax();

        g.drawString(s1, x_value, y_value);
        g.drawString(s2, x_value, y_value + 45);


    }

    @Override
    public void drawSpecialImage(Graphics g) {

    }

    @Override
    public void checkAllButtonReleased() {
        // TODO Auto-generated method stub

        scoll.checkReleased();
        use_button.isRelesedButton(currentX, currentY);
    }

    @Override
    public void checkAllButtonMoveIn() {
        // TODO Auto-generated method stub

        scoll.checkMoveIn();
        use_button.isMoveIn(currentX, currentY);
        isMoveIn();
    }

    @Override
    public void checkAllButtonPressed() {
        // TODO Auto-generated method stub

        scoll.checkPressed();
        use_button.isPressedButton(currentX, currentY);

        if (use_button.clicked) {
            if (currentDrug != null) {
                currentDrug.setNumberGOT((currentDrug.getNumberGOT() - 1));
                playMusic();
                addValue();
                if (currentDrug.getNumberGOT() == 0) {
                    currentDrug = null;
                    use_button.isDraw = MenuButton.No;
                }
            }
            upDateDrugList();

        }

    }

    private void playMusic() {
        // TODO Auto-generated method stub
        if (currentDrug.getAddHp() > currentDrug.getAddMp() && currentDrug.getAddHp() < 2100) {
            MusicReader.readMusic("命+.wav");
        }
        if (currentDrug.getAddHp() > currentDrug.getAddMp() && currentDrug.getAddHp() >= 2100) {
            MusicReader.readMusic("命++.wav");
        }
        if (currentDrug.getAddMp() > currentDrug.getAddHp() && currentDrug.getAddMp() < 2000) {
            MusicReader.readMusic("魔+.wav");
        }
        if (currentDrug.getAddMp() > currentDrug.getAddHp() && currentDrug.getAddMp() >= 2000) {
            MusicReader.readMusic("魔++.wav");
        }
    }

    public void upDateDrugList() {
        // TODO Auto-generated method stub
        list.clear();
        for (Drug e : DrugPack.drugList) {
            if (e.getNumberGOT() > 0) {
                list.add(e);
            }
        }

    }

    public void showDrug() {
        if (list.size() > 0) {
            currentDrug = list.get(0);
            use_button.isDraw = 1;
        } else {
            currentDrug = null;
            use_button.isDraw = 0;
        }
    }

    private void addValue() {
        // TODO Auto-generated method stub
        hero1.refreshValue();
        hero2.refreshValue();
        hero4.refreshValue();
        int h;
        int m;
        switch (scoll.whichHero) {
            case 1:
                hero = hero1;
                break;
            case 2:
                hero = hero2;
                break;
            case 4:
                hero = hero4;
                break;
            default:
                break;
        }

        hp = hero.getHp();
        mp = hero.getMp();
        h = hero.getHp() + currentDrug.getAddHp();
        m = hero.getMp() + currentDrug.getAddMp();
        if (h >= hero.getHpMax()) {
            hero.setHp(hero.getHpMax());
        } else {
            hero.setHp(h);
        }
        if (m >= hero.getMpMax()) {
            hero.setMp(hero.getMpMax());
        } else {
            hero.setMp(m);
        }

        repaint();
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<String> saveEquipInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialEquipInfo(ArrayList<String> menuInfo) {
        // TODO Auto-generated method stub

    }

    public DrugPack getDrugPack() {
        return drugPack;
    }

    public void setDrugPack(DrugPack drugPack) {
        this.drugPack = drugPack;
    }

    public Drug getCurrentDrug() {
        return currentDrug;
    }

    public void setCurrentDrug(Drug currentDrug) {
        this.currentDrug = currentDrug;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public MenuButton getUse_button() {
        return use_button;
    }

    public void setUse_button(MenuButton use_button) {
        this.use_button = use_button;
    }

    public int getWidth_button() {
        return width_button;
    }

    public void setWidth_button(int width_button) {
        this.width_button = width_button;
    }

    public int getHeight_button() {
        return height_button;
    }

    public void setHeight_button(int height_button) {
        this.height_button = height_button;
    }

    public Image getDruaImage() {
        return druaImage;
    }

    public void setDruaImage(Image druaImage) {
        this.druaImage = druaImage;
    }

    public int getX_picture() {
        return x_picture;
    }

    public void setX_picture(int x_picture) {
        this.x_picture = x_picture;
    }

    public int getY_picture() {
        return y_picture;
    }

    public void setY_picture(int y_picture) {
        this.y_picture = y_picture;
    }

    public int getX_start_point() {
        return x_start_point;
    }

    public void setX_start_point(int x_start_point) {
        this.x_start_point = x_start_point;
    }

    public int getY_start_point() {
        return y_start_point;
    }

    public void setY_start_point(int y_start_point) {
        this.y_start_point = y_start_point;
    }

    public int getX_button() {
        return x_button;
    }

    public void setX_button(int x_button) {
        this.x_button = x_button;
    }

    public int getY_button() {
        return y_button;
    }

    public void setY_button(int y_button) {
        this.y_button = y_button;
    }

    public String getMessage1() {
        return message1;
    }

    public void setMessage1(String message1) {
        this.message1 = message1;
    }

    public String getMessage2() {
        return message2;
    }

    public void setMessage2(String message2) {
        this.message2 = message2;
    }

    public String getMessage3() {
        return message3;
    }

    public void setMessage3(String message3) {
        this.message3 = message3;
    }

    public int getX_message() {
        return x_message;
    }

    public void setX_message(int x_message) {
        this.x_message = x_message;
    }

    public int getY_message() {
        return y_message;
    }

    public void setY_message(int y_message) {
        this.y_message = y_message;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }

    public int getX_value() {
        return x_value;
    }

    public void setX_value(int x_value) {
        this.x_value = x_value;
    }

    public int getY_value() {
        return y_value;
    }

    public void setY_value(int y_value) {
        this.y_value = y_value;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public ArrayList<Drug> getList() {
        return list;
    }

    public void setList(ArrayList<Drug> list) {
        this.list = list;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }
}
