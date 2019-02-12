import {ThunkDispatch} from "redux-thunk";
import {DummyState} from "../../reducers/dummy/dummy.reducer";
import {DummyActions} from "./dummy.actions";
import {API} from "../../../api/api.config";
import {AxiosResponse} from "axios";
import {Dummy, DummySourceSystem} from "../../../api/dummy/dummy.model";
import {findDummiesBySourceSystemSuccess, getDummyDetailSuccess, searchAllDummiesSuccess} from "./dummy.creators";

export const searchAllDummies = () => {
    return (dispatch: ThunkDispatch<DummyState, any, DummyActions>) => {
        return API.dummy.getDummies().then(
            (response: AxiosResponse<Dummy[]>) => {
                dispatch(searchAllDummiesSuccess(response.data));
            }
        );
    };
};

export const findDummiesBySourceSystem = (sourceSystem: DummySourceSystem) => {
    return (dispatch: ThunkDispatch<DummyState, any, DummyActions>) => {
        return API.dummy.getDummiesBySourceSystem(sourceSystem).then(
            (response: AxiosResponse<Dummy[]>) => {
                dispatch(findDummiesBySourceSystemSuccess(response.data));
            }
        )
    };
};

export const getDummyDetail = (id: number) => {
    return (dispatch: ThunkDispatch<DummyState, any, DummyActions>) => {
        return API.dummy.getDummyDetail(id).then(
            (response: AxiosResponse<Dummy>) => {
                console.log("trying it");
                dispatch(getDummyDetailSuccess(response.data));
            }
        )
    };
};