package cn.misection.booktowest.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import cn.misection.booktowest.util.Reader;

import java.util.ArrayList;
import javax.swing.JPanel;

import cn.misection.booktowest.app.GameApplication;

public class StartPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static int WIDTH = 32 * 32;
    private final static int HEIGHT = 32 * 20;

    // 缓冲图
    private final Image backgroundBufferImage;
    private Graphics backgroundGraphics;

    // 背景
    private final Image backgroundImage;

    // 初始化当前 游标的当前位置
    private int currentX = 0;
    private int currentY = 0;

    // 设置应有的动画
    // 按钮动画
    private ArrayList<StartAnimation> buttonAnimations = new ArrayList<StartAnimation>();
    // 鼠标动画
    private Mouse mouse;
    // 卷轴动画
    private StartAnimation scroll;
    // 反向卷轴动画
    private StartAnimation backScroll;
    // 云彩的动画
    private CloudAnimation upCloud;
    private CloudAnimation rightCloud;
    // 载入的动画
    private StartAnimation loadAnimation;
    private StartAnimation loadAnimation2;
    // 设置应有的按钮
    private StartButton start;
    private StartButton load;
    private StartButton about;
    private StartButton end;
    private StartButton back;
    private ArrayList<StartButton> buttons = new ArrayList<StartButton>();

    // 设置卷轴是否展开的状态
    private boolean isUnfolded = false;

    // 设置按钮的信号,0为开始，1为load，2为about
    int BUTTON_SIGNAL;

    // 判断aboutpanel何时出现的timer
    private StartTimer aboutTimer;
    private StartTimer loadTimer;

    public StartPanel() {
        // 初始化计时器
        loadTimer = new StartTimer();
        aboutTimer = new StartTimer();
        // 初始化背景的缓冲图片
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        backgroundBufferImage = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_ARGB);

        // 初始背景为黑色
        setBackground(new Color(0, 0, 0));
        backgroundGraphics = backgroundBufferImage.getGraphics();

        // 设置鼠标监听
        setMouse();
        // 初始化背景图片
        backgroundImage = Reader.readImage("sources/StartPanel/back.png");
        // 动画
        initialAnimations();
        startAnimationThread();
        // 按钮
        initialButtons();
        buttons.add(start);
        buttons.add(load);
        buttons.add(about);
        buttons.add(end);
    }

    private void initialAnimations() {
        // TODO Auto-generated method stub
        // 初始化背景动画
        // 按钮动画
        for (int i = 0; i < 4; i++) {
            StartAnimation buttonAnimation = new StartAnimation(4, "按钮动画",
                    this, 200, 150 + i * 100);
            buttonAnimations.add(buttonAnimation);
        }
        StartAnimation buttonAnimation = new StartAnimation(4, "按钮动画", this,
                800, 550);
        buttonAnimations.add(buttonAnimation);
        // 鼠标动画
        mouse = new Mouse(
                new StartAnimation(8, "鼠标", this, currentX, currentY), this);
        // 卷轴动画
        scroll = new StartAnimation(10, "卷轴", this, -320, -100);
        backScroll = new StartAnimation(10, "反向卷轴", this, -320, -100);
        // 云彩动画
        upCloud = new CloudAnimation(0, 360,
                Reader.readImage("sources/StartPanel/最终云彩.png"), 0, this);
        loadAnimation = new StartAnimation(10, "载入", this, 650, 480);
        loadAnimation2 = new StartAnimation(3, "载入2", this, 700, 200);
    }

    private void initialButtons() {
        // TODO Auto-generated method stub
        start = new StartButton(200, 150, 50, 50,
                Reader.readImage("sources/StartPanel/按钮/起.png"),
                Reader.readImage("sources/StartPanel/按钮/起2.png"),
                Reader.readImage("sources/StartPanel/按钮/起2.png"), this,
                buttonAnimations.get(0));
        load = new StartButton(200, 250, 50, 50,
                Reader.readImage("sources/StartPanel/按钮/承.png"),
                Reader.readImage("sources/StartPanel/按钮/承2.png"),
                Reader.readImage("sources/StartPanel/按钮/承2.png"), this,
                buttonAnimations.get(1));
        about = new StartButton(200, 350, 50, 50,
                Reader.readImage("sources/StartPanel/按钮/转.png"),
                Reader.readImage("sources/StartPanel/按钮/转2.png"),
                Reader.readImage("sources/StartPanel/按钮/转2.png"), this,
                buttonAnimations.get(2));
        end = new StartButton(200, 450, 50, 50,
                Reader.readImage("sources/StartPanel/按钮/结.png"),
                Reader.readImage("sources/StartPanel/按钮/结2.png"),
                Reader.readImage("sources/StartPanel/按钮/结2.png"), this,
                buttonAnimations.get(3));
        back = new StartButton(800, 550, 50, 50,
                Reader.readImage("sources/StartPanel/按钮/回.png"),
                Reader.readImage("sources/StartPanel/按钮/回2.png"),
                Reader.readImage("sources/StartPanel/按钮/回2.png"), this,
                buttonAnimations.get(4));
    }

    private void startAnimationThread() {
        Thread animation = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // 处理鼠标动画
                    mouse.startMouseAnimation();
                    mouse.getMouseAnimation().updateImage();
                    // 处理按钮动画
                    for (StartAnimation animation : buttonAnimations) {
                        animation.updateImage();
                    }
                    // 处理卷轴动画
                    scroll.updateImage();
                    backScroll.updateImage();
                    // 处理云彩动画
                    upCloud.updateCoordinate();
                    loadAnimation.updateImage();
                    loadAnimation2.updateImage();
                    // 处理about动画
                    aboutTimer.update();
                    loadTimer.update();
                    repaint();
                }
            }
        };
        // 开启线程
        animation.start();
    }

    private void setMouse() {

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                for (StartButton button : buttons) {
                    button.isPressedButton(currentX, currentY);
                }
            }

            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                // 检查按钮响应
                setButton();
                for (StartButton button : buttons) {
                    button.isRelesedButton(currentX, currentY);
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();

                for (StartButton button : buttons) {
                    button.isMoveIn(currentX, currentY);
                }
            }

            public void mouseDragged(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();
            }
        });
    }

    private void setButton() {
        if (!isUnfolded) {
            if (start.isIsclicked() == true) {
                scroll.startAnimation();
                BUTTON_SIGNAL = 0;
            }
            if (load.isIsclicked() == true) {
                scroll.startAnimation();
                BUTTON_SIGNAL = 1;
            }
            if (about.isIsclicked() == true) {
                scroll.startAnimation();
                aboutTimer.start(10);
                BUTTON_SIGNAL = 2;
            }
            if (end.isIsclicked() == true) {
                System.exit(0);
            }
        } else {
            if (back.isIsclicked() == true) {
                buttons.remove(back);
                aboutTimer.start(10);
                backScroll.startAnimation();
                BUTTON_SIGNAL = 3;
            }
        }
    }

    private void startButtonAction() {
        switch (BUTTON_SIGNAL) {
            case 0:
                loadAnimation.startAnimation();
                loadAnimation2.startAnimation();
                loadTimer.start(30);
                break;
            case 1:
                loadAnimation.startAnimation();
                loadAnimation2.startAnimation();
                loadTimer.start(30);
                break;
            case 2:
                buttons.add(back);
                break;
        }
    }

    public void paint(Graphics g) {
        // 画背景
        backgroundGraphics.drawImage(backgroundImage, 0, 0, this);
        // 绘制动画
        // 云
        upCloud.drawAnimation(backgroundGraphics);
        // 按钮
        for (StartButton button : buttons) {
            if (!button.equals(back)) {
                button.drawButton(backgroundGraphics);
            }
        }
        // 卷轴
        drawScroll();
        // 载入动画
        if (!loadAnimation.isStop()) {
            loadAnimation.drawAnimation(backgroundGraphics);
        }
        if (!loadAnimation2.isStop()) {
            loadAnimation2.drawAnimation(backgroundGraphics);
        }
        // 按钮
        if (buttons.contains(back)) {
            back.drawButton(backgroundGraphics);
        }
        // 画鼠标
        mouse.getMouseAnimation().setX(currentX);
        mouse.getMouseAnimation().setY(currentY);
        mouse.getMouseAnimation().drawAnimation(backgroundGraphics);
        // 加载缓存图
        g.drawImage(backgroundBufferImage, 0, 0, this);
    }

    private void drawScroll() {

        if (!isUnfolded) {
            scroll.drawAnimation(backgroundGraphics);
            if (BUTTON_SIGNAL == 2) {
                backgroundGraphics.drawImage(
                        Reader.readImage("sources/StartPanel/关于我们.png"), 0, 0,
                        100 * (9 - aboutTimer.getTimeLeft()), 640, 0, 0,
                        100 * (9 - aboutTimer.getTimeLeft()), 640, this);
            }
            if (scroll.isLoop() == true) {
                scroll.stopScorllAnimation();
                scroll.stopButtonAnimation();
                isUnfolded = true;
                // 将是否完成循环设为false
                scroll.setLoop(false);
                startButtonAction();
            }
        } else {
            backScroll.drawAnimation(backgroundGraphics);
            if (BUTTON_SIGNAL == 3) {
                backgroundGraphics.drawImage(
                        Reader.readImage("sources/StartPanel/关于我们.png"), 0, 0,
                        100 * (aboutTimer.getTimeLeft()), 640, 0, 0,
                        100 * (aboutTimer.getTimeLeft()), 640, this);
            }
            if (BUTTON_SIGNAL == 2) {
                backgroundGraphics.drawImage(
                        Reader.readImage("sources/StartPanel/关于我们.png"), 0, 0,
                        this);
            }
            startLoadAction();
            if (backScroll.isLoop()) {
                backScroll.stopScorllAnimation();
                backScroll.stopButtonAnimation();
                isUnfolded = false;
                // 将是否完成循环设为false
                backScroll.setLoop(false);
                BUTTON_SIGNAL = -1;
            }
        }
    }

    private void startLoadAction() {
        switch (BUTTON_SIGNAL) {
            case 0:
                if (loadTimer.stop()) {
                    //	Game.game.init();
                    GameApplication.switchTo("scene");
                    loadAnimation.stopButtonAnimation();
                    loadAnimation2.stopButtonAnimation();
                    loadTimer.initial();
                    isUnfolded = false;
                    Thread t = new Thread(GameApplication.scenePanel);
                    GameApplication.scenePanel.initiation("脚本1.txt");
                    t.start();
                }
                break;
            case 1:
                if (loadTimer.stop()) {
                    GameApplication.lsPanel.setLastPanel("start");
                    GameApplication.lsPanel.changeStateTo(LoadAndSavePanel.isLOAD());
                    GameApplication.switchTo("ls");
                    loadAnimation.stopButtonAnimation();
                    loadAnimation2.stopButtonAnimation();
                    loadTimer.initial();
                    isUnfolded = false;
                }
                break;
            default:
                break;
        }
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

    public Image getBackgroundImage() {
        return backgroundImage;
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

    public ArrayList<StartAnimation> getButtonAnimations() {
        return buttonAnimations;
    }

    public void setButtonAnimations(ArrayList<StartAnimation> buttonAnimations) {
        this.buttonAnimations = buttonAnimations;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public StartAnimation getScroll() {
        return scroll;
    }

    public void setScroll(StartAnimation scroll) {
        this.scroll = scroll;
    }

    public StartAnimation getBackScroll() {
        return backScroll;
    }

    public void setBackScroll(StartAnimation backScroll) {
        this.backScroll = backScroll;
    }

    public CloudAnimation getUpCloud() {
        return upCloud;
    }

    public void setUpCloud(CloudAnimation upCloud) {
        this.upCloud = upCloud;
    }

    public CloudAnimation getRightCloud() {
        return rightCloud;
    }

    public void setRightCloud(CloudAnimation rightCloud) {
        this.rightCloud = rightCloud;
    }

    public StartAnimation getLoadAnimation() {
        return loadAnimation;
    }

    public void setLoadAnimation(StartAnimation loadAnimation) {
        this.loadAnimation = loadAnimation;
    }

    public StartAnimation getLoadAnimation2() {
        return loadAnimation2;
    }

    public void setLoadAnimation2(StartAnimation loadAnimation2) {
        this.loadAnimation2 = loadAnimation2;
    }

    public StartButton getStart() {
        return start;
    }

    public void setStart(StartButton start) {
        this.start = start;
    }

    public StartButton getLoad() {
        return load;
    }

    public void setLoad(StartButton load) {
        this.load = load;
    }

    public StartButton getAbout() {
        return about;
    }

    public void setAbout(StartButton about) {
        this.about = about;
    }

    public StartButton getEnd() {
        return end;
    }

    public void setEnd(StartButton end) {
        this.end = end;
    }

    public StartButton getBack() {
        return back;
    }

    public void setBack(StartButton back) {
        this.back = back;
    }

    public ArrayList<StartButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<StartButton> buttons) {
        this.buttons = buttons;
    }

    public boolean isUnfolded() {
        return isUnfolded;
    }

    public void setUnfolded(boolean unfolded) {
        isUnfolded = unfolded;
    }

    public StartTimer getAboutTimer() {
        return aboutTimer;
    }

    public void setAboutTimer(StartTimer aboutTimer) {
        this.aboutTimer = aboutTimer;
    }

    public StartTimer getLoadTimer() {
        return loadTimer;
    }

    public void setLoadTimer(StartTimer loadTimer) {
        this.loadTimer = loadTimer;
    }
}
