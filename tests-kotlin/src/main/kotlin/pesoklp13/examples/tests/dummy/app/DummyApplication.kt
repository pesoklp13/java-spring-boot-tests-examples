package pesoklp13.examples.tests.dummy.app

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DummyApplication {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<DummyApplication>(*args) {
                setBannerMode(Banner.Mode.OFF)
            }
        }
    }

}