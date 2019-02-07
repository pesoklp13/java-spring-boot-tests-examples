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
    val furtherInformation: String,
    val internalInformation: String? = null,
    val externalInformation: String? = null
)

enum class DummySourceSystem {
    INTERNAL,
    EXTERNAL
}