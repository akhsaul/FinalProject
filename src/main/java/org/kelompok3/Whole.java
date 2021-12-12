package org.kelompok3;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class Whole {
    private final String id;
    private final Type type;
    private int point = 0;
    private int seed = 7;
    private int earlySeed = 0;
    private boolean isBigWhole = false;

    Whole(@NotNull String id, @NotNull Type type) {
        this.id = id;
        this.type = type;
    }

    Whole(String id, Type type, int seed) {
        this(id, type);

        this.seed = seed;
        this.earlySeed = seed;
    }

    Whole(String id, Type type, int seed, @NotNull Size size) {
        this(id, type, seed);

        this.isBigWhole = size == Size.BIG;
    }

    enum Size {
        BIG,
        LITTLE
    }

    enum Type {
        COMPUTER,
        HUMAN,
    }

    public String getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public boolean isBigWhole() {
        return this.isBigWhole;
    }

    void addSeed(int total) {
        this.seed += total;
        //this.earlySeed += total;
    }

    void setSeed(int total) {
        this.seed = total;
    }

    void removeSeed(int total) {
        this.seed -= total;
        //this.earlySeed -= total;
    }

    /**
     * Take all seed then clear these Whole.
     * So, you can't take seed twice or more
     */
    int takeSeed() {
        var seed = this.seed;
        earlySeed = this.seed;
        // clear seed because the seed has been taken
        this.seed = 0;

        return seed;
    }

    int totalSeed(){
        return seed;
    }

    int earlySeed() {
        return earlySeed;
    }

    boolean seedIsEmpty() {
        return seed == 0;
    }

    /**
     * Use these if you want to set a point without do "Shoot"
     */
    int addPoint(@NotNull Integer point) {
        this.point += point;

        return point;
    }

    void resetSeed(@NotNull Whole lastNode, @NotNull List<Whole> nodes) {

    }

    Whole getCrossNode(@NotNull List<Whole> nodes) {
        var indexOfNode = nodes.indexOf(this);
        return nodes.get((nodes.size() - 1) - indexOfNode);
    }

    int getPoint() {
        return point;
    }

    boolean hasPoint() {
        return point > 0;
    }

    @Override
    public String toString() {
        return "Whole{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", point=" + point +
                ", seed=" + seed +
                ", earlySeed=" + earlySeed +
                ", isBigWhole=" + isBigWhole +
                '}';
    }
}
