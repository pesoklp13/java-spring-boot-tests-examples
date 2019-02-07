package pesoklp13.examples.tests.dummy.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;

@ApiModel(
        value = "Dummy",
        description = "Dummy description"
)
@Getter
@Setter
public class Dummy {

    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private DummySourceSystem sourceSystem;

    @ApiModelProperty(required = true)
    private String furtherInformation;

    private String internalInformation;

    private String externalInformation;

}