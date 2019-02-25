import * as React from "react";
import {DummySourceSystem} from "../../api/dummy/dummy.model";
import {connect} from "react-redux";
import {Field, Form, Formik} from "formik";
import {LabelRadioInput} from "../../components/formik/label-radio-input.component";
import {ThunkDispatch} from "redux-thunk";
import {Action} from "redux";
import {AppState} from "../../store/store.config";
import {findDummiesBySourceSystem, searchAllDummies} from "../../store/actions/dummy/dummy-async.creators";

export interface DummiesFilterProps {
    filterDummies: (sourceSystem: DummySourceSystem | null) => void
}

export interface FormValues {
    sourceSystem: DummySourceSystem | null;
}

const initialValues: FormValues = {
    sourceSystem: null
};

export class DummiesFilterComponent extends React.Component<DummiesFilterProps> {

    constructor(props: DummiesFilterProps) {
        super(props);

        this.state = {};
    }

    public render(): React.ReactNode {
        return (
            <Formik initialValues={initialValues} onSubmit={(values) => {
                this.props.filterDummies(values.sourceSystem);
            }}>
                <Form>
                    <Field component={LabelRadioInput} name="sourceSystem" value={null} label={"ALL"}/>
                    <Field component={LabelRadioInput} name="sourceSystem"
                           value={DummySourceSystem.INTERNAL.toString()}/>
                    <Field component={LabelRadioInput} name="sourceSystem" value={DummySourceSystem.EXTERNAL}/>
                    <button type="submit">Get dummies</button>
                </Form>
            </Formik>
        );
    }
}

export const mapDispatchToProps = (dispatch: ThunkDispatch<AppState, any, Action>) => {
    return {
        filterDummies: (sourceSystem: DummySourceSystem | null) => {
            if(sourceSystem) {
                dispatch(findDummiesBySourceSystem(sourceSystem)).then();

                return;
            }

            dispatch(searchAllDummies()).then();
        }
    }
};

export default connect(
    null,
    mapDispatchToProps
)(DummiesFilterComponent);