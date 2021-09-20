package cn.misection.booktowest.start;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

import cn.misection.booktowest.app.GameApplication;
import cn.misection.booktowest.util.Reader;

public class LoadAndSavePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final static int WIDTH = 32 * 32;
    private final static int HEIGHT = 32 * 20;
    private Thread t = new Thread(GameApplication.scenePanel);
    // 缓冲图
    private final Image backgroundBufferImage;
    private Graphics backgroundGraphics;

    // 背景
    private Image backgroundImage;

    // 初始化当前 游标的当前位置
    private int currentX = 0;
    private int currentY = 0;

    // 设置应有的动画
    // 按钮动画
    private ArrayList<StartAnimation> buttonAnimations = new ArrayList<StartAnimation>();
    // 人物动画
    private ArrayList<StartAnimation> zhangXiaoFan = new ArrayList<StartAnimation>();
    private ArrayList<StartAnimation> luXueQi = new ArrayList<StartAnimation>();
    private ArrayList<StartAnimation> wenMin = new ArrayList<StartAnimation>();
    // 鼠标动画
    private Mouse mouse;

    // 设置应有的Button
    private ArrayList<StartButton> buttons = new ArrayList<StartButton>();

    // 创建读取和存档的装置
    private Loader loader;
    private Recorder recorder;

    // 判断画什么的数组
    private int[][] isRoleExist;
    private ArrayList<String> maps;
    private ArrayList<String> tasks;

    // 判断应该实现什么样的功能
    private static boolean PanelState = false;
    private static boolean SAVE = true;
    private static boolean LOAD = false;

    //记忆上一个panel是什么
    private String lastPanel;

    public LoadAndSavePanel() {
        // 初始化背景的缓冲图片
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        backgroundBufferImage = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_ARGB);

        // 初始背景为黑色
        setBackground(new Color(0, 0, 0));
        backgroundGraphics = backgroundBufferImage.getGraphics();

        // 设置鼠标监听
        setMouse();

        // 动画
        initialAnimations();
        startAnimationThread();
        // 按钮
        initialButtons();

        loader = new Loader();
        recorder = new Recorder();
        isRoleExist = new int[3][3];
        maps = new ArrayList<String>();
        tasks = new ArrayList<String>();

        prepareScenes();
    }

    public static boolean isPanelState() {
        return PanelState;
    }

    public static void setPanelState(boolean panelState) {
        PanelState = panelState;
    }

    public static boolean isSAVE() {
        return SAVE;
    }

    public static void setSAVE(boolean SAVE) {
        LoadAndSavePanel.SAVE = SAVE;
    }

    public static boolean isLOAD() {
        return LOAD;
    }

    public static void setLOAD(boolean LOAD) {
        LoadAndSavePanel.LOAD = LOAD;
    }

    private void initialAnimations() {
        // TODO Auto-generated method stub
        // 按钮动画和人物动画
        for (int i = 0; i < 3; i++) {
            StartAnimation buttonAnimation = new StartAnimation(4, "载入动画",
                    this, 800, 150 + i * 200);
            StartAnimation zhangAnimation = new StartAnimation(8, "张小凡", this,
                    300, 150 + i * 200);
            StartAnimation luAnimation = new StartAnimation(8, "陆雪琪", this,
                    400, 150 + i * 200);
            StartAnimation wenAnimation = new StartAnimation(8, "文敏", this,
                    500, 150 + i * 200);
            buttonAnimations.add(buttonAnimation);
            zhangXiaoFan.add(zhangAnimation);
            luXueQi.add(luAnimation);
            wenMin.add(wenAnimation);
        }
        // 鼠标动画
        mouse = new Mouse(
                new StartAnimation(8, "鼠标", this, currentX, currentY), this);
    }

    private void initialButtons() {
        // TODO Auto-generated method stub
        for (int i = 0; i < 3; i++) {
            StartButton load = new StartButton(800, 150 + i * 200, 80, 80,
                    Reader.readImage("sources/载入/按钮/空白.png"),
                    Reader.readImage("sources/载入/按钮/空白.png"),
                    Reader.readImage("sources/载入/按钮/空白.png"), this,
                    buttonAnimations.get(i));
            buttons.add(load);
        }
    }

    private void startAnimationThread() {
        // TODO Auto-generated method stub

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
                    for (StartAnimation animation : zhangXiaoFan) {
                        animation.updateImage();
                    }
                    for (StartAnimation animation : luXueQi) {
                        animation.updateImage();
                    }
                    for (StartAnimation animation : wenMin) {
                        animation.updateImage();
                    }
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
        if (PanelState == LOAD) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).isIsclicked()) {
                    if (!loader.isNull(i)) {
                        loader.load(i);
                        if (!t.isAlive()) {
                            t.start();
                        }
                        GameApplication.switchTo("scene");
                    }
                }
            }
        } else {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).isIsclicked()) {
                    recorder.save(i);
                    prepareScenes();
                }
            }
        }

    }

    private void prepareScenes() {
        maps.removeAll(maps);
        tasks.removeAll(tasks);
        for (int i = 0; i < 3; i++) {
            if (!loader.isNull(i)) {
                ArrayList<String> roleAndMapInfo = loader.getTextInfo(i);
                if (Boolean.parseBoolean(roleAndMapInfo.get(0))) {
                    isRoleExist[i][0] = 1;
                }
                if (Boolean.parseBoolean(roleAndMapInfo.get(1))) {
                    isRoleExist[i][1] = 1;
                }
                if (Boolean.parseBoolean(roleAndMapInfo.get(2))) {
                    isRoleExist[i][2] = 1;
                }
                maps.add(roleAndMapInfo.get(3));
                tasks.add(roleAndMapInfo.get(4));
            } else {
                maps.add("无");
                tasks.add("无");
            }
        }
    }

    public void changeStateTo(boolean state) {
        //初始化状态
        PanelState = state;
        // 初始化背景图片
        if (PanelState == SAVE) {
            backgroundImage = Reader.readImage("sources/载入/按钮/存档.png");
        } else {
            backgroundImage = Reader.readImage("sources/载入/按钮/读取.png");
        }
        setMouse();
    }

    public void paint(Graphics g) {

        // 画背景
        backgroundGraphics.drawImage(backgroundImage, 0, 0, this);
        // 绘制动画
        // 按钮

        for (int i = 0; i < 3; i++) {
            backgroundGraphics.drawImage(Reader.readImage("sources/载入/底板.png"),
                    90, 60 + i * 200, this);
            if (isRoleExist[i][0] == 1) {
                zhangXiaoFan.get(i).startAnimation();
                zhangXiaoFan.get(i).drawAnimation(backgroundGraphics);
            }
            if (isRoleExist[i][1] == 1) {
                luXueQi.get(i).startAnimation();
                luXueQi.get(i).drawAnimation(backgroundGraphics);
            }
            if (isRoleExist[i][2] == 1) {
                wenMin.get(i).startAnimation();
                wenMin.get(i).drawAnimation(backgroundGraphics);
            }
            // 画出缩略图
            backgroundGraphics.drawImage(
                    Reader.readImage("maps/" + maps.get(i)), 100,
                    100 + i * 200, 150, 100, this);
            // 画出当前所在位置
            String mapName = maps.get(i).split("\\.")[0];
            backgroundGraphics.setColor(Color.WHITE);
            backgroundGraphics.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 20));
            backgroundGraphics.drawString(mapName, 100, 220 + i * 200);
            if (tasks.get(i) != "无") {
                backgroundGraphics.drawString(tasks.get(i), 400, 120 + i * 200);
            }
        }
        for (StartButton button : buttons) {
            button.drawButton(backgroundGraphics);
        }
        // 画鼠标
        mouse.getMouseAnimation().setX(currentX);
        mouse.getMouseAnimation().setY(currentY);
        mouse.getMouseAnimation().drawAnimation(backgroundGraphics);
        // 加载缓存图
        g.drawImage(backgroundBufferImage, 0, 0, this);
    }

    public void setLastPanel(String lastPanel) {
        this.lastPanel = lastPanel;
    }

    private void returnToLastPanel() {
        GameApplication.switchTo(lastPanel);
    }

    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.VK_ESCAPE) {
            returnToLastPanel();
        }
    }


    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
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

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
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

    public ArrayList<StartAnimation> getZhangXiaoFan() {
        return zhangXiaoFan;
    }

    public void setZhangXiaoFan(ArrayList<StartAnimation> zhangXiaoFan) {
        this.zhangXiaoFan = zhangXiaoFan;
    }

    public ArrayList<StartAnimation> getLuXueQi() {
        return luXueQi;
    }

    public void setLuXueQi(ArrayList<StartAnimation> luXueQi) {
        this.luXueQi = luXueQi;
    }

    public ArrayList<StartAnimation> getWenMin() {
        return wenMin;
    }

    public void setWenMin(ArrayList<StartAnimation> wenMin) {
        this.wenMin = wenMin;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public ArrayList<StartButton> getButtons() {
        return buttons;
    }

    public void setButtons(ArrayList<StartButton> buttons) {
        this.buttons = buttons;
    }

    public Loader getLoader() {
        return loader;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Recorder getRecorder() {
        return recorder;
    }

    public void setRecorder(Recorder recorder) {
        this.recorder = recorder;
    }

    public int[][] getIsRoleExist() {
        return isRoleExist;
    }

    public void setIsRoleExist(int[][] isRoleExist) {
        this.isRoleExist = isRoleExist;
    }

    public ArrayList<String> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<String> maps) {
        this.maps = maps;
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public String getLastPanel() {
        return lastPanel;
    }
}
