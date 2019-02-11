import {
    DUMMY_DETAIL,
    DUMMY_FIND_BY_SOURCE_SYSTEM,
    DUMMY_SEARCH_ALL,
    FindDummiesBySourceSystem,
    GetDummyDetail,
    SearchAllDummies
} from "./dummy.actions";
import {Dummy} from "../../../api/dummy/dummy.model";

export const searchAllDummiesSuccess = (dummies: Dummy[]): SearchAllDummies => {
    return {
        type: DUMMY_SEARCH_ALL,
        payload: dummies
    }
};

export const findDummiesBySourceSystem = (dummies: Dummy[]): FindDummiesBySourceSystem => {
    return {
        type: DUMMY_FIND_BY_SOURCE_SYSTEM,
        payload: dummies
    }
};

export const getDummyDetail = (dummy: Dummy): GetDummyDetail => {
    return {
        type: DUMMY_DETAIL,
        payload: dummy
    }
};