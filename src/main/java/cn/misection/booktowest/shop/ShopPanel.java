package cn.misection.booktowest.shop;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import cn.misection.booktowest.scene.SaveAndLoad;
import cn.misection.booktowest.util.*;
import cn.misection.booktowest.app.GameApplication;
import cn.misection.booktowest.media.MusicReader;


public class ShopPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final static int WIDTH = 32 * 32;
    private final static int HEIGHT = 32 * 20;

    // 缓冲图
    private final Image backgroundBufferImage;
    private Graphics backgroundGraphics;
    // 鼠标指针的图形
    private final Image[] mouses = new Image[8];

    // 按钮的图片
    private List<Image> icons = new ArrayList<>();
    private Image image1;
    private Image drugImage;

    // 背景
    private final Image backgroundImage;
    private Image mouse;

    // 游标的当前位置
    private int currentX = 0;
    private int currentY = 0;
    // 偏移量
    private int move = 5;
    // 药店专属属性
    private List<Drug> drugList;
    //店主说的话
    private String message = "欢迎来到金陵大学医院";
    private String messageplus;
    //创建动画
    private List<ShopAnimation> ani = new ArrayList<>();


    // 各种按钮
    private GameButton buy = new GameButton(445, 75, 149, 40,
            Reader.readImage("sources/Shop/购买1.png"),
            Reader.readImage("sources/Shop/购买2.png"),
            Reader.readImage("sources/Shop/购买3.png"), this);
    private GameButton sell = new GameButton(594, 75, 149, 40,
            Reader.readImage("sources/Shop/卖出1.png"),
            Reader.readImage("sources/Shop/卖出2.png"),
            Reader.readImage("sources/Shop/卖出3.png"), this);
    private GameButton back = new GameButton(880, 20, 149, 40,
            Reader.readImage("sources/Shop/返回游戏 (1).png"),
            Reader.readImage("sources/Shop/返回游戏 (2).png"),
            Reader.readImage("sources/Shop/返回游戏 (3).png"), this);


    private List<GameButton> buttonlist = new ArrayList<>();


    public ShopPanel() {
        // 设定大小
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // 背景缓冲
        backgroundBufferImage = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        // 读入图片
        backgroundImage = Reader.readImage("sources/Shop/shopback.png");
        for (int i = 1; i <= 8; i++) {
            mouse = Reader.readImage("image/鼠标图/" + i + ".png");
            mouses[i - 1] = mouse;
        }
        // 初始背景为黑色
        setBackground(new Color(0, 0, 0));

        backgroundGraphics = backgroundBufferImage.getGraphics();
        //设置人物动画
        ani.add(new ShopAnimation("张小凡", 0, 0, 8, this));
        ani.add(new ShopAnimation("陆雪琪", 0, 160, 8, this));
        ani.add(new ShopAnimation("文敏", 0, 320, 8, this));
        ani.add(new ShopAnimation("店主", 364, 515, 8, this));
        // 设定鼠标监听
        setMouse();
        // 鼠标动画
        Thread mouseAnimation = new Thread() {
            @Override
            public void run() {
                while (true) {
                    for (int i = 0; i < 8; i++) {
                        mouse = mouses[i];
                        for (ShopAnimation animation : ani) {
                            animation.setImage(animation.getImages().get(i));
                        }
                        try {
                            Thread.sleep(120);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        repaint();
                    }
                }
            }
        };
        // 开启动画
        mouseAnimation.start();

        // 读入药品list
        drugList = ShopReader.readDrug();
        for (Drug drug : drugList) {
            drug.setNumber((int) (Math.random() * 10));
        }
        //读入按钮list
        buttonlist.add(buy);
        buttonlist.add(sell);
        buttonlist.add(back);
        int x = 453, y = 200;
        for (int i = 0; i < drugList.size(); i++) {
            GameButton decrease = new GameButton(x + 90, y - 10, 7, 12,
                    Reader.readImage("sources/Shop/减少1.png"),
                    Reader.readImage("sources/Shop/减少2.png"),
                    Reader.readImage("sources/Shop/减少2.png"), this);
            GameButton increase = new GameButton(x + 130, y - 10, 7, 12,
                    Reader.readImage("sources/Shop/增加1.png"),
                    Reader.readImage("sources/Shop/增加2.png"),
                    Reader.readImage("sources/Shop/增加2.png"), this);
            y += 20;
            buttonlist.add(decrease);
            buttonlist.add(increase);
        }
    }

    private void isMoveIn() {
        int oringinY = 180;
        for (int i = 0; i < drugList.size(); i++) {
            if (currentX > 440 && currentX < 795 && currentY > oringinY
                    && currentY < oringinY + 20) {
                drugImage = drugList.get(i).getPicture();
                message = "Hp恢复" + drugList.get(i).getAddHp() + ", Mp恢复" + drugList.get(i).getAddMp();
                if (drugList.get(i).getReduceMoney() >= 6000) {
                    messageplus = "药是好药，但是好像有点贵呢";
                } else {
                    messageplus = "物美价廉，呵呵";
                }
            }
            oringinY += 20;
        }
    }

    // 设置鼠标监听
    public void setMouse() {
        int[] pixels = new int[256];
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        // 制作一个透明的游标
        Cursor transparentCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(image, new Point(0, 0), "hidden");
        // 插入透明游标，以此模拟无游标状态
        setCursor(transparentCursor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                for (GameButton button : buttonlist) {
                    button.isPressedButton(currentX, currentY);
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                setButton();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();

                for (GameButton button : buttonlist) {
                    button.isMoveIn(currentX, currentY);
                }
                isMoveIn();
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();
                repaint();
            }
        });
    }

    //设置按钮按下后的事件
    public void setButton() {
        //触发购买事件
        if (buy.isClicked() == true) {
            MusicReader.readMusic("Clip986.wav");
            for (int i = 0; i < 6; i++) {
                int temp = Math.min(drugList.get(i).getPurchaseNumber(), drugList.get(i).getNumber());
                DrugPack.drugList.get(i).setNumberGOT(DrugPack.drugList.get(i).getNumberGOT() + temp);
                //计算剩余的钱
                Money.setCoins(Money.coins - drugList.get(i).getReduceMoney() * temp);
                //还剩的东西减少
                drugList.get(i).setNumber(drugList.get(i).getNumber() - temp);
            }
            if (Money.getCoins() < 0) {
                for (int i = 0; i < 6; i++) {
                    int temp = Math.min(drugList.get(i).getPurchaseNumber(), drugList.get(i).getNumber());
                    DrugPack.drugList.get(i).setNumberGOT(DrugPack.drugList.get(i).getNumberGOT() - temp);
                    //计算剩余的钱
                    Money.setCoins(Money.coins + drugList.get(i).getReduceMoney() * temp);
                    //还剩的东西减少
                    drugList.get(i).setNumber(drugList.get(i).getNumber() + temp);
                }
                message = "哎呀,小兄弟,你的钱不顾了,要省着点花啊";
                messageplus = null;
            }
            for (int i = 0; i < 6; i++) {
                drugList.get(i).setPurchaseNumber(0);
            }
        }
        //触发卖出事件
        if (sell.isClicked() == true) {
            MusicReader.readMusic("Clip986.wav");
            for (int i = 0; i < 6; i++) {
                int temp = Math.min(drugList.get(i).getPurchaseNumber(), DrugPack.drugList.get(i).getNumberGOT());
                DrugPack.drugList.get(i).setNumberGOT(DrugPack.drugList.get(i).getNumberGOT() - temp);
                //计算剩余的钱
                Money.setCoins(Money.coins + drugList.get(i).getReduceMoney() * temp);
                //还剩的东西减少
                drugList.get(i).setNumber(drugList.get(i).getNumber() + temp);
                drugList.get(i).setPurchaseNumber(0);
            }
        }
        if (back.isClicked() == true) {
            MusicReader.readMusic("换头像.wav");
            GameApplication.switchTo("scene");
        }
        for (int i = 3; i < buttonlist.size(); i += 2) {
            if (buttonlist.get(i).isClicked() == true) {
                MusicReader.readMusic("click.wav");
                if (drugList.get(i / 2 - 1).getPurchaseNumber() > 0) {
                    drugList.get(i / 2 - 1).setPurchaseNumber(
                            drugList.get(i / 2 - 1).getPurchaseNumber() - 1);
                }
            }
            if (buttonlist.get(i + 1).isClicked() == true) {
                MusicReader.readMusic("click.wav");
                drugList.get(i / 2 - 1).setPurchaseNumber(
                        drugList.get(i / 2 - 1).getPurchaseNumber() + 1);
            }
        }
        for (GameButton button : buttonlist) {
            button.isRelesedButton(currentX, currentY);
        }
    }


    // 画出图标的方法
    public void drawIcon(Graphics g) {
        // 读入图标的图片
        g.drawImage(drugImage, 820, 200, this);
        // 为画图而给出的坐标
        int x = 453, y = 200;
        int i = 0;
        for (Drug drug : drugList) {
            g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString(drug.getName(), x, y);
            g.drawString(" " + drug.getReduceMoney(), x + 180, y);
            g.drawString(" " + drug.getNumber(), x + 245, y);
            g.drawString(" " + DrugPack.drugList.get(i).getNumberGOT(), x + 300, y);
            g.drawString(" " + Money.getCoins(), 900, 20);
            g.drawString(" " + drug.getPurchaseNumber(), x + 100, y);
            y += 20;
            i++;
        }
        if (SaveAndLoad.isZhang()) {
            backgroundGraphics.drawImage(ani.get(0).getImage(),
                    ani.get(0).getAnimationX(), ani.get(0).getAnimationY(), this);
            g.drawString("体", 55, 30 + 0 * 150);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/体力.png"), 60, 30 + 0 * 150, this);
            g.drawString("敏", 55, 30 + 0 * 150 + 20);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/敏捷.png"), 60, 30 + 0 * 150 + 20, this);
            g.drawString("武", 55, 30 + 0 * 150 + 40);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/武力.png"), 60, 30 + 0 * 150 + 40, this);
            g.drawString("精", 55, 30 + 0 * 150 + 60);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/精气.png"), 60, 30 + 0 * 150 + 60, this);
        }
        if (SaveAndLoad.isLu()) {
            backgroundGraphics.drawImage(ani.get(1).getImage(),
                    ani.get(1).getAnimationX(), ani.get(1).getAnimationY(), this);
            g.drawString("体", 55, 30 + 1 * 150);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/体力.png"), 60, 30 + 1 * 150, this);
            g.drawString("敏", 55, 30 + 1 * 150 + 20);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/敏捷.png"), 60, 30 + 1 * 150 + 20, this);
            g.drawString("武", 55, 30 + 1 * 150 + 40);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/武力.png"), 60, 30 + 1 * 150 + 40, this);
            g.drawString("精", 55, 30 + 1 * 150 + 60);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/精气.png"), 60, 30 + 1 * 150 + 60, this);
        }
        if (SaveAndLoad.isWen()) {
            backgroundGraphics.drawImage(ani.get(2).getImage(),
                    ani.get(2).getAnimationX(), ani.get(2).getAnimationY(), this);
            g.drawString("体", 55, 30 + 2 * 150);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/体力.png"), 60, 30 + 2 * 150, this);
            g.drawString("敏", 55, 30 + 2 * 150 + 20);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/敏捷.png"), 60, 30 + 2 * 150 + 20, this);
            g.drawString("武", 55, 30 + 2 * 150 + 40);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/武力.png"), 60, 30 + 2 * 150 + 40, this);
            g.drawString("精", 55, 30 + 2 * 150 + 60);
            g.drawImage(Reader.readImage("sources/Shop/按钮组件/精气.png"), 60, 30 + 2 * 150 + 60, this);
        }
        backgroundGraphics.drawImage(ani.get(3).getImage(),
                ani.get(3).getAnimationX(), ani.get(3).getAnimationY(), this);
        g.drawImage(Reader.readImage("sources/Shop/按钮组件/招牌.png"), 200, 0, this);
        if (message != null) {
            g.drawString(message, 453, 610);
        }
        if (messageplus != null) {
            g.drawString(messageplus, 453, 630);
        }
        g.drawImage(Reader.readImage("sources/Shop/按钮组件/钱.png"), 880, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // 画背景
        backgroundGraphics.drawImage(backgroundImage, 0, 0, this);

        // 画出图标
        drawIcon(backgroundGraphics);
        // 画出按钮组件
        for (GameButton button : buttonlist) {
            button.drawButton(backgroundGraphics);
        }
        backgroundGraphics.drawImage(mouse, currentX, currentY, this);
        // 加载缓存图
        g.drawImage(backgroundBufferImage, 0, 0, this);
    }

    /**
     * 提供接口给Recorder和loader;
     * @return
     */
    public List<String> saveShopInfo() {
        List<String> shopInfo = new ArrayList<>();
        for (Drug drug : DrugPack.drugList) {
            shopInfo.add(Integer.toString(drug.getNumberGOT()));
        }
        shopInfo.add(Integer.toString(Money.getCoins()));
        return shopInfo;
    }

    public void initialShopInfo(List<String> shopInfo) {
        for (int i = 0; i < DrugPack.drugList.size(); i++) {
            DrugPack.drugList.get(i).setNumberGOT(Integer.parseInt(shopInfo.get(i)));
        }
        Money.setCoins(Integer.parseInt(shopInfo.get(drugList.size())));
    }

    public Image getBackgroundBufferImage() {
        return backgroundBufferImage;
    }

    public Graphics getBackgroundGraphics() {
        return backgroundGraphics;
    }

    public void setBackgroundGraphics(Graphics backgroundGraphics) {
        this.backgroundGraphics = backgroundGraphics;
    }

    public Image[] getMouses() {
        return mouses;
    }

    public List<Image> getIcons() {
        return icons;
    }

    public void setIcons(List<Image> icons) {
        this.icons = icons;
    }

    public Image getImage1() {
        return image1;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public Image getDrugImage() {
        return drugImage;
    }

    public void setDrugImage(Image drugImage) {
        this.drugImage = drugImage;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public Image getMouse() {
        return mouse;
    }

    public void setMouse(Image mouse) {
        this.mouse = mouse;
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

    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageplus() {
        return messageplus;
    }

    public void setMessageplus(String messageplus) {
        this.messageplus = messageplus;
    }

    public List<ShopAnimation> getAni() {
        return ani;
    }

    public void setAni(List<ShopAnimation> ani) {
        this.ani = ani;
    }

    public GameButton getBuy() {
        return buy;
    }

    public void setBuy(GameButton buy) {
        this.buy = buy;
    }

    public GameButton getSell() {
        return sell;
    }

    public void setSell(GameButton sell) {
        this.sell = sell;
    }

    public GameButton getBack() {
        return back;
    }

    public void setBack(GameButton back) {
        this.back = back;
    }

    public List<GameButton> getButtonlist() {
        return buttonlist;
    }

    public void setButtonlist(List<GameButton> buttonlist) {
        this.buttonlist = buttonlist;
    }
}
