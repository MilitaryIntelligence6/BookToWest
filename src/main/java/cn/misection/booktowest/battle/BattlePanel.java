package cn.misection.booktowest.battle;

import javax.swing.*;

import cn.misection.booktowest.util.Reader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.List;
import java.util.ArrayList;

import cn.misection.booktowest.media.*;

public class BattlePanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 4L;
    //定义宽度和高度
    private static final int WIDTH = 32 * 32;
    private static final int HEIGHT = 20 * 32;

    //战斗图片背景
    private Image backgroundImage;
    //缓冲图片
    private Image bufferedPic;
    //缓冲画笔
    private Graphics bufferedGraphics;
    //字体
    private Font font;
    //游标对象
    private Mouse mouse;

    //游标当前位置
    private int currentX;
    private int currentY;

    //控制台
    private Command command;

    //技能菜单
    private SkillMenu skillMenu;

    //药品菜单
    private DrugMenu drugMenu;

    //状态栏
    private StateBlank stateBlank;

    //指示标志
    private Instruct instruct;

    //怪物选择器
    private EnemySlector enemySlector;

    //攻击发动器
    private LaunchAttack launchAttack;

    //检查器
    private Check check;

    //伤害值显示
    private List<HurtValue> hurtValues;

    //开始动画
    private StartAnimation startAnimation;

    //背景动画
    private BackgroundAnimation backgroundAnimation;

    //技能动画
    private SkillAnimation skillAnimation;

    //提示
    private Reminder reminder;

    //战斗胜利提示
    private VictoryReminder victoryReminder;

    //怒气槽
    private ArrayList<AngryBar> angryBars = new ArrayList<>();

    //我方战斗单位引用
    private ZhangXiaoFan zxf;
    private YuJie yj;
    private LuXueQi lxq;
    //我方战斗单位集合
    private ArrayList<Hero> heroes = new ArrayList<>();

    //敌人引用
    private Enemy em1;
    private Enemy em2;
    private Enemy em3;
    private ArrayList<Enemy> enemies = new ArrayList<>();

    //怪物智能
    private EnemyAI enemyAI;

    //进度条
    private ProgressBar progressBar;

    //小精灵
    private Pet pet;

    //游戏结束画面
    private GameOver gameOver;

    //当前回合 1.张小凡 2.文敏 3.陆雪琪 4.宋大仁 5.怪物1 6.怪物2 7.怪物3
    private int currentRound;

    //当前被攻击对象 1.张小凡 2.文敏 3.陆雪琪 4.宋大仁 5.怪物1 6.怪物2 7.怪物3
    private int currentBeAttacked;

    //当前攻击模式  1.普通攻击 2.技能1 3.技能2 4.技能3 5.技能4 6.技能5
    private int currentPattern;

    public BattlePanel() {
        setPreferredSize(new Dimension(getWIDTH(), getHEIGHT()));
        //双缓冲准备
        setBufferedPic(new BufferedImage(getWIDTH(), getHEIGHT(), BufferedImage.TYPE_INT_ARGB));
        setBufferedGraphics(getBufferedPic().getGraphics());
        setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, 15));
        getBufferedGraphics().setFont(getFont());

        //创建游标
        setMouse(new Mouse(this));
        setMouse();
        requestFocus();


        //创建控制台
        setCommand(new Command(this));

        //创建指示图标
        setInstruct(new Instruct(this));

        //创建药品菜单
        setDrugMenu(new DrugMenu(this));

        //创建伤害值显示
        setHurtValues(new ArrayList<>());

        //创建背景动画
        setBackgroundAnimation(new BackgroundAnimation(this));

        //创建技能动画
        setSkillAnimation(new SkillAnimation(this));

        //创建提示
        setReminder(new Reminder(this, 500, 120));

        //创建检查器
        setCheck(new Check(this));

        //开启线程
        Thread t = new Thread(this);
        t.start();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    //初始化方法
    public void initial(String s, ZhangXiaoFan z, YuJie y, LuXueQi l, Enemy e1, Enemy e2, Enemy e3) {
        setBackgroundImage(Reader.readImage(s));
        //根据背景加入音乐
        switch (s) {
            case "image/背景图/伏魔山树林.png":
                MusicReader.readBgm("B6.mp3");
                break;
            case "image/背景图/校园小道.png":
                MusicReader.readBgm("B6.mp3");
                break;
            case "image/背景图/大活迷宫.png":
                MusicReader.readBgm("仗剑.mp3");
                break;
            case "image/背景图/藏宝阁外.png":
                MusicReader.readBgm("浣花洗剑.mp3");
                break;
            case "image/背景图/仙二迷宫.png":
                MusicReader.readBgm("会心一击.mp3");
                break;
            case "image/背景图/仙二教学楼.png":
                MusicReader.readBgm("浣花洗剑・变奏.mp3");
                break;
            case "image/背景图/藏经阁一层.png":
                MusicReader.readBgm("文学谷第一战.mp3");
                break;
            case "image/背景图/藏经阁三层.png":
                MusicReader.readBgm("临危恃勇.mp3");
                break;
            case "image/背景图/商塔迷宫.png":
                MusicReader.readBgm("镜影命缘.mp3");
                break;
            case "image/背景图/商塔顶层.png":
                MusicReader.readBgm("紧急.mp3");
                break;
            case "image/背景图/校园内部.png":
                MusicReader.readBgm("C45.mp3");
                break;
            case "image/背景图/比武场.png":
                MusicReader.readBgm("肆涌暗云.mp3");
                break;
        }


        //添加我方的战斗单位
        setZxf(z);
        setYj(y);
        setLxq(l);
        if (getZxf() != null) {
            getHeroes().add(z);
        }
        if (getYj() != null) {
            getHeroes().add(y);
        }
        if (getLxq() != null) {
            getHeroes().add(l);
        }
        //添加敌人
        setEm1(e1);
        setEm2(e2);
        setEm3(e3);

        //怪物智能
        setEnemyAI(new EnemyAI(this));
        //添加怪物 注意顺序 解决遮掩性问题
        if (getEm2() != null) {
            getEnemies().add(getEm2());
        }
        if (getEm1() != null) {
            getEnemies().add(getEm1());
        }
        if (getEm3() != null) {
            getEnemies().add(getEm3());
        }

        //创建小精灵
        setPet(null);

        setProgressBar(new ProgressBar(300, 50, this));
        setStateBlank(new StateBlank(this));

        //创建怒气槽
        getAngryBars().clear();
        for (Hero hero : getHeroes()) {
            AngryBar an = new AngryBar(this, hero);
            getAngryBars().add(an);
        }

        //创建技能菜单
        setSkillMenu(new SkillMenu(this));

        //创建怪物选择器
        setEnemySlector(new EnemySlector(this));

        //创建攻击发动器
        setLaunchAttack(new LaunchAttack(this));

        //创建胜利提示
        setVictoryReminder(new VictoryReminder(this));

        //创建游戏结束画面
        setGameOver(new GameOver(this));

        //创建开始动画
        setStartAnimation(new StartAnimation(this));

        //进度条开始
        getProgressBar().isStop = false;
        getCommand().isDraw = false;
        getDrugMenu().isDraw = false;
        getInstruct().isDraw = false;

        //检查上场战斗时候有人死亡,若有,每人回复10%的hp
        for (Hero hero : getHeroes()) {
            if (hero.isDead()) {
                hero.setDead(false);
                //如果hp为0
                if (hero.getHp() == 0) {
                    hero.setHp(((int) (hero.getHpMax() * 0.1)));
                }
            }
        }
    }

    //设置一个键盘监听(外挂)
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_J) {
            getEnemies().clear();
            setEm1(null);
            setEm2(null);
            setEm3(null);
            getCheck().checkEnemyDead();
        }
    }


    //设置鼠标
    public void setMouse() {
        int[] pixels = new int[256];
        Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
        // 制作一个透明的游标
        Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "hidden");
        // 插入透明游标，以此模拟无游标状态
        setCursor(transparentCursor);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setCurrentX(e.getX());
                setCurrentY(e.getY());

                if (getCommand().isDraw) {
                    getCommand().checkPressed();
                }

                if (getSkillMenu().isDraw) {
                    getSkillMenu().checkPressed();
                }

                if (getDrugMenu().isDraw) {
                    getDrugMenu().checkPressed();
                }

                if (getEnemySlector().isSlectable) {
                    getEnemySlector().checkClick(getCurrentX(), getCurrentY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCurrentX(e.getX());
                setCurrentY(e.getY());

                if (getCommand().isDraw) {
                    getCommand().checkReleased();
                }

                if (getSkillMenu().isDraw) {
                    getSkillMenu().checkReleased();
                }

                if (getDrugMenu().isDraw) {
                    getDrugMenu().checkReleased();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
                                   @Override
                                   public void mouseMoved(MouseEvent ex) {
                                       currentX = ex.getX();
                                       currentY = ex.getY();

                                       if (getCommand().isDraw) {
                                           getCommand().checkMoveIn();
                                       }

                                       if (getSkillMenu().isDraw) {
                                           getSkillMenu().checkMoveIn();
                                       }

                                       if (getDrugMenu().isDraw) {
                                           getDrugMenu().checkMoveIn();
                                       }

                                       getEnemySlector().checkMoveIn(getCurrentX(), getCurrentY());
                                   }

                                   @Override
                                   public void mouseDragged(MouseEvent ex) {
                                       setCurrentX(ex.getX());
                                       setCurrentY(ex.getY());

                                       if (getCommand().isDraw) {
                                           getCommand().checkMoveIn();
                                       }

                                       if (getSkillMenu().isDraw) {
                                           getSkillMenu().checkMoveIn();
                                       }

                                       if (getDrugMenu().isDraw) {
                                           getDrugMenu().checkMoveIn();
                                       }
                                   }
                               }
        );
    }

    @Override
    public void paint(Graphics g) {
        getBufferedGraphics().drawImage(backgroundImage, 0, 0, this);
        getBackgroundAnimation().drawBackAnimation(getBufferedGraphics());
        getStateBlank().drawStateBlank(getBufferedGraphics());
        for (AngryBar angryBar : getAngryBars()) {
            angryBar.drawAngryBar(getBufferedGraphics());
        }
        if (getCommand() != null) {
            getCommand().drawCommand(getBufferedGraphics());
        }

        getDrugMenu().drawDrugMenu(getBufferedGraphics());
        for (Hero hero : getHeroes()) {
            if (hero != null) {
                hero.drawHero(getBufferedGraphics());
            }
        }
        for (Hero hero : getHeroes()) {
            if (hero != null) {
                hero.getDeadAnimation().drawDeadAniamtion(getBufferedGraphics());
            }
        }
        for (Hero hero : getHeroes()) {
            if (hero != null) {
                hero.getVictoryAnimation().drawVictoryAnimation(getBufferedGraphics());
            }
        }
        for (Enemy enemy : getEnemies()) {
            if (enemy != null) {
                enemy.drawEnemy(getBufferedGraphics());
            }
        }
        if (getPet() != null) {
            getPet().drawPet(getBufferedGraphics());
        }
        if (getProgressBar() != null) {
            getProgressBar().drawProgressBar(getBufferedGraphics());
        }
        getSkillMenu().drawSkillMenu(getBufferedGraphics());
        for (Enemy enemy : getEnemies()) {
            if (enemy.beAttackedAnimation != null) {
                enemy.beAttackedAnimation.drawAnimation(getBufferedGraphics());
            }
        }
        for (Hero hero : getHeroes()) {
            if (hero.getBeAttackedAnimation() != null) {
                hero.getBeAttackedAnimation().drawAnimation(getBufferedGraphics());
            }
        }
        getSkillAnimation().drawAnimation(getBufferedGraphics());
        for (Hero hero : getHeroes()) {
            hero.getBattleState().drawState(getBufferedGraphics());
        }
        for (Enemy enemy : getEnemies()) {
            enemy.battleState.drawState(getBufferedGraphics());
        }
        for (HurtValue hurtValue : hurtValues) {
            hurtValue.drawHurtValue(getBufferedGraphics());
        }
        getInstruct().drawInstruct(getBufferedGraphics());

        getReminder().drawReminder(getBufferedGraphics());
        getVictoryReminder().drawVictoryReminder(getBufferedGraphics());
        getMouse().drawMouse(getBufferedGraphics());
        if (getGameOver() != null) {
            getGameOver().drawGameOver(getBufferedGraphics());
        }
        if (getStartAnimation() != null) {
            getStartAnimation().drawStartAnimation(getBufferedGraphics());
        }
        g.drawImage(getBufferedPic(), 0, 0, this);
    }

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
            if (getMouse() != null) {
                getMouse().update();
            }
            if (getBackgroundAnimation() != null) {
                getBackgroundAnimation().update();
            }
            for (Hero hero : getHeroes()) {
                if (hero != null) {
                    hero.doAction();
                }
            }
            for (Hero hero : getHeroes()) {
                if (hero != null) {
                    hero.getVictoryAnimation().update();
                }
            }
            for (Hero hero : getHeroes()) {
                if (hero != null) {
                    hero.getDeadAnimation().update();
                }
            }
            for (Enemy enemy : getEnemies()) {
                if (enemy != null) {
                    enemy.doAction();
                }
            }
            if (getProgressBar() != null) {
                getProgressBar().updateProgress();
            }
            getSkillAnimation().update();
            for (Enemy enemy : getEnemies()) {
                if (enemy.beAttackedAnimation != null) {
                    enemy.beAttackedAnimation.update();
                }
            }
            for (Hero hero : getHeroes()) {
                if (hero.getBeAttackedAnimation() != null) {
                    hero.getBeAttackedAnimation().update();
                }
            }
            if (getPet() != null) {
                getPet().update();
            }
            for (HurtValue hurtValue : getHurtValues()) {
                if (hurtValue != null) {
                    hurtValue.update();
                }
            }
            if (getInstruct() != null) {
                getInstruct().update();

            }
            if (getReminder() != null) {
                getReminder().update();
            }
            for (Enemy enemy : getEnemies()) {
                if (enemy != null) {
                    enemy.battleState.check();
                }
            }
            if (getLaunchAttack() != null) {
                getLaunchAttack().check();
            }

            if (getStateBlank() != null) {
                getStateBlank().update();
            }
            if (getAngryBars().size() != 0) {
                for (AngryBar angryBar : getAngryBars()) {
                    if (angryBar != null) {
                        angryBar.update();
                    }
                }
            }
            if (getVictoryReminder() != null) {
                getVictoryReminder().update();
            }
            for (Hero hero : getHeroes()) {
                if (hero != null) {
                    hero.getBattleState().check();
                }
            }
            if (getGameOver() != null) {
                getGameOver().update();
            }
            if (getStartAnimation() != null) {
                getStartAnimation().update();
            }
            repaint();
        }
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

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
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

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public SkillMenu getSkillMenu() {
        return skillMenu;
    }

    public void setSkillMenu(SkillMenu skillMenu) {
        this.skillMenu = skillMenu;
    }

    public DrugMenu getDrugMenu() {
        return drugMenu;
    }

    public void setDrugMenu(DrugMenu drugMenu) {
        this.drugMenu = drugMenu;
    }

    public StateBlank getStateBlank() {
        return stateBlank;
    }

    public void setStateBlank(StateBlank stateBlank) {
        this.stateBlank = stateBlank;
    }

    public Instruct getInstruct() {
        return instruct;
    }

    public void setInstruct(Instruct instruct) {
        this.instruct = instruct;
    }

    public EnemySlector getEnemySlector() {
        return enemySlector;
    }

    public void setEnemySlector(EnemySlector enemySlector) {
        this.enemySlector = enemySlector;
    }

    public LaunchAttack getLaunchAttack() {
        return launchAttack;
    }

    public void setLaunchAttack(LaunchAttack launchAttack) {
        this.launchAttack = launchAttack;
    }

    public Check getCheck() {
        return check;
    }

    public void setCheck(Check check) {
        this.check = check;
    }

    public List<HurtValue> getHurtValues() {
        return hurtValues;
    }

    public void setHurtValues(ArrayList<HurtValue> hurtValues) {
        this.hurtValues = hurtValues;
    }

    public StartAnimation getStartAnimation() {
        return startAnimation;
    }

    public void setStartAnimation(StartAnimation startAnimation) {
        this.startAnimation = startAnimation;
    }

    public BackgroundAnimation getBackgroundAnimation() {
        return backgroundAnimation;
    }

    public void setBackgroundAnimation(BackgroundAnimation backgroundAnimation) {
        this.backgroundAnimation = backgroundAnimation;
    }

    public SkillAnimation getSkillAnimation() {
        return skillAnimation;
    }

    public void setSkillAnimation(SkillAnimation skillAnimation) {
        this.skillAnimation = skillAnimation;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public VictoryReminder getVictoryReminder() {
        return victoryReminder;
    }

    public void setVictoryReminder(VictoryReminder victoryReminder) {
        this.victoryReminder = victoryReminder;
    }

    public ArrayList<AngryBar> getAngryBars() {
        return angryBars;
    }

    public void setAngryBars(ArrayList<AngryBar> angryBars) {
        this.angryBars = angryBars;
    }

    public ZhangXiaoFan getZxf() {
        return zxf;
    }

    public void setZxf(ZhangXiaoFan zxf) {
        this.zxf = zxf;
    }

    public YuJie getYj() {
        return yj;
    }

    public void setYj(YuJie yj) {
        this.yj = yj;
    }

    public LuXueQi getLxq() {
        return lxq;
    }

    public void setLxq(LuXueQi lxq) {
        this.lxq = lxq;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public Enemy getEm1() {
        return em1;
    }

    public void setEm1(Enemy em1) {
        this.em1 = em1;
    }

    public Enemy getEm2() {
        return em2;
    }

    public void setEm2(Enemy em2) {
        this.em2 = em2;
    }

    public Enemy getEm3() {
        return em3;
    }

    public void setEm3(Enemy em3) {
        this.em3 = em3;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public EnemyAI getEnemyAI() {
        return enemyAI;
    }

    public void setEnemyAI(EnemyAI enemyAI) {
        this.enemyAI = enemyAI;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public GameOver getGameOver() {
        return gameOver;
    }

    public void setGameOver(GameOver gameOver) {
        this.gameOver = gameOver;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getCurrentBeAttacked() {
        return currentBeAttacked;
    }

    public void setCurrentBeAttacked(int currentBeAttacked) {
        this.currentBeAttacked = currentBeAttacked;
    }

    public int getCurrentPattern() {
        return currentPattern;
    }

    public void setCurrentPattern(int currentPattern) {
        this.currentPattern = currentPattern;
    }
}
