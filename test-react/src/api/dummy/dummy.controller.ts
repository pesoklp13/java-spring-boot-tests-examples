import {AxiosInstance, AxiosPromise} from "axios";
import {Dummy, DummySourceSystem} from "./dummy.model";

export class DummyController {

    constructor(private apiClient: AxiosInstance) {}

    public getDummies() : AxiosPromise<Dummy[]> {
        return this.apiClient.get(`dummies`);
    }

    public getDummiesBySourceSystem(sourceSystem: DummySourceSystem): AxiosPromise<Dummy[]> {
        return this.apiClient.get(`dummies/source-system/${sourceSystem}`);
    }

    public getDummyDetail(id: number): AxiosPromise<Dummy> {
        return this.apiClient.get(`dummies/${id}`);
    }
}