package cr.ac.ucr.orientaucr.orientaucr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import jakarta.servlet.MultipartConfigElement;

@Configuration
public class WebConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
     
        return factory.createMultipartConfig();
    }
}