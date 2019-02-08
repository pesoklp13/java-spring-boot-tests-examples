import Axios, {AxiosInstance} from "axios";
import {DummyController} from "./dummy/dummy.controller";

export const apiClient: AxiosInstance = Axios.create({
    baseURL: `http://localhost:8080/api/`
});

export const API = {
    dummy: new DummyController(apiClient)
};