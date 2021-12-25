package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

@SuppressWarnings("unused")
public final class State {
    private static String playerName = "Pemain";
    private static Integer playerID = null;
    private static Human human = null;
    private static Computer computer = null;
    private static Integer currentTurn = null;
    private static boolean enableBgm = true;
    private static Player winner = null;

    private State() {
    }

    public static synchronized void setWinner(Player player) {
        winner = player;
    }

    public static synchronized Player getWinner() {
        return winner;
    }

    public static synchronized boolean hasWinner() {
        return winner != null;
    }

    public static synchronized void setCurrentTurn(int playerId) {
        currentTurn = playerId;
        if (!(isHumanTurn() || isComputerTurn())) {
            currentTurn = null;
            throw new IllegalArgumentException("Wrong id");
        }
    }

    public static synchronized boolean isComputerTurn() {
        return currentTurn == Utils.notNull(computer).id;
    }

    public static synchronized boolean isHumanTurn() {
        return currentTurn == Utils.notNull(human).id;
    }

    public static synchronized void setEnableBgm(boolean isEnable) {
        enableBgm = isEnable;
    }

    public static synchronized boolean isEnableBgm() {
        return enableBgm;
    }

    public static synchronized Human getHumanPLayer() {
        return human;
    }

    public static synchronized Computer getComputerPlayer() {
        return computer;
    }

    public static synchronized void setHumanPLayer(Human player) {
        human = player;
    }

    public static synchronized void setComputerPlayer(Computer player) {
        computer = player;
    }

    public static synchronized String getPlayerName() {
        return playerName;
    }

    public static synchronized void setPlayerName(@NotNull String name) {
        playerName = name;
    }

    public static void setPlayerID(int id) {
        playerID = id;
    }

    /**
     * only for saving score
     */
    public static Integer getPlayerID() {
        return Utils.notNull(playerID);
    }
}
