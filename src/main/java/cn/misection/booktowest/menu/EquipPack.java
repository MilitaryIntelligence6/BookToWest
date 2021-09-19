package cn.misection.booktowest.menu;

import cn.misection.booktowest.shop.*;
import cn.misection.booktowest.battle.*;

public class EquipPack {
    private Hero hero;

    private Equipment armor;//装甲
    private Equipment helmet;//头盔
    private Equipment weapon;
    private Equipment glove;
    private Equipment shoe;
    private Equipment decoration;


    public EquipPack(Hero a) {

    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Equipment getArmor() {
        return armor;
    }

    public void setArmor(Equipment armor) {
        this.armor = armor;
    }

    public Equipment getHelmet() {
        return helmet;
    }

    public void setHelmet(Equipment helmet) {
        this.helmet = helmet;
    }

    public Equipment getWeapon() {
        return weapon;
    }

    public void setWeapon(Equipment weapon) {
        this.weapon = weapon;
    }

    public Equipment getGlove() {
        return glove;
    }

    public void setGlove(Equipment glove) {
        this.glove = glove;
    }

    public Equipment getShoe() {
        return shoe;
    }

    public void setShoe(Equipment shoe) {
        this.shoe = shoe;
    }

    public Equipment getDecoration() {
        return decoration;
    }

    public void setDecoration(Equipment decoration) {
        this.decoration = decoration;
    }
}
