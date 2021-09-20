package cn.misection.booktowest.scene;

import java.util.ArrayList;
import java.util.List;

public class ExitEvent {
    ScenePanel scene;
    private List<String[]> exits;
    private List<String> nextScene;
    private List<String[]> entrance;
    public int nextSceneNo;
    public int dialogueOrder;

    public ExitEvent(ScenePanel scene, List<String[]> exits,
                     List<String> nextScene, List<String[]> entrance) {
        this.entrance = entrance;
        this.exits = exits;
        this.nextScene = nextScene;
        this.scene = scene;
    }

    public void nextScript() {
        scene.currentScript = scene.nextScript;
        scene.initiation(scene.nextScript[2]);
        scene.isScript = true;
        scene.role.setX(Integer.parseInt(scene.currentScript[0].split("/")[0]));
        scene.role.setY(Integer.parseInt(scene.currentScript[0].split("/")[1]));
    }

    // 检测出口
    public void checkExit() {
        int x = scene.role.getX();
        int y = scene.role.getY();
        for (int i = 0; i < exits.size(); i++) {
            for (int j = 0; j < exits.get(i).length; j++) {
                String[] ss = exits.get(i)[j].split(" ");
                int k = Integer.parseInt(ss[0]);
                int l = Integer.parseInt(ss[1]);
                if (k == x && l == y) {
                    scene.isInitiateOver = false;
                    // 现在场景的对话
                    if (nextScene.get(i).equals(scene.currentScript[1])) {
                        if (!scene.dialogueEvent.dialogueEventOver) {
                            dialogueOrder = scene.dialogueEvent
                                    .getDialogueOrder();
                        }
                    }
                    if (scene.dialogueEvent.dialogueEventOver) {
                        scene.currentScript = scene.nextScript;
                    }
                    if (scene.dialogueEvent.dialogueEventOver
                            && nextScene.get(i).equals(scene.nextScript[1])) {
                        scene.initiation(scene.nextScript[2]);
                        scene.isScript = true;
                        scene.role.setX(Integer.parseInt(scene.currentScript[0]
                                .split("/")[0]));
                        scene.role.setY(Integer.parseInt(scene.currentScript[0]
                                .split("/")[1]));
                    } else {
                        if (nextScene.get(i).equals(scene.currentScript[1])) {
                            scene.initiation(scene.currentScript[2]);
                            scene.isScript = true;
                            scene.dialogueEvent.setDialogueOrder(dialogueOrder);
                            scene.narratage.narratageOver = true;
                        } else {
                            scene.initiation(nextScene.get(i));
                            scene.isScript = false;
                        }
                        scene.role.setX(Integer.parseInt(entrance.get(i)[0]));
                        scene.role.setY(Integer.parseInt(entrance.get(i)[1]));
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
}
