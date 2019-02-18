import {LabeledRadioButton} from "./labeled-radio-button.component";
import * as React from "react";
import {Form, Formik, FormikActions} from "formik";
import {fireEvent, render, wait} from "react-testing-library";

type formikSubmit = (values: any, formikActions: FormikActions<any>) => void;

const renderForm = (fields: JSX.Element[], submitFn: formikSubmit) => {
    return (
        <Formik initialValues={{}} onSubmit={submitFn}>
            <Form data-testid="testId">
                {
                    fields.map((field: JSX.Element) => field)
                }
                <button type="submit">Submit</button>
            </Form>
        </Formik>
    );
};

describe("LabeledRadioButton", () => {

    const testValue1 = "value 1";
    const testValue2 = "value 2";
    const elementName = "test";
    const field1 = <LabeledRadioButton name={elementName} value={testValue1} key="1"/>;
    const field2 = <LabeledRadioButton name={elementName} value={testValue2} key="2"/>;
    const submitMock = jest.fn();

    it("should render input inside label", () => {
        const {getByText, getByValue} = render(renderForm([field1], submitMock));
        const label = getByText(/value 1/);

        expect(label.textContent).toEqual(testValue1);

        const input = getByValue(testValue1);
        expect(input).not.toBeNull();

    });

    it("should have proper value after submit", async () => {
        const {getByValue, getByTestId} = render(renderForm([field1, field2], submitMock));

        const input1 = getByValue(testValue1);
        expect(input1).not.toBeNull();

        const input2 = getByValue(testValue2);
        expect(input2).not.toBeNull();

        const form = getByTestId("testId");
        expect(form).not.toBeNull();

        submitMock.mockClear();

        fireEvent.submit(form);

        await wait(() => {
            expect(submitMock).toBeCalledWith({}, expect.anything());
        });

        submitMock.mockClear();

        fireEvent.click(input1);

        fireEvent.submit(form);

        await wait(() => {
            expect(submitMock).toBeCalledWith({"test": testValue1}, expect.anything());
        });

        submitMock.mockClear();

        fireEvent.click(input2);

        fireEvent.submit(form);

        await wait(() => {
            expect(submitMock).toBeCalledWith({"test": testValue2}, expect.anything());
        });

    });
});