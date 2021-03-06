package cn.misection.booktowest.menu;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import cn.misection.booktowest.util.*;

/*
 *
 * 继承自GameBu 主要实现是否显示的问题；
 */
public class MenuButton extends GameButton {
    private int isDraw = 0;
    private static int Yes = 1;
    private static int No = 0;

    public MenuButton(int x, int y, int width, int height, Image normalImage,
                      Image waitclickImage, Image pressedImage, JPanel mp) {
        super(x, y, width, height, normalImage, waitclickImage, pressedImage, mp);
        // TODO Auto-generated constructor stub
        this.isDraw = MenuButton.Yes;
    }

    public static int getYes() {
        return Yes;
    }

    public static void setYes(int yes) {
        Yes = yes;
    }

    public static int getNo() {
        return No;
    }

    public static void setNo(int no) {
        No = no;
    }

    @Override
    public void drawButton(Graphics g) {
        if (this.isDraw == MenuButton.Yes) {
            g.drawImage(getButtonImage(), getX(), getY(), getMp());
        }
        if (this.isDraw == MenuButton.No) {

        }
    }

    @Override
    public void isPressedButton(int currentX, int currentY) {

        if (this.isDraw == MenuButton.Yes) {
            if (currentX > getX() - 15 && currentX < (getX() + getWidth() - 15) && currentY > (getY() - 6) && currentY < (getY() + getHeight() - 6)) {
                setButtonImage(getPressedImage());
                setClicked(true);
            } else {
                setButtonImage(getNormalImage());
            }
        } else {

        }
    }

    public boolean isIsMoveIn() {
        return isMoveIn();
    }

    public int getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(int isDraw) {
        this.isDraw = isDraw;
    }
}
