import {DummiesListComponent, mapDispatchToProps, mapStateToProps} from "./dummies-list.component";
import {Dummy, DummySourceSystem} from "../../api/dummy/dummy.model";
import {getDummyDetail} from "../../store/actions/dummy/dummy-async.creators";
import {shallow} from "enzyme";
import * as React from "react";

jest.mock("../../store/actions/dummy/dummy-async.creators");

class MockedDummiesListComponent extends DummiesListComponent {

}

const renderRowMock = jest.fn();

MockedDummiesListComponent.prototype.renderRow = renderRowMock;

describe("DummiesListComponent", () => {

    const stubbedDummy: Dummy = {
        id: 1,
        name: "name",
        sourceSystem: DummySourceSystem.EXTERNAL,
        furtherInformation: "information"
    };

    const getDetail = jest.fn();

    it("should display no dummies selected when no dummies filtered", () => {
        const tree = shallow(<DummiesListComponent getDetail={getDetail}/>);
        const div = tree.find("div");
        expect(div).not.toBeNull();
        expect(div.text()).toStrictEqual("No dummies selected");
    });

    it("should display list of dummies when filtered", () => {
        const secondDummy = {...stubbedDummy, ...{id: 2}};

        const dummies = [stubbedDummy, secondDummy];

        shallow(<MockedDummiesListComponent getDetail={getDetail} dummies={dummies}/>);

        expect(renderRowMock).toBeCalledTimes(2);
        expect(renderRowMock).toBeCalledWith(stubbedDummy);
        expect(renderRowMock).toBeCalledWith(secondDummy);
    });

    it("should call getDetail when clicked on button", () => {

    });

    describe("mapStateToProps", () => {

        it("should set dummies when store was changed", () => {
            const {dummies} = mapStateToProps({
                dummies: {
                    filteredDummies: []
                }
            });

            expect(dummies).toStrictEqual([]);
        });

        it("should set dummy when store was changed", () => {
            const {dummy} = mapStateToProps({
                dummies: {
                    selectedDummy: stubbedDummy
                }
            });

            expect(dummy).toStrictEqual(stubbedDummy);
        });

    });

    describe("mapDispatchToProps", () => {

        const dispatch = jest.fn(() => Promise.resolve());

        it("should call getDummyDetail with id", () => {
            (getDummyDetail as jest.Mock).mockImplementation(() => 0);

            const {getDetail} = mapDispatchToProps(dispatch);

            getDetail(1);
            expect(dispatch).toBeCalledWith(0);
            expect(getDummyDetail).toBeCalledWith(1);
        });
    });
});