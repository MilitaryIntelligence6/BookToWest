package cn.misection.booktowest.shop;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import cn.misection.booktowest.util.*;

public class ShopAnimation {
    //图片集合
    private Image image;
    private List<Image> images = new ArrayList<>();

    //动画出现的坐标
    private int AnimationX;
    private int AnimationY;

    private JPanel mp;

    public ShopAnimation(String s, int x, int y, int length, JPanel panel) {
        this.setAnimationX(x);
        this.setAnimationY(y);
        for (int i = 1; i <= length; i++) {
            image = Reader.readImage("sources/Shop/商店人物/" + s + "/" + s + " (" + i + ").png");
            images.add(image);

        }
        this.mp = panel;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public JPanel getMp() {
        return mp;
    }

    public void setMp(JPanel mp) {
        this.mp = mp;
    }
}
