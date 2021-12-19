package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Human extends Player {
    private boolean hasMove = false;
    private boolean isMyTurn = false;

    public Human(int id, @NotNull List<LittleHole> littleWhole, @NotNull BigHole bigWhole) {
        super(id, littleWhole, bigWhole);
    }

    @Override
    public boolean isComputer() {
        return false;
    }

    @Override
    public void setMyTurn(boolean enable) {
        isMyTurn = enable;
    }

    @Override
    public boolean isMyTurn() {
        return isMyTurn;
    }

    @Override
    public boolean hasMove() {
        return hasMove;
    }

    @Override
    public String toString() {
        return "Human{" +
                "id=" + id +
                ", littleHole=" + littleHole +
                ", bigHole=" + bigHole +
                ", HoleList=" + holeList() +
                '}';
    }
}
