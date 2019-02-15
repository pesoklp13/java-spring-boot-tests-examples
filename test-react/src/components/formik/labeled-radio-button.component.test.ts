import {shallow} from "enzyme";
import {LabeledRadioButton} from "./labeled-radio-button.component";

describe("LabeledRadioButton", () => {

    it("should render input inside label", () => {
        const element = shallow(LabeledRadioButton({name: "test", value: "value1"}));
        console.log(element.getDOMNode());
        expect(element.contains("<label><input></label>")).toBeTruthy();
        expect(element.contains("<label><input name='test'>")).toBeTruthy();
    });
});