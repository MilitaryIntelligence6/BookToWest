package cn.misection.booktowest.scene;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cn.misection.booktowest.app.GameApplication;

import cn.misection.booktowest.shop.Money;
import cn.misection.booktowest.util.Reader;

/**
 * 实现选择（商店）selectShopPanel(NPC触发) 选择是否战斗selectBattlePanel(NPC触发)
 * 选择是否回答问题selectDialogue(NPC触发) 出题！question(selectDialogue触发)------回答正确后得到金钱！
 *
 * @author wanglizhi
 */
public class SelectEvent {
    private ScenePanel scene;
    private FightEvent fightEvent;
    private Image selectImage;
    private Image questionImage;
    private Image selectIcon;
    // 选择事件中的脚本数据(可以有多组但要一一对应)
    private List<String> selectShopPanel;
    private List<String> selectEquipmentShopPanel;
    private List<String[]> selectBattlePanel;
    private List<String[]> battle2;
    private List<String[]> selectQuestion;
    private List<List<String>> question;
    private List<String[]> answer;
    // 对话框相关的参数
    private int x_selectImage;
    private int y_selectImage;
    private int x1_questionImage;
    private int y1_questionImage;
    private int x2_questionImage;
    private int y2_questionImage;
    private String[] bufferedText;
    private int count_sentence = 1;
    private int count_word;
    private int count_bufferedSentence;
    private int maxLine = 20;
    private int maxLength = 22;
    private int fontSize = 20;
    private List<String> currentSentences;
    private Timer wordsRun = new Timer(30, new WordsRun());
    private Timer selectImageMove = new Timer(40, new SelectImageMove());
    private Timer questionImageMove = new Timer(50, new QuestionImageMove());
    private boolean shopSelect;
    private boolean equipmentSelect;
    private boolean battleSelect;
    private boolean questionSelect;
    private static List<String> mapName = new ArrayList<>();
    private static List<List<Boolean>> answeredRecorder = new ArrayList<>();
    private List<Boolean> haveAnswered = new ArrayList<>();
    private List<Boolean> haveFighted = new ArrayList<>();
    private boolean isQuestion;
    private boolean isAnswer;
    private int count_selectYesNo = 2;
    private int count_selectABCD;
    private int count_battle2;
    private int count_questionAndAnswer;
    // 与外界传输
    private boolean isSelect;
    private boolean haveEnteredTheScene;
    private int count_scene;

    public SelectEvent(ScenePanel scene, FightEvent fightEvent,
                       String fileName, List<String> selectShopPanel,
                       List<String> selectEquipmentShopPanel,
                       List<String[]> selectBattlePanel,
                       List<String[]> selectQuestion, List<String[]> battle2,
                       List<List<String>> question, List<String[]> answer) {
        this.scene = scene;
        this.fightEvent = fightEvent;
        this.selectShopPanel = selectShopPanel;
        this.selectEquipmentShopPanel = selectEquipmentShopPanel;
        this.selectBattlePanel = selectBattlePanel;
        this.selectQuestion = selectQuestion;
        this.question = question;
        this.battle2 = battle2;
        this.answer = answer;
        // 设置问题是否回答的信号
        if (question != null) {
            for (int i = 0; i < mapName.size(); i++) {
                if (mapName.get(i).equals(fileName)) {
                    haveEnteredTheScene = true;
                    count_scene = i;
                }
            }
            if (haveEnteredTheScene) {
                haveAnswered = answeredRecorder.get(count_scene);
                haveEnteredTheScene = false;
            } else {
                for (int i = 0; i < question.size(); i++) {
                    haveAnswered.add(false);
                }
                mapName.add(fileName);
                answeredRecorder.add(haveAnswered);
            }

        }
        // 选择战斗
        if (selectBattlePanel != null) {
            for (int i = 0; i < selectBattlePanel.size(); i++) {
                haveFighted.add(false);
            }
        }
        selectImage = Reader.readImage("dialogue//选择框.png");// 500*150
        questionImage = Reader.readImage("dialogue//问题框.png");// 500*500
        selectIcon = Reader.readImage("dialogue//icon.png");// 24*24
        bufferedText = new String[maxLine];
    }

    public static List<String> getMapName() {
        return mapName;
    }

    public static void setMapName(List<String> mapName) {
        SelectEvent.mapName = mapName;
    }

    public static List<List<Boolean>> getAnsweredRecorder() {
        return answeredRecorder;
    }

    public static void setAnsweredRecorder(List<List<Boolean>> answeredRecorder) {
        SelectEvent.answeredRecorder = answeredRecorder;
    }

    // 画图函数
    public void drawSelectImage(Graphics g) {
        if (shopSelect || battleSelect || questionSelect || equipmentSelect) {
            g.drawImage(selectImage, 262, 245, 262 + x_selectImage,
                    y_selectImage + 245, 0, 0, x_selectImage, y_selectImage,
                    scene);
            g.setColor(Color.CYAN);
            g.setFont(new Font("正楷", Font.BOLD, fontSize));
            for (int i = 0; i < bufferedText.length; i++) {
                if (bufferedText[i] != null) {
                    if (i == count_selectYesNo) {
                        g.drawImage(selectIcon, 292, 270 + 30 * i, scene);
                        g.setColor(Color.red);
                        g.drawString(bufferedText[i], 312, 285 + 30 * i);
                        g.setColor(Color.CYAN);
                    } else {
                        g.drawString(bufferedText[i], 292, 285 + 30 * i);
                    }
                }
            }
        } else if (isQuestion) {
            g.drawImage(questionImage, x1_questionImage, y1_questionImage,
                    x2_questionImage, y2_questionImage, x1_questionImage - 262,
                    y1_questionImage - 70, x2_questionImage - 262,
                    y2_questionImage - 70, scene);
            g.setColor(Color.CYAN);
            g.setFont(new Font("文鼎粗钢笔行楷", Font.BOLD, fontSize));
            for (int i = 0; i < bufferedText.length; i++) {
                if (bufferedText[i] != null) {
                    if (i == count_selectABCD) {
                        g.drawImage(selectIcon, 292, 83 + 30 * i, scene);
                        g.setColor(Color.red);
                        g.drawString(bufferedText[i], 312, 100 + 30 * i);
                        g.setColor(Color.CYAN);
                    } else {
                        g.drawString(bufferedText[i], 292, 100 + 30 * i);
                    }
                }
            }
        } else if (isAnswer) {
            g.drawImage(selectImage, 262, 245, 262 + x_selectImage,
                    y_selectImage + 245, 0, 0, x_selectImage, y_selectImage,
                    scene);
            g.setColor(Color.CYAN);
            g.setFont(new Font("正楷", Font.BOLD, fontSize));
            for (int i = 0; i < bufferedText.length; i++) {
                if (bufferedText[i] != null) {
                    g.drawString(bufferedText[i], 292, 285 + 30 * i);
                }
            }
        }
    }

    // 判断当前NPC是否存在选择事件需要处理
    public boolean checkSelectEvent(int npcNo) {
        if (selectShopPanel != null
                && npcNo == Integer.parseInt(selectShopPanel.get(0))) {
            if (!isSelect) {
                showSelectShopPanel();
            }
            return true;
        }
        if (selectEquipmentShopPanel != null
                && npcNo == Integer.parseInt(selectEquipmentShopPanel.get(0))) {
            if (!isSelect) {
                showSelectEquipmentShopPanel();
            }
            return true;
        }
        if (selectBattlePanel != null) {
            for (int i = 0; i < selectBattlePanel.size(); i++) {
                if (!haveFighted.get(i)) {
                    if (npcNo == Integer.parseInt(selectBattlePanel.get(i)[0])) {
                        if (!isSelect) {
                            showSelectBattlePanel(i);
                        }
                        count_battle2 = i;
                        return true;
                    }
                }
            }
        }
        if (selectQuestion != null) {
            if (isSelect) {
                return true;
            }
            for (int i = 0; i < selectQuestion.size(); i++) {
                if (!haveAnswered.get(i)) {
                    if (npcNo == Integer.parseInt(selectQuestion.get(i)[0])) {
                        if (!isSelect) {
                            showSelectQuestion(i);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 选择是否进入医院
    public void showSelectShopPanel() {
        isSelect = true;
        shopSelect = true;
        currentSentences = selectShopPanel;
        x_selectImage = 0;
        y_selectImage = 0;
        clear();
        count_selectYesNo = 2;
        maxLength = 22;
        selectImageMove.start();
    }

    // 选择是否进入装备商店
    public void showSelectEquipmentShopPanel() {
        isSelect = true;
        equipmentSelect = true;
        currentSentences = selectEquipmentShopPanel;
        x_selectImage = 0;
        y_selectImage = 0;
        clear();
        count_selectYesNo = 2;
        maxLength = 22;
        selectImageMove.start();
    }

    // 选择是否战斗
    public void showSelectBattlePanel(int battleNo) {
        isSelect = true;
        battleSelect = true;
        currentSentences = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            currentSentences.add(selectBattlePanel.get(battleNo)[i]);
        }
        x_selectImage = 0;
        y_selectImage = 0;
        clear();
        count_selectYesNo = 2;
        maxLength = 22;
        selectImageMove.start();
    }

    // 选择是否回答问题
    public void showSelectQuestion(int questionNo) {
        count_questionAndAnswer = questionNo;
        isSelect = true;
        questionSelect = true;
        currentSentences = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            currentSentences.add(selectQuestion.get(questionNo)[i]);
        }
        x_selectImage = 0;
        y_selectImage = 0;
        clear();
        count_selectYesNo = 2;
        maxLength = 22;
        selectImageMove.start();
    }

    // 回答问题阶段
    public void showQuestion(int questionNo) {
        isQuestion = true;
        currentSentences = question.get(questionNo);
        x1_questionImage = 512;
        y1_questionImage = 320;
        x2_questionImage = 512;
        y2_questionImage = 320;
        clear();
        maxLength = 44;
        count_selectABCD = question.get(questionNo).size() - 5;
        questionImageMove.start();
    }

    // 显示回答
    public void showAnswer(List<String> response) {
        isAnswer = true;
        currentSentences = response;
        x_selectImage = 0;
        y_selectImage = 0;
        clear();
        count_selectYesNo = 2;
        maxLength = 22;
        selectImageMove.start();
    }

    public void clear() {
        for (int i = 0; i < bufferedText.length; i++) {
            bufferedText[i] = null;
        }
        count_sentence = 1;
        count_word = 0;
        count_bufferedSentence = 0;
    }

    // 按键监听
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
            if (count_selectYesNo == 2) {
                count_selectYesNo = 3;
            } else if (count_selectYesNo == 3) {
                count_selectYesNo = 2;
            }
            if (isQuestion) {
                if (keyCode == KeyEvent.VK_DOWN) {
                    if (count_selectABCD < question
                            .get(count_questionAndAnswer).size() - 2) {
                        count_selectABCD++;
                    } else {
                        count_selectABCD = question
                                .get(count_questionAndAnswer).size() - 5;
                    }
                } else if (keyCode == KeyEvent.VK_UP) {
                    if (count_selectABCD > question
                            .get(count_questionAndAnswer).size() - 5) {
                        count_selectABCD--;
                    } else {
                        count_selectABCD = question
                                .get(count_questionAndAnswer).size() - 2;
                    }
                }
            }
        } else if (keyCode == KeyEvent.VK_ENTER) {
            if (shopSelect) {
                if (count_selectYesNo == 2) {
                    GameApplication.switchTo("shop");
                } else {
                    isSelect = false;
                    shopSelect = false;
                }
            } else if (equipmentSelect) {
                if (count_selectYesNo == 2) {
                    GameApplication.switchTo("equipmentShop");
                } else {
                    isSelect = false;
                    equipmentSelect = false;
                }
            } else if (battleSelect) {
                if (count_selectYesNo == 2) {
                    fightEvent.fight(battle2.get(count_battle2));
                    haveFighted.remove(count_battle2);
                    haveFighted.add(count_battle2, true);
                    GameApplication.switchTo("battle");
                    List<String> response = new ArrayList<>();
                    response.add(null);
                    response.add("既然身手那么好就别装嫩了!唉,看来以后我要收敛点了!  你们软件堂的人就是闷骚!");
                    showAnswer(response);
                    battleSelect = false;
                } else {
                    isSelect = false;
                    battleSelect = false;
                }
            } else if (questionSelect) {
                if (count_selectYesNo == 2) {
                    showQuestion(count_questionAndAnswer);
                    questionSelect = false;
                    isQuestion = true;
                } else {
                    isSelect = false;
                    questionSelect = false;
                }
            } else if (isQuestion) {
                if (count_selectABCD == Integer.parseInt(answer
                        .get(count_questionAndAnswer)[0])) {
                    // 回答正确，加钱
                    int i = 500 + (int) (500 * Math.random());
                    Money.addCoins(i);
                    scene.getEquipmentEvent().drawString("得到" + i + "个金币");
                    List<String> response = new ArrayList<>();
                    response.add(null);
                    response.add(answer.get(count_questionAndAnswer)[1]);
                    response.add(answer.get(count_questionAndAnswer)[3]);
                    isQuestion = false;
                    isAnswer = true;
                    showAnswer(response);
                    haveAnswered.remove(count_questionAndAnswer);
                    haveAnswered.add(count_questionAndAnswer, true);
                    answeredRecorder.remove(count_scene);
                    answeredRecorder.add(count_scene, haveAnswered);
                } else {
                    // 回答正确，加钱
                    int i = 500 + (int) (500 * Math.random());
                    Money.reduceCoins(i);
                    scene.getEquipmentEvent().drawString("回答错误，扣掉" + i + "个金币");
                    List<String> response = new ArrayList<>();
                    response.add(null);
                    response.add(answer.get(count_questionAndAnswer)[1]);
                    response.add(answer.get(count_questionAndAnswer)[2]);
                    isQuestion = false;
                    isAnswer = true;
                    showAnswer(response);
                    haveAnswered.remove(count_questionAndAnswer);
                    haveAnswered.add(count_questionAndAnswer, true);
                    answeredRecorder.remove(count_scene);
                    answeredRecorder.add(count_scene, haveAnswered);
                }
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            if (isAnswer) {
                isSelect = false;
                isAnswer = false;
            }
        }
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public FightEvent getFightEvent() {
        return fightEvent;
    }

    public void setFightEvent(FightEvent fightEvent) {
        this.fightEvent = fightEvent;
    }

    public Image getSelectImage() {
        return selectImage;
    }

    public void setSelectImage(Image selectImage) {
        this.selectImage = selectImage;
    }

    public Image getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(Image questionImage) {
        this.questionImage = questionImage;
    }

    public Image getSelectIcon() {
        return selectIcon;
    }

    public void setSelectIcon(Image selectIcon) {
        this.selectIcon = selectIcon;
    }

    public List<String> getSelectShopPanel() {
        return selectShopPanel;
    }

    public void setSelectShopPanel(List<String> selectShopPanel) {
        this.selectShopPanel = selectShopPanel;
    }

    public List<String> getSelectEquipmentShopPanel() {
        return selectEquipmentShopPanel;
    }

    public void setSelectEquipmentShopPanel(List<String> selectEquipmentShopPanel) {
        this.selectEquipmentShopPanel = selectEquipmentShopPanel;
    }

    public List<String[]> getSelectBattlePanel() {
        return selectBattlePanel;
    }

    public void setSelectBattlePanel(List<String[]> selectBattlePanel) {
        this.selectBattlePanel = selectBattlePanel;
    }

    public List<String[]> getBattle2() {
        return battle2;
    }

    public void setBattle2(List<String[]> battle2) {
        this.battle2 = battle2;
    }

    public List<String[]> getSelectQuestion() {
        return selectQuestion;
    }

    public void setSelectQuestion(List<String[]> selectQuestion) {
        this.selectQuestion = selectQuestion;
    }

    public List<List<String>> getQuestion() {
        return question;
    }

    public void setQuestion(List<List<String>> question) {
        this.question = question;
    }

    public List<String[]> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String[]> answer) {
        this.answer = answer;
    }

    public int getX_selectImage() {
        return x_selectImage;
    }

    public void setX_selectImage(int x_selectImage) {
        this.x_selectImage = x_selectImage;
    }

    public int getY_selectImage() {
        return y_selectImage;
    }

    public void setY_selectImage(int y_selectImage) {
        this.y_selectImage = y_selectImage;
    }

    public int getX1_questionImage() {
        return x1_questionImage;
    }

    public void setX1_questionImage(int x1_questionImage) {
        this.x1_questionImage = x1_questionImage;
    }

    public int getY1_questionImage() {
        return y1_questionImage;
    }

    public void setY1_questionImage(int y1_questionImage) {
        this.y1_questionImage = y1_questionImage;
    }

    public int getX2_questionImage() {
        return x2_questionImage;
    }

    public void setX2_questionImage(int x2_questionImage) {
        this.x2_questionImage = x2_questionImage;
    }

    public int getY2_questionImage() {
        return y2_questionImage;
    }

    public void setY2_questionImage(int y2_questionImage) {
        this.y2_questionImage = y2_questionImage;
    }

    public String[] getBufferedText() {
        return bufferedText;
    }

    public void setBufferedText(String[] bufferedText) {
        this.bufferedText = bufferedText;
    }

    public int getCount_sentence() {
        return count_sentence;
    }

    public void setCount_sentence(int count_sentence) {
        this.count_sentence = count_sentence;
    }

    public int getCount_word() {
        return count_word;
    }

    public void setCount_word(int count_word) {
        this.count_word = count_word;
    }

    public int getCount_bufferedSentence() {
        return count_bufferedSentence;
    }

    public void setCount_bufferedSentence(int count_bufferedSentence) {
        this.count_bufferedSentence = count_bufferedSentence;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public List<String> getCurrentSentences() {
        return currentSentences;
    }

    public void setCurrentSentences(List<String> currentSentences) {
        this.currentSentences = currentSentences;
    }

    public Timer getWordsRun() {
        return wordsRun;
    }

    public void setWordsRun(Timer wordsRun) {
        this.wordsRun = wordsRun;
    }

    public Timer getSelectImageMove() {
        return selectImageMove;
    }

    public void setSelectImageMove(Timer selectImageMove) {
        this.selectImageMove = selectImageMove;
    }

    public Timer getQuestionImageMove() {
        return questionImageMove;
    }

    public void setQuestionImageMove(Timer questionImageMove) {
        this.questionImageMove = questionImageMove;
    }

    public boolean isShopSelect() {
        return shopSelect;
    }

    public void setShopSelect(boolean shopSelect) {
        this.shopSelect = shopSelect;
    }

    public boolean isEquipmentSelect() {
        return equipmentSelect;
    }

    public void setEquipmentSelect(boolean equipmentSelect) {
        this.equipmentSelect = equipmentSelect;
    }

    public boolean isBattleSelect() {
        return battleSelect;
    }

    public void setBattleSelect(boolean battleSelect) {
        this.battleSelect = battleSelect;
    }

    public boolean isQuestionSelect() {
        return questionSelect;
    }

    public void setQuestionSelect(boolean questionSelect) {
        this.questionSelect = questionSelect;
    }

    public List<Boolean> getHaveAnswered() {
        return haveAnswered;
    }

    public void setHaveAnswered(List<Boolean> haveAnswered) {
        this.haveAnswered = haveAnswered;
    }

    public List<Boolean> getHaveFighted() {
        return haveFighted;
    }

    public void setHaveFighted(List<Boolean> haveFighted) {
        this.haveFighted = haveFighted;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public int getCount_selectYesNo() {
        return count_selectYesNo;
    }

    public void setCount_selectYesNo(int count_selectYesNo) {
        this.count_selectYesNo = count_selectYesNo;
    }

    public int getCount_selectABCD() {
        return count_selectABCD;
    }

    public void setCount_selectABCD(int count_selectABCD) {
        this.count_selectABCD = count_selectABCD;
    }

    public int getCount_battle2() {
        return count_battle2;
    }

    public void setCount_battle2(int count_battle2) {
        this.count_battle2 = count_battle2;
    }

    public int getCount_questionAndAnswer() {
        return count_questionAndAnswer;
    }

    public void setCount_questionAndAnswer(int count_questionAndAnswer) {
        this.count_questionAndAnswer = count_questionAndAnswer;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isHaveEnteredTheScene() {
        return haveEnteredTheScene;
    }

    public void setHaveEnteredTheScene(boolean haveEnteredTheScene) {
        this.haveEnteredTheScene = haveEnteredTheScene;
    }

    public int getCount_scene() {
        return count_scene;
    }

    public void setCount_scene(int count_scene) {
        this.count_scene = count_scene;
    }

    // 选择对话框
    class SelectImageMove implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (x_selectImage <= 500) {
                x_selectImage += 50;
                y_selectImage += 15;
            } else {
                selectImageMove.stop();
                wordsRun.start();
            }
        }
    }

    // 问题对话框
    class QuestionImageMove implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (x1_questionImage >= 262) {
                x1_questionImage -= 25;
                y1_questionImage -= 25;
                x2_questionImage += 25;
                y2_questionImage += 25;
            } else {
                questionImageMove.stop();
                wordsRun.start();
            }
        }

    }

    // 逐字打印
    class WordsRun implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (count_sentence < currentSentences.size()) {
                if (count_word < currentSentences.get(count_sentence).length()) {
                    count_word++;
                    if (count_word == maxLength) {
                        count_bufferedSentence++;
                    }
                    if (count_word >= maxLength) {
                        bufferedText[count_bufferedSentence] = currentSentences
                                .get(count_sentence).substring(maxLength - 1,
                                        count_word);
                    } else {
                        bufferedText[count_bufferedSentence] = currentSentences
                                .get(count_sentence).substring(0, count_word);
                    }

                } else {
                    count_sentence++;
                    count_bufferedSentence++;
                    count_word = 0;
                }
            } else {
                wordsRun.stop();
            }
        }

    }
}
