package cn.misection.booktowest.battle;


import cn.misection.booktowest.util.Reader;

import java.awt.*;
import java.util.ArrayList;

public class SkillAnimation {
    //��ǰͼƬ����
    private Image currentImage;
    //ͼƬ����
    private ArrayList<Image> Images = new ArrayList<Image>();
    //���
    private int code;
    //����
    private int length;
    //����λ��
    private int x;
    private int y;
    //��ʼλ��
    private int initialX;
    private int initialY;
    //ս���������
    private BattlePanel bp;
    //�Ƿ񻭳�
    private boolean isDraw;
    //�Ƿ�ֹͣ
    private boolean isStop;
    //�Ƿ񷢶��������ź�
    private boolean isOver;

    //����������ʼ���������ı��
    private int beAttackedCode;
    //�����������ŵĴ���
    private int beAttackedTimes;
    //���ܶ��������׶ε��ź�
//��λͼ
    private int runCode;
    //����ͼ
    private int attackCode;
    //����ͼ
    private int withdrawCode;

    //����ڹ���1(�м�)��ƫ����
    private int offsetTo1;
    //����ڹ���2(�Ϸ�)��ƫ����
    private int offsetTo2;
    //����ڹ���3(�·�)��ƫ����
    private int offsetTo3;

    private String name;
    ;

    //���췽��
    public SkillAnimation(String name, int length, int x, int y, BattlePanel bp, int beAttackedCode,
                          int beAttackedTimes, int runCode, int attackCode, int withdrawCode, int offsetTo1,
                          int offsetTo2, int offsetTo3) {
        this.bp = bp;

        this.name = name;
        this.x = x;
        this.y = y;

        this.initialX = x;
        this.initialY = y;

        this.length = length;
        this.beAttackedCode = beAttackedCode;
        this.beAttackedTimes = beAttackedTimes;
        this.runCode = runCode;
        this.attackCode = attackCode;
        this.withdrawCode = withdrawCode;
        this.offsetTo1 = offsetTo1;
        this.offsetTo2 = offsetTo2;
        this.offsetTo3 = offsetTo3;

        isDraw = false;
        isStop = true;
        isOver = false;
    }

    public SkillAnimation(BattlePanel bp) {
        this.bp = bp;
        isDraw = false;
        isStop = true;
        isOver = false;
    }

    //���÷���
    public void set(String name, int length, int x, int y, int beAttackedCode, int beAttackedTimes, int runCode,
                    int attackCode, int withdrawCode, int offsetTo1, int offsetTo2, int offsetTo3) {
        this.name = name;
        this.x = x;
        this.y = y;

        this.initialX = x;
        this.initialY = y;

        this.length = length;
        this.beAttackedCode = beAttackedCode;
        this.beAttackedTimes = beAttackedTimes;
        this.runCode = runCode;
        this.attackCode = attackCode;
        this.withdrawCode = withdrawCode;
        this.offsetTo1 = offsetTo1;
        this.offsetTo2 = offsetTo2;
        this.offsetTo3 = offsetTo3;

        currentImage = Reader.readImage("image/���ܶ���/" + name + "/1.png");
    }

    //��������
    public void drawAnimation(Graphics g) {
        if (isDraw && currentImage != null) {
            g.drawImage(currentImage, x, y, bp);
        }
    }

    //����
    public void update() {
        if (!isStop) {
            //����Ϊ����һ
            if (bp.getCurrentBeAttacked() == 5 || bp.getCurrentBeAttacked() == 1) {
                if (code < length) {
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 5) {
                    //�ù�����ʾ����Ч��
                    bp.getEnemyOne().setDraw(false);
                    bp.getEnemyOne().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyOne().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyOne().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 1) {
                    //����С����ʾ����Ч��
                    bp.getZxf().isDraw = false;
                    bp.getZxf().beAttackedAnimation.getTimes(beAttackedTimes);
                    bp.getZxf().beAttackedAnimation.setDraw(true);
                    bp.getZxf().beAttackedAnimation.setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //ֹͣ����
                    isStop = true;
                    //���ٻ���
                    isDraw = false;
                    //�������������ź�
                    isOver = true;
                }
            }//��������һ����

            //����Ϊ���˶�
            if (bp.getCurrentBeAttacked() == 6 || bp.getCurrentBeAttacked() == 2) {
                if (code < length) {
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 6) {
                    //�ù�����ʾ����Ч��
                    bp.getEnemyTwo().setDraw(false);
                    bp.getEnemyTwo().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyTwo().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyTwo().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 2) {
                    //��������ʾ����Ч��
                    bp.getYj().isDraw = false;
                    bp.getYj().beAttackedAnimation.getTimes(beAttackedTimes);
                    bp.getYj().beAttackedAnimation.setDraw(true);
                    bp.getYj().beAttackedAnimation.setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo2 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo2 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //ֹͣ����
                    isStop = true;
                    //���ٻ���
                    isDraw = false;
                    //�������������ź�
                    isOver = true;
                }
            }//��������2����

            //����Ϊ������
            if (bp.getCurrentBeAttacked() == 7 || bp.getCurrentBeAttacked() == 3) {
                if (code < length) {
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 7) {
                    //MusicReader.readmusic("����.wav");
                    //�ù�����ʾ����Ч��
                    bp.getEnemyThree().setDraw(false);
                    bp.getEnemyThree().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getEnemyThree().getBeAttackedAnimation().setDraw(true);
                    bp.getEnemyThree().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode && bp.getCurrentBeAttacked() == 3) {
                    //��½ѩ����ʾ����Ч��
                    bp.getLxq().setIsDraw(false);
                    bp.getLxq().getBeAttackedAnimation().getTimes(beAttackedTimes);
                    bp.getLxq().getBeAttackedAnimation().setDraw(true);
                    bp.getLxq().getBeAttackedAnimation().setStop(false);
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo3 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo3 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //ֹͣ����
                    isStop = true;
                    //���ٻ���
                    isDraw = false;
                    //�������������ź�
                    isOver = true;
                }
            }//��������3����

            //����Ϊ���˵�ȫ��
            if (bp.getCurrentBeAttacked() == 8) {
                if (code < length) {
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode) {
                    //MusicReader.readmusic("����.wav");
                    for (Enemy enemy : bp.getEnemyList()) {
                        enemy.setDraw(false);
                        enemy.getBeAttackedAnimation().getTimes(beAttackedTimes);
                        enemy.getBeAttackedAnimation().setDraw(true);
                        enemy.getBeAttackedAnimation().setStop(false);
                    }
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //ֹͣ����
                    isStop = true;
                    //���ٻ���
                    isDraw = false;
                    //�������������ź�
                    isOver = true;
                }
            }

            //����Ϊ�ҷ�ȫ��
            if (bp.getCurrentBeAttacked() == 4) {
                if (code < length) {
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code == beAttackedCode) {
                    for (Hero hero : bp.getHeroes()) {
                        if (!hero.isDead()) {
                            hero.setIsDraw(false);
                            hero.getBeAttackedAnimation().getTimes(beAttackedTimes);
                            hero.getBeAttackedAnimation().setDraw(true);
                            hero.getBeAttackedAnimation().setStop(false);
                        }
                    }
                    currentImage = Reader.readImage("image/���ܶ���/" + name + "/" + (code + 1) + ".png");
                    code++;
                }
                if (code < runCode) {
                    y -= Math.round(offsetTo1 / runCode);
                }
                if (attackCode <= code && code < withdrawCode) {
                    y += Math.round(offsetTo1 / (withdrawCode - attackCode));
                }
                if (code == length) {
                    code = 0;
                    currentImage = null;
                    x = initialX;
                    y = initialY;
                    //ֹͣ����
                    isStop = true;
                    //���ٻ���
                    isDraw = false;
                    //�������������ź�
                    isOver = true;
                }
            }
        }
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public ArrayList<Image> getImages() {
        return Images;
    }

    public void setImages(ArrayList<Image> images) {
        Images = images;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public int getInitialX() {
        return initialX;
    }

    public void setInitialX(int initialX) {
        this.initialX = initialX;
    }

    public int getInitialY() {
        return initialY;
    }

    public void setInitialY(int initialY) {
        this.initialY = initialY;
    }

    public BattlePanel getBp() {
        return bp;
    }

    public void setBp(BattlePanel bp) {
        this.bp = bp;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public int getBeAttackedCode() {
        return beAttackedCode;
    }

    public void setBeAttackedCode(int beAttackedCode) {
        this.beAttackedCode = beAttackedCode;
    }

    public int getBeAttackedTimes() {
        return beAttackedTimes;
    }

    public void setBeAttackedTimes(int beAttackedTimes) {
        this.beAttackedTimes = beAttackedTimes;
    }

    public int getRunCode() {
        return runCode;
    }

    public void setRunCode(int runCode) {
        this.runCode = runCode;
    }

    public int getAttackCode() {
        return attackCode;
    }

    public void setAttackCode(int attackCode) {
        this.attackCode = attackCode;
    }

    public int getWithdrawCode() {
        return withdrawCode;
    }

    public void setWithdrawCode(int withdrawCode) {
        this.withdrawCode = withdrawCode;
    }

    public int getOffsetTo1() {
        return offsetTo1;
    }

    public void setOffsetTo1(int offsetTo1) {
        this.offsetTo1 = offsetTo1;
    }

    public int getOffsetTo2() {
        return offsetTo2;
    }

    public void setOffsetTo2(int offsetTo2) {
        this.offsetTo2 = offsetTo2;
    }

    public int getOffsetTo3() {
        return offsetTo3;
    }

    public void setOffsetTo3(int offsetTo3) {
        this.offsetTo3 = offsetTo3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


