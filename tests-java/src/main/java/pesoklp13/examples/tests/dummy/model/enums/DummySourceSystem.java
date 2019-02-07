package pesoklp13.examples.tests.dummy.model.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(
        value = "DummySourceSystem",
        description = "Source System from which dummy were given"
)
public enum DummySourceSystem {
    INTERNAL,
    EXTERNAL
}
