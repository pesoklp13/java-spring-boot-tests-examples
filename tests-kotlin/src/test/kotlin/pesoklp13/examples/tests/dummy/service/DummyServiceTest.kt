package pesoklp13.examples.tests.dummy.service

import com.google.common.collect.ImmutableList
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.*
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit4.SpringRunner
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModel
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModelRepository
import pesoklp13.examples.tests.dummy.model.Dummy
import pesoklp13.examples.tests.dummy.model.DummySourceSystem
import pesoklp13.examples.tests.dummy.ws.ExternalDummy
import pesoklp13.examples.tests.dummy.ws.ExternalServiceApi
import pesoklp13.examples.tests.dummy.ws.InternalDummy
import pesoklp13.examples.tests.dummy.ws.InternalServiceApi
import java.util.*

@RunWith(SpringRunner::class)
class DummyServiceTest {

    @MockBean
    private val externalServiceApi: ExternalServiceApi? = null

    @MockBean
    private val internalServiceApi: InternalServiceApi? = null

    @MockBean
    private val dummyModelRepository: DummyModelRepository? = null

    @SpyBean
    private val dummyService: DummyService? = null

    @Rule
    @JvmField
    var expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        assertNotNull(dummyService)
        assertNotNull(dummyModelRepository)
        assertNotNull(internalServiceApi)
        assertNotNull(externalServiceApi)
    }

    @Test
    fun testGetAllDummies() {
        `when`(dummyModelRepository!!.findAll()).thenReturn(
            ImmutableList.of(
                createDummyModel(DUMMY_TEST_1, true, 1L),
                createDummyModel(DUMMY_TEST_2, true, 2L),
                createDummyModel(DUMMY_TEST_3, false, 3L)
            )
        )

        val dummyList = dummyService!!.getDummies(null)
        assertEquals(3, dummyList.size.toLong())
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummyList[0])
        assertDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL, 2L, dummyList[1])
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummyList[2])
    }

    @Test
    fun testGetInternalDummies() {
        `when`(dummyModelRepository!!.findByExternal(false)).thenReturn(
            listOf(
                createDummyModel(
                    DUMMY_TEST_3,
                    false,
                    3L
                )
            )
        )

        val dummyList = dummyService!!.getDummies(DummySourceSystem.INTERNAL)
        assertEquals(1, dummyList.size.toLong())
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummyList[0])
    }

    @Test
    fun testGetExternalDummies() {
        `when`(dummyModelRepository!!.findByExternal(true)).thenReturn(
            ImmutableList.of(
                createDummyModel(DUMMY_TEST_1, true, 1L),
                createDummyModel(DUMMY_TEST_2, true, 2L)
            )
        )

        val dummyList = dummyService!!.getDummies(DummySourceSystem.EXTERNAL)
        assertEquals(2, dummyList.size.toLong())
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummyList[0])
        assertDummy(DUMMY_TEST_2, DummySourceSystem.EXTERNAL, 2L, dummyList[1])
    }

    @Test
    fun testNotFoundDummies() {
        `when`(dummyModelRepository!!.findAll()).thenReturn(emptyList())

        val dummyList = dummyService!!.getDummies(null)
        assertEquals(0, dummyList.size.toLong())
    }

    @Test
    fun testGetExternalDummyById() {
        `when`(dummyModelRepository!!.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_1, true, 1L))
        `when`<ExternalDummy>(externalServiceApi!!.getDummy(anyLong())).thenReturn(createExternalDummy())

        val dummy = dummyService!!.getDummyDetail(1L)
        assertDummy(DUMMY_TEST_1, DummySourceSystem.EXTERNAL, 1L, dummy)

        assertEquals(COMMON_EXTERNAL, dummy!!.furtherInformation)
        assertEquals(TEST_DATA + "_" + NUMBER, dummy.externalInformation)

        verify(externalServiceApi, times(1)).getDummy(1L)
    }

    @Test
    fun testGetInternalDummyById() {
        `when`(dummyModelRepository!!.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_3, false, 3L))
        `when`<InternalDummy>(internalServiceApi!!.getDummyInfo(anyLong())).thenReturn(createInternalDummy())

        val dummy = dummyService!!.getDummyDetail(3L)
        assertDummy(DUMMY_TEST_3, DummySourceSystem.INTERNAL, 3L, dummy)

        assertEquals(COMMON_INTERNAL, dummy!!.furtherInformation)
        assertEquals(SPECIFIC_INTERNAL, dummy.internalInformation)

        verify(internalServiceApi, times(1)).getDummyInfo(3L)
    }

    @Test
    fun testDummyNotFound() {
        `when`(dummyModelRepository!!.findById(anyLong())).thenReturn(Optional.empty())

        val dummy = dummyService!!.getDummyDetail(4L)
        assertNull(dummy)
    }

    @Test
    fun testExternalServiceFailed() {
        expectedException.expect(Exception::class.java)
        `when`(dummyModelRepository!!.findById(anyLong())).thenReturn(createOptionalDummyModel(DUMMY_TEST_1, true, 1L))
        `when`<ExternalDummy>(externalServiceApi!!.getDummy(anyLong())).thenThrow(Exception())

        dummyService!!.getDummyDetail(1L)

        verify(externalServiceApi, times(1)).getDummy(1L)
    }

    private fun createOptionalDummyModel(name: String, external: Boolean, id: Long): Optional<DummyModel> {
        return Optional.of(createDummyModel(name, external, id))
    }

    private fun createDummyModel(name: String, external: Boolean, id: Long): DummyModel {
        return DummyModel(
            id = id,
            isExternal = external,
            name = name
        )
    }

    private fun assertDummy(name: String, dummySourceSystem: DummySourceSystem, id: Long?, dummy: Dummy?) {
        assertNotNull(dummy)
        assertEquals(id, dummy!!.id)
        assertEquals(name, dummy.name)
        assertEquals(dummySourceSystem, dummy.sourceSystem)
    }

    private fun createExternalDummy(): ExternalDummy {
        return ExternalDummy(
            id = 1,
            name = DUMMY_TEST_1,
            commonExternalValue = COMMON_EXTERNAL,
            number = NUMBER,
            part = TEST_DATA
        )
    }

    private fun createInternalDummy(): InternalDummy {
        return InternalDummy(
            id = 3,
            name = DUMMY_TEST_3,
            commonInternalValue = COMMON_INTERNAL,
            specificInternalValue = SPECIFIC_INTERNAL
        )
    }

    companion object {

        private const val DUMMY_TEST_1 = "dummyTest1"
        private const val DUMMY_TEST_2 = "dummyTest2"
        private const val DUMMY_TEST_3 = "dummyTest3"
        private const val COMMON_EXTERNAL = "COMMON_EXTERNAL"
        private const val TEST_DATA = "TEST_DATA"
        private const val COMMON_INTERNAL = "COMMON_INTERNAL"
        private const val SPECIFIC_INTERNAL = "SPECIFIC_INTERNAL"
        private const val NUMBER = 13L
    }
}