package pesoklp13.examples.tests.dummy.ws

import org.springframework.stereotype.Service

@Service
class InternalServiceApi {

    fun getDummyInfo(id: Long?): InternalDummy? {
        return null
    }

}

data class InternalDummy(
    val id: Long,
    val name: String,
    val commonInternalValue: String,
    val specificInternalValue: String
)