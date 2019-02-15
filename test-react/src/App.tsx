import React, {Component} from 'react';
import './App.css';
import {Field, Formik} from "formik";
import classNames from "classnames";
import {LabeledRadioButton} from "./components/formik/labeled-radio-button.component";

class App extends Component {
    render() {
        return (
            <div className="App">
                <Formik initialValues={{}} onSubmit={values => {
                    console.log(values)
                }}>{
                    (formik: any) => (
                        <div>
                            <LabeledRadioButton name={"pesok"} value={"value1"}/>
                            <LabeledRadioButton name={"pesok"} value={"supervalue"}/>
                            <button onClick={formik.submitForm}>submit</button>
                        </div>
                    )
                }
                </Formik>
            </div>
        );
    }
}

export default App;
