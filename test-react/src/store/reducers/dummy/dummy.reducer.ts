import {Dummy} from "../../../api/dummy/dummy.model";
import {Reducer} from "redux";
import {
    DUMMY_DETAIL,
    DUMMY_FIND_BY_SOURCE_SYSTEM,
    DUMMY_SEARCH_ALL,
    DummyActions
} from "../../actions/dummy/dummy.actions";

export interface DummyState {
    filteredDummies?: Dummy[],
    selectedDummy?: Dummy
}

export const DummyReducer: Reducer<DummyState, DummyActions> = (state: DummyState = {}, action: DummyActions) => {
    switch(action.type) {
        case DUMMY_SEARCH_ALL:
        case DUMMY_FIND_BY_SOURCE_SYSTEM:
            return {...state, ...{filteredDummies: action.payload}};
        case DUMMY_DETAIL:
            return {...state, ...{selectedDummy: action.payload}}
    }

    return state;
};