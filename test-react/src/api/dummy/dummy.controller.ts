import {AxiosInstance, AxiosPromise, AxiosResponse} from "axios";
import {Dummy, DummySourceSystem} from "./dummy.model";

export class DummyController {

    constructor(private apiClient: AxiosInstance) {}

    public getDummies() : Promise<AxiosResponse<Dummy[]>> {
        return this.apiClient.get(`dummies`);
    }

    public getDummiesBySourceSystem(sourceSystem: DummySourceSystem): Promise<AxiosResponse<Dummy[]>> {
        return this.apiClient.get(`dummies/source-system/${sourceSystem}`);
    }

    public getDummyDetail(id: number): Promise<AxiosResponse<Dummy>> {
        console.log("getDummyDetail");

        //return this.apiClient.get(`dummies/${id}`);
        return Promise.resolve({
            headers: null,
            config: {},
            status: 200,
            statusText: "OK",
            data: {
                id: 1,
                name: "name",
                sourceSystem: DummySourceSystem.EXTERNAL,
                furtherInformation: "information"
            }
        });
    }
}