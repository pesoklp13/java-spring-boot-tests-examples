package pesoklp13.examples.tests.dummy.service

import org.springframework.stereotype.Service
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModel
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModelRepository
import pesoklp13.examples.tests.dummy.model.Dummy
import pesoklp13.examples.tests.dummy.model.DummySourceSystem
import pesoklp13.examples.tests.dummy.ws.ExternalDummy
import pesoklp13.examples.tests.dummy.ws.ExternalServiceApi
import pesoklp13.examples.tests.dummy.ws.InternalDummy
import pesoklp13.examples.tests.dummy.ws.InternalServiceApi

@Service
class DummyService(
    private val dummyModelRepository: DummyModelRepository,
    private val externalServiceApi: ExternalServiceApi,
    private val internalServiceApi: InternalServiceApi
) {

    /**
     * return dummies by dummySourceSystem if null return all
     *
     * @param dummySourceSystem [DummySourceSystem]
     * @return [List] of [Dummy]
     */
    fun getDummies(dummySourceSystem: DummySourceSystem?): List<Dummy> {
        val dummyModels: Iterable<DummyModel>

        if (dummySourceSystem == null) {
            dummyModels = dummyModelRepository.findAll()
        } else {
            dummyModels = if (dummySourceSystem == DummySourceSystem.EXTERNAL) {
                dummyModelRepository.findByExternal(true)
            } else {
                dummyModelRepository.findByExternal(false)
            }
        }

        return dummyModels.map {
            this.convertToDummy(it)
        }
    }

    /**
     * return dummy by id and extend its information from sourceSystem [DummySourceSystem]
     *
     * @param id identifier of dummy
     * @return Dummy
     */
    fun getDummyDetail(id: Long): Dummy? {
        val dummyModelOptional = dummyModelRepository.findById(id)
        val dummyModel: DummyModel
        if (!dummyModelOptional.isPresent) {
            return null
        }

        dummyModel = dummyModelOptional.get()

        return if (dummyModel.external) {
            val externalDummy = externalServiceApi.getDummy(id)
            convertToDummy(dummyModel, externalDummy!!)
        } else {
            val internalDummy = internalServiceApi.getDummyInfo(id)
            convertToDummy(dummyModel, internalDummy!!)
        }
    }

    private fun convertToDummy(dummyModel: DummyModel, externalDummy: ExternalDummy): Dummy {
        val dummy = convertToDummy(dummyModel)
        dummy.furtherInformation = externalDummy.commonExternalValue
        dummy.externalInformation = "${externalDummy.part}_${externalDummy.number}"
        return dummy
    }

    private fun convertToDummy(dummyModel: DummyModel, internalDummy: InternalDummy): Dummy {
        val dummy = convertToDummy(dummyModel)
        dummy.furtherInformation = internalDummy.commonInternalValue
        dummy.internalInformation = internalDummy.specificInternalValue
        return dummy
    }

    private fun convertToDummy(dummyModel: DummyModel): Dummy {
        val sourceSystem = if(dummyModel.external) {
            DummySourceSystem.EXTERNAL
        } else {
            DummySourceSystem.INTERNAL
        }

        return Dummy(
            id = dummyModel.id!!,
            name = dummyModel.name,
            sourceSystem = sourceSystem)
    }
}