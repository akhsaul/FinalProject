package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("unused")
public class Solution {
    private boolean solution = false;
    private Integer indexHole = null;

    public boolean hasSolution() {
        return solution;
    }

    /**
     * Only set solution when the "firstNode" direct to "BigWhole" (LastNode)
     */
    public void setByBigHole(@NotNull Hole firstNode, List<Hole> nodes) {
        if (!firstNode.isBigHole()) {
            indexHole = nodes.indexOf(firstNode);
            solution = true;
        }
    }

    public void setByMaxPoint(@NotNull List<Hole> nodes) {
        if(!solution) {
            var maxPoint = 0;
            var length = nodes.size();
            for (int i = 0; i < length; i++) {
                var node = nodes.get(i);
                if (!node.isBigHole() && node.hasPoint()){
                    if (node.getPoint() > maxPoint){
                        maxPoint = node.getPoint();
                        indexHole = i;
                        solution = true;
                    }
                }
            }
        }
    }

    public Integer getIndexHole() {
        if (indexHole == null) {
            throw new IllegalStateException("Does not have a solution");
        }
        solution = false;
        return indexHole;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "solution=" + solution +
                ", indexHole=" + indexHole +
                '}';
    }
}
