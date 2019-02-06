package pesoklp13.examples.tests.dummy

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyApplication {

    companion object {
        @JvmStatic fun main(vararg args: String?) {
            runApplication<DummyApplication> {
                setBannerMode(Banner.Mode.OFF)
            }
        }
    }

}