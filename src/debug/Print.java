/**
 * Main
 * <p>
 * 7-Nov-18
 *
 * @author Gheoace Mihai
 */
package debug;

import goods.Goods;
import strategy.AbstractPlayer;

import java.util.ArrayList;

public final class Print {
    public static final Print INSTANCE = new Print();
    private ArrayList<AbstractPlayer> players;
    private ArrayList<Goods> cards;

    public void setPlayers(final ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    public void setCards(final ArrayList<Goods> cards) {
        this.cards = cards;
    }

    public void printCards() {
        final int cnt = 24;
        for (int i = 0; i < cnt; ++i) {
            System.out.print(cards.get(i).toString() + " ");
        }
        System.out.println("");
    }

    public void printPlayers() {
        System.out.println("players: ");
        for (int i = 0; i < players.size(); ++i) {
            System.out.print(players.get(i) + " ");
        }
        System.out.println("");
    }

    public void printAssetsInHand(final int id) {
        System.out.println(id + " player::assetInHand: ");
        for (Goods g : players.get(id).getAssetInHand().keySet()) {
            for (int i = 0; i < players.get(id).getAssetInHand().get(g); ++i) {
                System.out.print(g.toString() + " ");
            }
        }
        System.out.println("");
        for (Goods g : players.get(id).getAssetInHandPos()) {
            System.out.print(g.toString() + " ");
        }
        System.out.println("");
    }

    public void printBag(final int id) {
        System.out.println(id + " player::Bag: ");
        for (Goods g : players.get(id).getBag().keySet()) {
            for (int i = 0; i < players.get(id).getBag().get(g); ++i) {
                System.out.print(g.toString() + " ");
            }
        }
        System.out.println("");
    }

    public void printDecGoods(final int id) {
        System.out.println(id + " player::decGoods: " + players.get(id).getDecGood());
    }

    public void printCoins(final int id) {
        System.out.println(id + "player::coins: " + players.get(id).getCoins());
    }

    public void printAssetsOnMerchandStand(final int id) {
        System.out.println(id + "player::assetOnMerchandStand:");
        for (Goods g : players.get(id).getAssetOnMerchantStand().keySet()) {
            for (int i = 0; i < players.get(id).getAssetOnMerchantStand().get(g); ++i) {
                System.out.print(g.toString() + " ");
            }
        }
        System.out.println("");

    }

}
