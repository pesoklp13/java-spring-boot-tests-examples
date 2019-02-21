package pesoklp13.examples.tests.dummy.service;

import org.springframework.stereotype.Service;
import pesoklp13.examples.tests.dummy.model.Dummy;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;

import java.util.List;

@Service
public class DummyService {

    /**
     *
     * return dummies by dummySourceSystem if null return all
     *
     * @param dummySourceSystem {@link DummySourceSystem}
     * @return List<Dummy>
     */
    public List<Dummy> getDummies(DummySourceSystem dummySourceSystem){
        return null;
    }

    /**
     *
     * return dummy by id and extend its information from sourceSystem {@link DummySourceSystem}
     *
     * @param id identifier of dummy
     * @return Dummy
     */
    public Dummy getDummyDetail(Long id) {
        return null;
    }
}
