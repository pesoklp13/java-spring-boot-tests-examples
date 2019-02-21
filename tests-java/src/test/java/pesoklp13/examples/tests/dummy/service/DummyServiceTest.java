package pesoklp13.examples.tests.dummy.service;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModel;
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModelRepository;
import pesoklp13.examples.tests.dummy.model.Dummy;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;
import pesoklp13.examples.tests.dummy.ws.ExternalServiceApi;
import pesoklp13.examples.tests.dummy.ws.InternalServiceApi;
import pesoklp13.examples.tests.dummy.ws.model.ExternalDummy;
import pesoklp13.examples.tests.dummy.ws.model.InternalDummy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DummyServiceTest {

    private static final String DUMMY_TEST_1 = "dummyTest1";
    private static final String DUMMY_TEST_2 = "dummyTest2";
    private static final String DUMMY_TEST_3 = "dummyTest3";
    private static final String COMMON_EXTERNAL = "COMMON_EXTERNAL";
    private static final String TEST_DATA = "TEST_DATA";
    private static final String COMMON_INTERNAL = "COMMON_INTERNAL";
    private static final String SPECIFIC_INTERNAL = "SPECIFIC_INTERNAL";
    private static final long NUMBER = 13L;

    @MockBean
    private ExternalServiceApi externalServiceApi;

    @MockBean
    private InternalServiceApi internalServiceApi;

    @MockBean
    private DummyModelRepository dummyModelRepository;

    @SpyBean
    private DummyService dummyService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        assertNotNull(dummyService);
        assertNotNull(dummyModelRepository);
        assertNotNull(internalServiceApi);
        assertNotNull(externalServiceApi);
    }

    @Test
    public void testGetAllDummies() {
        when(dummyModelRepository.findAll()).thenReturn(ImmutableList.of(
                createDummyModel(DUMMY_TEST_1, true, 1L),
                createDummyModel(DUMMY_TEST_2, true, 2L),
                createDummyModel(DUMMY_TEST_3, false, 3L)
        ));

        List<Dummy> dummyList = dummyService.getDummies(null);
        assertEquals(3, dummyList.size());
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummyList.get(0));
        assertDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL, 2L, dummyList.get(1));
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummyList.get(2));
    }

    @Test
    public void testGetInternalDummies() {
        when(dummyModelRepository.findByExternal(false)).thenReturn(Collections.singletonList(
                createDummyModel(DUMMY_TEST_3, false, 3L)
        ));

        List<Dummy> dummyList = dummyService.getDummies(DummySourceSystem.INTERNAL);
        assertEquals(1, dummyList.size());
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummyList.get(0));
    }

    @Test
    public void testGetExternalDummies() {
        when(dummyModelRepository.findByExternal(true)).thenReturn(ImmutableList.of(
                createDummyModel(DUMMY_TEST_1, true, 1L),
                createDummyModel(DUMMY_TEST_2, true, 2L)
        ));

        List<Dummy> dummyList = dummyService.getDummies(DummySourceSystem.EXTERNAL);
        assertEquals(2, dummyList.size());
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummyList.get(0));
        assertDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL, 2L, dummyList.get(1));
    }

    @Test
    public void testNotFoundDummies() {
        when(dummyModelRepository.findAll()).thenReturn(Collections.emptyList());

        List<Dummy> dummyList = dummyService.getDummies(null);
        assertEquals(0, dummyList.size());
    }

    @Test
    public void testGetExternalDummyById() {
        when(dummyModelRepository.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_1, true, 1L));
        when(externalServiceApi.getDummy(anyLong())).thenReturn(createExternalDummy());

        Dummy dummy = dummyService.getDummyDetail(1L);
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummy);

        assertEquals(COMMON_EXTERNAL, dummy.getFurtherInformation());
        assertEquals(TEST_DATA + "_" + NUMBER, dummy.getExternalInformation());

        verify(externalServiceApi, times(1)).getDummy(1L);
    }

    @Test
    public void testGetInternalDummyById() {
        when(dummyModelRepository.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_3, false, 3L));
        when(internalServiceApi.getDummyInfo(anyLong())).thenReturn(createInternalDummy());

        Dummy dummy = dummyService.getDummyDetail(3L);
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummy);

        assertEquals(COMMON_INTERNAL, dummy.getFurtherInformation());
        assertEquals(SPECIFIC_INTERNAL, dummy.getInternalInformation());

        verify(internalServiceApi, times(1)).getDummyInfo(3L);
    }

    @Test
    public void testDummyNotFound() {
        when(dummyModelRepository.findById(anyLong())).thenReturn(Optional.empty());

        Dummy dummy = dummyService.getDummyDetail(4L);
        assertNull(dummy);
    }

    @Test
    public void testExternalServiceFailed() {
        expectedException.expect(Exception.class);
        when(dummyModelRepository.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_1, true, 1L));
        when(externalServiceApi.getDummy(anyLong())).thenThrow(new Exception());

        dummyService.getDummyDetail(1L);

        verify(externalServiceApi, times(1)).getDummy(1L);
    }

    private Optional<DummyModel> createOptionalDummyModel(String name, boolean external, Long id) {
        return Optional.of(createDummyModel(name, external, id));
    }

    private DummyModel createDummyModel(String name, boolean external, Long id) {
        DummyModel dummyModel = new DummyModel();
        dummyModel.setId(id);
        dummyModel.setExternal(external);
        dummyModel.setName(name);

        return dummyModel;
    }

    private void assertDummy(String name, DummySourceSystem dummySourceSystem, Long id, Dummy dummy) {
        assertNotNull(dummy);
        assertEquals(id, dummy.getId());
        assertEquals(name, dummy.getName());
        assertEquals(dummySourceSystem, dummy.getSourceSystem());
    }

    private ExternalDummy createExternalDummy() {
        ExternalDummy externalDummy = new ExternalDummy();
        externalDummy.setCommonExternalValue(COMMON_EXTERNAL);
        externalDummy.setNumber(NUMBER);
        externalDummy.setPart(TEST_DATA);
        return externalDummy;
    }

    private InternalDummy createInternalDummy() {
        InternalDummy internalDummy = new InternalDummy();
        internalDummy.setCommonInternalValue(COMMON_INTERNAL);
        internalDummy.setSpecificInternalValue(SPECIFIC_INTERNAL);
        return internalDummy;
    }
}