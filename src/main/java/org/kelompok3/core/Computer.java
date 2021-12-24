package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Computer extends Player {
    private boolean hasMove = false;
    private boolean isMyTurn = false;

    public Computer(int id, @NotNull List<LittleHole> littleWhole, @NotNull BigHole bigWhole) {
        super(id, littleWhole, bigWhole);
    }

    @Override
    public boolean isComputer() {
        return true;
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

    public Solution getSolution(@NotNull List<Hole> nodes){
        var BOT = new Bot();
        // start backtracking
        BOT.backtracking(nodes);
        return BOT.solution;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", littleHole=" + littleHole +
                ", bigHole=" + bigHole +
                ", HoleList=" + holeList() +
                '}';
    }
}
