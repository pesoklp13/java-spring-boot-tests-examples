import {DummyReducer} from "./dummy.reducer";
import {DUMMY_DETAIL, DUMMY_FIND_BY_SOURCE_SYSTEM, DUMMY_SEARCH_ALL} from "../../actions/dummy/dummy.actions";
import {Dummy, DummySourceSystem} from "../../../api/dummy/dummy.model";

describe("DummyReducer", () => {

    it("should return merged state with filteredDummies for DUMMY_SEARCH_ALL", () => {
        const state = DummyReducer({}, {type: DUMMY_SEARCH_ALL, payload: []});
        expect(state.selectedDummy).toBeFalsy();
        expect(state.filteredDummies).toStrictEqual([]);
    });

    it("should return merged state with filteredDummies for DUMMY_FIND_BY_SOURCE_SYSTEM", () => {
        const state = DummyReducer({}, {type: DUMMY_FIND_BY_SOURCE_SYSTEM, payload: []});
        expect(state.selectedDummy).toBeFalsy();
        expect(state.filteredDummies).toStrictEqual([]);
    });

    it("should return merged state with selectedDummy for DUMMY_DETAIL", () => {
        const dummy: Dummy = {
            id: 1,
            name: "name",
            sourceSystem: DummySourceSystem.EXTERNAL,
            furtherInformation: "information"
        };

        const state = DummyReducer({}, {type: DUMMY_DETAIL, payload: dummy});
        expect(state.selectedDummy).toStrictEqual(dummy);
        expect(state.filteredDummies).toBeFalsy();
    });
});