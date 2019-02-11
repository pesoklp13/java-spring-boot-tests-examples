import {ActionCreator} from "redux";
import {
    DUMMY_DETAIL,
    DUMMY_FIND_BY_SOURCE_SYSTEM,
    DUMMY_SEARCH_ALL,
    FindDummiesBySourceSystem,
    GetDummyDetail,
    SearchAllDummies
} from "./dummy.actions";
import {DummySourceSystem} from "../../../api/dummy/dummy.model";

export const searchAllDummies: ActionCreator<SearchAllDummies> = () => {
    return {
        type: DUMMY_SEARCH_ALL
    }
};

export const findDummiesBySourceSystem: ActionCreator<FindDummiesBySourceSystem> = (sourceSystem: DummySourceSystem) => {
    return {
        type: DUMMY_FIND_BY_SOURCE_SYSTEM,
        payload: sourceSystem
    }
};

export const getDummyDetail: ActionCreator<GetDummyDetail> = (id: number) => {
    return {
        type: DUMMY_DETAIL,
        payload: id
    }
};