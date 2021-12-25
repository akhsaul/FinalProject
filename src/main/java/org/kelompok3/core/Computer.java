package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Computer extends Player {
    public Computer(int id, @NotNull List<LittleHole> littleWhole, @NotNull BigHole bigWhole) {
        super(id, littleWhole, bigWhole);
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
