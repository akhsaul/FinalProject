package org.kelompok3.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import javax.swing.*;

public class LittleHole extends Hole {
    private int x = -1;
    private int y = -1;
    @JsonIgnore
    private JButton button = null;

    @JsonIgnore
    public void setButton(@NotNull JButton holeBtn, int x, int y) {
        button = holeBtn;
        this.x = x;
        this.y = y;
        changeImg();
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @JsonIgnore
    public @NotNull JButton getButton() {
        return Utils.notNull(button);
    }

    private LittleHole() {
        super();
    }

    public LittleHole(@NotNull String id, int index) {
        this(id, index, 7);
    }

    public LittleHole(String id, int index, int seed) {
        super(id, index, seed);
    }

    protected void changeImg() {
        if (button != null && label != null) {
            var path = "";
            if (seed == 0) {
                path = "hole_L.png";
            } else if (seed <= 20) {
                path += "hole_L_" + seed + ".png";
            } else {
                path = "hole_L_20.png";
            }
            label.setText(String.valueOf(seed));
            button.setIcon(Utils.getIcon(path));
        }
    }

    @Override
    public boolean isBigHole() {
        return false;
    }
}
