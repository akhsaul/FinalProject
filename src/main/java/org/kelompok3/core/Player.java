package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {
    public int id;
    public List<LittleHole> littleHole;
    public BigHole bigHole;

    Player(int id, @NotNull List<LittleHole> littleHole, @NotNull BigHole bigHole) {
        this.id = id;
        this.littleHole = Collections.unmodifiableList(littleHole);
        this.bigHole = bigHole;
    }

    @Override
    public abstract String toString();

    public List<Hole> holeList(){
        List<Hole> tmp = new ArrayList<>(this.littleHole);
        tmp.add(this.bigHole);
        return Collections.unmodifiableList(tmp);
    }
}
