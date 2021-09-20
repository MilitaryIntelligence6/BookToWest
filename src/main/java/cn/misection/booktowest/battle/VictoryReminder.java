package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

import java.util.*;

import cn.misection.booktowest.shop.*;
import cn.misection.booktowest.app.GameApplication;

//战胜结束后的提示
public class VictoryReminder {
    //底部图片引用
    private Image back;
    //获得物品底部图片
    private Image thingBack;
    private int thing_dx1;
    private int thing_dy1;
    private int thing_dx2;
    private int thing_dy2;
    private int thing_sx1;
    private int thing_sy1;
    private int thing_sx2;
    private int thing_sy2;
    //图片集合
    private ArrayList<Image> images = new ArrayList<Image>();
    //张小凡
    private Image zhang1;
    private Image zhang2;
    //文敏
    private Image wen1;
    private Image wen2;
    //陆雪琪
    private Image lu1;
    private Image lu2;
    //宋大仁
    private Image song1;
    private Image song2;
    //升级图片
    private Image levelUpImage;
    //获得物品的图片
    private Image getThingImage;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;

    //第一次图片是否画出
    private boolean firstIsDraw;
    //第二次图片是否画出
    private boolean secondIsDraw;
    //升级小图片是否画出
    private boolean levelUpIsDraw;
    //获得物品图片是否画出
    private boolean getThingIsDraw;

    //第一次字符是否显示
    private boolean firstString;
    //第二次字符是否显示
    private boolean secondString;
    //第三次字符是否显示
    private boolean thirdString;

    //计时编号
    private int timeCode;

    //所要显示字符的集合
    private ArrayList<Integer> showNums = new ArrayList<Integer>();

    //战斗面板引用
    private BattlePanel bp;

    //目标矩形第一个角的坐标
    private int dx1;
    private int dy1;
    //目标矩形第二个角的坐标
    private int dx2;
    private int dy2;
    //源矩形第一个角的坐标
    private int sx1;
    private int sy1;
    //源矩形第二个角的坐标
    private int sx2;
    private int sy2;

    //第一张图片坐标
    private int firstX;
    private int firstY;

    //获得经验坐标
    private int firstStringX;
    private int firstStringY;

    //体力坐标
    private int secondStringX;
    private int secondStringY;

    //第一个获得物品坐标
    private int thirdStringX;
    private int thirdStringY;

    //升级图片坐标
    private int levelUpX;
    private int levelUpY;

    //获得物品图片坐标
    private int thingX;
    private int thingY;

    //当前战斗可以获得的经验值
    private int expToGet;
    //当前战斗可以获得的物品
    private ArrayList<String> things = new ArrayList<String>();
    //当前战斗可以获得的金钱
    private int moneyToGet;

    //构造方法
    public VictoryReminder(BattlePanel bp) {
        this.bp = bp;

        isDraw = false;
        isStop = true;

        dx1 = 412;
        dy1 = 80;
        dx2 = 612;
        dy2 = 80;

        sx1 = 0;
        sy1 = 0;
        sx2 = 200;
        sy2 = 0;

        thing_dx1 = 710;
        thing_dy1 = 155;
        thing_dx2 = 710;
        thing_dy2 = 155;
        thing_sx1 = 60;
        thing_sy1 = 75;
        thing_sx2 = 60;
        thing_sy2 = 75;

        levelUpX = 412 + 75;
        levelUpY = 80 + 60;

        thingX = 660;
        thingY = 80;

        firstX = 412;
        firstY = 160;

        firstStringX = 532;
        firstStringY = 80 + 120;

        secondStringX = 412 + 90;
        secondStringY = 80 + 100;

        thirdStringX = 690;
        thirdStringY = 80 + 70;

        loadImage();
        getInformation();
    }

    //载入图片
    public void loadImage() {
        back = Reader.readImage("image/战斗胜利/0副本.png");
        thingBack = Reader.readImage("image/战斗胜利/物品.png");
        levelUpImage = Reader.readImage("image/战斗胜利/9.png");
        getThingImage = Reader.readImage("image/战斗胜利/获得物品.png");
        for (int i = 1; i <= 8; i++) {
            //0--3是第一组 4--7是第二组
            Image image = Reader.readImage("image/战斗胜利/" + i + ".png");
            images.add(image);
        }
        zhang1 = images.get(0);
        zhang2 = images.get(4);
        wen1 = images.get(1);
        wen2 = images.get(5);
        lu1 = images.get(2);
        lu2 = images.get(6);
        song1 = images.get(3);
        song2 = images.get(7);
    }

    //得到战斗信息
    public void getInformation() {
        //计算当前战斗可以获得多少经验,金钱,物品
        for (Enemy e : bp.getEnemyList()) {
            expToGet += e.getExp();
            moneyToGet += e.getMoney();
            things.add(e.getThing());
        }

        //得到数据 (0--2是各个英雄当前升级所需经验 3--6是张小凡的数据 7--10是文敏
        //11--14是陆雪琪)
        Integer i = ZhangXiaoFan.expToLevelUp - ZhangXiaoFan.exp;
        if (i < 0) {
            i = 0;
        }
        showNums.add(i);
        i = YuJie.expToLevelUp - YuJie.exp;
        if (i < 0) {
            i = 0;
        }
        showNums.add(i);
        i = LuXueQi.expToLevelUp - LuXueQi.exp;
        if (i < 0) {
            i = 0;
        }
        showNums.add(i);

        i = ZhangXiaoFan.physicalPower;
        showNums.add(i);
        i = ZhangXiaoFan.sprit;
        showNums.add(i);
        i = ZhangXiaoFan.agile;
        showNums.add(i);
        i = ZhangXiaoFan.strength;
        showNums.add(i);

        i = YuJie.physicalPower;
        showNums.add(i);
        i = YuJie.sprit;
        showNums.add(i);
        i = YuJie.agile;
        showNums.add(i);
        i = YuJie.strength;
        showNums.add(i);

        i = LuXueQi.physicalPower;
        showNums.add(i);
        i = LuXueQi.sprit;
        showNums.add(i);
        i = LuXueQi.agile;
        showNums.add(i);
        i = LuXueQi.strength;
        showNums.add(i);
    }

    //属性增加的动画效果
    public void addValue(int start, Hero hero) {
        if (showNums.get(start) < hero.getPhysicalPower()) {
            showNums.set(start, showNums.get(start) + 1);
        }
        if (showNums.get(start + 1) < hero.getSprit()) {
            showNums.set(start + 1, showNums.get(start + 1) + 1);
        }
        if (showNums.get(start + 2) < hero.getAgile()) {
            showNums.set(start + 2, showNums.get(start + 2) + 1);
        }
        if (showNums.get(start + 3) < hero.getStrength()) {
            showNums.set(start + 3, showNums.get(start + 3) + 1);
        }
    }

    //画出
    public void drawVictoryReminder(Graphics g) {
        if (isDraw) {
            g.drawImage(back, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bp);
            g.drawImage(thingBack, thing_dx1, thing_dy1, thing_dx2, thing_dy2, thing_sx1, thing_sy1, thing_sx2,
                    thing_sy2, bp);
            if (firstIsDraw) {
                if (bp.getZxf() != null) {
                    g.drawImage(zhang1, firstX, firstY, bp);
                }
                if (bp.getYj() != null) {
                    g.drawImage(wen1, firstX, firstY + 100, bp);
                }
                if (bp.getLxq() != null) {
                    g.drawImage(lu1, firstX, firstY + 100 * 2, bp);
                }
            }
            if (secondIsDraw) {
                if (bp.getZxf() != null && bp.getZxf().isLevelUp == true) {
                    g.drawImage(zhang2, firstX, firstY, bp);
                }
                if (bp.getYj() != null && bp.getYj().isLevelUp == true) {
                    g.drawImage(wen2, firstX, firstY + 100, bp);
                }
                if (bp.getLxq() != null && bp.getLxq().isLevelUpped() == true) {
                    g.drawImage(lu2, firstX, firstY + 100 * 2, bp);
                }
            }
            if (firstString) {
                if (bp.getZxf() != null) {
                    g.drawString(expToGet + "", firstStringX, firstStringY);
                    g.drawString(showNums.get(0) + "", firstStringX, firstStringY + 30);
                }
                if (bp.getYj() != null) {
                    g.drawString(expToGet + "", firstStringX, firstStringY + 100);
                    g.drawString(showNums.get(1) + "", firstStringX, firstStringY + 100 + 30);
                }
                if (bp.getLxq() != null) {
                    g.drawString(expToGet + "", firstStringX, firstStringY + 100 * 2);
                    g.drawString(showNums.get(2) + "", firstStringX, firstStringY + 100 * 2 + 30);
                }
            }

            if (secondString) {
                if (bp.getZxf() != null && bp.getZxf().isLevelUp == true) {
                    for (int i = 3; i <= 6; i++) {
                        g.drawString(showNums.get(i) + "", secondStringX, secondStringY + (i - 3) * 20);
                    }
                }

                if (bp.getYj() != null && bp.getYj().isLevelUp == true) {
                    for (int i = 7; i <= 10; i++) {
                        g.drawString(showNums.get(i) + "", secondStringX, secondStringY + (i - 7) * 20 + 100);
                    }
                }

                if (bp.getLxq() != null && bp.getLxq().isLevelUpped() == true) {
                    for (int i = 11; i <= 14; i++) {
                        g.drawString(showNums.get(i) + "", secondStringX, secondStringY + (i - 11) * 20 + 100 * 2);
                    }
                }
            }
            if (thirdString) {
                for (int i = 0; i < things.size(); i++) {
                    g.drawString(things.get(i).split("/")[0], thirdStringX, thirdStringY + i * 20);
                }
                g.drawString("金钱 " + moneyToGet, thirdStringX, thirdStringY + things.size() * 20);
            }

            if (levelUpIsDraw) {
                g.drawImage(levelUpImage, levelUpX, levelUpY, bp);
            }
            if (getThingIsDraw) {
                g.drawImage(getThingImage, thingX, thingY, bp);
            }
        }
    }

    //更新
    public void update() {
        if (!isStop) {
            if (sy2 < 480) {
                dy2 += 20;
                sy2 += 20;
            }
            if (thing_sx1 > 0) {
                thing_dx1 -= 4;
                thing_dx2 += 4;
                thing_sx1 -= 4;
                thing_sx2 += 4;
                thing_dy1 -= 5;
                thing_dy2 += 5;
                thing_sy1 -= 5;
                thing_sy2 += 5;
            }
            if (thing_sx1 == 4) {
                //获得金钱和物品
                for (String s : things) {
                    switch (s.split("/")[1]) {
                        case "1":
                            DrugPack.addDrug(s.split("/")[0], 1);
                            break;
                        case "2":
                            EquipmentPack.addEqupment(s.split("/")[0], 1);
                            break;
                    }
                }
                Money.addCoins(moneyToGet);
            }
            if (thing_sx1 == 0) {
                getThingIsDraw = true;
                thirdString = true;
            }
            if (sy2 == 480) {
                if (secondIsDraw == false && secondString == false) {
                    firstIsDraw = true;
                    firstString = true;
                }
                if (timeCode < 10) {
                    timeCode++;
                }
                if (timeCode == 10) {
                    //开始进行经验值的减少动画
                    if (expToGet > 0) {
                        expToGet -= 40;
                        for (int i = 0; i <= 2; i++) {
                            if (showNums.get(i) > 0) {
                                showNums.set(i, showNums.get(i) - 40);
                            } else {
                                showNums.set(i, 0);
                            }
                        }
                    }
                }//经验值减少的动画效果播放完毕
                if (expToGet <= 0) {
                    expToGet = 0;
                    //延迟一下
                    timeCode++;
                }
                if (timeCode == 15) {
                    for (Hero hero : bp.getHeroes()) {
                        if (hero.isLevelUpped() == true) {
                            levelUpIsDraw = true;
                        }
                    }
                    //没有升级就退出系统
                    if (!levelUpIsDraw) {
                        //levelUpIsDraw=false;
                        GameApplication.zhangXiaoFan.isLevelUp = false;
                        GameApplication.yuJie.isLevelUp = false;
                        GameApplication.luXueQi.setLevelUpped(false);
                        bp.getHeroes().clear();
                        GameApplication.switchTo("scene");
                    }
                }
                if (timeCode == 25) {
                    firstIsDraw = false;
                    firstString = false;
                    //显示第二个画面
                    secondIsDraw = true;
                    secondString = true;
                }
                if (timeCode >= 35 && timeCode < 55) {
                    //开始属性增加的动画效果
                    if (bp.getZxf() != null) {
                        addValue(3, bp.getZxf());
                    }
                    if (bp.getYj() != null) {
                        addValue(7, bp.getYj());
                    }
                    if (bp.getLxq() != null) {
                        addValue(11, bp.getLxq());
                    }
                }
                if (timeCode == 55) {
                    //结束
                    //System.exit(0);
                    levelUpIsDraw = false;
                    GameApplication.zhangXiaoFan.isLevelUp = false;
                    GameApplication.yuJie.isLevelUp = false;
                    GameApplication.luXueQi.setLevelUpped(false);
                    bp.getHeroes().clear();
                    GameApplication.switchTo("scene");
                }
            }
        }
    }

    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
    }

    public Image getThingBack() {
        return thingBack;
    }

    public void setThingBack(Image thingBack) {
        this.thingBack = thingBack;
    }

    public int getThing_dx1() {
        return thing_dx1;
    }

    public void setThing_dx1(int thing_dx1) {
        this.thing_dx1 = thing_dx1;
    }

    public int getThing_dy1() {
        return thing_dy1;
    }

    public void setThing_dy1(int thing_dy1) {
        this.thing_dy1 = thing_dy1;
    }

    public int getThing_dx2() {
        return thing_dx2;
    }

    public void setThing_dx2(int thing_dx2) {
        this.thing_dx2 = thing_dx2;
    }

    public int getThing_dy2() {
        return thing_dy2;
    }

    public void setThing_dy2(int thing_dy2) {
        this.thing_dy2 = thing_dy2;
    }

    public int getThing_sx1() {
        return thing_sx1;
    }

    public void setThing_sx1(int thing_sx1) {
        this.thing_sx1 = thing_sx1;
    }

    public int getThing_sy1() {
        return thing_sy1;
    }

    public void setThing_sy1(int thing_sy1) {
        this.thing_sy1 = thing_sy1;
    }

    public int getThing_sx2() {
        return thing_sx2;
    }

    public void setThing_sx2(int thing_sx2) {
        this.thing_sx2 = thing_sx2;
    }

    public int getThing_sy2() {
        return thing_sy2;
    }

    public void setThing_sy2(int thing_sy2) {
        this.thing_sy2 = thing_sy2;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public Image getZhang1() {
        return zhang1;
    }

    public void setZhang1(Image zhang1) {
        this.zhang1 = zhang1;
    }

    public Image getZhang2() {
        return zhang2;
    }

    public void setZhang2(Image zhang2) {
        this.zhang2 = zhang2;
    }

    public Image getWen1() {
        return wen1;
    }

    public void setWen1(Image wen1) {
        this.wen1 = wen1;
    }

    public Image getWen2() {
        return wen2;
    }

    public void setWen2(Image wen2) {
        this.wen2 = wen2;
    }

    public Image getLu1() {
        return lu1;
    }

    public void setLu1(Image lu1) {
        this.lu1 = lu1;
    }

    public Image getLu2() {
        return lu2;
    }

    public void setLu2(Image lu2) {
        this.lu2 = lu2;
    }

    public Image getSong1() {
        return song1;
    }

    public void setSong1(Image song1) {
        this.song1 = song1;
    }

    public Image getSong2() {
        return song2;
    }

    public void setSong2(Image song2) {
        this.song2 = song2;
    }

    public Image getLevelUpImage() {
        return levelUpImage;
    }

    public void setLevelUpImage(Image levelUpImage) {
        this.levelUpImage = levelUpImage;
    }

    public Image getGetThingImage() {
        return getThingImage;
    }

    public void setGetThingImage(Image getThingImage) {
        this.getThingImage = getThingImage;
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

    public boolean isFirstIsDraw() {
        return firstIsDraw;
    }

    public void setFirstIsDraw(boolean firstIsDraw) {
        this.firstIsDraw = firstIsDraw;
    }

    public boolean isSecondIsDraw() {
        return secondIsDraw;
    }

    public void setSecondIsDraw(boolean secondIsDraw) {
        this.secondIsDraw = secondIsDraw;
    }

    public boolean isLevelUpIsDraw() {
        return levelUpIsDraw;
    }

    public void setLevelUpIsDraw(boolean levelUpIsDraw) {
        this.levelUpIsDraw = levelUpIsDraw;
    }

    public boolean isGetThingIsDraw() {
        return getThingIsDraw;
    }

    public void setGetThingIsDraw(boolean getThingIsDraw) {
        this.getThingIsDraw = getThingIsDraw;
    }

    public boolean isFirstString() {
        return firstString;
    }

    public void setFirstString(boolean firstString) {
        this.firstString = firstString;
    }

    public boolean isSecondString() {
        return secondString;
    }

    public void setSecondString(boolean secondString) {
        this.secondString = secondString;
    }

    public boolean isThirdString() {
        return thirdString;
    }

    public void setThirdString(boolean thirdString) {
        this.thirdString = thirdString;
    }

    public int getTimeCode() {
        return timeCode;
    }

    public void setTimeCode(int timeCode) {
        this.timeCode = timeCode;
    }

    public ArrayList<Integer> getShowNums() {
        return showNums;
    }

    public void setShowNums(ArrayList<Integer> showNums) {
        this.showNums = showNums;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }

    public int getDx1() {
        return dx1;
    }

    public void setDx1(int dx1) {
        this.dx1 = dx1;
    }

    public int getDy1() {
        return dy1;
    }

    public void setDy1(int dy1) {
        this.dy1 = dy1;
    }

    public int getDx2() {
        return dx2;
    }

    public void setDx2(int dx2) {
        this.dx2 = dx2;
    }

    public int getDy2() {
        return dy2;
    }

    public void setDy2(int dy2) {
        this.dy2 = dy2;
    }

    public int getSx1() {
        return sx1;
    }

    public void setSx1(int sx1) {
        this.sx1 = sx1;
    }

    public int getSy1() {
        return sy1;
    }

    public void setSy1(int sy1) {
        this.sy1 = sy1;
    }

    public int getSx2() {
        return sx2;
    }

    public void setSx2(int sx2) {
        this.sx2 = sx2;
    }

    public int getSy2() {
        return sy2;
    }

    public void setSy2(int sy2) {
        this.sy2 = sy2;
    }

    public int getFirstX() {
        return firstX;
    }

    public void setFirstX(int firstX) {
        this.firstX = firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public void setFirstY(int firstY) {
        this.firstY = firstY;
    }

    public int getFirstStringX() {
        return firstStringX;
    }

    public void setFirstStringX(int firstStringX) {
        this.firstStringX = firstStringX;
    }

    public int getFirstStringY() {
        return firstStringY;
    }

    public void setFirstStringY(int firstStringY) {
        this.firstStringY = firstStringY;
    }

    public int getSecondStringX() {
        return secondStringX;
    }

    public void setSecondStringX(int secondStringX) {
        this.secondStringX = secondStringX;
    }

    public int getSecondStringY() {
        return secondStringY;
    }

    public void setSecondStringY(int secondStringY) {
        this.secondStringY = secondStringY;
    }

    public int getThirdStringX() {
        return thirdStringX;
    }

    public void setThirdStringX(int thirdStringX) {
        this.thirdStringX = thirdStringX;
    }

    public int getThirdStringY() {
        return thirdStringY;
    }

    public void setThirdStringY(int thirdStringY) {
        this.thirdStringY = thirdStringY;
    }

    public int getLevelUpX() {
        return levelUpX;
    }

    public void setLevelUpX(int levelUpX) {
        this.levelUpX = levelUpX;
    }

    public int getLevelUpY() {
        return levelUpY;
    }

    public void setLevelUpY(int levelUpY) {
        this.levelUpY = levelUpY;
    }

    public int getThingX() {
        return thingX;
    }

    public void setThingX(int thingX) {
        this.thingX = thingX;
    }

    public int getThingY() {
        return thingY;
    }

    public void setThingY(int thingY) {
        this.thingY = thingY;
    }

    public int getExpToGet() {
        return expToGet;
    }

    public void setExpToGet(int expToGet) {
        this.expToGet = expToGet;
    }

    public ArrayList<String> getThings() {
        return things;
    }

    public void setThings(ArrayList<String> things) {
        this.things = things;
    }

    public int getMoneyToGet() {
        return moneyToGet;
    }

    public void setMoneyToGet(int moneyToGet) {
        this.moneyToGet = moneyToGet;
    }
}




