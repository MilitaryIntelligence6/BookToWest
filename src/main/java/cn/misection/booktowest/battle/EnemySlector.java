package cn.misection.booktowest.battle;

/**
 * @author javaman
 * 怪物选择器类;
 */
public class EnemySlector {

    /**
     * 是否可以选择;
     */
    private boolean selectable;

    /**
     * 怪物1的选择范围;
     */
    private int x1;

    private int y1;

    private int width1;

    private int height1;

    /**
     * 怪物2的选择范围;
     */
    private int x2;

    private int y2;

    private int width2;

    private int height2;

    /**
     * 怪物3的选择范围;
     */
    private int x3;

    private int y3;

    private int width3;

    private int height3;

    /**
     * 战斗面板;
     */
    private BattlePanel battlePanel;

    public EnemySlector(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
        selectable = false;
        //怪物1
        if (battlePanel.getEnemyOne() != null) {
            //得到图片的宽和高
            x1 = battlePanel.getEnemyOne().getX();
            y1 = battlePanel.getEnemyOne().getY();
            width1 = battlePanel.getEnemyOne().getImages().get(0).getWidth(battlePanel);
            height1 = battlePanel.getEnemyOne().getImages().get(0).getHeight(battlePanel);
        }

        //怪物2
        if (battlePanel.getEnemyTwo() != null) {
            //得到图片的宽和高
            x2 = battlePanel.getEnemyTwo().getX();
            y2 = battlePanel.getEnemyTwo().getY();
            width2 = battlePanel.getEnemyTwo().getImages().get(0).getWidth(battlePanel);
            height2 = battlePanel.getEnemyTwo().getImages().get(0).getHeight(battlePanel);
        }

        //怪物3
        if (battlePanel.getEnemyThree() != null) {
            //得到图片的宽和高
            x3 = battlePanel.getEnemyThree().getX();
            y3 = battlePanel.getEnemyThree().getY();
            width3 = battlePanel.getEnemyThree().getImages().get(0).getWidth(battlePanel);
            height3 = battlePanel.getEnemyThree().getImages().get(0).getHeight(battlePanel);
        }
    }

    /**
     * 判断是否移动到某个敌人区域内;
     * @param currentX
     * @param currentY
     */
    public void checkMoveIn(int currentX, int currentY) {
        if (selectable) {
            if (battlePanel.getEnemyOne() != null) {
                if (currentX >= x1 && currentX <= x1 + width1 && currentY >= y1 && currentY <= y1 + height1) {
                    battlePanel.getEnemyOne().setCurrentImage(battlePanel.getEnemyOne().getSelectedImage());
                    battlePanel.getEnemyOne().setStop(true);
                } else {
                    battlePanel.getEnemyOne().setStop(false);
                }
            }
            if (battlePanel.getEnemyTwo() != null) {
                if (currentX >= x2 && currentX <= x2 + width2 && currentY >= y2 && currentY <= y2 + height2) {
                    battlePanel.getEnemyTwo().setCurrentImage(battlePanel.getEnemyTwo().getSelectedImage());
                    battlePanel.getEnemyTwo().setStop(true);
                } else {
                    battlePanel.getEnemyTwo().setStop(false);
                }
            }
            if (battlePanel.getEnemyThree() != null) {
                if (currentX >= x3 && currentX <= x3 + width3 && currentY >= y3 && currentY <= y3 + height1) {
                    battlePanel.getEnemyThree().setCurrentImage(battlePanel.getEnemyThree().getSelectedImage());
                    battlePanel.getEnemyThree().setStop(true);
                } else {
                    battlePanel.getEnemyThree().setStop(false);
                }
            }
        }
    }

    /**
     * 判断是否点击了某个敌人区域;
     * @param currentX
     * @param currentY
     */
    public void checkClick(int currentX, int currentY) {
        if (selectable) {
            //检查怪物1
            if (battlePanel.getEnemyOne() != null) {
                if (currentX >= x1 && currentX <= x1 + width1 && currentY >= y1 && currentY <= y1 + height1) {
                    battlePanel.setCurrentBeAttacked(5);
                    selectable = false;
                    //恢复动态
                    battlePanel.getEnemyOne().setDraw(true);
                    battlePanel.getEnemyOne().setStop(false);
                }
            }

            //检查怪物2
            if (battlePanel.getEnemyTwo() != null) {
                if (currentX >= x2 && currentX <= x2 + width2 && currentY >= y2 && currentY <= y2 + height2) {
                    battlePanel.setCurrentBeAttacked(6);
                    selectable = false;
                    //恢复动态
                    battlePanel.getEnemyTwo().setDraw(true);
                    battlePanel.getEnemyTwo().setStop(false);
                }
            }

            //检查怪物3
            if (battlePanel.getEnemyThree() != null) {
                if (currentX >= x3 && currentX <= x3 + width3 && currentY >= y3 && currentY <= y3 + height1) {
                    battlePanel.setCurrentBeAttacked(7);
                    selectable = false;
                    //恢复动态
                    battlePanel.getEnemyThree().setDraw(true);
                    battlePanel.getEnemyThree().setStop(false);
                }
            }

        }
    }

    public boolean isSelectable() {
        return selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getWidth1() {
        return width1;
    }

    public void setWidth1(int width1) {
        this.width1 = width1;
    }

    public int getHeight1() {
        return height1;
    }

    public void setHeight1(int height1) {
        this.height1 = height1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public int getWidth2() {
        return width2;
    }

    public void setWidth2(int width2) {
        this.width2 = width2;
    }

    public int getHeight2() {
        return height2;
    }

    public void setHeight2(int height2) {
        this.height2 = height2;
    }

    public int getX3() {
        return x3;
    }

    public void setX3(int x3) {
        this.x3 = x3;
    }

    public int getY3() {
        return y3;
    }

    public void setY3(int y3) {
        this.y3 = y3;
    }

    public int getWidth3() {
        return width3;
    }

    public void setWidth3(int width3) {
        this.width3 = width3;
    }

    public int getHeight3() {
        return height3;
    }

    public void setHeight3(int height3) {
        this.height3 = height3;
    }

    public BattlePanel getBattlePanel() {
        return battlePanel;
    }

    public void setBattlePanel(BattlePanel battlePanel) {
        this.battlePanel = battlePanel;
    }
}
