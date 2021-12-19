package org.kelompok3.database;

import org.jetbrains.annotations.NotNull;
import org.kelompok3.Utils;
import org.kelompok3.core.State;
import org.kelompok3.ui.Status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConnector {
    private static final String dbName = "congklak";
    private static Connection connection = null;
    private static Statement statement = null;
    private static final String tbSetting = "setting";
    private static final String tbScore = "score";
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static void connect() {
        try {
            // register driver
            Class.forName(JDBC_DRIVER);
            if (connection == null) {

                // connect without database
                connection = DriverManager.getConnection(url,
                        "root", "");
                var statement = connection.createStatement();

                // make database
                var sql = "create database if not exists " + dbName;
                statement.execute(sql);

                // make table "setting"
                sql = "create table if not exists " + dbName + "." + tbSetting +
                        " (`player_id` int not null auto_increment primary key ," +
                        "`player_name` varchar(9) not null," +
                        "`bgm_enabled` BOOLEAN not null ," +
                        "`sfx_enabled` BOOLEAN not null)";
                statement.execute(sql);

                // make table "score"
                sql = "create table if not exists congklak.score(\n" +
                        "    `id_score` int not null auto_increment primary key,\n" +
                        "    `player_id` int not null,\n" +
                        "    `score` int not null ,\n" +
                        "    `status` enum('Menang','Kalah','Seri') not null,\n" +
                        "    FOREIGN KEY(`player_id`) REFERENCES setting(`player_id`)\n" +
                        ")";
                statement.execute(sql);

                // close all
                statement.close();
                connection.close();

                // reconnect with database
                connection = DriverManager.getConnection(url + dbName,
                        "root", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 0 is false.
     * <p>
     * 1 is true.
     */
    private static int toInt(boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void initSetting() {
        connect();

        try {
            var sql = "insert into " + tbSetting + " (`player_name`, `bgm_enabled`, `sfx_enabled`)" +
                    " values ('" + State.getPlayerName() + "', '" +
                    toInt(State.isEnableBgm()) + "', '" +
                    toInt(State.isEnableSfx()) + "')";
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveSetting(String oldPlayerName, String newPlayerName, boolean bgm, boolean sfx) {
        connect();

        try {
            var sql = "update " + tbSetting + " set `player_name` = '" + newPlayerName + "'," +
                    "`bgm_enabled` = '" + toInt(bgm) + "'," +
                    "`sfx_enabled` = '" + toInt(sfx) + "' " +
                    "WHERE `setting`.`player_name` = '" + oldPlayerName + "'; ";
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveScore(int playerID, int score, Status status) {
        connect();
        try {
            String stats = "";
            switch (status) {
                case SERI -> stats = "Seri";
                case MENANG -> stats = "Menang";
                case KALAH -> stats = "Kalah";
            }

            var sql = "insert into " + tbScore + " (`id_score`, `player_id`, `score`, `status`)" +
                    " values (null, '" + playerID + "', '" + score + "', '" + stats + "')";
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static @NotNull ResultSet getScore() {
        connect();
        ResultSet resultSet = null;
        try {
            var sql ="SELECT `setting`.`player_id`, `setting`.`player_name`, `score`.`score`, `score`.`status` FROM score" +
                    " INNER JOIN setting ON score.player_id = setting.player_id;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Utils.notNull(resultSet);
    }

    public static @NotNull ResultSet getSetting() {
        connect();
        ResultSet resultSet = null;
        try {
            var sql = "SELECT * FROM " + tbSetting + " LIMIT 1";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Utils.notNull(resultSet);
    }

    public static synchronized void closeStatement() {
        try {
            if (statement != null) {
                if (!statement.isClosed()) {
                    statement.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}