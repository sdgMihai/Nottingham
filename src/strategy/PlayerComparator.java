/**
 * PlayerComparator
 * <p>
 * 29-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import java.util.Comparator;

public final class PlayerComparator implements Comparator<AbstractPlayer> {
    @Override
    public int compare(final AbstractPlayer a, final AbstractPlayer b) {
        return b.getCoins() - a.getCoins();
    }
}
