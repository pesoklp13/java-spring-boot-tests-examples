package pesoklp13.examples.tests.dummy.service

import org.springframework.stereotype.Service
import pesoklp13.examples.tests.dummy.model.Dummy
import pesoklp13.examples.tests.dummy.model.DummySourceSystem

@Service
class DummyService {

    /**
     *
     * return dummies by dummySourceSystem if null return all
     *
     * @param dummySourceSystem [DummySourceSystem]
     * @return [List] of [Dummy]
     */
    fun getDummies(dummySourceSystem: DummySourceSystem?): List<Dummy>? {
        return null
    }

    /**
     *
     * return dummy by id and extend its information from sourceSystem [DummySourceSystem]
     *
     * @param id identifier of dummy
     * @return [Dummy]
     */
    fun getDummyDetail(id: Long?): Dummy? {
        return null
    }
}