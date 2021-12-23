package org.kelompok3.model;

import java.util.ArrayList;

public class NodesModel {
    private PlayerModel human;
    private PlayerModel computer;
    public NodesModel(){}

    public PlayerModel getHuman() {
        return human;
    }

    public void setHuman(PlayerModel human) {
        this.human = human;
    }

    public PlayerModel getComputer() {
        return computer;
    }

    public void setComputer(PlayerModel computer) {
        this.computer = computer;
    }

    @Override
    public String toString() {
        return "{" +
                "human=" + human +
                ", computer=" + computer +
                '}';
    }

    public static class PlayerModel {
        private ArrayList<HoleModel> littleHole;
        private HoleModel bigHole;
        public PlayerModel(){}

        public ArrayList<HoleModel> getLittleHole() {
            return littleHole;
        }

        public void setLittleHole(ArrayList<HoleModel> littleHole) {
            this.littleHole = littleHole;
        }

        public HoleModel getBigHole() {
            return bigHole;
        }

        public void setBigHole(HoleModel bigHole) {
            this.bigHole = bigHole;
        }

        @Override
        public String toString() {
            return "{" +
                    "littleHole=" + littleHole +
                    ", bigHole=" + bigHole +
                    '}';
        }

        public static class HoleModel {
            private String name;
            private Integer seed;
            private Boolean isBigHole;
            public HoleModel(){}

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSeed() {
                return seed;
            }

            public void setSeed(Integer seed) {
                this.seed = seed;
            }

            public Boolean getisBigHole() {
                return isBigHole;
            }

            public void setisBigHole(Boolean bigHole) {
                isBigHole = bigHole;
            }

            @Override
            public String toString() {
                return "{" +
                        "name='" + name + '\'' +
                        ", seed=" + seed +
                        ", isBigHole=" + isBigHole +
                        '}';
            }
        }
    }
}
