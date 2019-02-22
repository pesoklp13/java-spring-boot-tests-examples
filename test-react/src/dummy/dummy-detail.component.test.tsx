import {shallow} from "enzyme";
import {DummyDetailComponent} from "./dummy-detail.component";
import {Dummy, DummySourceSystem} from "../api/dummy/dummy.model";
import * as React from "react";

describe("DummyDetailComponent", () => {

    const stubbedDummy: Dummy = {
        id: 1,
        name: "name",
        sourceSystem: DummySourceSystem.EXTERNAL,
        furtherInformation: "information"
    };

    it("should render detail", () => {
       const tree = shallow(<DummyDetailComponent dummy={stubbedDummy}/>);

       expect(tree.find(".name").text()).toStrictEqual("Name: name");
       expect(tree.find(".system").text()).toStrictEqual(`System: ${DummySourceSystem.EXTERNAL}`);
       expect(tree.find(".common-info").text()).toStrictEqual("Common info: information");
    });

    it("should render with internal", () => {
        const internalInformation = "Internal data";
        const dummy = {...stubbedDummy, ...{internalInformation, sourceSystem: DummySourceSystem.INTERNAL}};
        const tree = shallow(<DummyDetailComponent dummy={dummy}/>);

        expect(tree.find(".system").text()).toStrictEqual(`System: ${DummySourceSystem.INTERNAL}`);
        expect(tree.find(".specific-info").text()).toStrictEqual(internalInformation);
    });

    it("should render with external", () => {
        const externalInformation = "External data";
        const dummy = {...stubbedDummy, ...{externalInformation}};
        const tree = shallow(<DummyDetailComponent dummy={dummy}/>);

        expect(tree.find(".specific-info").text()).toStrictEqual(externalInformation);
    });
});