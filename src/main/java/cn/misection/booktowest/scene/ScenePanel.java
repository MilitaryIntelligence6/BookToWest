package cn.misection.booktowest.scene;

import cn.misection.booktowest.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import cn.misection.booktowest.app.GameApplication;
import cn.misection.booktowest.media.MusicReader;

public class ScenePanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    // 规格
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 640;
    // 地图对象、主角对象、NPC对象、Reader对象
    private Dialogue dialogue;
    private Narratage narratage;
    private Role role;
    private List<NPC> npcs;
    private Map map;
    private Reader reader;
    // 地图图片、缓冲背景图片、
    private Image backImage;
    private Graphics backImageGraphics;
    // 地图数组
    private int[][] mapSet;
    // 各类事件
    private ExitEvent exitEvent;
    private DialogueEvent dialogueEvent;
    private RoleEvent roleEvent;
    private NPCEvent npcEvent;
    private OtherEvent otherEvent;
    private FightEvent fightEvent;
    private SelectEvent selectEvent;
    private EquipmentEvent equipmentEvent;
    private SaveAndLoad sal;
    private boolean isInitiateOver = false;
    private boolean isPaintOver = false;
    private boolean b;
    private List<String> memory = new ArrayList<>();
    // 当前场景以及下一个场景
    private String fileName;
    private String[] currentScript = new String[3];
    private String[] nextScript = new String[3];
    private GameApplication game;
    private boolean isScript = true;

    // 构造函数
    public ScenePanel(GameApplication gameApplication) {
        this.game = gameApplication;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        currentScript[0] = "7/7";
        currentScript[1] = "宿舍.txt";
        currentScript[2] = "脚本1.txt";
        sal = new SaveAndLoad(this);
        setMouse();
    }

    @Override
    public void paint(Graphics g) {
        isPaintOver = false;
        otherEvent.calOffset();
        super.paint(backImageGraphics);
        // 注意顺序1.是否有旁白
        if (!narratage.isNarratage()) {
            map.drawMap(backImageGraphics, otherEvent.getFirstTileX(),
                    otherEvent.getFirstTileY(), otherEvent.getLastTileX(),
                    otherEvent.getLastTileY());
            equipmentEvent.drawTreasureBox(backImageGraphics);
            boolean b = false;
            for (int i = 0; i < npcs.size(); i++) {
                if (role.getY() > npcs.get(i).getY()
                        && role.getX() - 2 <= npcs.get(i).getX()
                        && role.getX() + 1 >= npcs.get(i).getX()) {
                    b = true;
                }
            }
            if (b) {
                for (int i = 0; i < npcs.size(); i++) {
                    npcs.get(i).drawNPC(backImageGraphics,
                            otherEvent.getFirstTileX(), otherEvent.getFirstTileY());
                }
                role.drawHero(backImageGraphics, otherEvent.getOffsetX(),
                        otherEvent.getOffsetY());
            } else {
                role.drawHero(backImageGraphics, otherEvent.getOffsetX(),
                        otherEvent.getOffsetY());
                for (int i = 0; i < npcs.size(); i++) {
                    npcs.get(i).drawNPC(backImageGraphics,
                            otherEvent.getFirstTileX(), otherEvent.getFirstTileY());
                }
            }
            // 地图遮掩
            otherEvent.addMap(backImageGraphics);
            // 2.是否正在对话
            if (dialogueEvent.isSpeaking() || npcEvent.isOral()) {
                dialogue.drawDialogue(backImageGraphics);
            }
            // 3.画出选择对话
			if (selectEvent.isSelect()) {
				selectEvent.drawSelectImage(backImageGraphics);
			}
            // 4.画出提示对话
            equipmentEvent.drawPresentation(backImageGraphics);

        } else {
            if (!narratage.isNarratageOver()) {
                narratage.drawNarratage(backImageGraphics);
            }
        }
        // 将缓冲背景画到画布上
        g.drawImage(backImage, 0, 0, this);
        // 所有帧已画完
        this.isPaintOver = true;
    }

    public void initiation(String fileName) {
        this.fileName = fileName;
        // 缓冲背景图片
        backImage = new BufferedImage(WIDTH, HEIGHT,
                BufferedImage.TYPE_INT_ARGB);
        backImageGraphics = backImage.getGraphics();
        // 新建阅读器，传给他要读的脚本文件地址
        reader = new Reader(fileName);
        // 得到地图数组
        mapSet = reader.getMapSet();
        // 新建地图对象
        map = new Map(reader.getMapName(), this, reader.getRow(),
                reader.getCol());
        // 创建NPC对象列表
        npcs = new ArrayList<>();
        if (reader.getNpcs() != null) {
            npcs = reader.getNpcs();
        }
        // 新建主角对象
        role = new Role(reader.getRoleX() * Map.CS, reader.getRoleY() * Map.CS,
                this);
        // 创建主角事件对象
        roleEvent = new RoleEvent(role, map, mapSet, npcs);
        npcEvent = new NPCEvent(this);
        // 创建对话对象
        dialogue = new Dialogue(this);
        // 将该场景的对话编号，所有对话，及其对应的类型载入
        if (reader.getDialogueCode() != null) {
            dialogueEvent = new DialogueEvent(
                    this,
                    reader.getDialogueCode(),
                    reader.getDialogue());
        } else if (sal.isLoad()) {
            dialogueEvent = new DialogueEvent(this,
                    reader.getDialogueCode(),
                    reader.getDialogue());
            sal.setLoad(false);
        }
        // 创建旁白对象
        narratage = new Narratage(this, reader.getNarratage());
        // 该场景的出口及对应下一场景载入
        exitEvent = new ExitEvent(this, reader.getExits(),
                reader.getNextScene(), reader.getEntrance());
        otherEvent = new OtherEvent(this, role, map);
        if (reader.getNextScript() != null) {
            nextScript = reader.getNextScript();
        }
        fightEvent = new FightEvent(this, reader.getBattle0(),
                reader.getBattle1());
        selectEvent = new SelectEvent(this, fightEvent, fileName,
                reader.getSelectShopPanel(),
                reader.getSelectEquipmentShopPanel(),
                reader.getSelectBattlePanel(), reader.getSelectQuestion(),
                reader.getBattle2(), reader.getQuestion(), reader.getAnswer());
        equipmentEvent = new EquipmentEvent(this, reader.getTreasureBox());
        MusicReader.readBgm(reader.getSceneMusic());
        isInitiateOver = true;
    }

    // 伪键盘监听代码
    public void keyPressed(int keyCode, boolean isControl) {
        if (!narratage.isNarratage()) {
            if (!dialogueEvent.isSpeaking()) {
                if (keyCode == KeyEvent.VK_SPACE) {
                    if (reader.getDialogueCode() != null) {
                        b = dialogueEvent.checkDialogue();
                    } else {
                        b = false;
                    }
                }
                if (!npcEvent.isOral()) {
					if (keyCode == KeyEvent.VK_DOWN
							|| keyCode == KeyEvent.VK_UP
							|| keyCode == KeyEvent.VK_LEFT
							|| keyCode == KeyEvent.VK_RIGHT) {
						if (!selectEvent.isSelect()) {
							if (isControl) {
								roleEvent.checkRun();
							}
							roleEvent.switchWalk(keyCode);

						}
					}
                    if (keyCode == KeyEvent.VK_SPACE && !b) {
                        npcEvent.checkNPCOral();
                    }
					if (reader.getTreasureBox() != null) {
						equipmentEvent.keyPressed(keyCode);
					}
					if (selectEvent.isSelect()) {
						selectEvent.keyPressed(keyCode);
					}
                } else {
                    npcEvent.keyPress(keyCode);
                }
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    GameApplication.switchTo("menu");
                }
            } else {
                dialogueEvent.keyPressed(keyCode);
            }
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            // 初始化完毕后再启动
            if (isInitiateOver) {
                // 1.检查旁白
				if (GameApplication.currentPanel.equals(GameApplication.scenePanel)
						&& isScript && !narratage.isNarratage()
						&& !narratage.isNarratageOver()) {
					narratage.checkNarratage();
				}
                // 2.检查自动的对话
                if (isScript && !dialogueEvent.isSpeaking()
                        && !dialogueEvent.isDialogueEventOver()
                        && !narratage.isNarratage()) {
                    dialogueEvent.checkAutoDialogue();
                }
                // 3.检查NPC
				if (!dialogueEvent.isSpeaking() && !narratage.isNarratage()) {
					npcEvent.checkNPCStop();
				}
                // 4.检查出口
                if (exitEvent.getExits() != null) {
                    if (fightEvent.getBattle1() != null
                            && fightEvent.getBattle1().size() > 1) {
						if (fightEvent.isBattle1Over()) {
							exitEvent.checkExit();
						}
                    } else {
                        exitEvent.checkExit();
                    }
                }
                // 5.检查位置对话
                if (isScript && !dialogueEvent.isSpeaking()
                        && !dialogueEvent.isDialogueEventOver()
                        && !narratage.isNarratage()) {
                    dialogueEvent.checkLocationDialogue();
                }
                // 6.检查宝箱
				if (reader.getTreasureBox() != null) {
					equipmentEvent.checBoxes(role.getX(), role.getY());
				}

                // 7.检查计步战斗
                if (fightEvent.getBattle0() != null) {
                    fightEvent.checkBattle0();
                }
                if (GameApplication.SCENE_SIGNAL == 1) {
                    MusicReader.readBgm(reader.getSceneMusic());
                    GameApplication.SCENE_SIGNAL = 0;
                }
				if (isPaintOver) {
					this.repaint();
				}
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void keyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                role.setEvent(true);
                break;
        }
    }

    // 一堆getters and setters
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<NPC> npcs) {
        this.npcs = npcs;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Image getBackImage() {
        return backImage;
    }

    public void setBackImage(Image backImage) {
        this.backImage = backImage;
    }

    public Graphics getBackImageGraphics() {
        return backImageGraphics;
    }

    public void setBackImageGraphics(Graphics backImageGraphics) {
        this.backImageGraphics = backImageGraphics;
    }

    public int[][] getMapSet() {
        return mapSet;
    }

    public void setMapSet(int[][] mapSet) {
        this.mapSet = mapSet;
    }

    public void setMouse() {
        int[] pixels = new int[256];
        Image image = Toolkit.getDefaultToolkit().createImage(
                new MemoryImageSource(16, 16, pixels, 0, 16));
        // 制作一个透明的游标
        Cursor transparentCursor = Toolkit.getDefaultToolkit()
                .createCustomCursor(image, new Point(0, 0), "hidden");
        // 插入透明游标，以此模拟无游标状态
        setCursor(transparentCursor);
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Narratage getNarratage() {
        return narratage;
    }

    public void setNarratage(Narratage narratage) {
        this.narratage = narratage;
    }

    public ExitEvent getExitEvent() {
        return exitEvent;
    }

    public void setExitEvent(ExitEvent exitEvent) {
        this.exitEvent = exitEvent;
    }

    public DialogueEvent getDialogueEvent() {
        return dialogueEvent;
    }

    public void setDialogueEvent(DialogueEvent dialogueEvent) {
        this.dialogueEvent = dialogueEvent;
    }

    public RoleEvent getRoleEvent() {
        return roleEvent;
    }

    public void setRoleEvent(RoleEvent roleEvent) {
        this.roleEvent = roleEvent;
    }

    public NPCEvent getNpcEvent() {
        return npcEvent;
    }

    public void setNpcEvent(NPCEvent npcEvent) {
        this.npcEvent = npcEvent;
    }

    public OtherEvent getOtherEvent() {
        return otherEvent;
    }

    public void setOtherEvent(OtherEvent otherEvent) {
        this.otherEvent = otherEvent;
    }

    public FightEvent getFightEvent() {
        return fightEvent;
    }

    public void setFightEvent(FightEvent fightEvent) {
        this.fightEvent = fightEvent;
    }

    public SelectEvent getSelectEvent() {
        return selectEvent;
    }

    public void setSelectEvent(SelectEvent selectEvent) {
        this.selectEvent = selectEvent;
    }

    public EquipmentEvent getEquipmentEvent() {
        return equipmentEvent;
    }

    public void setEquipmentEvent(EquipmentEvent equipmentEvent) {
        this.equipmentEvent = equipmentEvent;
    }

    public SaveAndLoad getSal() {
        return sal;
    }

    public void setSal(SaveAndLoad sal) {
        this.sal = sal;
    }

    public boolean isInitiateOver() {
        return isInitiateOver;
    }

    public void setInitiateOver(boolean initiateOver) {
        isInitiateOver = initiateOver;
    }

    public boolean isPaintOver() {
        return isPaintOver;
    }

    public void setPaintOver(boolean paintOver) {
        isPaintOver = paintOver;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public List<String> getMemory() {
        return memory;
    }

    public void setMemory(List<String> memory) {
        this.memory = memory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[] getCurrentScript() {
        return currentScript;
    }

    public void setCurrentScript(String[] currentScript) {
        this.currentScript = currentScript;
    }

    public String[] getNextScript() {
        return nextScript;
    }

    public void setNextScript(String[] nextScript) {
        this.nextScript = nextScript;
    }

    public GameApplication getGame() {
        return game;
    }

    public void setGame(GameApplication game) {
        this.game = game;
    }

    public boolean isScript() {
        return isScript;
    }

    public void setScript(boolean script) {
        isScript = script;
    }
}
