package org.kelompok3.server;

import org.kelompok3.core.State;
import org.kelompok3.database.DBConnector;
import org.kelompok3.model.ScoreModel;
import org.kelompok3.ui.Status;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class SpringController {
    @GetMapping("/")
    String index() {
        return "Greetings from Spring Boot!<br>" +
                "Jika kamu ingin memainkan permainan congklak,<br>" +
                "Buka <a href='/congklak'>link ini</a>";
    }

    @GetMapping("/congklak")
    ModelAndView mainGUI() {
        DBConnector.prepareAll();
        return new ModelAndView("/main/");
    }

    @GetMapping("/congklak/pengaturan")
    ModelAndView settingGUI() {
        return new ModelAndView("/setting/");
    }

    @GetMapping("/congklak/suit")
    ModelAndView suitGUI() {
        return new ModelAndView("/suit/");
    }

    @GetMapping("/congklak/skor")
    ModelAndView scoreGUI() {
        return new ModelAndView("/score/");
    }

    @GetMapping(value = "/api/congklak/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> get(@PathVariable String key) {
        Object result = null;
        switch (key) {
            case "playerName":
                result = Map.of("playerName", State.getPlayerName());
                break;
            case "bgmEnabled":
                result = Map.of("bgmEnabled", State.isEnableBgm());
                break;
            case "score":
                try {
                    var resultSet = DBConnector.getScore();
                    ArrayList<ScoreModel> array = new ArrayList<>();
                    while (resultSet.next()) {
                        var model = new ScoreModel();
                        model.setScore(resultSet.getInt("score"));
                        model.setPlayerName(resultSet.getString("player_name"));
                        model.setStatus(Status.valueOf(resultSet.getString("status")));
                        array.add(model);
                    }
                    result = array;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DBConnector.closeStatement();
                }
                break;
            default:
                break;
        }

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/api/congklak/{key}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<?> post(@PathVariable String key, @RequestBody Object obj){
        Object result = null;

        switch (key){
            case "score":
                break;
            case "setting":
                break;
            default:
                break;
        }

        if (result != null){
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
