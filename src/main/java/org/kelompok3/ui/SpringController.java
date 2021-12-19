package org.kelompok3.ui;

import org.kelompok3.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringController {
    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    @GetMapping("/congklak")
    public String main() {
        return Utils.readFile("static/web/index.html");
    }
}
