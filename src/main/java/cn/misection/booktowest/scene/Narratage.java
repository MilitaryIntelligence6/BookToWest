package cn.misection.booktowest.scene;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import cn.misection.booktowest.util.Reader;

/**
 * @author javaman
 */
public class Narratage {
    private ScenePanel scene;
    // 背景图片
    private List<Image> backImages1 = new ArrayList<>();
    private List<String> narratage;
    private int index = 0;
    private Timer background = new Timer(180, new Background());
    private Timer wordRun = new Timer(50, new WordRun());
    // 最大行数
    private int maxLine = 10;
    private Color color1;
    private Font font;
    // 记载第几句话
    private int count0;
    // 记录第几个字
    private int count1;
    // 记录第几行
    private int count2;
    private String[] bufferedText;
    private int fontSize;
    private boolean isRun;
    private boolean isNarratage;
    private boolean narratageOver;

    public Narratage(ScenePanel scene, List<String> narratage) {
        this.narratage = narratage;
        if (narratage == null) {
            narratageOver = true;
        }
        this.scene = scene;
        for (int i = 2; i <= 53; i++) {
            backImages1.add(Reader
                    .readImage("backImages//NarratageBackImages//all_magic_21-"
                            + i + ".png"));
        }
    }

    public void drawNarratage(Graphics g) {
        g.drawImage(backImages1.get(index), 0, 0, 1024, 640, 0, 0, 639, 395,
                scene);
        g.setColor(color1);
        g.setFont(font);
        for (int i = 0; i < maxLine; i++) {
            // 转换当前字体top位置
            Double top = new Double((double) fontSize
                    * (3.0D + 2.0D * (double) i));
            if (bufferedText[i] != null) {
                g.drawString(bufferedText[i], 50, top.intValue());
            }
        }
    }

    public void init() {
        bufferedText = new String[maxLine];
        fontSize = 20;
        font = new Font("文鼎粗钢笔行楷", Font.BOLD, fontSize);
        color1 = Color.WHITE;
        background.start();
        wordRun.start();
    }

    public void stop() {
        count0 = 0;
        count1 = 0;
        count2 = 0;
        wordRun.stop();
        background.stop();
        isNarratage = false;
        narratageOver = true;
        narratage.clear();
    }

    public void begin() {
        // 清除所有缓存数据。
        for (int i = 0; i < bufferedText.length; i++) {
            bufferedText[i] = null;
            count2 = 0;
        }
    }

    public void checkNarratage() {
        if (!narratage.isEmpty()) {
            if (!isRun) {
                init();
                isRun = true;
                isNarratage = true;
            }
        }
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public List<Image> getBackImages1() {
        return backImages1;
    }

    public void setBackImages1(List<Image> backImages1) {
        this.backImages1 = backImages1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Timer getBackground() {
        return background;
    }

    public void setBackground(Timer background) {
        this.background = background;
    }

    public Timer getWordRun() {
        return wordRun;
    }

    public void setWordRun(Timer wordRun) {
        this.wordRun = wordRun;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getCount0() {
        return count0;
    }

    public void setCount0(int count0) {
        this.count0 = count0;
    }

    public int getCount1() {
        return count1;
    }

    public void setCount1(int count1) {
        this.count1 = count1;
    }

    public int getCount2() {
        return count2;
    }

    public void setCount2(int count2) {
        this.count2 = count2;
    }

    public String[] getBufferedText() {
        return bufferedText;
    }

    public void setBufferedText(String[] bufferedText) {
        this.bufferedText = bufferedText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public boolean isNarratage() {
        return isNarratage;
    }

    public void setNarratage(boolean narratage) {
        isNarratage = narratage;
    }

    public boolean isNarratageOver() {
        return narratageOver;
    }

    public void setNarratageOver(boolean narratageOver) {
        this.narratageOver = narratageOver;
    }

    class WordRun implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (count1 == narratage.get(count0).length()) {
                bufferedText[count2] = narratage.get(count0).substring(0,
                        count1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                if (count2 == maxLine) {
                    begin();
                }
                count0++;
                count2++;
                count1 = 0;
            } else {
                count1++;
                bufferedText[count2] = narratage.get(count0).substring(0,
                        count1);
            }
            if (count0 == narratage.size()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                stop();
            }
        }

    }

    class Background implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (index < backImages1.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
    }

    public List<String> getNarratage() {
        return narratage;
    }

    public void setNarratage(List<String> narratage) {
        this.narratage = narratage;
    }
}
