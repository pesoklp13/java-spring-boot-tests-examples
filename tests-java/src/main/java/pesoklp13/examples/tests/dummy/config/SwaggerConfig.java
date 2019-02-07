package pesoklp13.examples.tests.dummy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("pesoklp13.examples.tests.dummy.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Dummy API tests",
                "Dummy API tests - to describe TDD",
                "1.0.0",
                null,
                new Contact("Peter Sokolík", null, "pesoklp13@gmail.com"),
                "GNU General Public License v3.0", "https://github.com/pesoklp13/kotlin-spring-boot-tests-examples/blob/master/LICENSE", Collections.emptyList());
    }
}
