package pesoklp13.examples.tests.dummy.ws

import org.springframework.stereotype.Service

@Service
class ExternalServiceApi {

    fun getDummy(id: Long?): ExternalDummy? {
        return null
    }

}

data class ExternalDummy(
    val id: Long,
    val name: String,
    val commonExternalValue: String,
    val number: Long,
    val part: String
)