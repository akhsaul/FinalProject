package org.kelompok3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    int id;
    List<Whole> littleWhole;
    Whole bigWhole;
    List<Whole> wholeList;

    Player(int id, @NotNull List<Whole> littleWhole, @NotNull Whole bigWhole) {
        this.id = id;
        this.littleWhole = Collections.unmodifiableList(littleWhole);
        this.bigWhole = bigWhole;

        var tmp = new ArrayList<>(this.littleWhole);
        tmp.add(bigWhole);
        wholeList = Collections.unmodifiableList(tmp);
    }

    @Override
    public String toString() {
        return "me.akhsaul.congklak.Player{" +
                "id='" + id + '\'' +
                ", littleWhole=" + littleWhole +
                ", bigWhole=" + bigWhole +
                ", wholeList=" + wholeList +
                '}';
    }
}
