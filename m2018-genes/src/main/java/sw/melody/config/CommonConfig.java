package sw.melody.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
@Setter
@Configuration
public class CommonConfig {

    @Value("${system.temppath}")
    private String tempPath;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024L * 1024L * 100);
        factory.setLocation(tempPath);
        return factory.createMultipartConfig();
    }
}
