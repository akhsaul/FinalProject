package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import javax.swing.*;

public class BigHole extends Hole {
    private JLabel labelImg = null;

    public BigHole(@NotNull String id) {
        this(id, 0);
    }

    public BigHole(String id, int seed) {
        super(id, seed);
    }

    @Override
    protected void changeImg() {
        if (label != null && labelImg != null) {
            var path = "";
            if (seed <= 30) {
                path += "assets/hole_B_" + seed + ".png";
            } else {
                path = "assets/hole_B_30.png";
            }
            label.setText(String.valueOf(seed));
            labelImg.setIcon(Utils.getIcon(path));
        }
    }

    public void setLabelImg(@NotNull JLabel label){
        labelImg = label;
        changeImg();
    }

    @Override
    public boolean isBigHole() {
        return true;
    }

    @Override
    public Hole clone() {
        return new BigHole(this.getId(), this.seed);
    }
}
