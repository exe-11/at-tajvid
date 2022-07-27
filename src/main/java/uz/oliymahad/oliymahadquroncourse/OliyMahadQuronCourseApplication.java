package uz.oliymahad.oliymahadquroncourse;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.oliymahad.oliymahadquroncourse.config.security.AppProperties;
import uz.oliymahad.oliymahadquroncourse.config.security.OpenApiProperties;


@OpenAPIDefinition
@SpringBootApplication
@EnableConfigurationProperties({
        AppProperties.class,
        OpenApiProperties.class
})
public class OliyMahadQuronCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliyMahadQuronCourseApplication.class, args);
    }

}
