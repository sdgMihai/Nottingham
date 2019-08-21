/**
 * RoyalBonus
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package goods;

import strategy.AbstractPlayer;

import java.util.ArrayList;

public final class RoyalBonus {
    public static final RoyalBonus INSTANCE  = new RoyalBonus();
    private boolean existKing;
    private boolean existQueen;
    private ArrayList<Integer> kingId;
    private ArrayList<Integer> queenId;

    private RoyalBonus() {
        kingId = new ArrayList<>();
        queenId = new ArrayList<>();
    }

    public ArrayList<Integer> getKingId() {
        return kingId;
    }

    public ArrayList<Integer> getQueenId() {
        return queenId;
    }

    /**
     * Set king qnd queen for given type g.
     * @param players
     * @param g
     */
    public void setKingAndQueen(final ArrayList<AbstractPlayer> players, final Goods g) {
        if (!g.isLegal()) {
            return;
        }

        existKing = false;
        existQueen = false;

        ArrayList<Integer> id = new ArrayList<>();
        for (int i = 0; i < players.size(); ++i) {
            id.add(i);
        }

        sort(players, g, id);
        // king's nr of elem
        int firstQuantity = players.get(id.get(0)).getAssetOnMerchantStand().getOrDefault(g, 0);
        int kingCnt = 1;
        // get Nr of players with king's nr of elem
        for (int i = 1; i < players.size(); ++i) {
            if (firstQuantity
                    == players.get(id.get(i)).getAssetOnMerchantStand().getOrDefault(g, 0)) {
                kingCnt++;
            }
        }
        for (int i = 0; i < kingCnt; ++i) {
            kingId.add(id.get(i));
        }
        existKing = true;
        if (kingCnt != players.size()) {
            int queenCnt = 1;
            int secondQuantity =
                    players.get(id.get(kingCnt)).getAssetOnMerchantStand().getOrDefault(g, 0);
            for (int i = kingCnt + 1; i < players.size(); ++i) {
                if (secondQuantity
                        == players.get(id.get(i)).getAssetOnMerchantStand().getOrDefault(g, 0)) {
                    queenCnt++;
                }
            }
            for (int i = kingCnt; i < kingCnt + queenCnt; ++i) {
                queenId.add(id.get(i));
            }
            existQueen = true;
        }
    }

    public boolean existKing() {
        return existKing;
    }

    public boolean existQueen() {
        return existQueen;
    }

    /**
     * Sort players' id by g's frequency.
     * @param players
     * @param g
     * @param id
     */
    private void sort(final ArrayList<AbstractPlayer> players,
                      final Goods g,
                      final ArrayList<Integer> id) {
        boolean ok = false;
        Integer idSize = id.size();
        while (!ok) {
            ok = true;
            for (int i = 1; i < idSize; ++i) {
                if (players.get(id.get(i)).getAssetOnMerchantStand().getOrDefault(g, 0)
                        > players.get(id.get(i - 1)).getAssetOnMerchantStand().getOrDefault(g, 0)) {
                    Integer aux = id.get(i);
                    id.set(i, id.get(i - 1));
                    id.set(i - 1, aux);
                    ok = false;
                }
            }
            idSize--;
        }
    }

    /**
     *  Get king Bonus for given type.
     * @param good
     * @return king bonus for given type
     */
    public short getKingsBonus(final Goods good) {
        final int appleBonus = 20;
        final int cheeseBreadBonus = 15;
        final int chickenBonus = 10;
        switch (good) {
            case APPLE:
                return appleBonus;
            case CHEESE: case BREAD:
                return cheeseBreadBonus;
            case CHICKEN:
                return chickenBonus;
            default:
                return 0;
        }
    }

    /**
     *  Get queens Bonus for given type.
     * @param good
     * @return queen bonus for given type
     */
    public short getQueensBonus(final Goods good) {
        final int appleCheeseBreadBonus = 10;
        final int chickenBonus = 5;
        switch (good) {
            case APPLE: case CHEESE: case BREAD:
                return appleCheeseBreadBonus;
            case CHICKEN:
                return chickenBonus;
            default:
                return 0;
        }
    }

    public void resetKingQueen() {
        kingId.clear();
        queenId.clear();
    }
}
