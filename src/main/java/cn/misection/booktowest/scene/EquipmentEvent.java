package cn.misection.booktowest.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cn.misection.booktowest.util.Reader;
import cn.misection.booktowest.media.*;

public class EquipmentEvent {
    private ScenePanel scene;
    private int x_presentImage;
    private Timer presentImageMove = new Timer(50, new PresentImage());
    private Timer wordsRun = new Timer(100, new WordsRun());
    private String bufferedText;
    private String text;
    private int count_word;
    private boolean isDrawString;
    private List<String[]> treasureBox;
    private List<TreasureBox> treasureBoxes;

    public EquipmentEvent(ScenePanel scene, List<String[]> treasureBox) {
        this.scene = scene;
        this.treasureBox = treasureBox;
        if (treasureBox != null) {
            treasureBoxes = new ArrayList<>();
            for (int i = 0; i < treasureBox.size(); i++) {
                TreasureBox box = new TreasureBox(treasureBox.get(i)[0],
                        treasureBox.get(i)[1], this);
                treasureBoxes.add(box);
            }
        }
    }

    // 画出得到物品的对话框的函数
    public void drawString(String s) {
        x_presentImage = -320;
        bufferedText = null;
        count_word = 0;
        text = s;
        isDrawString = true;
        presentImageMove.start();
        MusicReader.readMusic("Clip750.wav");
    }

    // 画宝箱
    public void drawTreasureBox(Graphics g) {
        if (treasureBox != null) {
            for (int i = 0; i < treasureBoxes.size(); i++) {
                treasureBoxes.get(i).paintBox(g);
            }
        }
    }

    // 轮询宝箱
    public void checBoxes(int x, int y) {
        if (treasureBox != null) {
            for (int i = 0; i < treasureBoxes.size(); i++) {
                treasureBoxes.get(i).checkHero(x, y);
            }
        }
    }

    public void keyPressed(int keyCode) {
        if (treasureBox != null) {
            for (int i = 0; i < treasureBoxes.size(); i++) {
                treasureBoxes.get(i).keyPressed(keyCode);
            }
        }
    }

    // 画出提示信息
    public void drawPresentation(Graphics g) {
        if (isDrawString) {
            g.drawImage(Reader.readImage("dialogue//提示框.png"), x_presentImage,
                    288, scene);
            if (bufferedText != null) {
                g.setColor(Color.red);
                g.setFont(new Font("宋体", Font.BOLD, 30));
                g.drawString(bufferedText, x_presentImage + 20, 320);
            }
        }
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public int getX_presentImage() {
        return x_presentImage;
    }

    public void setX_presentImage(int x_presentImage) {
        this.x_presentImage = x_presentImage;
    }

    public Timer getPresentImageMove() {
        return presentImageMove;
    }

    public void setPresentImageMove(Timer presentImageMove) {
        this.presentImageMove = presentImageMove;
    }

    public Timer getWordsRun() {
        return wordsRun;
    }

    public void setWordsRun(Timer wordsRun) {
        this.wordsRun = wordsRun;
    }

    public String getBufferedText() {
        return bufferedText;
    }

    public void setBufferedText(String bufferedText) {
        this.bufferedText = bufferedText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getCount_word() {
        return count_word;
    }

    public void setCount_word(int count_word) {
        this.count_word = count_word;
    }

    public boolean isDrawString() {
        return isDrawString;
    }

    public void setDrawString(boolean drawString) {
        isDrawString = drawString;
    }

    public List<String[]> getTreasureBox() {
        return treasureBox;
    }

    public void setTreasureBox(List<String[]> treasureBox) {
        this.treasureBox = treasureBox;
    }

    public List<TreasureBox> getTreasureBoxes() {
        return treasureBoxes;
    }

    public void setTreasureBoxes(List<TreasureBox> treasureBoxes) {
        this.treasureBoxes = treasureBoxes;
    }

    // 提示对话框的移动
    class PresentImage implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (x_presentImage <= 352) {
                x_presentImage += 32;
            }
            if (x_presentImage == 352) {
                wordsRun.start();
                presentImageMove.stop();
            }
            if (x_presentImage > 352 && x_presentImage <= 1024) {
                x_presentImage += 32;
            } else if (x_presentImage >= 1024) {
                presentImageMove.stop();
            }
        }

    }

    // 提示信息的逐行打印
    class WordsRun implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (count_word < text.length()) {
                count_word++;
                bufferedText = text.substring(0, count_word);
            } else {
                wordsRun.stop();
                presentImageMove.start();
            }
        }

    }
}
