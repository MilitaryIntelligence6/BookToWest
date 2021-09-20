package cn.misection.booktowest.scene;

import java.awt.event.KeyEvent;
import java.util.List;

import cn.misection.booktowest.app.GameApplication;

/**
 * @author javaman
 */
public class DialogueEvent {

    private ScenePanel scene;

    private boolean dialogueEventOver;

    private boolean isSpeaking;
    private int dialogueOrder = 0;
    private int sentenceOrder = 0;
    private boolean dialogueOver;
    private boolean dialogueFight;
    private boolean gameOver;
    private List<String> dialogueCode;
    private List<List<String[]>> dialogues;

    public DialogueEvent(ScenePanel scene,
                         List<String> dialogueCode,
                         List<List<String[]>> dialogues) {
        this.scene = scene;
        this.dialogueCode = dialogueCode;
        this.dialogues = dialogues;
        if (dialogueCode == null) {
            dialogueEventOver = true;
        }
    }

    public void checkLocationDialogue() {
        if (dialogueOrder < dialogueCode.size()) {
            if (!dialogueEventOver
                    && dialogueCode.get(dialogueOrder).length() > 2) {
                String[] locations = dialogueCode.get(dialogueOrder).split(",");
                int x1 = scene.getRole().getX();
                int y1 = scene.getRole().getY();
                for (int i = 0; i < locations.length; i++) {
                    int x2 = Integer.parseInt((locations[i].split(" ")[0]));
                    int y2 = Integer.parseInt((locations[i].split(" ")[1]));
                    if (x2 == x1 && y2 == y1) {
                        startSpeak();
                    }
                }
            }
        }
    }

    public boolean checkDialogue() {
        if (scene.isScript() && !dialogueEventOver
                && dialogueCode.get(dialogueOrder).length() <= 2) {
            if (Integer.parseInt(dialogueCode.get(dialogueOrder)) != -1) {
                int type = scene.getNpcs().get(
                                Integer.parseInt(dialogueCode.get(dialogueOrder)))
                        .getType();
                if (type == 0) {
                    int x1 = scene.getNpcs().get(
                                    Integer.parseInt(dialogueCode.get(dialogueOrder)))
                            .getX();
                    int y1 = scene.getNpcs().get(
                                    Integer.parseInt(dialogueCode.get(dialogueOrder)))
                            .getY();
                    int x2 = scene.getRole().getX();
                    int y2 = scene.getRole().getY();
                    if ((x1 - 1 == x2 && y1 == y2 - 1)
                            || (x1 + 1 == x2 && y1 == y2 - 1)
                            || (x1 == x2 && y1 == y2)
                            || (x1 == x2 && y1 + 2 == y2)) {
                        startSpeak();
                        return true;
                    }
                } else if (type == 1) {
                    if (!scene.getNpcs()
                            .get(Integer.parseInt(dialogueCode
                                    .get(dialogueOrder))).getWalk().isRunning()) {
                        startSpeak();
                        return true;
                    }
                } else if (type == 2) {
                    if (!scene.getNpcs()
                            .get(Integer.parseInt(dialogueCode
                                    .get(dialogueOrder))).getAction()
                            .isRunning()) {
                        startSpeak();
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public void checkAutoDialogue() {
        if (dialogueOrder == 0) {
            if (!dialogueEventOver
                    && dialogueCode.get(dialogueOrder).length() <= 2) {
                if (Integer.parseInt(dialogueCode.get(dialogueOrder)) == -1) {
                    startSpeak();
                }
            }
        }
    }

    public void startSpeak() {
        dialogueOver = false;
        sentenceOrder = 0;
        isSpeaking = true;
        scene.getRole().setEvent(true);
        scene.getDialogue().showSentence(dialogues.get(dialogueOrder).get(
                sentenceOrder));
        sentenceOrder++;
        dialogueOrder++;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            if (dialogueOver) {
                if (dialogueOrder >= dialogues.size()) {
                    dialogueEventOver = true;
                }
                isSpeaking = false;
                if (dialogueFight) {
                    scene.getFightEvent().startBattle1();
                    dialogueFight = false;
                }
                if (gameOver) {
                    GameApplication.switchTo("end");
                    gameOver = false;
                }
            } else {
                if (scene.getDialogue().isBufferedTextOver()) {
                    scene.getDialogue().begin();
                } else if (scene.getDialogue().isSentenceOver()) {
                    scene.getDialogue().showSentence(dialogues
                            .get(dialogueOrder - 1).get(sentenceOrder));
                    sentenceOrder++;
                    scene.getDialogue().getIcon1Run().stop();
                    if (sentenceOrder >= dialogues.get(dialogueOrder - 1)
                            .size()) {
                        scene.getDialogue().getIcon1Run().stop();
                        dialogueOver = true;
                    }
                }
            }
        }
    }

    public int getDialogueOrder() {
        return dialogueOrder;
    }

    public void setDialogueOrder(int dialogueOrder) {
        this.dialogueOrder = dialogueOrder;
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    /**
     * 有无主线对话;
     */
    public boolean isDialogueEventOver() {
        return dialogueEventOver;
    }

    public void setDialogueEventOver(boolean dialogueEventOver) {
        this.dialogueEventOver = dialogueEventOver;
    }

    /**
     * 是否正在进行主线对话;
     */
    public boolean isSpeaking() {
        return isSpeaking;
    }

    public void setSpeaking(boolean speaking) {
        isSpeaking = speaking;
    }

    public int getSentenceOrder() {
        return sentenceOrder;
    }

    public void setSentenceOrder(int sentenceOrder) {
        this.sentenceOrder = sentenceOrder;
    }

    public boolean isDialogueOver() {
        return dialogueOver;
    }

    public void setDialogueOver(boolean dialogueOver) {
        this.dialogueOver = dialogueOver;
    }

    public boolean isDialogueFight() {
        return dialogueFight;
    }

    public void setDialogueFight(boolean dialogueFight) {
        this.dialogueFight = dialogueFight;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public List<String> getDialogueCode() {
        return dialogueCode;
    }

    public void setDialogueCode(List<String> dialogueCode) {
        this.dialogueCode = dialogueCode;
    }

    public List<List<String[]>> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<List<String[]>> dialogues) {
        this.dialogues = dialogues;
    }
}
