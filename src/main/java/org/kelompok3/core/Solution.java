package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class Solution {
    private boolean solution = false;
    private LittleHole hole = null;

    public boolean hasSolution() {
        return solution;
    }

    /**
     * Only set solution when the "firstNode" direct to "BigWhole" (LastNode)
     */
    public void setByBigHole(@NotNull Hole firstNode) {
        if (!firstNode.isBigHole()) {
            firstNode.clearPoint();
            solution = true;
            hole = (LittleHole) firstNode;
        }
    }

    public void setByMaxPoint(@NotNull List<Hole> nodes) {
        if(!solution) {
            var maxPoint = 0;
            for (Hole node : nodes) {
                if (!node.isBigHole() && node.hasPoint()) {
                    if (node.getPoint() > maxPoint) {
                        maxPoint = node.getPoint();
                        hole = (LittleHole) node;
                        solution = true;
                    }
                    node.clearPoint();
                }
            }
        }
    }

    public LittleHole getHole() {
        if (hole == null) {
            throw new IllegalStateException("Does not have a solution");
        }
        solution = false;
        return hole;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solution=" + solution +
                ", hole=" + hole +
                '}';
    }
}
