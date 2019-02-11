import {API} from "../../../api/api.config";
import {findDummiesBySourceSystemSuccess, getDummyDetailSuccess, searchAllDummiesSuccess} from "./dummy.creators";
import {findDummiesBySourceSystem, getDummyDetail, searchAllDummies} from "./dummy-async.creators";
import {Dummy, DummySourceSystem} from "../../../api/dummy/dummy.model";

jest.mock("./dummy.creators");

describe("Dummy async creators", () => {

    const dispatch = () => {
    };

    const dummy: Dummy = {
        id: 1,
        name: "name",
        sourceSystem: DummySourceSystem.EXTERNAL,
        furtherInformation: "information"
    };

    const response = (data: any) => {
        return {
            headers: null,
            config: {},
            status: 200,
            statusText: "OK",
            data: data
        }
    };

    it("searchAllDummies should return function dispatching searchAllDummiesSuccess", () => {
        const dummies = jest.spyOn(API.dummy, "getDummies");
        dummies.mockImplementationOnce(() =>
            Promise.resolve(response([dummy]))
        );

        searchAllDummies()(dispatch).then(() => {
            expect(API.dummy.getDummies).toBeCalled();
            expect(searchAllDummiesSuccess).toBeCalledWith([dummy]);
        });
    });

    it("findDummiesBySourceSystem should return function dispatching findDummiesBySourceSystemSuccess", () => {
        const dummies = jest.spyOn(API.dummy, "getDummiesBySourceSystem");
        dummies.mockImplementationOnce(() =>
            Promise.resolve(response([dummy]))
        );

        findDummiesBySourceSystem(DummySourceSystem.EXTERNAL)(dispatch).then(() => {
            expect(API.dummy.getDummiesBySourceSystem).toBeCalledWith(DummySourceSystem.EXTERNAL);
            expect(findDummiesBySourceSystemSuccess).toBeCalledWith([dummy]);
        });
    });

    it("getDummyDetail should return function dispatching getDummyDetailSuccess", () => {
        const dummies = jest.spyOn(API.dummy, "getDummyDetail");
        dummies.mockImplementationOnce(() =>
            Promise.resolve(response(dummy))
        );

        getDummyDetail(1)(dispatch).then(() => {
            expect(API.dummy.getDummyDetail).toBeCalledWith(1);
            expect(getDummyDetailSuccess).toBeCalledWith(dummy);
        });
    });

});