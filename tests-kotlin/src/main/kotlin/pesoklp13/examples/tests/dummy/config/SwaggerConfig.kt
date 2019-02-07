package pesoklp13.examples.tests.dummy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("pesoklp13.examples.tests.dummy.controller"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "Dummy API tests",
            "Dummy API tests - to describe TDD",
            "1.0.0",
            null,
            Contact("Peter Sokolík", null, "pesoklp13@gmail.com"),
            "GNU General Public License v3.0",
            "https://github.com/pesoklp13/kotlin-spring-boot-tests-examples/blob/master/LICENSE",
            emptyList()
        )
    }
}