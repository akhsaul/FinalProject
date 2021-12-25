package org.kelompok3.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.kelompok3.core.*;
import org.kelompok3.database.DBConnector;
import org.kelompok3.model.NodesModel;
import org.kelompok3.model.ScoreModel;
import org.kelompok3.model.SettingModel;
import org.kelompok3.ui.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SpringController {
    private final Logger LOGGER = LoggerFactory.getLogger(SpringController.class);

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

    @GetMapping("/congklak/papan")
    ModelAndView boardGUI() {
        return new ModelAndView("/board/");
    }

    @GetMapping(value = "/api/congklak/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> get(@PathVariable String key, HttpServletRequest request) {
        LOGGER.info(request.getHeader("Host") + request.getRequestURI());

        Object result = null;
        switch (key) {
            case "playerName" -> result = Map.of("playerName", State.getPlayerName());
            case "bgmEnabled" -> result = Map.of("bgmEnabled", State.isEnableBgm());
            case "score" -> {
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
                    LOGGER.error("Get Request. ", e);
                } finally {
                    DBConnector.closeStatement();
                }
            }
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
    ResponseEntity<?> post(@PathVariable String key, @RequestBody String data, HttpServletRequest request) {
        LOGGER.info(request.getHeader("Host") + request.getRequestURI());
        ObjectMapper mapper = new ObjectMapper();

        Object result = null;

        try {
            switch (key) {
                case "combination" -> {
                    var nodes = prepareNodes(mapper.readValue(data, NodesModel.class));

                    var bot = new Bot();
                    bot.backtracking(nodes);
                    var solution = bot.solution;
                    if (solution.hasSolution()) {
                        result = "{\"hasSolution\": true, \"result\": " +
                                mapper.writeValueAsString(nodes.get(solution.getIndexHole())) + "}";
                    } else {
                        result = "{\"hasSolution\": false}";
                    }
                }
                case "score" -> {
                    ScoreModel scoreModel = mapper.readValue(data, ScoreModel.class);
                    if (State.getPlayerName().equals(scoreModel.getPlayerName())) {
                        DBConnector.saveScore(State.getPlayerID(), scoreModel.getScore(), scoreModel.getStatus());
                        result = "{\"success\": true}";
                    }
                }
                case "setting" -> {
                    SettingModel settingModel = mapper.readValue(data, SettingModel.class);
                    DBConnector.saveSetting(settingModel.getPlayerName(), settingModel.getBgmEnabled());
                    var resultModel = new SettingModel();
                    resultModel.setPlayerName(State.getPlayerName());
                    resultModel.setBgmEnabled(State.isEnableBgm());
                    result = resultModel;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Post Request. ", e);
        }

        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private @NotNull List<Hole> prepareNodes(@NotNull NodesModel dataModel) {
        List<Hole> nodes = new ArrayList<>();
        dataModel.getComputer().getLittleHole().forEach((e) -> nodes.add(
                new LittleHole(e.getId(), e.getSeed())
        ));

        var bigHole = dataModel.getComputer().getBigHole();
        nodes.add(new BigHole(bigHole.getId(), bigHole.getSeed()));

        dataModel.getHuman().getLittleHole().forEach((e) -> nodes.add(
                new LittleHole(e.getId(), e.getSeed())
        ));

        return nodes;
    }
}
