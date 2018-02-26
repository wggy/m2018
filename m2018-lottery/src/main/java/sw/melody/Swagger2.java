package sw.melody;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 * Created by ping on 2018-2-26
 */
@Data
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${sw.melody.project.title}")
    private String title;

    @Value("${sw.melody.project.name}")
    private String name;

    @Value("${sw.melody.project.version}")
    private String version;

    @Value("${sw.melody.project.contact}")
    private String contact;

    @Value("${sw.melody.project.serviceUrl}")
    private String serviceUrl;

    @Value("${sw.melody.project.desc}")
    private String desc;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("sw.melody"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(desc)
                .termsOfServiceUrl(serviceUrl)
                .contact(contact)
                .version(version)
                .build();
    }
}
