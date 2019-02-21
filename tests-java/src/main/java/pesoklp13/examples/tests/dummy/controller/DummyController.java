package pesoklp13.examples.tests.dummy.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pesoklp13.examples.tests.dummy.model.Dummy;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;
import pesoklp13.examples.tests.dummy.service.DummyService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(
        tags = "Dummy Controller"
)
@RequestMapping(
        value = "/api/dummies",
        produces = { "application/json" }
)
@RestController
@RequiredArgsConstructor
public class DummyController {

    private final DummyService dummyService;

    @ApiOperation(
            value = "get list of dummies",
            response = Dummy.class,
            responseContainer =  "List",
            tags = "dummy",
            nickname = "getDummies"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful", response = Dummy.class),
                    @ApiResponse(code = 404, message = "Dummies not found")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<Dummy> getDummies(HttpServletResponse response){
        List<Dummy> dummies = dummyService.getDummies(null);
        if(dummies == null || dummies.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return dummies;
    }

    @ApiOperation(
            value = "get list of dummies filtered by source system",
            response = Dummy.class,
            responseContainer =  "List",
            tags = "dummy",
            nickname = "getDummiesBySourceSystem"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful", response = Dummy.class),
                    @ApiResponse(code = 400, message = "Bad request"),
                    @ApiResponse(code = 404, message = "Dummies not found")
            }
    )
    @RequestMapping(
            path = "/source-system/{sourceSystem}",
            method = RequestMethod.GET
    )
    public List<Dummy> getDummiesBySourceSystem(
            @ApiParam(value = "sourceSystem", required = true, allowableValues = "INTERNAL, EXTERNAL")
            @PathVariable("sourceSystem") DummySourceSystem sourceSystem, HttpServletResponse response){
        List<Dummy> dummies = dummyService.getDummies(sourceSystem);
        if(dummies == null || dummies.size() == 0){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return dummies;
    }

    @ApiOperation(
            value = "get detail of dummy",
            response = Dummy.class,
            tags = "dummy",
            nickname = "getDummyDetail"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful", response = Dummy.class),
                    @ApiResponse(code = 400, message = "Bad request"),
                    @ApiResponse(code = 404, message = "Dummy not found")
            }
    )
    @RequestMapping(
            path = "/{id}",
            method = RequestMethod.GET
    )
    public Dummy getDummyDetail(
            @ApiParam(value = "id", required = true, example = "1")
            @PathVariable("id") Long id, HttpServletResponse response){
        Dummy dummy = dummyService.getDummyDetail(id);
        if (dummy == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        return dummy;
    }
}
