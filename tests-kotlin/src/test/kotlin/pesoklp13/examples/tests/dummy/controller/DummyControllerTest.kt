package pesoklp13.examples.tests.dummy.controller

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pesoklp13.examples.tests.dummy.model.Dummy
import pesoklp13.examples.tests.dummy.model.DummySourceSystem
import pesoklp13.examples.tests.dummy.service.DummyService
import java.util.*

@RunWith(SpringRunner::class)
@WebMvcTest(DummyController::class)
class DummyControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    @SpyBean
    private val dummyController: DummyController? = null

    @MockBean
    private val dummyService: DummyService? = null

    @Before
    fun setUp() {
        assertNotNull(mvc)
        assertNotNull(dummyController)
        assertNotNull(dummyService)
    }

    @Test
    @Throws(Exception::class)
    fun testGetAllDummies() {
        `when`<List<Dummy>>(dummyService!!.getDummies(null))
            .thenReturn(
                Arrays.asList(
                    createInternalDummy(DUMMY_TEST_1),
                    createExternalDummy(DUMMY_TEST_2)
                )
            )

        mvc!!.perform(get("/api/dummies"))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Collection<*>>("$", hasSize<Any>(2)))
            .andExpect(jsonPath<String>("$[0].name", `is`<String>(DUMMY_TEST_1)))
            .andExpect(jsonPath<String>("$[0].sourceSystem", `is`<String>(INTERNAL)))
            .andExpect(jsonPath<String>("$[1].name", `is`<String>(DUMMY_TEST_2)))
            .andExpect(jsonPath<String>("$[1].sourceSystem", `is`<String>(EXTERNAL)))
    }

    @Test
    @Throws(Exception::class)
    fun testGetOnlyExternalDummies() {
        `when`<List<Dummy>>(dummyService!!.getDummies(DummySourceSystem.EXTERNAL))
            .thenReturn(
                listOf(createExternalDummy(DUMMY_TEST_2))
            )

        mvc!!.perform(get("/api/dummies/source-system/EXTERNAL"))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Collection<*>>("$", hasSize<Any>(1)))
            .andExpect(jsonPath<String>("$[0].name", `is`<String>(DUMMY_TEST_2)))
            .andExpect(jsonPath<String>("$[0].sourceSystem", `is`<String>(EXTERNAL)))
    }

    @Test
    @Throws(Exception::class)
    fun testGetOnlyInternalDummies() {
        `when`<List<Dummy>>(dummyService!!.getDummies(DummySourceSystem.INTERNAL))
            .thenReturn(
                listOf(createInternalDummy(DUMMY_TEST_1))
            )

        mvc!!.perform(get("/api/dummies/source-system/INTERNAL"))
            .andExpect(status().isOk)
            .andExpect(jsonPath<Collection<*>>("$", hasSize<Any>(1)))
            .andExpect(jsonPath<String>("$[0].name", `is`<String>(DUMMY_TEST_1)))
            .andExpect(jsonPath<String>("$[0].sourceSystem", `is`<String>(INTERNAL)))
    }

    @Test
    @Throws(Exception::class)
    fun testNoDummiesFound() {
        `when`<List<Dummy>>(dummyService!!.getDummies(null))
            .thenReturn(emptyList())

        mvc!!.perform(get("/api/dummies"))
            .andExpect(status().isNotFound)
    }

    @Test
    @Throws(Exception::class)
    fun testNoDummiesFoundByExternal() {
        `when`<List<Dummy>>(dummyService!!.getDummies(DummySourceSystem.EXTERNAL))
            .thenReturn(emptyList())

        mvc!!.perform(get("/api/dummies/source-system/EXTERNAL"))
            .andExpect(status().isNotFound)
    }

    @Test
    @Throws(Exception::class)
    fun testBadRequest() {
        mvc!!.perform(get("/api/dummies/source-system/INCORRECT"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @Throws(Exception::class)
    fun testDummyDetailInternal() {
        `when`<Dummy>(dummyService!!.getDummyDetail(anyLong()))
            .thenReturn(createInternalDummy(DUMMY_TEST_1))

        mvc!!.perform(get("/api/dummies/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath<String>("$.name", `is`<String>(DUMMY_TEST_1)))
            .andExpect(jsonPath<String>("$.sourceSystem", `is`<String>(INTERNAL)))
            .andExpect(jsonPath<String>("$.furtherInformation", `is`<String>(FURTHER_INFO)))
            .andExpect(jsonPath<String>("$.internalInformation", `is`<String>(INTERNAL_INFO)))
            .andExpect(jsonPath("$.externalInformation").doesNotExist())
    }

    @Test
    @Throws(Exception::class)
    fun testDummyDetailExternal() {
        `when`<Dummy>(dummyService!!.getDummyDetail(anyLong()))
            .thenReturn(createExternalDummy(DUMMY_TEST_2))

        mvc!!.perform(get("/api/dummies/2"))
            .andExpect(status().isOk)
            .andExpect(jsonPath<String>("$.name", `is`<String>(DUMMY_TEST_2)))
            .andExpect(jsonPath<String>("$.sourceSystem", `is`<String>(EXTERNAL)))
            .andExpect(jsonPath<String>("$.furtherInformation", `is`<String>(FURTHER_INFO)))
            .andExpect(jsonPath("$.internalInformation").doesNotExist())
            .andExpect(jsonPath<String>("$.externalInformation", `is`<String>(EXTERNAL_INFO)))
    }

    @Test
    @Throws(Exception::class)
    fun testDummyDetailNotFound() {
        `when`<Dummy>(dummyService!!.getDummyDetail(anyLong()))
            .thenReturn(null)

        mvc!!.perform(get("/api/dummies/3"))
            .andExpect(status().isNotFound)
    }

    private fun createInternalDummy(name: String): Dummy {
        return Dummy(
            id = 1,
            name = name,
            sourceSystem = DummySourceSystem.INTERNAL,
            furtherInformation = FURTHER_INFO,
            internalInformation = INTERNAL_INFO
        )
    }

    private fun createExternalDummy(name: String): Dummy {
        return Dummy(
            id = 2,
            name = name,
            sourceSystem = DummySourceSystem.EXTERNAL,
            furtherInformation = FURTHER_INFO,
            externalInformation = EXTERNAL_INFO
        )
    }

    companion object {

        private const val DUMMY_TEST_1 = "dummyTest1"
        private const val DUMMY_TEST_2 = "dummyTest2"
        private const val INTERNAL = "INTERNAL"
        private const val EXTERNAL = "EXTERNAL"
        private const val FURTHER_INFO = "FURTHER_INFO"
        private const val INTERNAL_INFO = "INTERNAL_INFO"
        private const val EXTERNAL_INFO = "EXTERNAL_INFO"
    }
}