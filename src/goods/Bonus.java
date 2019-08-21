/**
 * Bonus
 * <p>
 * 23-Oct-18
 *
 * @author Gheoace Mihai
 */

package goods;


import utilitary.Pair;

public final class Bonus {
    public static final Bonus INSTANCE = new Bonus();
    static final Integer SILK_BONUSCNT = 3;
    static final Integer PEPPER_BONUSCNT = 2;
    static final Integer BARREL_BONUSCNT = 2;

    public Pair<Goods, Integer> getBonus(final Goods good) {
        switch (good) {
            case SILK:
                return new Pair<Goods, Integer>(Goods.CHEESE, SILK_BONUSCNT);
            case PEPPER:
                return new Pair<Goods, Integer>(Goods.CHICKEN, PEPPER_BONUSCNT);
            case BARREL:
                return new Pair<Goods, Integer>(Goods.BREAD, BARREL_BONUSCNT);
            default:
                System.out.println("default Bonus");
                System.exit(1);
        }
        return new Pair<Goods, Integer>(Goods.BREAD, -1);
    }
}
