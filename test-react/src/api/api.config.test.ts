import {API, apiClient} from "./api.config";
import {DummyController} from "./dummy/dummy.controller";

describe("Axios api configuration", () => {

    it("should have apiClient instance with baseURL `http://localhost:8080/api/`", () => {
        expect(apiClient.defaults.baseURL).toBe(`http://localhost:8080/api/`);
    });

    it("should have API instance with dummy resource", () => {
        const dummyController: DummyController = API.dummy;
        expect(dummyController).not.toBeNull();
    });

});