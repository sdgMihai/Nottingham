/**
 * BasePlayer
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import goods.Goods;

import java.util.ArrayList;
import java.util.HashMap;

public class BasePlayer extends AbstractPlayer {
    public BasePlayer() {
        final int nrCoins = 50;
        bag = new HashMap<>();
        assetsCount = 0;
        coins = nrCoins;
        assetInHand = new HashMap<>();
        assetOnMerchantStand = new HashMap<>();
        assetInHandPos = new ArrayList<>();
    }

    /**
     * Make baseCommerciant Bag.
     */
    @Override
    public void makeBag() {
        // iterate throw enum.values
        // select just legal goods
        assetsCount = 0;  // bag isEmpty
        Goods assets = Goods.APPLE;
        int profit = 0;
        bag.clear();

        for (Goods g : assetInHand.keySet()) {
            if (!g.isLegal()) {
                continue;
            }
            // get the legal good with maxFrec
            // Goods names are already sorted by profit
            if (assetInHand.get(g) > assetsCount
                    || (assetInHand.get(g) == assetsCount &&  profit <= g.getProfit())) {
                assetsCount = assetInHand.get(g);
                assets = g;
                profit = g.getProfit();
            }
        }
        // get first elem with same profit and freq
        for (Goods g : assetInHandPos) {
            if (!g.isLegal()) {
                continue;
            }
            if (assetInHand.get(g) == assetsCount && profit == g.getProfit()) {
                assets = g;
                break;
            }
        }
        final int maxNrOfCardsInHand = 5;
        if (assetsCount != 0) {
            if (assetsCount > maxNrOfCardsInHand) { // the bag supports only 5 cards
                assetsCount = maxNrOfCardsInHand;
            }
            bag.put(assets, assetsCount);
            if (assetsCount < assetInHand.get(assets)) { // there are 5 cards in the bag
                assetInHand.put(assets, assetInHand.get(assets) - assetsCount);
            } else { // there are less then 5 cards
                assetInHand.remove(assets);
            }
            int destCnt = 0;
            for (int i = 0; i < assetInHandPos.size() && destCnt < assetsCount; ++i) {
                if (assetInHandPos.get(i).equals(assets)) {
                    assetInHandPos.remove(i);
                    i--;
                    destCnt++;
                }
            }
            assetCountInHand -= assetsCount;
            decGood = assets;
            return;
        }
        // there are no legal goods, insert one illegal
        for (Goods g : Goods.values()) {
            if (g.isLegal()) {
                continue;
            }
            if (assetInHand.containsKey(g)) {
                assetsCount = 1;
                bag.put(g, 1);

                if (assetInHand.get(g) == 1) {
                    assetInHand.remove(g);
                } else {
                    assetInHand.put(g, assetInHand.get(g) - 1);
                }
                int destCnt = 0;
                for (int i = 0; i < assetInHandPos.size() && destCnt < assetsCount; ++i) {
                    if (assetInHandPos.get(i).equals(g)) {
                        assetInHandPos.remove(i);
                        destCnt++;
                    }
                }
                assetCountInHand--;

                decGood = Goods.APPLE;
                return;
            }
        }
    }

    /**
     *  Inspect commerciant, and add illegal cards, if found, in queue(cards).
     * @param commerciant - who is inspected
     * @param cards - input queue
     */
    @Override
    public void inspection(final AbstractPlayer commerciant, final ArrayList<Goods> cards) {
        // inspect one commerciant at a time
        ArrayList<Goods> removable = new ArrayList<>();
        boolean legalTicket = true;
        for (Goods it : commerciant.getBag().keySet()) {
            if (!it.isLegal()) {
                legalTicket = false;

                int x;
                // get nr of illegal Goods of type it.
                x = commerciant.getBag().get(it);
                coins += x * it.getPenalty();
                for (int i = 0; i < x; ++i) {
                    cards.add(it);
                }

                //reset bribe for commerciant
                commerciant.setCoins(commerciant.getCoins()
                        + commerciant.getBribe()
                        - x * it.getPenalty()
                );
                commerciant.setBribe(0);
                removable.add(it);
            }
        }
        for (Goods g : removable) {
            commerciant.getBag().remove(g);
        }

        if (legalTicket) {  // commerciant is HONEST
            coins -=
                    commerciant.getBag().get(commerciant.getDecGood())
                            * commerciant.getDecGood().getPenalty();
            commerciant.setCoins(
                    commerciant.getCoins()
                        + commerciant.getBag().get(commerciant.getDecGood())
                            * commerciant.getDecGood().getPenalty()
            );
        }
    }

    /**
     * It should be overwritten in every subclass.
     * @return
     */
    @Override
    public String toString() {
        return "BASIC";
    }
}
