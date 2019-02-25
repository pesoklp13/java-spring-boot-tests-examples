import React, {Component} from 'react';
import './App.css';
import {Formik} from "formik";

class App extends Component {
    render() {
        return (
            <div className="App">
                <Formik initialValues={{}} onSubmit={values => {
                    console.log(values)
                }}>{
                    (formik: any) => (
                        <div>
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
