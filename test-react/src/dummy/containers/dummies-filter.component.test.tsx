import {shallow, ShallowWrapper} from "enzyme";
import * as React from "react";
import {DummiesFilterComponent, mapDispatchToProps} from "./dummies-filter.component";
import {Formik} from "formik";
import {DummySourceSystem} from "../../api/dummy/dummy.model";
import {findDummiesBySourceSystem, searchAllDummies} from "../../store/actions/dummy/dummy-async.creators";

jest.mock("../../store/actions/dummy/dummy-async.creators");

describe("DummiesFilterComponent", () => {

    const filterMock = jest.fn();
    let tree, form: ShallowWrapper;

    beforeEach(() => {
        filterMock.mockReset();
        tree = shallow(<DummiesFilterComponent filterDummies={filterMock}/>);
        form = tree.find("Formik");

        expect(form).not.toBeNull();
    });

    it("should call filterDummies with null", () => {
        form.simulate("submit", {sourceSystem: null});

        expect(filterMock).toBeCalledWith(null);
    });

    it("should call filterDummies with INTERNAL", () => {
        form.simulate("submit", {sourceSystem: DummySourceSystem.INTERNAL});

        expect(filterMock).toBeCalledWith(DummySourceSystem.INTERNAL);
    });

    it("should call filterDummies with EXTERNAL", () => {
        form.simulate("submit", {sourceSystem: DummySourceSystem.EXTERNAL});

        expect(filterMock).toBeCalledWith(DummySourceSystem.EXTERNAL);
    });

    describe("mapDispatchToProps", () => {
        const dispatch = jest.fn(() => Promise.resolve());
        const {filterDummies} = mapDispatchToProps(dispatch);

        const SEARCH_ALL = 0;
        const SEARCH_BY_SOURCE_SYSTEM = 1;

        beforeEach(() => {
            (searchAllDummies as jest.Mock).mockImplementation(() => SEARCH_ALL);
            (findDummiesBySourceSystem as jest.Mock).mockImplementation(() => SEARCH_BY_SOURCE_SYSTEM);
        });

        afterEach(() => {
            jest.clearAllMocks();
        });

        it("should call searchAllDummies when null given", () => {
            filterDummies(null);

            expect(searchAllDummies).toBeCalled();
            expect(findDummiesBySourceSystem).not.toBeCalled();

            expect(dispatch).toBeCalledWith(SEARCH_ALL);
        });

        it("should call findDummiesBySourceSystem", () => {
            filterDummies(DummySourceSystem.EXTERNAL);

            expect(findDummiesBySourceSystem).toBeCalledWith(DummySourceSystem.EXTERNAL);
            expect(dispatch).toBeCalledWith(SEARCH_BY_SOURCE_SYSTEM);

            filterDummies(DummySourceSystem.INTERNAL);

            expect(findDummiesBySourceSystem).toBeCalledWith(DummySourceSystem.INTERNAL);

            expect(searchAllDummies).not.toBeCalled();
            expect(findDummiesBySourceSystem).toBeCalledTimes(2);
            expect(dispatch).toBeCalledWith(SEARCH_BY_SOURCE_SYSTEM);
            expect(dispatch).not.toBeCalledWith(SEARCH_ALL);

        });
    });
});