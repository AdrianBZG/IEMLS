package util;

/**
 * Util class, add a parametric tuple type
 * Created by eleazardd on 29/11/16.
 */
public class Tuple<A, B> {
    private A fst;
    private B snd;

    public Tuple(A fst, B snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public A getWidth() {
        return fst;
    }

    public B getHeight() {
        return snd;
    }

    public A getX() {
        return fst;
    }

    public B getY() {
        return snd;
    }

    public A getFst() {
        return fst;
    }

    public void setFst(A fst) {
        this.fst = fst;
    }

    public B getSnd() {
        return snd;
    }

    public void setSnd(B snd) {
        this.snd = snd;
    }
}
