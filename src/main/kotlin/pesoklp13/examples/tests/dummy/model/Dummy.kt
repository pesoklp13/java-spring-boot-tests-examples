package pesoklp13.examples.tests.dummy.model

import io.swagger.annotations.ApiModel

@ApiModel(
    value = "Dummy",
    description = "Dummy description"
)
class Dummy(
    private var id: Long,
    private var name: String,
    private var sourceSystem: DummySourceSystem,
    private var furhterInformation: String,
    private var internalInformation: String? = null,
    private var externalInformation: String? = null
)

@ApiModel(
    value = "DummySourceSystem",
    description = "Source System from which dummy were given"
)
enum class DummySourceSystem {
    INTERNAL,
    EXTERNAL
}