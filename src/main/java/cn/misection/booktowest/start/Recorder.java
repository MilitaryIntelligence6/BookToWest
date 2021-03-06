package cn.misection.booktowest.start;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.misection.booktowest.app.*;
import cn.misection.booktowest.scene.*;
import cn.misection.booktowest.util.Reader;

public class Recorder {
    //接受四个信息列表，分别为role，cn.misection.booktowest.scene，cn.misection.booktowest.menu，cn.misection.booktowest.shop
    private List<String> roleAndMapInfo;
    private List<String> zhangXiaoFanInfo;
    private List<String> luXueQiInfo;
    private List<String> yuJieInfo;
    private List<String> sceneInfo;
    private List<String> menuInfo;
    private List<String> shopInfo;
    private List<String> equipmentShopInfo;
    private List<String> questionInfo;
    private List<String> answerInfo;
    private SaveAndLoad sa;
    private BufferedWriter writer;

    public Recorder() {
        sa = new SaveAndLoad(GameApplication.scenePanel);
    }

    public void save(int textCode) {
        roleAndMapInfo = new ArrayList<>();
        roleAndMapInfo.add("" + SaveAndLoad.isZhang());
        roleAndMapInfo.add("" + SaveAndLoad.isLu());
        roleAndMapInfo.add("" + SaveAndLoad.isWen());
        roleAndMapInfo.add(SaveAndLoad.getMapName());
        roleAndMapInfo.add(Reader.task);
        zhangXiaoFanInfo = GameApplication.zhangXiaoFan.saveRoleInfo();
        luXueQiInfo = GameApplication.luXueQi.saveRoleInfo();
        yuJieInfo = GameApplication.yuJie.saveRoleInfo();
        sceneInfo = sa.saveSceneInfo();
        menuInfo = GameApplication.menuPanel.getEquipPanel().saveEquipInfo();
        shopInfo = GameApplication.shopPanel.saveShopInfo();
        equipmentShopInfo = GameApplication.equipmentShopPanel.saveEquipmentShopInfo();
        questionInfo = sa.saveQuestion();
        answerInfo = sa.saveAnswer();
        writeInfo(textCode);
    }

    private void writeInfo(int textcode) {
        try {
            writer = new BufferedWriter(new FileWriter(new File("sources/Record/存档" + textcode + ".txt")));
            for (String word : roleAndMapInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : zhangXiaoFanInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : luXueQiInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : yuJieInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : sceneInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : menuInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : shopInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : equipmentShopInfo) {
                writer.write(word + "A");
            }
            for (String word : questionInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            for (String word : answerInfo) {
                writer.write(word + "A");
            }
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
