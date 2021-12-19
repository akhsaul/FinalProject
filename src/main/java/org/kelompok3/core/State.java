package org.kelompok3.core;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;

@SuppressWarnings("unused")
public final class State {
    private static String playerName = "Pemain";
    private static Human human = null;
    private static Computer computer = null;
    private static Integer currentTurn = null;
    private static boolean enableBgm = true;
    private static boolean enableSfx = true;

    private State() {
    }

    public static synchronized void setCurrentTurn(int playerId) {
        currentTurn = playerId;
        if(isHumanTurn()){
            human.setMyTurn(true);
        } else if (isComputerTurn()){
            computer.setMyTurn(true);
        } else {
            currentTurn = null;
            throw new IllegalArgumentException("Wrong id");
        }
    }

    public static synchronized boolean isComputerTurn(){
        Utils.notNull(computer);
        return currentTurn == computer.id;
    }

    public static synchronized boolean isHumanTurn(){
        Utils.notNull(human);
        return currentTurn == human.id;
    }

    public static synchronized void setEnableBgm(boolean isEnable) {
        enableBgm= isEnable;
    }

    public static synchronized boolean isEnableBgm() {
        return enableBgm;
    }

    public static synchronized void setEnableSfx(boolean isEnable) {
        enableSfx = isEnable;
    }

    public static synchronized boolean isEnableSfx() {
        return enableSfx;
    }

    public static synchronized Human getHumanPLayer(){
        return human;
    }
    public static synchronized Computer getComputerPlayer(){
        return computer;
    }

    public static synchronized void setHumanPLayer(Human player){
        human = player;
    }
    public static synchronized void setComputerPlayer(Computer player){
        computer = player;
    }

    public static synchronized String getPlayerName(){return playerName;}

    public static synchronized void setPlayerName(@NotNull String name){playerName = name;}
}
