package cn.misection.booktowest.scene;

import java.awt.event.KeyEvent;

public class NPCEvent {
    private ScenePanel scene;
    private boolean isOral;
    private boolean oralOver;
    private int oralCount;
    private int oralNPC;
    private String[] oral;

    public NPCEvent(ScenePanel scene) {
        this.scene = scene;
    }

    public void sayOral(int oralNPC) {
        isOral = true;
        this.oralNPC = oralNPC;
        String[] ss = new String[3];
        ss[0] = String.valueOf(1);
        ss[1] = scene.getNpcs().get(oralNPC).getName();
        oral = scene.getNpcs().get(oralNPC).getOral();
        ss[2] = oral[oralCount];
        scene.getDialogue().showSentence(ss);
        oralCount++;
    }

    public void keyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {

            if (scene.getDialogue().isBufferedTextOver()) {
                scene.getDialogue().begin();
            } else if (scene.getDialogue().isSentenceOver()) {
                scene.getDialogue().getIcon1Run().stop();
                if (oralCount >= oral.length) {
                    oralOver = true;
                    oralCount = 0;
                    isOral = false;
                } else {
                    sayOral(oralNPC);
                    oralOver = false;
                }
            }

        }
    }

    public void checkNPCOral() {
        // 是否说话
        for (int i = 0; i < scene.getNpcs().size(); i++) {
            int type = scene.getNpcs().get(i).getType();
            if (type == 0) {
                int x1 = scene.getNpcs().get(i).getX();
                int y1 = scene.getNpcs().get(i).getY();
                int x2 = scene.getRole().getX();
                int y2 = scene.getRole().getY();
                if ((x1 - 1 == x2 && y1 == y2 - 1)
                        || (x1 + 1 == x2 && y1 == y2 - 1)
                        || (x1 == x2 && y1 == y2) || (x1 == x2 && y1 + 2 == y2)) {
                    // 先判断是否有选择事件触发
                    if (!scene.getSelectEvent().checkSelectEvent(i)) {
                        sayOral(i);
                    }
                }
            } else if (type == 1) {
                if (!scene.getNpcs().get(i).getWalk().isRunning()) {
                    if (!scene.getSelectEvent().checkSelectEvent(i)) {
                        sayOral(i);
                    }
                }
            } else if (type == 2) {
                if (!scene.getNpcs().get(i).getAction().isRunning()) {
                    if (!scene.getSelectEvent().checkSelectEvent(i)) {
                        sayOral(i);
                    }
                }
            }

        }
    }

    // 对NPC判断
    public void checkNPCStop() {
        // 判断NPC是否停止运动
        for (int i = 0; i < scene.getNpcs().size(); i++) {
            if (scene.getNpcs().get(i).getType() == 1) {
                int x1 = scene.getNpcs().get(i).getX();
                int y1 = scene.getNpcs().get(i).getY();
                int x2 = scene.getRole().getX();
                int y2 = scene.getRole().getY();
                switch (scene.getNpcs().get(i).getDirection()) {
                    case 1:
                        if (x1 == x2 && y1 == y2 - 1) {
                            scene.getNpcs().get(i).getWalk().stop();
                        } else if (!scene.getNpcs().get(i).getWalk().isRunning()) {
                            scene.getNpcs().get(i).getWalk().start();
                        }
                        break;
                    case 5:
                        if (x1 + 1 == x2 && y1 == y2 - 1) {
                            scene.getNpcs().get(i).getWalk().stop();
                        } else if (!scene.getNpcs().get(i).getWalk().isRunning()) {
                            scene.getNpcs().get(i).getWalk().start();
                        }
                        break;
                    case 9:
                        if (y1 + 2 == y2 && x1 == x2) {
                            scene.getNpcs().get(i).getWalk().stop();
                        } else if (!scene.getNpcs().get(i).getWalk().isRunning()) {
                            scene.getNpcs().get(i).getWalk().start();
                        }
                        break;
                    case 13:
                        if (y1 + 1 == y2 && x1 == x2) {
                            scene.getNpcs().get(i).getWalk().stop();
                        } else if (!scene.getNpcs().get(i).getWalk().isRunning()) {
                            scene.getNpcs().get(i).getWalk().start();
                        }
                        break;
                }
            } else if (scene.getNpcs().get(i).getType() == 2) {
                int x1 = scene.getNpcs().get(i).getX();
                int y1 = scene.getNpcs().get(i).getY();
                int x2 = scene.getRole().getX();
                int y2 = scene.getRole().getY();
                if ((x1 - 1 == x2 && y1 == y2 - 1)
                        || (x1 + 1 == x2 && y1 == y2 - 1)
                        || (x1 == x2 && y1 == y2) || (x1 == x2 && y1 + 2 == y2)) {
                    scene.getNpcs().get(i).getAction().stop();
                } else {
                    scene.getNpcs().get(i).getAction().start();
                }
            }
        }
    }

    public ScenePanel getScene() {
        return scene;
    }

    public void setScene(ScenePanel scene) {
        this.scene = scene;
    }

    public boolean isOral() {
        return isOral;
    }

    public void setOral(boolean oral) {
        isOral = oral;
    }

    public boolean isOralOver() {
        return oralOver;
    }

    public void setOralOver(boolean oralOver) {
        this.oralOver = oralOver;
    }

    public int getOralCount() {
        return oralCount;
    }

    public void setOralCount(int oralCount) {
        this.oralCount = oralCount;
    }

    public int getOralNPC() {
        return oralNPC;
    }

    public void setOralNPC(int oralNPC) {
        this.oralNPC = oralNPC;
    }

    public String[] getOral() {
        return oral;
    }

    public void setOral(String[] oral) {
        this.oral = oral;
    }
}
