import {findDummiesBySourceSystemSuccess, getDummyDetailSuccess, searchAllDummiesSuccess} from "./dummy.creators";
import {DUMMY_DETAIL, DUMMY_FIND_BY_SOURCE_SYSTEM, DUMMY_SEARCH_ALL, DummyActions} from "./dummy.actions";
import {Dummy, DummySourceSystem} from "../../../api/dummy/dummy.model";

describe("Dummy creators tests", () => {

    it("searchAllDummiesSuccess should return SearchAllDummies instance", () => {
        const action: DummyActions = searchAllDummiesSuccess([]);
        expect(action.type).toBe(DUMMY_SEARCH_ALL);
        expect(action.payload).toEqual([]);
    });

    it("findDummiesBySourceSystemSuccess should return FindDummiesBySourceSystem instance", () => {
        const action: DummyActions = findDummiesBySourceSystemSuccess([]);
        expect(action.type).toBe(DUMMY_FIND_BY_SOURCE_SYSTEM);
        expect(action.payload).toEqual([]);
    });

    it("getDummyDetailSuccess should return GetDummyDetail instance", () => {
        const dummy: Dummy = {
            id: 1,
            name: "name",
            sourceSystem: DummySourceSystem.EXTERNAL,
            furtherInformation: "information"
        };
        const action: DummyActions = getDummyDetailSuccess(dummy);
        expect(action.type).toBe(DUMMY_DETAIL);
        expect(action.payload).toBe(dummy);
    });

});