package org.kelompok3.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import javax.swing.*;
import java.util.List;

@SuppressWarnings("unused")
public abstract class Hole implements Cloneable{
    private String id;
    @JsonIgnore
    private int seed;
    @JsonIgnore
    private int point = 0;
    @JsonIgnore
    private int earlySeed;
    @JsonIgnore
    protected JLabel label = null;

    Hole() {
    }

    private Hole(@NotNull String id) {
        this.id = id;
    }

    public Hole(String id, int seed) {
        this(id);

        this.seed = seed;
        this.earlySeed = seed;
    }

    public void setLabel(@NotNull JLabel holeLabel) {
        label = holeLabel;
        changeImg();
    }

    public void clearPoint(){
        point = 0;
    }

    public @NotNull JLabel getLabel() {
        return Utils.notNull(label);
    }

    public String getId() {
        return this.id;
    }

    public void addSeed(int total) {
        this.seed += total;
    }

    public void addSeed(int total, boolean changeImg) {
        addSeed(total);
        if (changeImg) {
            changeImg();
        }
    }

    public void setSeed(int total) {
        this.seed = total;
    }

    public void removeSeed(int total) {
        this.seed -= total;
    }

    /**
     * Take all seed then clear these Hole.
     * So, you can't take seed twice or more
     */
    public int takeSeed() {
        var seed = this.seed;
        earlySeed = this.seed;
        // clear seed because the seed has been taken
        this.seed = 0;

        return seed;
    }

    public int takeSeed(boolean changeImg) {
        var result = takeSeed();
        if (changeImg) {
            changeImg();
        }
        return result;
    }

    public int totalSeed() {
        return seed;
    }

    public int earlySeed() {
        return earlySeed;
    }

    public boolean seedIsEmpty() {
        return seed == 0;
    }

    /**
     * Use these if you want to set a point without do "Shoot"
     */
    public int addPoint(@NotNull Integer point) {
        this.point += point;

        return point;
    }

    public Hole getCrossNode(@NotNull List<Hole> nodes) {
        var indexOfNode = nodes.indexOf(this);
        return nodes.get((nodes.size() - 1) - indexOfNode);
    }

    public int getPoint() {
        return point;
    }

    public boolean hasPoint() {
        return point > 0;
    }

    protected abstract void changeImg();

    public abstract boolean isBigHole();

    @Override
    public String toString() {
        var name = this.getClass().getSimpleName();
        return name + "{" +
                "id='" + id + '\'' +
                ", point=" + point +
                ", seed=" + seed +
                ", earlySeed=" + earlySeed +
                '}';
    }
    // clone
    public abstract Hole clone();
}
