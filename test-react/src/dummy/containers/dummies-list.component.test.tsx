import {DummiesListComponent, mapDispatchToProps, mapStateToProps} from "./dummies-list.component";
import {Dummy, DummySourceSystem} from "../../api/dummy/dummy.model";
import {getDummyDetail} from "../../store/actions/dummy/dummy-async.creators";
import {shallow} from "enzyme";
import * as React from "react";
import {closeDummyDetail} from "../../store/actions/dummy/dummy.creators";

jest.mock("../../store/actions/dummy/dummy-async.creators");
jest.mock("../../store/actions/dummy/dummy.creators");

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

    it("should display list of dummies when filtered (just verify called method)", () => {
        const secondDummy = {...stubbedDummy, ...{id: 2}};

        const dummies = [stubbedDummy, secondDummy];

        shallow(<MockedDummiesListComponent getDetail={getDetail} dummies={dummies}/>);

        expect(renderRowMock).toBeCalledTimes(2);
        expect(renderRowMock).toBeCalledWith(stubbedDummy);
        expect(renderRowMock).toBeCalledWith(secondDummy);
    });

    it("should display list of dummies when filtered", () => {
        const secondDummy = {...stubbedDummy, ...{id: 2, name: "testName"}};

        const dummies = [stubbedDummy, secondDummy];

        const tree = shallow(<DummiesListComponent getDetail={getDetail} dummies={dummies}/>);
        const divs = tree.find(".row");
        expect(divs.length).toEqual(2);

        // using first or last will return node
        const row1 = divs.first();
        // also using find(...).at(...) will return node
        const row2 = divs.at(1);

        // check if rendered properly
        let button;

        expect(row1.find(".name").text()).toStrictEqual(stubbedDummy.name);
        button = row1.find("button");
        expect(button.text()).toStrictEqual("Show detail");

        expect(row2.find(".name").text()).toStrictEqual(secondDummy.name);
        button = row2.find("button");
        expect(button.text()).toStrictEqual("Show detail");
    });

    it("should call getDetail when clicked on button", () => {
        const tree = shallow(<DummiesListComponent getDetail={getDetail} dummies={[stubbedDummy]}/>);
        const button = tree.find(".row button");

        button.simulate('click');

        expect(getDetail).toBeCalledTimes(1);
    });

    it("should display modal when getDetail when modal closed", () => {
        const tree = shallow(<DummiesListComponent getDetail={getDetail} dummy={stubbedDummy}/>);

        // no need to check if list of dummies is rendered (there is no logic to avoid it)

        const modal = tree.find("Modal");
        expect(modal).not.toBeNull();

        // check if we called detail component with stubbedDummy
        const innerComponent = modal.find("DummyDetailComponent");
        expect((innerComponent.props() as any).dummy).toStrictEqual(stubbedDummy);

        expect((modal.props() as any).isOpen).toBeTruthy(); // should use some interface containing isOpen as it's props

        // no need to simulate close of modal window (we are assuming it's working properly)
        // just simulate change of state
        tree.setProps({dummy: undefined});

        expect(tree.find("Modal").length).toEqual(0);
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

        it("should call closeDummyDetail", () => {
            (closeDummyDetail as jest.Mock).mockImplementation(() => 0);

            const {closeDetail} = mapDispatchToProps(dispatch);

            closeDetail();
            expect(dispatch).toBeCalledWith(0);
            expect(closeDummyDetail).toBeCalledTimes(1);
        });
    });
});