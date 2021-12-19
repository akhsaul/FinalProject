package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import javax.swing.*;

public class BigHole extends Hole {
    private JLabel labelImg = null;

    public BigHole(@NotNull String id) {
        this(id, 0);
    }

    private BigHole(String id, int seed) {
        super(id, -1, seed);
    }

    @Override
    protected void changeImg() {
        if (label != null && labelImg != null) {
            var path = "";
            if (seed == 0) {
                path = "hole_B.png";
            } else if (seed <= 30) {
                path += "hole_B_" + seed + ".png";
            } else {
                path = "hole_B_20.png";
            }
            label.setText(String.valueOf(seed));
            labelImg.setIcon(Utils.getIcon(path));
        }
    }

    public void setLabelImg(@NotNull JLabel label){
        labelImg = label;
    }

    @Override
    public boolean isBigHole() {
        return true;
    }
}
