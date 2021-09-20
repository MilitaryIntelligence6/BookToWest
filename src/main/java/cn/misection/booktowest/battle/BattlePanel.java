package cn.misection.booktowest.battle;

import cn.misection.booktowest.media.MusicReader;
import cn.misection.booktowest.util.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.util.List;
import java.util.ArrayList;

/**
 * @author javaman
 */
public class BattlePanel extends JPanel implements Runnable {

    private static final long serialVersionUID = 4L;

    /**
     * 定义宽度和高度
     */
    private static final int WIDTH = 32 * 32;

    private static final int HEIGHT = 20 * 32;

    /**
     * 战斗图片背景
     */
    private Image backgroundImage;

    /**
     * 缓冲图片
     */
    private Image bufferedPic;

    /**
     * 缓冲画笔
     */
    private Graphics bufferedGraphics;

    /**
     * 字体
     */
    private Font font;

    /**
     * 游标对象
     */
    private Mouse mouse;

    /**
     * 游标当前位置
     */
    private int currentX;
    private int currentY;

    /**
     * 控制台
     */
    private Command command;

    /**
     * 技能菜单
     */
    private SkillMenu skillMenu;

    /**
     * 药品菜单
     */
    private DrugMenu drugMenu;

    /**
     * 状态栏
     */
    private StateBlank stateBlank;

    /**
     * 指示标志
     */
    private Instruct instruct;

    /**
     * 怪物选择器
     */
    private EnemySlector enemySlector;

    /**
     * 攻击发动器
     */
    private LaunchAttack launchAttack;

    /**
     * 检查器
     */
    private Check check;

    /**
     * 伤害值显示
     */
    private List<HurtValue> hurtValues;

    /**
     * 开始动画
     */
    private StartAnimation startAnimation;

    /**
     * 背景动画
     */
    private BackgroundAnimation backgroundAnimation;

    /**
     * 技能动画
     */
    private SkillAnimation skillAnimation;

    /**
     * 提示
     */
    private Reminder reminder;

    /**
     * 战斗胜利提示
     */
    private VictoryReminder victoryReminder;

    /**
     * 怒气槽
     */
    private List<AngryBar> angryBars = new ArrayList<>();

    /**
     * 我方战斗单位引用
     */
    private ZhangXiaoFan zxf;
    private YuJie yj;
    private LuXueQi lxq;

    /**
     * 我方战斗单位集合
     */
    private List<Hero> heroes = new ArrayList<>();

    // FIXME: 2021/9/20
    /**
     * 敌人引用
     */
    private Enemy enemyOne;

    private Enemy enemyTwo;

    private Enemy enemyThree;

    private List<Enemy> enemyList = new ArrayList<>();

    /**
     * 怪物智能
     */
    private EnemyAI enemyAI;

    /**
     * 进度条
     */
    private ProgressBar progressBar;

    /**
     * 小精灵
     */
    private Pet pet;

    /**
     * 游戏结束画面
     */
    private GameOver gameOver;

    /**
     * 当前回合 1.张小凡 2.文敏 3.陆雪琪 4.宋大仁 5.怪物1 6.怪物2 7.怪物3
     */
    private int currentRound;

    /**
     * 当前被攻击对象 1.张小凡 2.文敏 3.陆雪琪 4.宋大仁 5.怪物1 6.怪物2 7.怪物3
     */
    private int currentBeAttacked;

    /**
     * 当前攻击模式  1.普通攻击 2.技能1 3.技能2 4.技能3 5.技能4 6.技能5
     */
    private int currentPattern;

    public BattlePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        //双缓冲准备
        bufferedPic = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = bufferedPic.getGraphics();
        font = new Font("文鼎粗钢笔行楷", Font.BOLD, 15);
        bufferedGraphics.setFont(font);
        //创建游标
        mouse = new Mouse(this);
        setMouse();
        requestFocus();
        //创建控制台
        command = new Command(this);
        //创建指示图标
        instruct = new Instruct(this);
        //创建药品菜单
        drugMenu = new DrugMenu(this);
        //创建伤害值显示
        hurtValues = new ArrayList<>();
        //创建背景动画
        backgroundAnimation = new BackgroundAnimation(this);
        //创建技能动画
        skillAnimation = new SkillAnimation(this);
        //创建提示
        reminder = new Reminder(this, 500, 120);
        //创建检查器
        check = new Check(this);
        //开启线程
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * 初始化方法;
     * @param s
     * @param z
     * @param y
     * @param l
     * @param e1
     * @param e2
     * @param e3
     */
    public void initial(String s, ZhangXiaoFan z, YuJie y, LuXueQi l, Enemy e1, Enemy e2, Enemy e3) {
        backgroundImage = Reader.readImage(s);
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
                MusicReader.readBgm("浣花洗剑·变奏.mp3");
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
            default:
                break;
        }

        //添加我方的战斗单位
        zxf = z;
        yj = y;
        lxq = l;
        if (zxf != null) {
            heroes.add(z);
        }
        if (yj != null) {
            heroes.add(y);
        }
        if (lxq != null) {
            heroes.add(l);
        }
        //添加敌人
        enemyOne = e1;
        enemyTwo = e2;
        enemyThree = e3;

        //怪物智能
        enemyAI = new EnemyAI(this);
        //添加怪物 注意顺序 解决遮掩性问题
        if (enemyTwo != null) {
            enemyList.add(enemyTwo);
        }
        if (enemyOne != null) {
            enemyList.add(enemyOne);
        }
        if (enemyThree != null) {
            enemyList.add(enemyThree);
        }

        //创建小精灵
        pet = null;

        progressBar = new ProgressBar(300, 50, this);
        stateBlank = new StateBlank(this);

        //创建怒气槽
        angryBars.clear();
        for (Hero hero : heroes) {
            AngryBar an = new AngryBar(this, hero);
            angryBars.add(an);
        }

        //创建技能菜单
        skillMenu = new SkillMenu(this);

        //创建怪物选择器
        enemySlector = new EnemySlector(this);

        //创建攻击发动器
        launchAttack = new LaunchAttack(this);

        //创建胜利提示
        victoryReminder = new VictoryReminder(this);

        //创建游戏结束画面
        gameOver = new GameOver(this);

        //创建开始动画
        startAnimation = new StartAnimation(this);

        //进度条开始
        progressBar.setStop(false);
        command.setDraw(false);
        drugMenu.setDraw(false);
        instruct.setDraw(false);

        //检查上场战斗时候有人死亡,若有,每人回复10%的hp
        for (Hero hero : heroes) {
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
            enemyList.clear();
            enemyOne = null;
            enemyTwo = null;
            enemyThree = null;
            check.checkEnemyDead();
        }
    }


    /**
     * 设置鼠标;
     */
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
                currentX = e.getX();
                currentY = e.getY();

                if (command.isDraw()) {
                    command.checkPressed();
                }

                if (skillMenu.isDraw()) {
                    skillMenu.checkPressed();
                }

                if (drugMenu.isDraw()) {
                    drugMenu.checkPressed();
                }

                if (enemySlector.isSlectable()) {
                    enemySlector.checkClick(currentX, currentY);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();

                if (command.isDraw()) {
                    command.checkReleased();
                }

                if (skillMenu.isDraw()) {
                    skillMenu.checkReleased();
                }

                if (drugMenu.isDraw()) {
                    drugMenu.checkReleased();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();

                if (command.isDraw()) {
                    command.checkMoveIn();
                }

                if (skillMenu.isDraw()) {
                    skillMenu.checkMoveIn();
                }

                if (drugMenu.isDraw()) {
                    drugMenu.checkMoveIn();
                }

                enemySlector.checkMoveIn(currentX, currentY);
            }

            @Override
            public void mouseDragged(MouseEvent ex) {
                currentX = ex.getX();
                currentY = ex.getY();

                if (command.isDraw()) {
                    command.checkMoveIn();
                }

                if (skillMenu.isDraw()) {
                    skillMenu.checkMoveIn();
                }

                if (drugMenu.isDraw()) {
                    drugMenu.checkMoveIn();
                }
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        bufferedGraphics.drawImage(backgroundImage, 0, 0, this);
        backgroundAnimation.drawBackAnimation(bufferedGraphics);
        stateBlank.drawStateBlank(bufferedGraphics);
        for (AngryBar angryBar : angryBars) {
            angryBar.drawAngryBar(bufferedGraphics);
        }
        if (command != null) {
            command.drawCommand(bufferedGraphics);
        }

        drugMenu.drawDrugMenu(bufferedGraphics);
        for (Hero hero : heroes) {
            if (hero != null) {
                hero.drawHero(bufferedGraphics);
            }
        }
        for (Hero hero : heroes) {
            if (hero != null) {
                hero.getDeadAnimation().drawDeadAniamtion(bufferedGraphics);
            }
        }
        for (Hero hero : heroes) {
            if (hero != null) {
                hero.getVictoryAnimation().drawVictoryAnimation(bufferedGraphics);
            }
        }
        for (Enemy enemy : enemyList) {
            if (enemy != null) {
                enemy.drawEnemy(bufferedGraphics);
            }
        }
        if (pet != null) {
            pet.drawPet(bufferedGraphics);
        }
        if (progressBar != null) {
            progressBar.drawProgressBar(bufferedGraphics);
        }
        skillMenu.drawSkillMenu(bufferedGraphics);
        for (Enemy enemy : enemyList) {
            if (enemy.getBeAttackedAnimation() != null) {
                enemy.getBeAttackedAnimation().drawAnimation(bufferedGraphics);
            }
        }
        for (Hero hero : heroes) {
            if (hero.getBeAttackedAnimation() != null) {
                hero.getBeAttackedAnimation().drawAnimation(bufferedGraphics);
            }
        }
        skillAnimation.drawAnimation(bufferedGraphics);
        for (Hero hero : heroes) {
            hero.getBattleState().drawState(bufferedGraphics);
        }
        for (Enemy enemy : enemyList) {
            enemy.getBattleState().drawState(bufferedGraphics);
        }
        for (HurtValue hurtValue : hurtValues) {
            hurtValue.drawHurtValue(bufferedGraphics);
        }
        instruct.drawInstruct(bufferedGraphics);

        reminder.drawReminder(bufferedGraphics);
        victoryReminder.drawVictoryReminder(bufferedGraphics);
        mouse.drawMouse(bufferedGraphics);
        if (gameOver != null) {
            gameOver.drawGameOver(bufferedGraphics);
        }
        if (startAnimation != null) {
            startAnimation.drawStartAnimation(bufferedGraphics);
        }
        g.drawImage(bufferedPic, 0, 0, this);
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
            if (mouse != null) {
                mouse.update();
            }
            if (backgroundAnimation != null) {
                backgroundAnimation.update();
            }
            for (Hero hero : heroes) {
                if (hero != null) {
                    hero.doAction();
                }
            }
            for (Hero hero : heroes) {
                if (hero != null) {
                    hero.getVictoryAnimation().update();
                }
            }
            for (Hero hero : heroes) {
                if (hero != null) {
                    hero.getDeadAnimation().update();
                }
            }
            for (Enemy enemy : enemyList) {
                if (enemy != null) {
                    enemy.doAction();
                }
            }
            if (progressBar != null) {
                progressBar.updateProgress();
            }
            skillAnimation.update();
            for (Enemy enemy : enemyList) {
                if (enemy.getBeAttackedAnimation() != null) {
                    enemy.getBeAttackedAnimation().update();
                }
            }
            for (Hero hero : heroes) {
                if (hero.getBeAttackedAnimation() != null) {
                    hero.getBeAttackedAnimation().update();
                }
            }
            if (pet != null) {
                pet.update();
            }
            for (HurtValue hurtValue : hurtValues) {
                if (hurtValue != null) {
                    hurtValue.update();
                }
            }
            if (instruct != null) {
                instruct.update();

            }
            if (reminder != null) {
                reminder.update();
            }
            for (Enemy enemy : enemyList) {
                if (enemy != null) {
                    enemy.getBattleState().check();
                }
            }
            if (launchAttack != null) {
                launchAttack.check();
            }

            if (stateBlank != null) {
                stateBlank.update();
            }
            if (angryBars.size() != 0) {
                for (AngryBar angryBar : angryBars) {
                    if (angryBar != null) {
                        angryBar.update();
                    }
                }
            }
            if (victoryReminder != null) {
                victoryReminder.update();
            }
            for (Hero hero : heroes) {
                if (hero != null) {
                    hero.getBattleState().check();
                }
            }
            if (gameOver != null) {
                gameOver.update();
            }
            if (startAnimation != null) {
                startAnimation.update();
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

    public void setHurtValues(List<HurtValue> hurtValues) {
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

    public List<AngryBar> getAngryBars() {
        return angryBars;
    }

    public void setAngryBars(List<AngryBar> angryBars) {
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

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    public Enemy getEnemyOne() {
        return enemyOne;
    }

    public void setEnemyOne(Enemy enemyOne) {
        this.enemyOne = enemyOne;
    }

    public Enemy getEnemyTwo() {
        return enemyTwo;
    }

    public void setEnemyTwo(Enemy enemyTwo) {
        this.enemyTwo = enemyTwo;
    }

    public Enemy getEnemyThree() {
        return enemyThree;
    }

    public void setEnemyThree(Enemy enemyThree) {
        this.enemyThree = enemyThree;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
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
