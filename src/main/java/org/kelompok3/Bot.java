package org.kelompok3;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Bot {
    Solution solution = new Solution();

    boolean cek = false;
    boolean doShoot = false;

    int addedPoint = 0;
    int seedInHand = 0;

    List<List<Integer>> history = new ArrayList<>();

    Whole firstNode;
    Whole lastNode;

    void backtracking(@NotNull List<Whole> nodes) {
        backtracking(nodes, 4);
    }

    void backtracking(@NotNull List<Whole> nodes, int i) {
        history.clear();
        if (i > 6) {
            solution.setByMaxPoint(nodes);
        } else {
            addHistory(i);
            firstNode = nodes.get(i);
            lastNode = nodes.get(i);
            // if node doesn't have seed
            // then take another node
            if (nodes.get(i).seedIsEmpty()) {
                System.out.println("SKIP " + nodes.get(i));
                backtracking(nodes, ++i);
            } else {
                var deep = 0;
                while (true) {
                    // stop if deep more than 10
                    if (deep > 10) {
                        break;
                    }

                    var start = getIndexLastHistory();
                    if (cek) {
                        // TODO
                        //   do unMove
                        //   backTrack all node
                        removeLastHistory();
                        previous(nodes, start);
                        seedInHand = 0;
                        // after moving backwards
                        // stop if history only contain index of first node
                        if (history.size() == 1) {
                            cek = false;
                            break;
                        }
                    } else {
                        // TODO
                        //  maybe will be replace with recursive
                        //  find out how to undo action (backtrack)
                        //  with recursive or using for loop
                        seedInHand = nodes.get(start).takeSeed();
                        setHistory(start, seedInHand);
                        next(nodes, start + 1);
                        System.out.println("(" + addedPoint + ", " + nodes.get(start).earlySeed() + ") " + nodes.get(start));
                        System.out.println("NextNode " + lastNode);
                        seedInHand = 0;
                        addedPoint = 0;

                        // if last node is Big Whole
                        // then set into solution
                        if (lastNode.isBigWhole()) {
                            solution.setByBigWhole(firstNode);
                        }
                        deep++;
                    }
                }
                // if previous node doesn't have solution
                // then take another node
                if (!solution.hasSolution()) {
                    backtracking(nodes, ++i);
                }
                System.out.println();
                for (Whole node : nodes) {
                    System.out.println(node);
                }
            }
        }
    }

    void next(@NotNull List<Whole> nodes, int i) {
        if (i >= nodes.size()) {
            i = 0;
        }
        lastNode = nodes.get(i);
        if (!lastNode.isBigWhole() && lastNode.seedIsEmpty() && seedInHand == 1) {
            cek = true;
            doShoot = true;
            //do "Shoot" here
            addedPoint += firstNode.addPoint(1 + lastNode.getCrossNode(nodes).takeSeed());
        } else {
            if (lastNode.isBigWhole()) {
                addedPoint += firstNode.addPoint(1);
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

    /*
    void move(@NotNull List<Whole> nodes, int i) {
        seedInHand = 0;
        seedInHand = nodes.get(i).takeSeed();
        var loop = seedInHand + i;
        var addedPoint = 0;
        for (int j = i + 1; j <= loop; j++) {
            if (j >= nodes.size()) {
                loop = seedInHand - 1;
                j = 0;
            }

            lastNode = nodes.get(j);

            if (!nodes.get(j).isBigWhole() && nodes.get(j).seedIsEmpty() && seedInHand == 1) {
                cek = true;
                doShoot = true;
                 //do "Shoot" here
                addedPoint = firstNode.addPoint(1 + lastNode.getCrossNode(nodes).takeSeed());
            } else {
                if (nodes.get(j).isBigWhole()) {
                    addedPoint = firstNode.addPoint(1);
                    if (seedInHand == 0) {
                        cek = true;
                    }
                }
            }
            nodes.get(j).addSeed(1);

            seedInHand--;
        }

        history.add(loop);
        System.out.println("(" + addedPoint + ", " + nodes.get(i).earlySeed() + ") " + nodes.get(i));
        System.out.println("NextNode " + lastNode);
    }
    */

    void previous(List<Whole> nodes, int i) {
        if (i < 0) {
            i = lastIndex(nodes);
        }
        // undo action "do Shoot"
        if (doShoot) {
            var crossNode = lastNode.getCrossNode(nodes);
            crossNode.setSeed(crossNode.earlySeed());
            doShoot = false;
        }

        /*
        if (loop == 0 || (firstNode == lastNode && seedInHand > 0)) {
            nodes.get(i).setSeed(seedInHand);
            return i;
        } else {
            nodes.get(i).removeSeed(1);
            seedInHand++;
            return unMove(nodes, --i, --loop);
        }
         */
        // TODO
        //  if there is loop between 0, 14, 13, ...
        //  and the last history is 0
        //  then it will be run (won't be a loop)
        //  Need to prevent this behaviour
        /**
         * history = [4, 9, 0]
         * i = 0
         * seedInHand = 1
         * C-7 should be 15 seed not 1 seed
         * */
        var history = getLastHistory();
        if (i == history.get(0) && seedInHand >= history.get(1)) {
            nodes.get(i).setSeed(seedInHand);
        } else {
            nodes.get(i).removeSeed(1);
            seedInHand++;
            previous(nodes, --i);
        }
    }

    void removeLastHistory(){
        history.remove(lastIndex(history));
    }

    void addHistory(int index) {
        history.add(List.of(index, 0));
    }

    void setHistory(int index, int value){
        history.set(lastIndex(history), List.of(index, value));
    }

    int getIndexLastHistory() {
        return getLastHistory().get(0);
    }

    List<Integer> getLastHistory(){
        return history.get(lastIndex(history));
    }

    <T> int lastIndex(@NotNull List<T> list) {
        return list.size() - 1;
    }
}
