package cn.misection.booktowest.scene;

import java.util.List;

public class ExitEvent {
    private ScenePanel scene;
    private List<String[]> exits;
    private List<String> nextScene;
    private List<String[]> entrance;
    private int nextSceneNo;
    private int dialogueOrder;

    public ExitEvent(ScenePanel scene, List<String[]> exits,
                     List<String> nextScene, List<String[]> entrance) {
        this.entrance = entrance;
        this.exits = exits;
        this.nextScene = nextScene;
        this.scene = scene;
    }

    public void nextScript() {
        scene.setCurrentScript(scene.getNextScript());
        scene.initiation(scene.getNextScript()[2]);
        scene.setScript(true);
        scene.getRole().setX(Integer.parseInt(scene.getCurrentScript()[0].split("/")[0]));
        scene.getRole().setY(Integer.parseInt(scene.getCurrentScript()[0].split("/")[1]));
    }

    // 检测出口
    public void checkExit() {
        int x = scene.getRole().getX();
        int y = scene.getRole().getY();
        for (int i = 0; i < exits.size(); i++) {
            for (int j = 0; j < exits.get(i).length; j++) {
                String[] ss = exits.get(i)[j].split(" ");
                int k = Integer.parseInt(ss[0]);
                int l = Integer.parseInt(ss[1]);
                if (k == x && l == y) {
                    scene.setInitiateOver(false);
                    // 现在场景的对话
                    if (nextScene.get(i).equals(scene.getCurrentScript()[1])) {
                        if (!scene.getDialogueEvent().isDialogueEventOver()) {
                            dialogueOrder = scene.getDialogueEvent()
                                    .getDialogueOrder();
                        }
                    }
                    if (scene.getDialogueEvent().isDialogueEventOver()) {
                        scene.setCurrentScript(scene.getNextScript());
                    }
                    if (scene.getDialogueEvent().isDialogueEventOver()
                            && nextScene.get(i).equals(scene.getNextScript()[1])) {
                        scene.initiation(scene.getNextScript()[2]);
                        scene.setScript(true);
                        scene.getRole().setX(Integer.parseInt(scene.getCurrentScript()[0]
                                .split("/")[0]));
                        scene.getRole().setY(Integer.parseInt(scene.getCurrentScript()[0]
                                .split("/")[1]));
                    } else {
                        if (nextScene.get(i).equals(scene.getCurrentScript()[1])) {
                            scene.initiation(scene.getCurrentScript()[2]);
                            scene.setScript(true);
                            scene.getDialogueEvent().setDialogueOrder(dialogueOrder);
                            scene.getNarratage().setNarratageOver(true);
                        } else {
                            scene.initiation(nextScene.get(i));
                            scene.setScript(false);
                        }
                        scene.getRole().setX(Integer.parseInt(entrance.get(i)[0]));
                        scene.getRole().setY(Integer.parseInt(entrance.get(i)[1]));
                    }
                }
            }
        }
    }

    public List<String[]> getExits() {
        return exits;
    }

    public void setExits(List<String[]> exits) {
        this.exits = exits;
    }

    public List<String> getNextScene() {
        return nextScene;
    }

    public void setNextScene(List<String> nextScene) {
        this.nextScene = nextScene;
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public List<String[]> getEntrance() {
        return entrance;
    }

    public void setEntrance(List<String[]> entrance) {
        this.entrance = entrance;
    }

    public int getNextSceneNo() {
        return nextSceneNo;
    }

    public void setNextSceneNo(int nextSceneNo) {
        this.nextSceneNo = nextSceneNo;
    }

    public int getDialogueOrder() {
        return dialogueOrder;
    }

    public void setDialogueOrder(int dialogueOrder) {
        this.dialogueOrder = dialogueOrder;
    }
}
