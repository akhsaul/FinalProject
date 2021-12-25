package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Human extends Player {
    public Human(int id, @NotNull List<LittleHole> littleWhole, @NotNull BigHole bigWhole) {
        super(id, littleWhole, bigWhole);
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
