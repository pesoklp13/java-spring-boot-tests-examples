import * as React from "react";
import {shallow, ShallowWrapper} from "enzyme";
import {LabelRadioInput} from "./label-radio-input.component";

describe("test", () => {

    const onChangeMock = jest.fn();
    const onBlurStub = jest.fn();
    const value = "value1";
    const name = "name";

    const field = {
        name: name,
        value: null,
        onChange: onChangeMock,
        onBlur: onBlurStub
    };

    it("should render label > input", () => {

        const tree: ShallowWrapper = shallow(<LabelRadioInput field={field} value={value}/>);
        const label = tree.find("label");
        expect(label.text()).toEqual(value);

        const input = tree.find("input");
        expect(input.prop("className")).toEqual("radio-button");
        expect(input.prop("value")).toEqual(value);
        expect(input.prop("name")).toEqual(name);
    });

    it("should render label with overwritten value", () => {
        const overwriteLabel = "overwrite";
        const tree: ShallowWrapper = shallow(<LabelRadioInput field={field} value={value} label={overwriteLabel}/>);

        const label = tree.find("label");
        expect(label.text()).toEqual(overwriteLabel);
    });

    it("should call onChange when change event occurs", () => {
        const tree: ShallowWrapper = shallow(<LabelRadioInput field={field} value={value}/>);
        const input = tree.find("input");

        const event = {target: {name, value}};

        input.simulate("change", event);

        expect(onChangeMock).toBeCalledTimes(1);
        expect(onChangeMock).toBeCalledWith(event);
    });

});