package org.kelompok3.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ServerConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("*");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/setting/").setViewName(
                "forward:/web/setting.html"
        );
        registry.addViewController("/main/").setViewName(
                "forward:/web/index.html"
        );
        registry.addViewController("/suit/").setViewName(
                "forward:/web/suit.html"
        );
        registry.addViewController("/score/").setViewName(
                "forward:/web/score.html"
        );
    }
}