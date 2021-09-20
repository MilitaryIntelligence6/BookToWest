package cn.misection.booktowest.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * @author javaman
 */
public class DrugPack {
    public static List<Drug> drugList = new ArrayList<>();

    public DrugPack() {
        drugList = ShopReader.readDrug();
    }

    /**
     * 对DrugPack中的药品进行操作
     * @param name
     * @param number
     */
    public static void addDrug(String name, int number) {
        for (Drug drug : drugList) {
            if (drug.getName().equals(name)) {
                number = drug.getNumberGOT() + number;
                drug.setNumberGOT(number);
            }
        }
    }
}
