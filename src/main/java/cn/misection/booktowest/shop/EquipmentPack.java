package cn.misection.booktowest.shop;

import java.util.ArrayList;
import java.util.List;

public class EquipmentPack {
    //设置不同种装备的list
    public static List<Equipment> helmetList = new ArrayList<>();
    public static List<Equipment> armorList = new ArrayList<>();
    public static List<Equipment> weaponList = new ArrayList<>();
    public static List<Equipment> gloveList = new ArrayList<>();
    public static List<Equipment> shoeList = new ArrayList<>();
    public static List<Equipment> decorationList = new ArrayList<>();

    public EquipmentPack() {
        helmetList = ShopReader.readEquipment("头");
        armorList = ShopReader.readEquipment("盔甲");
        gloveList = ShopReader.readEquipment("手");
        shoeList = ShopReader.readEquipment("脚");
        decorationList = ShopReader.readEquipment("饰品");
        weaponList = ShopReader.readEquipment("武器");
    }

    public static List<Equipment> listTable(String s) {
        switch (s) {
            case "armor":
                return armorList;
            case "helmet":
                return helmetList;
            case "glove":
                return gloveList;
            case "shoe":
                return shoeList;
            case "weapon":
                return weaponList;
            case "decoration":
                return decorationList;
            default:
                return null;
        }
    }

    /**
     * 对装备栏中的装备数量进行操作
     * @param name
     * @param number
     */
    public static void addEqupment(String name, int number) {
        for (Equipment e1 : helmetList) {
            if (e1.getName().equals(name)) {
                number = e1.getNumberGOT() + number;
                e1.setNumberGOT(number);
            }
        }
        for (Equipment e2 : armorList) {
            if (e2.getName().equals(name)) {
                number = e2.getNumberGOT() + number;
                e2.setNumberGOT(number);
            }
        }
        for (Equipment e3 : weaponList) {
            if (e3.getName().equals(name)) {
                number = e3.getNumberGOT() + number;
                e3.setNumberGOT(number);
            }
        }
        for (Equipment e4 : gloveList) {
            if (e4.getName().equals(name)) {
                number = e4.getNumberGOT() + number;
                e4.setNumberGOT(number);
            }
        }
        for (Equipment e5 : shoeList) {
            if (e5.getName().equals(name)) {
                number = e5.getNumberGOT() + number;
                e5.setNumberGOT(number);
            }
        }
        for (Equipment e6 : decorationList) {
            if (e6.getName().equals(name)) {
                number = e6.getNumberGOT() + number;
                e6.setNumberGOT(number);
            }
        }
    }
}

