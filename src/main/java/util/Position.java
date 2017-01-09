package util;

/**
 * Created by rudy on 31/12/16.
 */
public class Position {

    /* Returns the position in the given direction */
    public static Tuple<Integer, Integer> getInDirection (Tuple<Integer, Integer> pos, Directions dir) {
        switch (dir) {
            case UP:
                return new Tuple<> (pos.getFst(), pos.getSnd() - 1);
            case DOWN:
                return new Tuple<> (pos.getFst(), pos.getSnd() + 1);
            case RIGHT:
                return new Tuple<> (pos.getFst() + 1, pos.getSnd());
            case LEFT:
                return new Tuple<> (pos.getFst() - 1, pos.getSnd());
            default:
                return null;
        }

    }

    public static Directions getDirectionFromPositions (Tuple<Integer, Integer> from, Tuple<Integer, Integer> to) {
        if (from.getFst() == to.getFst() && from.getSnd() - 1 == to.getSnd())
            return Directions.UP;
        else if (from.getFst() == to.getFst() && from.getSnd() + 1 == to.getSnd())
            return Directions.DOWN;
        else if (from.getFst() + 1 == to.getFst() && from.getSnd()  == to.getSnd())
            return Directions.RIGHT;
        else if (from.getFst() - 1 == to.getFst() && from.getSnd() == to.getSnd())
            return Directions.LEFT;
        else
            return null;
    }


}
