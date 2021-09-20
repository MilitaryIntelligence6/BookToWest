package cn.misection.booktowest.battle;

import java.awt.*;

import cn.misection.booktowest.util.*;

import java.util.*;
import java.util.List;

/**
 * 技能菜单类;
 */
public class SkillMenu {

    /**
     * 技能显示框的背景
     */
    private Image background;

    /**
     * 按钮
     */
    private GameButton skillButton;

    private List<GameButton> zhangButtons = new ArrayList<>();

    private List<GameButton> wenButtons = new ArrayList<>();

    private List<GameButton> luButtons = new ArrayList<>();

    private List<GameButton> skillButtons;

    /**
     * 返回按钮
     */
    private GameButton returnButton;

    /**
     * 图片引用
     */
    private List<Image> zhangImages = new ArrayList<>();
    private List<Image> wenImages = new ArrayList<>();
    private List<Image> luImages = new ArrayList<>();

    /**
     * 菜单出现的位置
     */
    private int x;

    private int y;

    /**
     * 是否被画出
     */
    private boolean isDraw;

    /**
     * 介绍性图片
     */
    private Image introduceImage;

    /**
     * 介绍图位置
     */
    private int introX;

    private int introY;

    /**
     * 介绍图是否画出
     */
    private boolean isDrawIntro;

    /**
     * 各主角介绍图
     */
    private List<Image> zhangIntros = new ArrayList<>();

    private List<Image> wenIntros = new ArrayList<>();

    private List<Image> luIntros = new ArrayList<>();

    /**
     * 战斗面板引用
     */
    private BattlePanel battlePanel;

    /**
     * 构造方法;
     * @param battlePanel
     */
    public SkillMenu(BattlePanel battlePanel) {
        this.x = 340;
        this.y = 200;

        this.introX = 160;

        background = Reader.readImage("image/技能菜单/技能显示框.png");
        this.battlePanel = battlePanel;
        isDraw = false;

        loadImage();
        addButton();
        skillButtons = zhangButtons;
    }

    /**
     * 读入图片;
     */
    public void loadImage() {
        //读入张小凡的技能按钮图片
        if (battlePanel.getZxf() != null) {
            for (int i = 1; i <= ZhangXiaoFan.skillNumber; i++) {
                for (int j = 1; j <= 3; j++) {
                    Image image = Reader.readImage("image/技能菜单/技能按钮/张小凡/技能" + i + "按钮" + j + ".png");
                    zhangImages.add(image);
                }
                Image intro = Reader.readImage("image/技能说明/张小凡/" + i + ".png");
                zhangIntros.add(intro);
            }
        }
        //读入文敏的技能按钮图片
        if (battlePanel.getYj() != null) {
            for (int i = 1; i <= YuJie.skillNumber; i++) {
                for (int j = 1; j <= 3; j++) {
                    Image image = Reader.readImage("image/技能菜单/技能按钮/文敏/技能" + i + "按钮" + j + ".png");
                    wenImages.add(image);
                }
                Image intro = Reader.readImage("image/技能说明/文敏/" + i + ".png");
                wenIntros.add(intro);
            }
        }
        //读入陆雪琪的技能按钮图片
        if (battlePanel.getLxq() != null) {
            for (int i = 1; i <= LuXueQi.getSkillNumber(); i++) {
                for (int j = 1; j <= 3; j++) {
                    Image image = Reader.readImage("image/技能菜单/技能按钮/陆雪琪/技能" + i + "按钮" + j + ".png");
                    luImages.add(image);
                }
                Image intro = Reader.readImage("image/技能说明/陆雪琪/" + i + ".png");
                luIntros.add(intro);
            }
        }
    }

    /**
     * 按下按钮后发动攻击;
     * @param currentPattern
     * @param isSlectable
     * @param currentBeAttacked
     */
    public void afterClicked(int currentPattern, boolean isSlectable, int currentBeAttacked) {
        //把菜单隐藏掉
        isDraw = false;
        battlePanel.setCurrentPattern(currentPattern);
        //打开怪物选择
        battlePanel.getEnemySlector().setSelectable(isSlectable);
        battlePanel.setCurrentBeAttacked(currentBeAttacked);
        //指示器结束
        battlePanel.getInstruct().end();
    }

    /**
     * 添加按钮的方法;
     */
    public void addButton() {
        //创建张小凡的按钮
        if (battlePanel.getZxf() != null) {
            for (int i = 0; i < zhangImages.size(); i = i + 3) {
                skillButton = new GameButton(395, 226 + i * 10, 215, 28, zhangImages.get(i), zhangImages.get(i + 1),
                        zhangImages.get(i + 2), battlePanel);
                zhangButtons.add(skillButton);
            }
        }

        //创建文敏的按钮
        if (battlePanel.getYj() != null) {
            for (int i = 0; i < wenImages.size(); i = i + 3) {
                skillButton = new GameButton(395, 226 + i * 10, 215, 28, wenImages.get(i), wenImages.get(i + 1),
                        wenImages.get(i + 2), battlePanel);
                wenButtons.add(skillButton);
            }
        }

        //创建陆雪琪的按钮
        if (battlePanel.getLxq() != null) {
            for (int i = 0; i < luImages.size(); i = i + 3) {
                skillButton = new GameButton(395, 226 + i * 10, 215, 28, luImages.get(i), luImages.get(i + 1),
                        luImages.get(i + 2), battlePanel);
                luButtons.add(skillButton);
            }
        }
    }

    /**
     * 检查当前为谁的回合;
     */
    public void checkRound() {
        if (battlePanel.getCurrentRound() == 1) {
            skillButtons = zhangButtons;
            returnButton = new GameButton(395, 226 + zhangButtons.size() * 30, 215, 28, Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回1.png"), Reader.readImage("image/技能菜单/技能按钮/返回/返回2.png"), Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回3.png"), battlePanel);
        } else if (battlePanel.getCurrentRound() == 2) {
            skillButtons = wenButtons;
            returnButton = new GameButton(395, 226 + wenButtons.size() * 30, 215, 28, Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回1.png"), Reader.readImage("image/技能菜单/技能按钮/返回/返回2.png"), Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回3.png"), battlePanel);
        } else if (battlePanel.getCurrentRound() == 3) {
            skillButtons = luButtons;
            returnButton = new GameButton(395, 226 + luButtons.size() * 30, 215, 28, Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回1.png"), Reader.readImage("image/技能菜单/技能按钮/返回/返回2.png"), Reader.readImage("image" +
                    "/技能菜单/技能按钮/返回/返回3.png"), battlePanel);
        }
    }

    /**
     * 检查是否移动鼠标进入菜单;
     */
    public void checkMoveIn() {
        for (GameButton button : skillButtons) {
            button.isMoveIn(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
        returnButton.isMoveIn(battlePanel.getCurrentX(), battlePanel.getCurrentY());

        //张小凡的回合
        if (battlePanel.getCurrentRound() == 1) {
            for (int i = 0; i < zhangButtons.size(); i++) {
                if (battlePanel.getCurrentX() > 395 && battlePanel.getCurrentX() < 610 && battlePanel.getCurrentY() > 226 + i * 30 && battlePanel.getCurrentY() < 226 + (i + 1) * 30) {
                    introduceImage = zhangIntros.get(i);
                    this.introY = 226 + i * 30;
                    isDrawIntro = true;
                }
            }
        }

        if (battlePanel.getCurrentRound() == 2) {
            for (int i = 0; i < wenButtons.size(); i++) {
                if (battlePanel.getCurrentX() > 395 && battlePanel.getCurrentX() < 610 && battlePanel.getCurrentY() > 226 + i * 30 && battlePanel.getCurrentY() < 226 + (i + 1) * 30) {
                    introduceImage = wenIntros.get(i);
                    this.introY = 226 + i * 30;
                    isDrawIntro = true;
                }
            }
        }

        if (battlePanel.getCurrentRound() == 3) {
            for (int i = 0; i < luButtons.size(); i++) {
                if (battlePanel.getCurrentX() > 395 && battlePanel.getCurrentX() < 610 && battlePanel.getCurrentY() > 226 + i * 30 && battlePanel.getCurrentY() < 226 + (i + 1) * 30) {
                    introduceImage = luIntros.get(i);
                    this.introY = 226 + i * 30;
                    isDrawIntro = true;
                }
            }
        }

    }

    /**
     * 检查鼠标是否点击菜单;
     */
    public void checkPressed() {
        for (GameButton button : skillButtons) {
            button.isPressedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
        returnButton.isPressedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
    }

    /**
     * 检查是否松开鼠标;
     */
    public void checkReleased() {
        if (returnButton.clicked) {
            isDraw = false;
            battlePanel.getCommand().setDrawn(true);
        }
        if (battlePanel.getCurrentRound() == 1) {
            if (skillButtons.get(0).clicked) {
                afterClicked(2, true, 0);
            }
            if (skillButtons.get(1).clicked) {
                afterClicked(3, true, 0);
            }
            if (ZhangXiaoFan.skillNumber >= 3) {
                if (skillButtons.get(2).clicked) {
                    afterClicked(4, true, 0);
                }
            }
            if (ZhangXiaoFan.skillNumber >= 4) {
                if (skillButtons.get(3).clicked) {
                    afterClicked(5, false, 8);
                }
            }
            if (ZhangXiaoFan.skillNumber >= 5) {
                if (skillButtons.get(4).clicked) {
                    afterClicked(6, false, 8);
                }
            }
        }
        if (battlePanel.getCurrentRound() == 2) {
            if (skillButtons.get(0).clicked) {
                afterClicked(2, true, 0);
            }
            if (skillButtons.get(1).clicked) {
                afterClicked(3, true, 0);
            }
            if (YuJie.skillNumber >= 3) {
                if (skillButtons.get(2).clicked) {
                    afterClicked(4, false, 8);
                }
            }
            if (YuJie.skillNumber >= 4) {
                if (skillButtons.get(3).clicked) {
                    afterClicked(5, false, 4);
                }
            }
            if (YuJie.skillNumber >= 5) {
                if (skillButtons.get(4).clicked) {
                    afterClicked(6, true, 0);
                }
            }
        }
        if (battlePanel.getCurrentRound() == 3) {
            if (skillButtons.get(0).clicked) {
                afterClicked(2, false, 8);
            }
            if (skillButtons.get(1).clicked) {
                afterClicked(3, false, 8);
            }
            if (LuXueQi.getSkillNumber() >= 3) {
                if (skillButtons.get(2).clicked) {
                    afterClicked(4, false, 8);
                }
            }
            if (LuXueQi.getSkillNumber() >= 4) {
                if (skillButtons.get(3).clicked) {
                    afterClicked(5, true, 0);
                }
            }
            if (LuXueQi.getSkillNumber() >= 5) {
                if (skillButtons.get(4).clicked) {
                    afterClicked(6, false, 8);
                }
            }
        }
        for (GameButton button : skillButtons) {
            button.isRelesedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
        }
        returnButton.isRelesedButton(battlePanel.getCurrentX(), battlePanel.getCurrentY());
    }

    //画出菜单
    public void drawSkillMenu(Graphics g) {
        if (isDraw) {
            g.drawImage(background, x, y, battlePanel);
            for (GameButton button : skillButtons) {
                button.drawButton(g);
            }
            returnButton.drawButton(g);

            if (isDrawIntro) {
                g.drawImage(introduceImage, introX, introY, battlePanel);
            }
        }
    }

    public Image getBackground() {
        return background;
    }

    public void setBackground(Image background) {
        this.background = background;
    }

    public GameButton getSkillButton() {
        return skillButton;
    }

    public void setSkillButton(GameButton skillButton) {
        this.skillButton = skillButton;
    }

    public List<GameButton> getZhangButtons() {
        return zhangButtons;
    }

    public void setZhangButtons(List<GameButton> zhangButtons) {
        this.zhangButtons = zhangButtons;
    }

    public List<GameButton> getWenButtons() {
        return wenButtons;
    }

    public void setWenButtons(List<GameButton> wenButtons) {
        this.wenButtons = wenButtons;
    }

    public List<GameButton> getLuButtons() {
        return luButtons;
    }

    public void setLuButtons(List<GameButton> luButtons) {
        this.luButtons = luButtons;
    }

    public List<GameButton> getSkillButtons() {
        return skillButtons;
    }

    public void setSkillButtons(List<GameButton> skillButtons) {
        this.skillButtons = skillButtons;
    }

    public GameButton getReturnButton() {
        return returnButton;
    }

    public void setReturnButton(GameButton returnButton) {
        this.returnButton = returnButton;
    }

    public List<Image> getZhangImages() {
        return zhangImages;
    }

    public void setZhangImages(List<Image> zhangImages) {
        this.zhangImages = zhangImages;
    }

    public List<Image> getWenImages() {
        return wenImages;
    }

    public void setWenImages(List<Image> wenImages) {
        this.wenImages = wenImages;
    }

    public List<Image> getLuImages() {
        return luImages;
    }

    public void setLuImages(List<Image> luImages) {
        this.luImages = luImages;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public Image getIntroduceImage() {
        return introduceImage;
    }

    public void setIntroduceImage(Image introduceImage) {
        this.introduceImage = introduceImage;
    }

    public int getIntroX() {
        return introX;
    }

    public void setIntroX(int introX) {
        this.introX = introX;
    }

    public int getIntroY() {
        return introY;
    }

    public void setIntroY(int introY) {
        this.introY = introY;
    }

    public boolean isDrawIntro() {
        return isDrawIntro;
    }

    public void setDrawIntro(boolean drawIntro) {
        isDrawIntro = drawIntro;
    }

    public List<Image> getZhangIntros() {
        return zhangIntros;
    }

    public void setZhangIntros(List<Image> zhangIntros) {
        this.zhangIntros = zhangIntros;
    }

    public List<Image> getWenIntros() {
        return wenIntros;
    }

    public void setWenIntros(List<Image> wenIntros) {
        this.wenIntros = wenIntros;
    }

    public List<Image> getLuIntros() {
        return luIntros;
    }

    public void setLuIntros(List<Image> luIntros) {
        this.luIntros = luIntros;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
