package dev.migwel.sts.website;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("index");
        registry.addViewController("/sonos").setViewName("index");
        registry.addViewController("/spotify").setViewName("index");
        registry.addViewController("/database").setViewName("index");
    }
}
