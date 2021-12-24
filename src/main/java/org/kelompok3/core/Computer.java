package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Computer extends Player {
    private boolean hasMove = false;
    private boolean isMyTurn = false;
    public static final Bot BOT = new Bot();

    public Computer(int id, @NotNull List<LittleHole> littleWhole, @NotNull BigHole bigWhole) {
        super(id, littleWhole, bigWhole);
    }

    @Override
    public boolean isComputer() {
        return true;
    }

    @Override
    public void setMyTurn(boolean enable) {
        isMyTurn = enable;
    }

    @Override
    public boolean isMyTurn() {
        return isMyTurn;
    }

    @Override
    public boolean hasMove() {
        return hasMove;
    }

    public Solution getSolution(@NotNull List<Hole> nodes){
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

    public static final class Bot {
        public final Solution solution = new Solution();

        private boolean cek = false;
        private boolean doShoot = false;

        private int addedPoint = 0;
        private int seedInHand = 0;

        private final LinkedList<List<Integer>> history = new LinkedList<>();

        private Hole firstNode = null;
        private Hole lastNode = null;

        public void backtracking(@NotNull List<Hole> nodes) {
            backtracking(nodes, 0);
        }

        private void backtracking(@NotNull List<Hole> nodes, int i) {
            history.clear();
            if (i > 6) {
                solution.setByMaxPoint(nodes);
            } else {
                addHistory(i);
                firstNode = nodes.get(i);
                // if node doesn't have seed
                // then take another node
                if (firstNode.seedIsEmpty()) {
                    backtracking(nodes, ++i);
                } else {
                    var deep = 0;
                    while (true) {
                        // stop if deep more than 10
                        if (deep > 10) {
                            cek = true;
                        }

                        var start = getIndexLastHistory();
                        if (cek) {
                            history.removeLast();
                            previous(nodes, start);
                            seedInHand = 0;
                            // after moving backwards
                            // stop if history only contain index of first node
                            if (history.size() == 1) {
                                break;
                            }
                        } else {
                            seedInHand = nodes.get(start).takeSeed();
                            setHistory(start, seedInHand);
                            next(nodes, start + 1);
                            seedInHand = 0;

                            // if last node is Big Hole
                            // then set into solution
                            if (lastNode.isBigHole()) {
                                solution.setByBigHole(firstNode, nodes);
                                break;
                            }
                            deep++;
                        }
                    }
                }
                // if previous node doesn't have solution
                // then take another node
                if (!solution.hasSolution()) {
                    backtracking(nodes, ++i);
                }
            }
        }

        private void next(@NotNull List<Hole> nodes, int i) {
            if (i >= nodes.size()) {
                i = 0;
            }
            lastNode = nodes.get(i);
            if (!lastNode.isBigHole() && lastNode.seedIsEmpty() && seedInHand == 1) {
                cek = true;
                doShoot = true;
                //do "Shoot" here
                firstNode.addPoint(1 + lastNode.getCrossNode(nodes).takeSeed());
            } else {
                if (lastNode.isBigHole()) {
                    firstNode.addPoint(1);
                    if (seedInHand == 1) {
                        cek = true;
                    }
                }
            }
            lastNode.addSeed(1);
            seedInHand--;

            if (seedInHand > 0) {
                next(nodes, ++i);
            } else {
                addHistory(i);
            }
        }

        private void previous(List<Hole> nodes, int i) {
            if (i < 0) {
                i = Utils.lastIndex(nodes);
            }
            // undo action "do Shoot"
            if (doShoot) {
                var crossNode = lastNode.getCrossNode(nodes);
                crossNode.setSeed(crossNode.earlySeed());
                doShoot = false;
            }

            var historyLast = history.getLast();
            if (i == historyLast.get(0) && seedInHand >= historyLast.get(1)) {
                nodes.get(i).setSeed(seedInHand);
            } else {
                nodes.get(i).removeSeed(1);
                seedInHand++;
                previous(nodes, --i);
            }
        }

        private void addHistory(int index) {
            history.add(List.of(index, 0));
        }

        private void setHistory(int index, int value){
            history.set(Utils.lastIndex(history), List.of(index, value));
        }

        private int getIndexLastHistory() {
            return history.getLast().get(0);
        }
    }
}
