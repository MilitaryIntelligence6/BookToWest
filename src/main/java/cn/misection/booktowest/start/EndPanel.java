package cn.misection.booktowest.start;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import cn.misection.booktowest.util.*;

public class EndPanel extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    //背景图
    private Image back;
    private Image currentImage;
    private Image word;
    private Image blank;

    private int Width;
    private int Height;

    private BufferedImage bufferedImage;
    private Graphics bufferGraphics;

    private boolean isDraw;
    private boolean isStop;

    private int wordX;
    private int wordY;

    private int blankX;
    private int blankY;

    private int code;

    public EndPanel() {
        Width = 1024;
        Height = 640;
        setPreferredSize(new Dimension(Width, Height));
        bufferedImage = new BufferedImage(Width, Height, BufferedImage.TYPE_INT_ARGB);
        bufferGraphics = bufferedImage.getGraphics();
        back = Reader.readImage("sources/End/黑色背景.png");
        word = Reader.readImage("sources/End/结束字幕.png");
        blank = Reader.readImage("sources/End/结束侧栏.png");
        isDraw = false;
        isStop = true;
        wordX = 0;
        wordY = 640;
        blankX = 700;
        blankY = -1920;
        code = 1;

    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
        isDraw = true;
        isStop = false;
    }

    public void paint(Graphics g) {
        if (isDraw) {
            bufferGraphics.drawImage(back, 0, 0, this);
            bufferGraphics.drawImage(currentImage, 0, 0, this);
            bufferGraphics.drawImage(word, wordX, wordY, this);
            bufferGraphics.drawImage(blank, blankX, blankY, this);
            g.drawImage(bufferedImage, 0, 0, this);
        }
    }

    public void update() {
        if (!isStop) {
            if (code < 25) {
                currentImage = Reader.readImage("sources/End/" + code + ".jpg");
                code++;
            }
            if (code == 25) {
                currentImage = Reader.readImage("sources/End/" + code + ".jpg");
                code = 1;
            }

            if (wordY > -1280) {
                wordY -= 5;
                blankY += 5;
            }
            if (wordY == -1280) {
                isStop = true;
            }
            repaint();

        }
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(100);

            } catch (Exception e) {
                e.printStackTrace();
            }
            update();
        }
    }

    public Image getBack() {
        return back;
    }

    public void setBack(Image back) {
        this.back = back;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public Image getWord() {
        return word;
    }

    public void setWord(Image word) {
        this.word = word;
    }

    public Image getBlank() {
        return blank;
    }

    public void setBlank(Image blank) {
        this.blank = blank;
    }

    @Override
    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    @Override
    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Graphics getBufferGraphics() {
        return bufferGraphics;
    }

    public void setBufferGraphics(Graphics bufferGraphics) {
        this.bufferGraphics = bufferGraphics;
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

    public int getWordX() {
        return wordX;
    }

    public void setWordX(int wordX) {
        this.wordX = wordX;
    }

    public int getWordY() {
        return wordY;
    }

    public void setWordY(int wordY) {
        this.wordY = wordY;
    }

    public int getBlankX() {
        return blankX;
    }

    public void setBlankX(int blankX) {
        this.blankX = blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    public void setBlankY(int blankY) {
        this.blankY = blankY;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
