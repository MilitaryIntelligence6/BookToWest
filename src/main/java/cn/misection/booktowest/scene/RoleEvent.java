package cn.misection.booktowest.scene;

import java.awt.event.KeyEvent;
import java.util.List;

/**
 * @author javaman
 */
public class RoleEvent {

    private Map map;
    private int[][] mapSet;
    private List<NPC> npcs;
    private Role role;
    // 成员变量
    // 用于跑步状态判断的变量
    private boolean b = false;
    private long l;
    private long c;

    public RoleEvent(Role role, Map map, int[][] mapSet, List<NPC> npcs) {
        this.role = role;
        this.map = map;
        this.mapSet = mapSet;
        this.npcs = npcs;
    }

    // 达到跑步的条件
    public boolean checkRun() {
        role.setRun(true);
        return false;
    }

    // 主角的走动
    public void switchWalk(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                role.setEvent(Role.getLEFT());
                break;
            case KeyEvent.VK_RIGHT:
                role.setEvent(Role.getRIGHT());
                break;
            case KeyEvent.VK_UP:
                role.setEvent(Role.getUP());
                break;
            case KeyEvent.VK_DOWN:
                role.setEvent(Role.getDOWN());
                break;
        }
    }

    // 判断主角是否可以走动
    public boolean isAllow(int x, int y) {
        // 超出边界的情况
        if (x < 0 || x >= map.getCol() || y < 0 || y >= map.getRow()) {
            return false;
        }
        if (mapSet[y][x] != 0) {
            return false;
        }
        for (int i = 0; i < npcs.size(); i++) {
            if (x == npcs.get(i).getX() && y == npcs.get(i).getY() + 1) {
                return false;
            }
        }
        return true;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public int[][] getMapSet() {
        return mapSet;
    }

    public void setMapSet(int[][] mapSet) {
        this.mapSet = mapSet;
    }

    public List<NPC> getNpcs() {
        return npcs;
    }

    public void setNpcs(List<NPC> npcs) {
        this.npcs = npcs;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }
}
