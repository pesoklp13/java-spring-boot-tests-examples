package pesoklp13.examples.tests.dummy.model

import io.swagger.annotations.ApiModel

@ApiModel(
    value = "Dummy",
    description = "Dummy description"
)
data class Dummy(
    val id: Long,
    val name: String,
    val sourceSystem: DummySourceSystem,
    var furtherInformation: String? = null,
    var internalInformation: String? = null,
    var externalInformation: String? = null
)

enum class DummySourceSystem {
    INTERNAL,
    EXTERNAL
}