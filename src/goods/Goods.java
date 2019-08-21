/**
 * Goods
 * <p>
 * 22-Oct-18
 *
 * @author Gheoace Mihai
 */

package goods;

/**.
 * based on https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
 * sorting
 * https://stackoverflow.com/questions/8007108/java-sorting-based-on-enum-constants
 */

public enum Goods {
    BREAD(true, (short) 4, (short) 2),
    CHICKEN(true, (short) 4, (short) 2),
    CHEESE(true, (short) 3, (short) 2),
    APPLE(true, (short) 2, (short) 2),
    SILK(false, (short) 9, (short) 4),
    PEPPER(false, (short) 8, (short) 4),
    BARREL(false, (short) 7, (short) 4);

    private final boolean type;  // false - illegal, true - legal
    private final short profit;
    private final short penalty;

    Goods(final boolean type,
          final short profit,
          final short penalty) {
        this.type = type;
        this.profit = profit;
        this.penalty = penalty;
    }

    public boolean isLegal() {
        return type;
    }

    public short getPenalty() {
        return penalty;
    }

    public short getProfit() {
        return profit;
    }
}
