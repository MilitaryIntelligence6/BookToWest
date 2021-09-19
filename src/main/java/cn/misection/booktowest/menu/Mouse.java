package cn.misection.booktowest.menu;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

//用于画出新的鼠标
public class Mouse {

    //引用
    private FatherPanel fp;

    private Image mouseImage;//图片引用
    private Image currentImage;//当前的图片
    private ArrayList<Image> images = new ArrayList<Image>();//图片集合
    private int x;
    private int y;//坐标
    private int code;//编号

    //构造方法
    public Mouse(FatherPanel a) {
        this.fp = a;
        x = fp.getCurrentX();
        y = fp.getCurrentY();
        getImage();

    }

    //读取图片
    public void getImage() {
        for (int i = 1; i <= 8; i++) {
            mouseImage = new ImageIcon("sources/菜单/鼠标图/" + i + ".png").getImage();
            images.add(mouseImage);
        }
        currentImage = images.get(0);
    }

    //画出游标
    public void drawMouse(Graphics g) {
        g.drawImage(currentImage, x, y, fp);
    }

    //更新方法
    public void update() {
        x = fp.getCurrentX();
        y = fp.getCurrentY();
        if (code < 8) {
            currentImage = images.get(code);
            code++;
        } else if (code == 8) {
            code = 1;
        }
    }

    public FatherPanel getFp() {
        return fp;
    }

    public void setFp(FatherPanel fp) {
        this.fp = fp;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
