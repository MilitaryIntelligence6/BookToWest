package cn.misection.booktowest.scene;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

import cn.misection.booktowest.shop.DrugPack;
import cn.misection.booktowest.util.Reader;

public class TreasureBox {
    private Image fullBox = Reader.readImage("dialogue//fullBox.png");
    private Image emptyBox = Reader.readImage("dialogue//emptyBox.png");
    private String treasureName;
    private int x;
    private int y;
    private EquipmentEvent equipmentEvent;
    private boolean isEmpty;
    private boolean AroundHero;

    public TreasureBox(String location, String treasureName,
                       EquipmentEvent equipmentEvent) {
        this.x = Integer.parseInt(location.split("/")[0]);
        this.y = Integer.parseInt(location.split("/")[1]);
        this.treasureName = treasureName;
        this.equipmentEvent = equipmentEvent;
    }

    public void paintBox(Graphics g) {
        if (isEmpty) {
            g.drawImage(emptyBox, x * 32
                            - equipmentEvent.getScene().getOtherEvent().getFirstTileX() * 8, y * 32
                            - equipmentEvent.getScene().getOtherEvent().getFirstTileY() * 8,
                    equipmentEvent.getScene());
        } else {
            g.drawImage(fullBox, x * 32
                            - equipmentEvent.getScene().getOtherEvent().getFirstTileX() * 8, y * 32
                            - equipmentEvent.getScene().getOtherEvent().getFirstTileY() * 8,
                    equipmentEvent.getScene());
        }
    }

    public void checkHero(int x_hero, int y_hero) {
        if (!isEmpty) {
            if ((x - 1 == x_hero && y == y_hero)
                    || (x + 1 == x_hero && y == y_hero)
                    || (x == x_hero && y - 1 == y_hero)
                    || (x == x_hero && y + 1 == y_hero)) {
                AroundHero = true;
            }
        }
    }

    public void keyPressed(int keyCode) {
        if (AroundHero && keyCode == KeyEvent.VK_SPACE) {
            if (!isEmpty) {
                isEmpty = true;
                int i = 1 + (int) (2 * Math.random());
                DrugPack.addDrug(treasureName, i);
                equipmentEvent.drawString("得到" + treasureName + " * " + i);
            }
        }
    }

    public Image getFullBox() {
        return fullBox;
    }

    public void setFullBox(Image fullBox) {
        this.fullBox = fullBox;
    }

    public Image getEmptyBox() {
        return emptyBox;
    }

    public void setEmptyBox(Image emptyBox) {
        this.emptyBox = emptyBox;
    }

    public String getTreasureName() {
        return treasureName;
    }

    public void setTreasureName(String treasureName) {
        this.treasureName = treasureName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EquipmentEvent getEquipmentEvent() {
        return equipmentEvent;
    }

    public void setEquipmentEvent(EquipmentEvent equipmentEvent) {
        this.equipmentEvent = equipmentEvent;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isAroundHero() {
        return AroundHero;
    }

    public void setAroundHero(boolean aroundHero) {
        AroundHero = aroundHero;
    }
}
