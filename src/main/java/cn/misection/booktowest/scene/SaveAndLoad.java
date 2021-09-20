package cn.misection.booktowest.scene;

import java.util.ArrayList;
import java.util.List;

public class SaveAndLoad {
    private ScenePanel scene;
    private static boolean zhang;
    private static boolean lu;
    private static boolean wen;
    private static String mapName = "宿舍.png";
    private boolean isLoad;

    public SaveAndLoad(ScenePanel scene) {
        this.scene = scene;
    }

    public static boolean isZhang() {
        return zhang;
    }

    public static void setZhang(boolean zhang) {
        SaveAndLoad.zhang = zhang;
    }

    public static boolean isLu() {
        return lu;
    }

    public static void setLu(boolean lu) {
        SaveAndLoad.lu = lu;
    }

    public static boolean isWen() {
        return wen;
    }

    public static void setWen(boolean wen) {
        SaveAndLoad.wen = wen;
    }

    public static String getMapName() {
        return mapName;
    }

    public static void setMapName(String mapName) {
        SaveAndLoad.mapName = mapName;
    }

    // 存档
    public List<String> saveSceneInfo() {
        List<String> sceneInfo = new ArrayList<>();
        /**
         * 要存储的东西 1.当前是否是脚本剧情 2.当前文件名称 3.对话是否结束 4.对话编号 5.主角位置 6.currentScript
         * 7.nextScript
         */
        sceneInfo.add(scene.isScript() + "");
        sceneInfo.add(scene.getFileName());
        sceneInfo.add(scene.getDialogueEvent().isDialogueEventOver() + "");
        sceneInfo.add(scene.getDialogueEvent().getDialogueOrder() + "");
        sceneInfo.add(scene.getRole().getX() + "");
        sceneInfo.add(scene.getRole().getY() + "");
        sceneInfo.add(scene.getCurrentScript()[0] + " " + scene.getCurrentScript()[1]
                + " " + scene.getCurrentScript()[2]);
        sceneInfo.add(scene.getNextScript()[0] + " " + scene.getNextScript()[1] + " "
                + scene.getNextScript()[2]);
        sceneInfo.add(scene.getFightEvent().isBattle1Over() + "");
        sceneInfo.add(scene.getFightEvent().getCountOfBattle1() + "");
        return sceneInfo;
    }

    // 保存回答问题的情况
    public List<String> saveQuestion() {
        return SelectEvent.getMapName();
    }

    public List<String> saveAnswer() {
        List<String> answer = new ArrayList<>();
        for (int i = 0; i < SelectEvent.getAnsweredRecorder().size(); i++) {
            String s = "";
            for (int j = 0; j < SelectEvent.getAnsweredRecorder().get(i).size(); j++) {
                s = s + SelectEvent.getAnsweredRecorder().get(i).get(j) + " ";
            }
            answer.add(s);
        }
        return answer;
    }

    // 加载回答问题的情况
    public void loadQuestion(List<String> question) {
        SelectEvent.setMapName(question);
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
        SelectEvent.setAnsweredRecorder(answeredRecorder);
    }

    // 加载存档
    public void loadSceneInfo(List<String> sceneInfo) {
        isLoad = true;
        if (sceneInfo.get(0).equals("true")) {
            scene.setScript(true);
        } else {
            scene.setScript(false);
        }
        scene.setInitiateOver(false);
        scene.initiation(sceneInfo.get(1));
        scene.getNarratage().setNarratageOver(true);
        if (sceneInfo.get(2).equals("true")) {
            scene.getDialogueEvent().setDialogueEventOver(true);
        } else {
            scene.getDialogueEvent().setDialogueEventOver(false);
        }
        scene.getDialogueEvent()
                .setDialogueOrder(Integer.parseInt(sceneInfo.get(3)));
        scene.getRole().setX(Integer.parseInt(sceneInfo.get(4)));
        scene.getRole().setY(Integer.parseInt(sceneInfo.get(5)));
        scene.setCurrentScript(sceneInfo.get(6).split(" "));
        scene.setNextScript(sceneInfo.get(7).split(" "));
        if (sceneInfo.get(8).equals("true")) {
            scene.getFightEvent().setBattle1Over(true);
        } else {
            scene.getFightEvent().setBattle1Over(false);
        }
        scene.getFightEvent().setCountOfBattle1(Integer.parseInt(sceneInfo.get(9)));
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }
}
