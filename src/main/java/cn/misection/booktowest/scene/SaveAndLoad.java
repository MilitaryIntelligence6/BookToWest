package cn.misection.booktowest.scene;

import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    ScenePanel scene;
    public static boolean zhang;
    public static boolean lu;
    public static boolean wen;
    public static String mapName = "宿舍.png";
    public boolean isLoad;

    public SaveAndLoad(ScenePanel scene) {
        this.scene = scene;
    }

    // 存档
    public List<String> saveSceneInfo() {
        List<String> sceneInfo = new ArrayList<>();
        /**
         * 要存储的东西 1.当前是否是脚本剧情 2.当前文件名称 3.对话是否结束 4.对话编号 5.主角位置 6.currentScript
         * 7.nextScript
         */
        sceneInfo.add(scene.isScript + "");
        sceneInfo.add(scene.fileName);
        sceneInfo.add(scene.dialogueEvent.isDialogueEventOver() + "");
        sceneInfo.add(scene.dialogueEvent.getDialogueOrder() + "");
        sceneInfo.add(scene.role.getX() + "");
        sceneInfo.add(scene.role.getY() + "");
        sceneInfo.add(scene.currentScript[0] + " " + scene.currentScript[1]
                + " " + scene.currentScript[2]);
        sceneInfo.add(scene.nextScript[0] + " " + scene.nextScript[1] + " "
                + scene.nextScript[2]);
        sceneInfo.add(scene.fightEvent.isBattle1Over() + "");
        sceneInfo.add(scene.fightEvent.getCountOfBattle1() + "");
        return sceneInfo;
    }

    // 保存回答问题的情况
    public List<String> saveQuestion() {
        return SelectEvent.mapName;
    }

    public List<String> saveAnswer() {
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < SelectEvent.answeredRecorder.size(); i++) {
            String s = "";
            for (int j = 0; j < SelectEvent.answeredRecorder.get(i).size(); j++) {
                s = s + SelectEvent.answeredRecorder.get(i).get(j) + " ";
            }
            answer.add(s);
        }
        return answer;
    }

    // 加载回答问题的情况
    public void loadQuestion(List<String> question) {
        SelectEvent.mapName = question;
    }

    public void loadAnswer(List<String> answer) {
        List<List<Boolean>> answeredRecorder = new ArrayList<>();
        for (int i = 0; i < answer.size(); i++) {
            List<Boolean> b = new ArrayList<>();
            String[] ss = answer.get(i).split(" ");
            for (int j = 0; j < ss.length; j++) {
                if (ss[j].equals("true")) {
                    b.add(true);
                } else if (ss[j].equals("false")) {
                    b.add(false);
                }
            }
            answeredRecorder.add(b);
        }
        SelectEvent.answeredRecorder = answeredRecorder;
    }

    // 加载存档
    public void loadSceneInfo(List<String> sceneInfo) {
        isLoad = true;
        if (sceneInfo.get(0).equals("true")) {
            scene.isScript = true;
        } else {
            scene.isScript = false;
        }
        scene.isInitiateOver = false;
        scene.initiation(sceneInfo.get(1));
        scene.narratage.narratageOver = true;
        if (sceneInfo.get(2).equals("true")) {
            scene.dialogueEvent.setDialogueEventOver(true);
        } else {
            scene.dialogueEvent.setDialogueEventOver(false);
        }
        scene.dialogueEvent
                .setDialogueOrder(Integer.parseInt(sceneInfo.get(3)));
        scene.role.setX(Integer.parseInt(sceneInfo.get(4)));
        scene.role.setY(Integer.parseInt(sceneInfo.get(5)));
        scene.currentScript = sceneInfo.get(6).split(" ");
        scene.nextScript = sceneInfo.get(7).split(" ");
        if (sceneInfo.get(8).equals("true")) {
            scene.fightEvent.setBattle1Over(true);
        } else {
            scene.fightEvent.setBattle1Over(false);
        }
        scene.fightEvent.setCountOfBattle1(Integer.parseInt(sceneInfo.get(9)));
    }
}
