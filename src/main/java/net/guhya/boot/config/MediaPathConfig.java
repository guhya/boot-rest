package net.guhya.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource({ 
	  "classpath:/application${env:}.properties"
})
public class MediaPathConfig {
	
    private String mediaPath = "C:/localhost/rest/"; 
    private String pathPatterns = "/public/**";

    @Bean
    public WebMvcConfigurer webMvcConfigurerAdapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                if (!registry.hasMappingForPattern(pathPatterns)) {
                    registry.addResourceHandler(pathPatterns)
                            .addResourceLocations("file:" + mediaPath);
                }
            }
        };
    }
}