package org.kelompok3.model;

import org.kelompok3.ui.Status;

public class ScoreModel {
    private String playerName;
    private Integer score;
    private Status status;

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getScore() {
        return score;
    }

    public Status getStatus() {
        return status;
    }
}
