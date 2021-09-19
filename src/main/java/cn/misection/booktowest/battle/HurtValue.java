package cn.misection.booktowest.battle;

import java.util.*;

import cn.misection.booktowest.util.*;

import java.awt.*;

//用于显示伤害值的类
public class HurtValue {
    //图片引用
    private Image image;
    //图片集合
    private ArrayList<Image> images = new ArrayList<Image>();
    //当前显示的图片集合
    private ArrayList<Image> currentImages = new ArrayList<Image>();
    //出现位置
    private int x;
    private int y;
    //伤害值
    private int hurt;
    //类型 1.伤害 2.回复
    private int type;
    //个位
    private int unit;
    //十位
    private int ten;
    //百位
    private int hundred;
    //千位
    private int thousand;
    //编号
    private int code;

    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;

    //战斗面板引用
    private BattlePanel bp;

    //构造函数
    public HurtValue(BattlePanel bp) {
        this.bp = bp;

        code = 1;
        isDraw = false;
        isStop = true;

        loadImage();
    }

    //读入图片
    public void loadImage() {
        for (int i = 0; i <= 9; i++) {
            image = Reader.readImage("image/伤害值数字/伤害/" + i + ".png");
            images.add(image);
        }
        for (int i = 0; i <= 9; i++) {
            image = Reader.readImage("image/伤害值数字/回复/" + i + ".png");
            images.add(image);
        }
    }

    //计算各位数字
    public void calcalate() {
        unit = hurt % 10;
        ten = (int) ((hurt % 100) / 10);
        hundred = (int) ((hurt % 1000) / 100);
        thousand = (int) (hurt / 1000);
    }

    //根据数字获得当前图片集合
    public void requireCurrentImages() {
        //用于判断首数字的信号
        boolean firstNum;
        switch (type) {
            //伤害值
            case 1:
                //千位
                switchNum(thousand, 0);
                if (thousand == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }
                //百位
                switchNum(hundred, 0);
                if (firstNum == false && hundred == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }

                //十位
                switchNum(ten, 0);
                if (firstNum == false && ten == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }

                //个位
                switchNum(unit, 0);
                currentImages.add(image);
                break;

            //回复值
            case 2:
                //千位
                switchNum(thousand, 10);
                if (thousand == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }
                //百位
                switchNum(hundred, 10);
                if (firstNum == false && hundred == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }

                //十位
                switchNum(ten, 10);
                if (firstNum == false && ten == 0) {
                    firstNum = false;
                } else {
                    firstNum = true;
                    currentImages.add(image);
                }

                //个位
                switchNum(unit, 10);
                currentImages.add(image);
                break;

        }
    }

    //画出
    public void drawHurtValue(Graphics g) {
        if (isDraw) {
            for (int i = 0; i < currentImages.size(); i++) {
                g.drawImage(currentImages.get(i), x + 16 * i, y, bp);
            }
        }
    }

    //重载版本的显示伤害值
    public void show(int hurt, int type, int x, int y) {
        this.hurt = hurt;
        this.type = type;
        calcalate();
        requireCurrentImages();
        this.x = x;
        this.y = y;

    }

    //开始
    public void start() {
        isDraw = true;
        isStop = false;
    }

    //更新
    public void update() {
        if (!isStop) {
            if (code <= 5) {
                this.y -= 2;
                code++;
            } else if (code <= 10) {
                this.y += 1;
                code++;
            } else {
                isStop = true;
                isDraw = false;
                code = 1;
                //清空
                currentImages.clear();
            }
        }
    }

    //对应数字和图片
    public void switchNum(int num, int offset) {
        switch (num) {
            case 0:
                image = images.get(0 + offset);
                break;
            case 1:
                image = images.get(1 + offset);
                break;
            case 2:
                image = images.get(2 + offset);
                break;
            case 3:
                image = images.get(3 + offset);
                break;
            case 4:
                image = images.get(4 + offset);
                break;
            case 5:
                image = images.get(5 + offset);
                break;
            case 6:
                image = images.get(6 + offset);
                break;
            case 7:
                image = images.get(7 + offset);
                break;
            case 8:
                image = images.get(8 + offset);
                break;
            case 9:
                image = images.get(9 + offset);
                break;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ArrayList<Image> getCurrentImages() {
        return currentImages;
    }

    public void setCurrentImages(ArrayList<Image> currentImages) {
        this.currentImages = currentImages;
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

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getTen() {
        return ten;
    }

    public void setTen(int ten) {
        this.ten = ten;
    }

    public int getHundred() {
        return hundred;
    }

    public void setHundred(int hundred) {
        this.hundred = hundred;
    }

    public int getThousand() {
        return thousand;
    }

    public void setThousand(int thousand) {
        this.thousand = thousand;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
