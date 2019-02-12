import {
    DUMMY_DETAIL,
    DUMMY_FIND_BY_SOURCE_SYSTEM,
    DUMMY_SEARCH_ALL,
    FindDummiesBySourceSystem,
    GetDummyDetail,
    SearchAllDummies
} from "./dummy.actions";
import {Dummy} from "../../../api/dummy/dummy.model";

// sync action creators

export const searchAllDummiesSuccess = (dummies: Dummy[]): SearchAllDummies => {
    return {
        type: DUMMY_SEARCH_ALL,
        payload: dummies
    }
};

export const findDummiesBySourceSystemSuccess = (dummies: Dummy[]): FindDummiesBySourceSystem => {
    return {
        type: DUMMY_FIND_BY_SOURCE_SYSTEM,
        payload: dummies
    }
};

export const getDummyDetailSuccess = (dummy: Dummy): GetDummyDetail => {
    console.log("getting data");
    return {
        type: DUMMY_DETAIL,
        payload: dummy
    }
};