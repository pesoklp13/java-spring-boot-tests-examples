package pesoklp13.examples.tests.dummy;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DummyApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DummyApplication.class).bannerMode(Banner.Mode.OFF).run(args);
    }
}
