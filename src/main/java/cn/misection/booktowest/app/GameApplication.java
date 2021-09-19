package cn.misection.booktowest.app;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cn.misection.booktowest.scene.*;
import cn.misection.booktowest.media.MusicReader;
import cn.misection.booktowest.menu.*;
import cn.misection.booktowest.shop.*;
import cn.misection.booktowest.start.*;
import cn.misection.booktowest.battle.*;
import cn.misection.util.uiutil.CenterUtil;

import javax.swing.*;


/**
 * @author javaman
 */
public class GameApplication extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;

    /**
     * 框架容器;
     */
    private static Container container;

    /**
     * 开始界面;
     */
    public static StartPanel startPanel;

    /**
     * 战斗界面
     */
    public static BattlePanel battlePanel;

    /**
     * 场景界面
     */
    public static ScenePanel scenePanel;

    /**
     * 商店界面
     */
    public static EquipmentShopPanel equipmentShopPanel;

    /**
     * 药店
     */
    public static ShopPanel shopPanel;

    /**
     * 菜单
     */
    public static MenuPanel menuPanel;

    /**
     * load和save的panel
     */
    public static LoadAndSavePanel lsPanel;

    public static EndPanel endPanel;

    /**
     * 我方的战斗单位
     */
    public static ZhangXiaoFan zhangXiaoFan;

    public static YuJie yuJie;

    public static LuXueQi luXueQi;

    public static int SCENE_SIGNAL = 0;

    public static CardLayout switcher = new CardLayout();

    public static JPanel currentPanel = new JPanel();

    /**
     * 构造方法;
     */
    public GameApplication() {
        MusicReader.closeBgm();
        container = this.getContentPane();
        startPanel = new StartPanel();

        scenePanel = new ScenePanel(this);
        battlePanel = new BattlePanel();
        //创建我方的三个对象
        zhangXiaoFan = new ZhangXiaoFan(560, 160, battlePanel);
        yuJie = new YuJie(750, 150, battlePanel);
        luXueQi = new LuXueQi(800, 330, battlePanel);
        menuPanel = new MenuPanel(zhangXiaoFan, luXueQi, yuJie);
        shopPanel = new ShopPanel();
        equipmentShopPanel = new EquipmentShopPanel();
        lsPanel = new LoadAndSavePanel();
        endPanel = new EndPanel();

        this.setLayout();
        this.addKeyListener(this);
        this.setTitle("天书西游");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //	this.setSize(1040,680);
        this.setResizable(false);
        this.pack();
        //	this.setMiddle();
        CenterUtil.keepCenter(this);
        this.setVisible(true);
        currentPanel = null;
        switchTo("start");
    }

    private void setLayout() {
        // TODO Auto-geerated method stub
        container.setLayout(switcher);
        container.add("startPanel", startPanel);
        container.add("scenePanel", scenePanel);
        container.add("battlePanel", battlePanel);
        container.add("shopPanel", shopPanel);
        container.add("equipmentShopPanel", equipmentShopPanel);
        container.add("menuPanel", menuPanel);
        container.add("lsPanel", lsPanel);
        container.add("endPanel", endPanel);

    }

    public void init() {
        container.remove(battlePanel);
        container.remove(equipmentShopPanel);
        container.remove(menuPanel);
        container.remove(shopPanel);
        battlePanel = new BattlePanel();
        //创建我方的三个对象
        zhangXiaoFan = new ZhangXiaoFan(560, 160, battlePanel);
        yuJie = new YuJie(750, 150, battlePanel);
        luXueQi = new LuXueQi(800, 330, battlePanel);
        menuPanel = new MenuPanel(zhangXiaoFan, luXueQi, yuJie);
        shopPanel = new ShopPanel();
        equipmentShopPanel = new EquipmentShopPanel();
        container.add("battlePanel", battlePanel);
        container.add("shopPanel", shopPanel);
        container.add("equipmentShopPanel", equipmentShopPanel);
        container.add("menuPanel", menuPanel);
        currentPanel = null;
        switchTo("start");
    }

    public static void switchTo(String panelName) {
        switch (panelName) {
            // 如果进入战斗界面的信号为1
            case "battle":
                switcher.show(container, "battlePanel");
                currentPanel = battlePanel;
                break;
            case "shop":
                // 如果进入商店界面的信号为1
                switcher.show(container, "shopPanel");
                currentPanel = shopPanel;
                break;
            case "equipmentShop":
                // 如果进入装备商店信号为1
                switcher.show(container, "equipmentShopPanel");
                currentPanel = equipmentShopPanel;
                break;
            case "scene":
                // 如果进入场景界面的信号为1
                switcher.show(container, "scenePanel");
                currentPanel = scenePanel;
                SCENE_SIGNAL = 1;
                break;
            case "menu":
                // 如果进入菜单界面的信号为1
                switcher.show(container, "menuPanel");
                currentPanel = menuPanel;
                menuPanel.hero1.refreshValue();
                menuPanel.hero2.refreshValue();
                menuPanel.hero4.refreshValue();
                break;
            // 如果进入开始界面的信号为1
            case "start":
                switcher.show(container, "startPanel");
                currentPanel = startPanel;
                MusicReader.readBgm("主题曲.mp3");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                MusicReader.openBgm();
                break;
            case "ls":
                // 如果进入存档界面的信号为1
                switcher.show(container, "lsPanel");
                currentPanel = lsPanel;
                break;
            case "end":
                switcher.show(container, "endPanel");
                endPanel.start();
                break;
            default:
                break;
        }
    }

    /**
     * 处理（所有）键盘和鼠标监听事件;
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentPanel == scenePanel) {
            int keyCode = e.getKeyCode();
            scenePanel.keyPressed(keyCode, e.isControlDown());
        }
        if (currentPanel == lsPanel) {
            int keyCode = e.getKeyCode();
            lsPanel.keyPressed(keyCode);
        }
        if (currentPanel == battlePanel) {
            int keyCode = e.getKeyCode();
            battlePanel.keyPressed(keyCode);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (currentPanel == scenePanel) {
            int keyCode = e.getKeyCode();
            scenePanel.keyReleased(keyCode);
        }
    }
}
