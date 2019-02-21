package pesoklp13.examples.tests.dummy.app;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "pesoklp13.examples.tests.dummy")
public class DummyApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DummyApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }
}
