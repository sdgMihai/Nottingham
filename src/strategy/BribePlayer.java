/**
 * BribePlayer
 * <p>
 * 28-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import goods.Goods;

import java.util.ArrayList;

public class BribePlayer extends BasePlayer {
    private int bribe;

    public BribePlayer() {
        super();
        bribe = 0;
    }

    /**
     * Getter for players' bribe.
     * @return An int representing the bribe instance player is using.
     */
    public int getBribe() {
        return bribe;
    }

    /**
     * Setter for players' bribe.
     * @param bribe Bribe value to be set.
     */
    public void setBribe(final int bribe) {
        this.bribe = bribe;
    }

    /**
    * Calculating correct bribe and number of cards to be added in bag.
     * @see this.makeBag()
     * @param cnt - nr of potential elements to be added, strictly positive
    *@return number of illegal assets with respect
    * to nr of coins.
     */
    private int bribeCalculator(final int cnt) {
        final int lBribe = 5;
        final int bBribe = 10;
        if (coins < lBribe && assetsCount == 0) {
            bribe = 0;
            return 0;
        }
        if (cnt + assetsCount <= 2 || (coins + bribe < bBribe && cnt + assetsCount > 2)) {
            bribe = lBribe;
            return (cnt + assetsCount > 2 ? 0 : cnt);
        }
        bribe = bBribe;
        return (cnt + assetsCount > lBribe ? 0 : cnt);
    }

    /**
     * Make Briber's bag.
     */
    @Override
    public void makeBag() {
        //TODO bribe pt feluri diferite de obiecte se suprascrie
        bag.clear();
        assetsCount = 0;
        for (Goods g : Goods.values()) {
            if (!g.isLegal()
            && assetInHand.containsKey(g)) {
                int cnt = bribeCalculator(assetInHand.get(g));
                assetsCount += cnt;
                bag.put(g, cnt);
                assetCountInHand -= cnt;
                decGood = Goods.APPLE;
                if (cnt == assetInHand.get(g)) {
                    assetInHand.remove(g);
                } else {
                    assetInHand.put(g, assetInHand.get(g) - cnt);
                }
                int destCnt = 0;
                for (int i = 0; i < assetInHandPos.size() && destCnt < cnt; ++i) {
                    if (assetInHandPos.get(i).equals(g)) {
                        assetInHandPos.remove(i);
                        destCnt++;
                    }
                }
            }
        }
        coins -= bribe;
        if (bribe == 0) {
            super.makeBag();
        }
    }

    /**
     * Inspect as BasePlayer.
     * @param commerciant - who is inspected
     * @param cards - input queue
     */
    @Override
    public void inspection(final AbstractPlayer commerciant, final ArrayList<Goods> cards) {
        super.inspection(commerciant, cards);
    }

    /**
     * It should be overwritten.
     * @return A String representing the class name.
     */
    @Override
    public String toString() {
        return "BRIBED";
    }
}
