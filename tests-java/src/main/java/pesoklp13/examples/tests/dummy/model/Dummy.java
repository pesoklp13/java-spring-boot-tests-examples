package pesoklp13.examples.tests.dummy.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@ApiModel(
        value = "Dummy",
        description = "Dummy description"
)
@Getter
@Setter
public class Dummy {

    private Long id;

    private String name;

    private DummySourceSystem sourceSystem;

    private String furtherInformation;

    private String internalInformation;

    private String externalInformation;

}

@ApiModel(
        value = "DummySourceSystem",
        description = "Source System from which dummy were given"
)
enum DummySourceSystem {
    INTERNAL,
    EXTERNAL
}