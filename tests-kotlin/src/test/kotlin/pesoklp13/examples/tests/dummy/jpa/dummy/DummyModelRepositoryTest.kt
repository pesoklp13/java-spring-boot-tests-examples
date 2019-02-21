package pesoklp13.examples.tests.dummy.jpa.dummy

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
class DummyModelRepositoryTest {

    @Autowired
    private val entityManager: TestEntityManager? = null

    @Autowired
    private val dummyModelRepository: DummyModelRepository? = null

    @Before
    fun setUp() {
        assertNotNull(entityManager)
        assertNotNull(dummyModelRepository)

        persistDummy(DUMMY_TEST_1, true)
        persistDummy(DUMMY_TEST_2, true)
        persistDummy(DUMMY_TEST_3, false)
    }

    @Test
    fun findByExternal() {
        val externals = dummyModelRepository!!.findByExternal(true)
        assertEquals(2, externals.size.toLong())

        val internals = dummyModelRepository.findByExternal(false)
        assertEquals(1, internals.size.toLong())
    }

    private fun persistDummy(name: String, external: Boolean) {
        val dummyModel = DummyModel(
            external = external,
            name = name
        )

        entityManager!!.persist(dummyModel)
    }

    companion object {

        private const val DUMMY_TEST_1 = "dummyTest1"
        private const val DUMMY_TEST_2 = "dummyTest2"
        private const val DUMMY_TEST_3 = "dummyTest3"
    }
}