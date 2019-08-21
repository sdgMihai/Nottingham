/**
 * GreedyPlayer
 * <p>
 * 26-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import goods.Goods;

import java.util.ArrayList;

public class GreedyPlayer extends BasePlayer {
    private static int roundCnt;
    public GreedyPlayer() {
        super();
        roundCnt = 0;
    }

    /**
     * Make Greedy commerciant bag.
     */
    @Override
    public void makeBag() {
        assetsCount = 0;  // bag isEmpty
        final int maxNrGoodsInBag = 5;
        roundCnt++;

        super.makeBag();
        if (roundCnt % 2 == 0 && assetsCount <= maxNrGoodsInBag) {  // evenRound
            short profit = 0;
            Goods assets = Goods.APPLE;
            for (Goods it : assetInHand.keySet()) {
                if (!it.isLegal() && profit < it.getProfit()) {
                    profit = it.getProfit();
                    assets = it;
                }
            }
            if (assets == Goods.APPLE) { // there aren't any
                return;
            }
            bag.put(assets, 1);
            if (assetInHand.get(assets) > 1) { // some illegal cards of assets type remain
                assetInHand.put(assets, assetInHand.get(assets) - 1);
            } else { // there is just One card
                assetInHand.remove(assets);
            }
            for (int i = 0; i < assetInHandPos.size(); ++i) {
                if (assetInHandPos.get(i).equals(assets)) {
                    assetInHandPos.remove(i);
                    break;
                }
            }
            assetCountInHand--;
        }
    }

    /**
     * Inspect commerciant, and add illegal cards, if found and there's no bribe, in queue(cards).
     * If there is some bribe, take bribe, let the commerciant pass free.
     * @param commerciant - who is inspected
     * @param cards - input queue
     */
    @Override
    public void inspection(final AbstractPlayer commerciant, final ArrayList<Goods> cards) {
        if (commerciant.getBribe() != 0) {  // commerciant = BribePlayer
            coins += commerciant.getBribe();
            commerciant.setBribe(0);
        } else {
            super.inspection(commerciant, cards);
        }
    }

    /**
     * It should be overwritten.
     * @return A String representing the class name.
     */
    @Override
    public String toString() {
        return "GREEDY";
    }
}
