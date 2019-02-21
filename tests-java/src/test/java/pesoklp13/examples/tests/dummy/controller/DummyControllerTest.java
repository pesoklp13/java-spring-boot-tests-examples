package pesoklp13.examples.tests.dummy.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pesoklp13.examples.tests.dummy.model.Dummy;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;
import pesoklp13.examples.tests.dummy.service.DummyService;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DummyController.class)
public class DummyControllerTest {

    private static final String DUMMY_TEST_1 = "dummyTest1";
    private static final String DUMMY_TEST_2 = "dummyTest2";
    private static final String INTERNAL = "INTERNAL";
    private static final String EXTERNAL = "EXTERNAL";
    private static final String FURTHER_INFO = "FURTHER_INFO";
    private static final String INTERNAL_INFO = "INTERNAL_INFO";
    private static final String EXTERNAL_INFO = "EXTERNAL_INFO";

    @Autowired
    private MockMvc mvc;

    @SpyBean
    private DummyController dummyController;

    @MockBean
    private DummyService dummyService;

    @Before
    public void setUp(){
        assertNotNull(mvc);
        assertNotNull(dummyController);
        assertNotNull(dummyService);
    }

    @Test
    public void testGetAllDummies() throws Exception {
        when(dummyService.getDummies(null))
                .thenReturn(
                        Arrays.asList(
                                createDummy(DUMMY_TEST_1, DummySourceSystem.INTERNAL),
                                createDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL)
                        )
                );

        mvc.perform(get("/api/dummies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(DUMMY_TEST_1)))
                .andExpect(jsonPath("$[0].sourceSystem", is(INTERNAL)))
                .andExpect(jsonPath("$[1].name", is(DUMMY_TEST_2)))
                .andExpect(jsonPath("$[1].sourceSystem", is(EXTERNAL)));
    }

    @Test
    public void testGetOnlyExternalDummies() throws Exception {
        when(dummyService.getDummies(DummySourceSystem.EXTERNAL))
                .thenReturn(
                        Collections.singletonList(
                                createDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL)
                        )
                );

        mvc.perform(get("/api/dummies/source-system/EXTERNAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(DUMMY_TEST_2)))
                .andExpect(jsonPath("$[0].sourceSystem", is(EXTERNAL)));
    }

    @Test
    public void testGetOnlyInternalDummies() throws Exception {
        when(dummyService.getDummies(DummySourceSystem.INTERNAL))
                .thenReturn(
                        Collections.singletonList(
                                createDummy(DUMMY_TEST_1, DummySourceSystem.INTERNAL)
                        )
                );

        mvc.perform(get("/api/dummies/source-system/INTERNAL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(DUMMY_TEST_1)))
                .andExpect(jsonPath("$[0].sourceSystem", is(INTERNAL)));
    }

    @Test
    public void testNoDummiesFound() throws Exception {
        when(dummyService.getDummies(null))
                .thenReturn(Collections.emptyList());

        mvc.perform(get("/api/dummies"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testNoDummiesFoundByExternal() throws Exception {
        when(dummyService.getDummies(DummySourceSystem.EXTERNAL))
                .thenReturn(Collections.emptyList());

        mvc.perform(get("/api/dummies/source-system/EXTERNAL"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testBadRequest() throws Exception {
        mvc.perform(get("/api/dummies/source-system/INCORRECT"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDummyDetailInternal() throws Exception {
        when(dummyService.getDummyDetail(anyLong()))
                .thenReturn(createInternalDummy(DUMMY_TEST_1));

        mvc.perform((get("/api/dummies/1")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DUMMY_TEST_1)))
                .andExpect(jsonPath("$.sourceSystem", is(INTERNAL)))
                .andExpect(jsonPath("$.furtherInformation", is(FURTHER_INFO)))
                .andExpect(jsonPath("$.internalInformation", is(INTERNAL_INFO)))
                .andExpect(jsonPath("$.externalInformation").doesNotExist());
    }

    @Test
    public void testDummyDetailExternal() throws Exception {
        when(dummyService.getDummyDetail(anyLong()))
                .thenReturn(createExternalDummy(DUMMY_TEST_2));

        mvc.perform((get("/api/dummies/2")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DUMMY_TEST_2)))
                .andExpect(jsonPath("$.sourceSystem", is(EXTERNAL)))
                .andExpect(jsonPath("$.furtherInformation", is(FURTHER_INFO)))
                .andExpect(jsonPath("$.internalInformation").doesNotExist())
                .andExpect(jsonPath("$.externalInformation", is(EXTERNAL_INFO)));
    }

    @Test
    public void testDummyDetailNotFound() throws Exception {
        when(dummyService.getDummyDetail(anyLong()))
                .thenReturn(null);

        mvc.perform(get("/api/dummies/3"))
                .andExpect(status().isNotFound());
    }

    private Dummy createDummy(String name, DummySourceSystem sourceSystem){
        Dummy dummy = new Dummy();
        dummy.setName(name);
        dummy.setSourceSystem(sourceSystem);
        return dummy;
    }

    private Dummy createInternalDummy(String name) {
        Dummy dummy = createDummy(name, DummySourceSystem.INTERNAL);
        dummy.setFurtherInformation(FURTHER_INFO);
        dummy.setInternalInformation(INTERNAL_INFO);
        return dummy;
    }

    private Dummy createExternalDummy(String name) {
        Dummy dummy = createDummy(name, DummySourceSystem.EXTERNAL);
        dummy.setFurtherInformation(FURTHER_INFO);
        dummy.setExternalInformation(EXTERNAL_INFO);
        return dummy;
    }
}