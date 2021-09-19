package cn.misection.booktowest.battle;

import java.awt.*;
import java.util.*;

import cn.misection.booktowest.util.*;

//战斗标记指示类
public class Instruct {
    //当前图片
    private Image currentImage;
    private ArrayList<Image> images = new ArrayList<Image>();
    //编号
    private int code;
    //长度
    private int length;
    //出现位置
    private int x;
    private int y;
    //战斗面板
    private BattlePanel bp;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;

    public void getImage() {
        for (int i = 1; i <= length; i++) {
            Image image = Reader.readImage("image/指示图/" + i + ".png");
            images.add(image);
        }

        currentImage = images.get(0);
    }

    //构造方法
    public Instruct(BattlePanel bp) {
        this.bp = bp;
        this.length = 5;
        isDraw = false;
        isStop = true;

        getImage();
    }

    //开始出现
    public void start() {
        int i = bp.getCurrentRound();
        switch (i) {
            //张小凡
            case 1:
                x = bp.getZxf().x + 210;
                y = bp.getZxf().y + 75;
                break;
            //文敏
            case 2:
                x = bp.getYj().x + 85;
                y = bp.getYj().y - 20;
                break;
            //陆雪琪
            case 3:
                x = bp.getLxq().getX() + 45;
                y = bp.getLxq().getY() - 20;
                break;
        }
        isDraw = true;
        isStop = false;
    }

    //结束
    public void end() {
        isDraw = false;
        isStop = true;
    }

    //画出
    public void drawInstruct(Graphics g) {

        if (isDraw) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //更新
    public void update() {
        if (!isStop && code < 5) {
            currentImage = images.get(code);
            code++;
        } else if (code == 5) {
            code = 0;
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
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
}
