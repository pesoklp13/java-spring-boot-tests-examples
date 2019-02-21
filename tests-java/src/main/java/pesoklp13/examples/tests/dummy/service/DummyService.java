package pesoklp13.examples.tests.dummy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModel;
import pesoklp13.examples.tests.dummy.jpa.dummy.DummyModelRepository;
import pesoklp13.examples.tests.dummy.model.Dummy;
import pesoklp13.examples.tests.dummy.model.enums.DummySourceSystem;
import pesoklp13.examples.tests.dummy.ws.ExternalServiceApi;
import pesoklp13.examples.tests.dummy.ws.InternalServiceApi;
import pesoklp13.examples.tests.dummy.ws.model.ExternalDummy;
import pesoklp13.examples.tests.dummy.ws.model.InternalDummy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DummyService {

    private final DummyModelRepository dummyModelRepository;

    private final ExternalServiceApi externalServiceApi;

    private final InternalServiceApi internalServiceApi;

    /**
     * return dummies by dummySourceSystem if null return all
     *
     * @param dummySourceSystem {@link DummySourceSystem}
     * @return List<Dummy>
     */
    public List<Dummy> getDummies(DummySourceSystem dummySourceSystem) {
        List<DummyModel> dummyModels;

        if (dummySourceSystem == null) {
            dummyModels = (List<DummyModel>) dummyModelRepository.findAll();
        } else {
            if (dummySourceSystem == DummySourceSystem.EXTERNAL) {
                dummyModels = dummyModelRepository.findByExternal(true);
            } else {
                dummyModels = dummyModelRepository.findByExternal(false);
            }
        }

        return dummyModels.stream().map(this::convertToDummy).collect(Collectors.toList());
    }

    /**
     * return dummy by id and extend its information from sourceSystem {@link DummySourceSystem}
     *
     * @param id identifier of dummy
     * @return Dummy
     */
    public Dummy getDummyDetail(Long id) {
        Optional<DummyModel> dummyModelOptional = dummyModelRepository.findById(id);
        DummyModel dummyModel;
        if (!dummyModelOptional.isPresent()) {
            return null;
        }

        dummyModel = dummyModelOptional.get();

        if (dummyModel.isExternal()) {
            ExternalDummy externalDummy = externalServiceApi.getDummy(id);
            return convertToDummy(dummyModel, externalDummy);
        } else {
            InternalDummy internalDummy = internalServiceApi.getDummyInfo(id);
            return convertToDummy(dummyModel, internalDummy);
        }
    }

    private Dummy convertToDummy(DummyModel dummyModel, ExternalDummy externalDummy) {
        Dummy dummy = convertToDummy(dummyModel);
        dummy.setExternalInformation(externalDummy.getPart() + "_" + externalDummy.getNumber());
        dummy.setFurtherInformation(externalDummy.getCommonExternalValue());
        return dummy;
    }

    private Dummy convertToDummy(DummyModel dummyModel, InternalDummy internalDummy) {
        Dummy dummy = convertToDummy(dummyModel);
        dummy.setFurtherInformation(internalDummy.getCommonInternalValue());
        dummy.setInternalInformation(internalDummy.getSpecificInternalValue());
        return dummy;
    }

    private Dummy convertToDummy(DummyModel dummyModel) {
        Dummy dummy = new Dummy();
        dummy.setId(dummyModel.getId());
        dummy.setName(dummyModel.getName());

        if (dummyModel.isExternal()) {
            dummy.setSourceSystem(DummySourceSystem.EXTERNAL);
        } else {
            dummy.setSourceSystem(DummySourceSystem.INTERNAL);
        }

        return dummy;
    }
}
