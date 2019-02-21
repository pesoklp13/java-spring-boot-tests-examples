package pesoklp13.examples.tests.dummy.jpa.dummy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DummyModelRepositoryTest {

    private static final String DUMMY_TEST_1 = "dummyTest1";
    private static final String DUMMY_TEST_2 = "dummyTest2";
    private static final String DUMMY_TEST_3 = "dummyTest3";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DummyModelRepository dummyModelRepository;

    @Before
    public void setUp(){
        assertNotNull(entityManager);
        assertNotNull(dummyModelRepository);

        persistDummy(DUMMY_TEST_1, true);
        persistDummy(DUMMY_TEST_2, true);
        persistDummy(DUMMY_TEST_3, false);
    }

    @Test
    public void findByExternal() {
        List<DummyModel> externals = dummyModelRepository.findByExternal(true);
        assertEquals(2, externals.size());

        List<DummyModel> internals = dummyModelRepository.findByExternal(false);
        assertEquals(1, internals.size());
    }

    private void persistDummy(String name, boolean external){
        DummyModel dummyModel = new DummyModel();
        dummyModel.setExternal(external);
        dummyModel.setName(name);

        entityManager.persist(dummyModel);
    }
}
