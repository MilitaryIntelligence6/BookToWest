package cn.misection.booktowest.battle;

import cn.misection.booktowest.util.*;

import java.awt.*;
import java.util.*;

//游标类
public class Mouse {
    //图片引用
    private Image mouseImage;
    //当前的图片
    private Image currentImage;
    //图片集合
    private ArrayList<Image> images = new ArrayList<Image>();
    //坐标
    private int x;
    private int y;
    //是否画出
    private boolean isDraw;
    //是否停止
    private boolean isStop;
    //战斗面板引用
    private BattlePanel bp;

    //编号
    private int code;

    //构造方法
    public Mouse(BattlePanel bp) {
        this.bp = bp;
        x = bp.getCurrentX();
        y = bp.getCurrentY();
        getImage();
        isDraw = true;
        isStop = false;
    }

    //读取图片
    public void getImage() {
        for (int i = 1; i <= 8; i++) {
            mouseImage = Reader.readImage("image/鼠标图/" + i + ".png");
            images.add(mouseImage);
        }
    }

    //画出游标
    public void drawMouse(Graphics g) {
        if (isDraw) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //更新方法
    public void update() {
        x = bp.getCurrentX();
        y = bp.getCurrentY();
        if (!isStop && code < 8) {
            currentImage = images.get(code);
            code++;
        } else if (code == 8) {
            code = 0;
        }
    }

    public Image getMouseImage() {
        return mouseImage;
    }

    public void setMouseImage(Image mouseImage) {
        this.mouseImage = mouseImage;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
