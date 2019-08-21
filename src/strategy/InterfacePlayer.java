/**
 * InterfacePlayer
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package strategy;

import goods.Goods;

import java.util.ArrayList;

public interface InterfacePlayer {
    void makeBag();

    void inspection(AbstractPlayer commerciant, ArrayList<Goods> cards);

    void replenish(ArrayList<Goods> queue);

    int getBribe();

    void setBribe(int bribe);

    void setScore();
}
