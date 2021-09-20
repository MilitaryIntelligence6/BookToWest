package cn.misection.booktowest.shop;

/**
 * @author javaman
 */
public class Money {

    // FIXME: 2021/9/20
    static int coins = 10000;

    public static int getCoins() {
        return coins;
    }

    public static void setCoins(int coins) {
        Money.coins = coins;
    }

    /**
     * 此方法为增加金钱;
     * @param addCoins
     */
    public static void addCoins(int addCoins) {
        coins = coins + addCoins;
    }

    /**
     * 此方法为减少金钱;
     * @param reduceCoins
     */
    public static void reduceCoins(int reduceCoins) {
        coins = coins - reduceCoins;
    }
}
