import {findDummiesBySourceSystem, getDummyDetail, searchAllDummies} from "./dummy.creators";
import {DUMMY_DETAIL, DUMMY_FIND_BY_SOURCE_SYSTEM, DUMMY_SEARCH_ALL, DummyActions} from "./dummy.actions";
import {DummySourceSystem} from "../../../api/dummy/dummy.model";

describe("Dummy creators tests", () => {

    it("searchAllDummies should return SearchAllDummies instance", () => {
        const action: DummyActions = searchAllDummies();
        expect(action.type).toBe(DUMMY_SEARCH_ALL);
    });

    it("findDummiesBySourceSystem should return FindDummiesBySourceSystem instance", () => {
        const action: DummyActions = findDummiesBySourceSystem(DummySourceSystem.EXTERNAL);
        expect(action.type).toBe(DUMMY_FIND_BY_SOURCE_SYSTEM);
        expect(action.payload).toBe(DummySourceSystem.EXTERNAL);
    });

    it("getDummyDetail should return GetDummyDetail instance", () => {
        const action: DummyActions = getDummyDetail(1);
        expect(action.type).toBe(DUMMY_DETAIL);
        expect(action.payload).toBe(1);
    });

});