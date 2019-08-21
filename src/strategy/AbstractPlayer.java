/**
 * AbstractPlayer
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import goods.Bonus;
import goods.Goods;
import utilitary.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractPlayer implements InterfacePlayer {
    protected Role role;  // commerciant or sherrif
    protected int coins;

    protected int assetCountInHand;
    protected HashMap<Goods, Integer> assetInHand;

    protected HashMap<Goods, Integer> assetOnMerchantStand;

    protected HashMap<Goods, Integer> bag;
    protected int assetsCount;  // nrOfItemsInTheBag
    protected Goods decGood;

    protected ArrayList<Goods> assetInHandPos;

    /**
     *  It shouldn't be overwritten.
     * @return An int representing players' coins.
     */
    public int getCoins() {
        return coins;
    }

    /**
     *  It shouldn't be overwritten.
     * @return An HashMap<Goods, Integer> representing players' cards on merchand stand.
     */
    public HashMap<Goods, Integer> getAssetOnMerchantStand() {
        return assetOnMerchantStand;
    }

    /**
     *  It shouldn't be overwritten.
     * @return An HashMap<Goods, Integer> representing players' cards in hand.
     */
    public HashMap<Goods, Integer> getAssetInHand() {
        return assetInHand;
    }

    /**
     *  It shouldn't be overwritten.
     * @return An ArrayList<Goods> representing players' cards in hand.
     */
    public ArrayList<Goods> getAssetInHandPos() {
        return assetInHandPos;
    }

    /**
     *  It shouldn't be overwritten.
     * @return An HashMap<Goods, Integer> representing players' cards in bag.
     */
    public HashMap<Goods, Integer> getBag() {
        return bag;
    }

    /**
     * Players who use bribe must overwrite function.
     * @return int type 0 value for players that don't use bribe.
     */
    public int getBribe() {
        return 0;
    }

    /**
     *  It shoudn't be overwritten.
     * @return A Goods value representing declared goods.
     */
    public Goods getDecGood() {
        return decGood;
    }

    /**
     *  Players who use bribe must overwrite function.
     * @param bribe Bribe value to be used by overwritten function in
     *              subclasses with a field holding bribe's value.
     */
    public void setBribe(final int bribe) {
    }

    /**
     * It shouldn't be overwritten. Sets players role.
     * @param role
     */
    public void setRole(final Role role) {
        this.role = role;
    }

    /**
     * Setter for players' coins.
     * @param coins Number of coins.
     */
    public void setCoins(final int coins) {
        this.coins = coins;
    }

    /**
     * Set's players score, without taking into consideration king's or queen's bonus.
     * Final score is stored in coins field.
     */
    public void setScore() {
        for (Goods g : Goods.values()) {
            if (!g.isLegal() && assetOnMerchantStand.containsKey(g)) {
                Pair<Goods, Integer> bonus = Bonus.INSTANCE.getBonus(g);
                assetOnMerchantStand.put(bonus.getFirst(),
                        assetOnMerchantStand.getOrDefault(bonus.getFirst(), 0)
                                + bonus.getSecond() * assetOnMerchantStand.get(g));
            }
        }
        for (Goods g : assetOnMerchantStand.keySet()) {
            coins += g.getProfit() * assetOnMerchantStand.get(g);
        }
    }

    /**
     * It shouldn't be overwritten.
     */
    public void transferBagOnStand() {
        // bag -> assetsOnMerchantStand
        for (Goods g : bag.keySet()) {
            assetOnMerchantStand.put(g,
                    assetOnMerchantStand.getOrDefault(g, 0) + bag.get(g));
        }
        assetsCount = 0;
        bag.clear();
    }

    /**
     * Makes a full refill. The player will have 6 cards in hand, after function's execution.
     * @param queue The queue used in main for storing games' cards.
     */
    @Override
    public void replenish(final ArrayList<Goods> queue) {
        final int nrCardsInHand = 6;
        while (assetCountInHand < nrCardsInHand) {
            assetInHandPos.add(queue.get(0));
            assetInHand.put(queue.get(0), assetInHand.getOrDefault(queue.get(0), 0) + 1);
            queue.remove(0);
            assetCountInHand++;
        }
    }

}
