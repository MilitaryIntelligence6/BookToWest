package cn.misection.booktowest.menu;

import cn.misection.booktowest.battle.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;


public abstract class FatherPanel extends JPanel implements Runnable {
    /**
     *
     */
    private static final long serialVersionUID = -1663046739601211478L;
    //窗体大小
    private final static int WIDTH = 32 * 32;
    private final static int HEIGHT = 32 * 20;
    //引用
    //人物引用
    private ZhangXiaoFan hero1;
    private LuXueQi hero2;
    private SongDaRen hero3;
    private YuJie hero4;
    private MenuPanel menuPanel;
    private Mouse mouse;
    private Scoll scoll;
    // 背景 在抽象方法 readBackgroundImage()中 由各自的panel实现
    private Image backgroundImage;
    //缓冲图片
    private Image bufferedPic;
    //缓冲画笔
    private Graphics bufferedGraphics;


    // 游标的当前位置
    private int currentX = 0;
    private int currentY = 0;
    // 偏移量
    private int move = 5;

    public FatherPanel(MenuPanel a, ZhangXiaoFan h1, LuXueQi h2, YuJie h4) {
        menuPanel = a;
        hero1 = h1;
        hero2 = h2;
        hero4 = h4;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //双缓冲准备
        bufferedPic = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = bufferedPic.getGraphics();

        mouse = new Mouse(this);


        // 读入图片
        readBackgroundImage();


        //开启线程
        Thread t = new Thread(this);
        t.start();
    }

    public abstract void readBackgroundImage();

    //伪监听！
    public void mousePressed(int x, int y) {
        currentX = x;
        currentY = y;
        checkAllButtonPressed();

    }

    public void mouseReleased(int x, int y) {
        currentX = x;
        currentY = y;
        checkAllButtonReleased();
    }


    public void mouseMoved(int x, int y) {
        currentX = x;
        currentY = y;
        checkAllButtonMoveIn();

    }

    public void mouseDragged(int x, int y) {
        currentX = x;
        currentY = y;
        checkAllButtonMoveIn();

    }

    public abstract void checkAllButtonReleased();

    public abstract void checkAllButtonMoveIn();

    public abstract void checkAllButtonPressed();

    @Override
    public void paint(Graphics g) {

        super.paint(g);
        // 画背景
        bufferedGraphics.drawImage(backgroundImage, 0, 0, this);
        drawSpecialImage(bufferedGraphics);
        menuPanel.getCommand().drawCommand(bufferedGraphics);
        if (scoll != null) {
            scoll.drawScoll(bufferedGraphics);
        }
        drawThisPanel(bufferedGraphics);

        mouse.drawMouse(bufferedGraphics);
        g.drawImage(bufferedPic, 0, 0, this);

    }

    public abstract void drawSpecialImage(Graphics g);

    public abstract void drawThisPanel(Graphics g);

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            update();
            mouse.update();

            repaint();
        }
    }

    public abstract void update();

    public abstract List<String> saveEquipInfo();

    public abstract void initialEquipInfo(List<String> menuInfo);

    public ZhangXiaoFan getHero1() {
        return hero1;
    }

    public void setHero1(ZhangXiaoFan hero1) {
        this.hero1 = hero1;
    }

    public LuXueQi getHero2() {
        return hero2;
    }

    public void setHero2(LuXueQi hero2) {
        this.hero2 = hero2;
    }

    public SongDaRen getHero3() {
        return hero3;
    }

    public void setHero3(SongDaRen hero3) {
        this.hero3 = hero3;
    }

    public YuJie getHero4() {
        return hero4;
    }

    public void setHero4(YuJie hero4) {
        this.hero4 = hero4;
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public void setMenuPanel(MenuPanel menuPanel) {
        this.menuPanel = menuPanel;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public Scoll getScoll() {
        return scoll;
    }

    public void setScoll(Scoll scoll) {
        this.scoll = scoll;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public Image getBufferedPic() {
        return bufferedPic;
    }

    public void setBufferedPic(Image bufferedPic) {
        this.bufferedPic = bufferedPic;
    }

    public Graphics getBufferedGraphics() {
        return bufferedGraphics;
    }

    public void setBufferedGraphics(Graphics bufferedGraphics) {
        this.bufferedGraphics = bufferedGraphics;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }
}
