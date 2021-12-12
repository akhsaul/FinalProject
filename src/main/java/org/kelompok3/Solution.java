package org.kelompok3;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class Solution {
    private boolean solution = false;
    private Whole whole = null;

    boolean hasSolution() {
        return solution;
    }

    /**
     * Only set solution when the "firstNode" direct to "BigWhole" (LastNode)
     */
    void setByBigWhole(@NotNull Whole firstNode) {
        solution = true;
        whole = firstNode;
    }

    void setByMaxPoint(@NotNull List<Whole> nodes) {
        if(!solution) {
            var maxPoint = 0;
            for (Whole node : nodes) {
                if (!node.isBigWhole() && node.hasPoint()) {
                    if (node.getPoint() > maxPoint) {
                        maxPoint = node.getPoint();
                        whole = node;
                        solution = true;
                    }
                }
            }
        }
    }

    Whole getWhole() {
        if (whole == null) {
            throw new IllegalStateException("Does not have a solution");
        }
        return whole;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solution=" + solution +
                ", whole=" + whole +
                '}';
    }
}
