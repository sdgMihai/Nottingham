/**
 * Main
 * <p>
 * 7-Nov-18
 *
 * @author Gheoace Mihai
 */
package utilitary;

public final class Pair<T, U> {
    private T first;
    private U second;

    public Pair(final T first, final U second) {
        this.first = first;
        this.second = second;
    }

    public Pair() {
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public void setFirst(final T first) {
        this.first = first;
    }

    public void setSecond(final U second) {
        this.second = second;
    }
}
