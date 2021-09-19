package cn.misection.booktowest.menu;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import cn.misection.booktowest.util.*;

public class MagicAnimation {
    private FatherPanel fp;
    //图片集合
    private Image image;
    private int hero;
    private String skillName = "";
    private int skillNumber;

    private int length = 0;//动画长度
    //动画出现的坐标
    private int AnimationX;
    private int AnimationY;
    private int code = 1;
    private String[] magicDiscription;
    private int x_discription;
    private int y_discription;

    public MagicAnimation(int hero, int skillNumber, int length, int x, int y, String[] discription, int a, int b,
                          FatherPanel f) {
        fp = f;
        this.hero = hero;
        this.skillNumber = skillNumber;
        this.length = length;
        this.length = length;
        this.AnimationX = x;
        this.AnimationY = y;
        this.magicDiscription = discription;
        importImage();
        x_discription = a;
        y_discription = b;
    }

    private void importImage() {
        // TODO Auto-generated method stub
        skillName += "image/技能动画/";
        switch (this.hero) {
            case 1:
                skillName += "张小凡技能";
                break;
            case 2:
                skillName += "陆雪琪技能";
                break;
            case 4:
                skillName += "文敏技能";
                break;
            default:
                break;

        }
        switch (this.skillNumber) {
            case 1:
                skillName += "1";
                break;
            case 2:
                skillName += "2";
                break;
            case 3:
                skillName += "3";
                break;
            case 4:
                skillName += "4";
                break;
            case 5:
                skillName += "5";
                break;
            default:
                break;
        }
        image = Reader.readImage(skillName + "/" + code + ".png");
    }

    public int getAnimationX() {
        return AnimationX;
    }

    public void setAnimationX(int animationX) {
        AnimationX = animationX;
    }

    public int getAnimationY() {
        return AnimationY;
    }

    public void setAnimationY(int animationY) {
        AnimationY = animationY;
    }

    public void drawMagicAnimation(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 27));
        g.drawString(magicDiscription[0], x_discription, y_discription);
        g.drawString(magicDiscription[1], x_discription, y_discription + 34);
        g.drawImage(image, AnimationX, AnimationY, fp);
    }

    public void update() {
        if (code < length) {
            code++;
            image = Reader.readImage(skillName + "/" + code + ".png");
        }
    }

    public FatherPanel getFp() {
        return fp;
    }

    public void setFp(FatherPanel fp) {
        this.fp = fp;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getHero() {
        return hero;
    }

    public void setHero(int hero) {
        this.hero = hero;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public int getSkillNumber() {
        return skillNumber;
    }

    public void setSkillNumber(int skillNumber) {
        this.skillNumber = skillNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String[] getMagicDiscription() {
        return magicDiscription;
    }

    public void setMagicDiscription(String[] magicDiscription) {
        this.magicDiscription = magicDiscription;
    }

    public int getX_discription() {
        return x_discription;
    }

    public void setX_discription(int x_discription) {
        this.x_discription = x_discription;
    }

    public int getY_discription() {
        return y_discription;
    }

    public void setY_discription(int y_discription) {
        this.y_discription = y_discription;
    }
}
