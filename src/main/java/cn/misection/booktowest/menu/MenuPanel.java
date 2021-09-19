package cn.misection.booktowest.menu;

import java.awt.*;

import cn.misection.booktowest.app.*;

import java.awt.event.*;
import java.awt.image.MemoryImageSource;
import javax.swing.*;

import cn.misection.booktowest.battle.LuXueQi;
import cn.misection.booktowest.battle.YuJie;
import cn.misection.booktowest.battle.ZhangXiaoFan;

/*
 *
 * 最后的菜单栏 有四个panel构成 自己完成不同panel的切换；
 *
 */
public class MenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private final static int WIDTH = 32 * 32;
    private final static int HEIGHT = 32 * 20;

    //人物引用
    private int currentX;
    private int currentY;
    private ZhangXiaoFan hero1;
    private LuXueQi hero2;
    private YuJie hero4;
    private FatherPanel thingPanel;
    private FatherPanel magicPanel;
    private FatherPanel funcPanel;
    private FatherPanel equipPanel;
    private FatherPanel currentPanel;
    private Command command;
    private CardLayout switcher = new CardLayout();

    public MenuPanel(ZhangXiaoFan h1, LuXueQi h2, YuJie h4) {
        hero1 = h1;
        hero2 = h2;
        hero4 = h4;
        setMouse();
        command = new Command(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLayout(switcher);

        thingPanel = new DrugPanel(this, hero1, hero2, hero4);
        this.add(thingPanel.getName(), thingPanel);

        magicPanel = new MagicPanel(this, hero1, hero2, hero4);
        this.add(magicPanel.getName(), magicPanel);

        funcPanel = new FuncPanel(this, hero1, hero2, hero4);
        this.add(funcPanel.getName(), funcPanel);

        equipPanel = new EquipPanel(this, hero1, hero2, hero4);
        this.add(equipPanel.getName(), equipPanel);
        currentPanel = thingPanel;
    }

    public MenuPanel() {

        hero1 = new ZhangXiaoFan();
        hero2 = new LuXueQi();
        hero4 = new YuJie();
        setMouse();
        command = new Command(this);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.setLayout(switcher);


        thingPanel = new DrugPanel(this, hero1, hero2, hero4);
        this.add(thingPanel.getName(), thingPanel);
        magicPanel = new MagicPanel(this, hero1, hero2, hero4);
        this.add(magicPanel.getName(), magicPanel);
        funcPanel = new FuncPanel(this, hero1, hero2, hero4);
        this.add(funcPanel.getName(), funcPanel);
        equipPanel = new EquipPanel(this, hero1, hero2, hero4);
        this.add(equipPanel.getName(), equipPanel);
        currentPanel = thingPanel;
    }


    public void setMouse() {
        int[] pixels = new int[256];
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        // 制作一个透明的游标
        Cursor transparentCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(image, new Point(0, 0), "hidden");
        //插入透明游标，以此模拟无游标状态
        setCursor(transparentCursor);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                command.checkPressed();
                currentPanel.mousePressed(currentX, currentY);

            }

            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                command.checkReleased();
                currentPanel.mouseReleased(currentX, currentY);

            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();
                command.checkMoveIn();
                currentPanel.mouseMoved(currentX, currentY);

            }

            public void mouseDragged(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();
                command.checkMoveIn();
                currentPanel.mouseDragged(currentX, currentY);

            }
        });
    }

    public void keyPressed(int keyCode) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.VK_ESCAPE) {
            GameApplication.switchTo("scene");
        }
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

    public YuJie getHero4() {
        return hero4;
    }

    public void setHero4(YuJie hero4) {
        this.hero4 = hero4;
    }

    public FatherPanel getThingPanel() {
        return thingPanel;
    }

    public void setThingPanel(FatherPanel thingPanel) {
        this.thingPanel = thingPanel;
    }

    public FatherPanel getMagicPanel() {
        return magicPanel;
    }

    public void setMagicPanel(FatherPanel magicPanel) {
        this.magicPanel = magicPanel;
    }

    public FatherPanel getFuncPanel() {
        return funcPanel;
    }

    public void setFuncPanel(FatherPanel funcPanel) {
        this.funcPanel = funcPanel;
    }

    public FatherPanel getEquipPanel() {
        return equipPanel;
    }

    public void setEquipPanel(FatherPanel equipPanel) {
        this.equipPanel = equipPanel;
    }

    public FatherPanel getCurrentPanel() {
        return currentPanel;
    }

    public void setCurrentPanel(FatherPanel currentPanel) {
        this.currentPanel = currentPanel;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public CardLayout getSwitcher() {
        return switcher;
    }

    public void setSwitcher(CardLayout switcher) {
        this.switcher = switcher;
    }
}
