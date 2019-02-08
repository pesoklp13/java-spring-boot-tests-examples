import {AxiosInstance} from "axios";
import {DummyController} from "./dummy.controller";
import {DummySourceSystem} from "./dummy.model";

describe("DummyController", () => {

    let apiClientMock: AxiosInstance = jest.genMockFromModule<AxiosInstance>("axios");
    let dummyController: DummyController = new DummyController(apiClientMock);

    beforeEach(() => {
        jest.clearAllMocks();
    });

    it("should called HTTP GET `dummies` via axios", () => {
        dummyController.getDummies();
        expect(apiClientMock.get).toBeCalled();
    });

    it("should called HTTP GET `dummies/source-system/INTERNAL` via axios", () => {
       dummyController.getDummiesBySourceSystem(DummySourceSystem.INTERNAL);
       expect(apiClientMock.get).toBeCalledWith(`dummies/source-system/INTERNAL`);
    });

    it("should be called HTTP GET `dummies/1` via axios", () => {
       dummyController.getDummyDetail(1);
       expect(apiClientMock.get).toBeCalledWith(`dummies/1`);
    });

});