package cn.misection.booktowest.battle;

//怪物选择器类
public class EnemySlector {
    //是否可以选择
    private boolean isSlectable;
    //怪物1的选择范围
    private int x1;
    private int y1;
    private int width1;
    private int height1;
    //怪物2的选择范围
    private int x2;
    private int y2;
    private int width2;
    private int height2;
    //怪物3的选择范围
    private int x3;
    private int y3;
    private int width3;
    private int height3;

    //战斗面板
    private BattlePanel bp;

    //构造方法
    public EnemySlector(BattlePanel bp) {
        this.bp = bp;
        isSlectable = false;
        //怪物1
        if (bp.getEnemyOne() != null) {
            //得到图片的宽和高
            x1 = bp.getEnemyOne().getX();
            y1 = bp.getEnemyOne().getY();
            width1 = bp.getEnemyOne().getImages().get(0).getWidth(bp);
            height1 = bp.getEnemyOne().getImages().get(0).getHeight(bp);
        }

        //怪物2
        if (bp.getEnemyTwo() != null) {
            //得到图片的宽和高
            x2 = bp.getEnemyTwo().getX();
            y2 = bp.getEnemyTwo().getY();
            width2 = bp.getEnemyTwo().getImages().get(0).getWidth(bp);
            height2 = bp.getEnemyTwo().getImages().get(0).getHeight(bp);
        }

        //怪物3
        if (bp.getEnemyThree() != null) {
            //得到图片的宽和高
            x3 = bp.getEnemyThree().getX();
            y3 = bp.getEnemyThree().getY();
            width3 = bp.getEnemyThree().getImages().get(0).getWidth(bp);
            height3 = bp.getEnemyThree().getImages().get(0).getHeight(bp);
        }
    }

    //判断是否移动到某个敌人区域内
    public void checkMoveIn(int currentX, int currentY) {
        if (isSlectable) {
            if (bp.getEnemyOne() != null) {
                if (currentX >= x1 && currentX <= x1 + width1 && currentY >= y1 && currentY <= y1 + height1) {
                    bp.getEnemyOne().setCurrentImage(bp.getEnemyOne().getSelectedImage());
                    bp.getEnemyOne().setStop(true);
                } else {
                    bp.getEnemyOne().setStop(false);
                }
            }
            if (bp.getEnemyTwo() != null) {
                if (currentX >= x2 && currentX <= x2 + width2 && currentY >= y2 && currentY <= y2 + height2) {
                    bp.getEnemyTwo().setCurrentImage(bp.getEnemyTwo().getSelectedImage());
                    bp.getEnemyTwo().setStop(true);
                } else {
                    bp.getEnemyTwo().setStop(false);
                }
            }
            if (bp.getEnemyThree() != null) {
                if (currentX >= x3 && currentX <= x3 + width3 && currentY >= y3 && currentY <= y3 + height1) {
                    bp.getEnemyThree().setCurrentImage(bp.getEnemyThree().getSelectedImage());
                    bp.getEnemyThree().setStop(true);
                } else {
                    bp.getEnemyThree().setStop(false);
                }
            }
        }
    }

    //判断是否点击了某个敌人区域
    public void checkClick(int currentX, int currentY) {
        if (isSlectable) {
            //检查怪物1
            if (bp.getEnemyOne() != null) {
                if (currentX >= x1 && currentX <= x1 + width1 && currentY >= y1 && currentY <= y1 + height1) {
                    bp.setCurrentBeAttacked(5);
                    isSlectable = false;
                    //恢复动态
                    bp.getEnemyOne().setDraw(true);
                    bp.getEnemyOne().setStop(false);
                }
            }

            //检查怪物2
            if (bp.getEnemyTwo() != null) {
                if (currentX >= x2 && currentX <= x2 + width2 && currentY >= y2 && currentY <= y2 + height2) {
                    bp.setCurrentBeAttacked(6);
                    isSlectable = false;
                    //恢复动态
                    bp.getEnemyTwo().setDraw(true);
                    bp.getEnemyTwo().setStop(false);
                }
            }

            //检查怪物3
            if (bp.getEnemyThree() != null) {
                if (currentX >= x3 && currentX <= x3 + width3 && currentY >= y3 && currentY <= y3 + height1) {
                    bp.setCurrentBeAttacked(7);
                    isSlectable = false;
                    //恢复动态
                    bp.getEnemyThree().setDraw(true);
                    bp.getEnemyThree().setStop(false);
                }
            }

        }
    }

    public boolean isSlectable() {
        return isSlectable;
    }

    public void setSlectable(boolean slectable) {
        isSlectable = slectable;
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

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }
}
