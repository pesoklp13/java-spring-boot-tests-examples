import {Field, FieldProps} from "formik";
import React from "react";

export interface LabeledRadioButtonProps {
    name: string
    value: string
}

export const LabeledRadioButton = (props: LabeledRadioButtonProps) => {
    return (
        <Field name={props.name}>
            {
                ({field}: FieldProps) => (
                    <label>
                        <input type={"radio"} {...props} onChange={field.onChange}/>
                        {props.value}
                    </label>
                )
            }
        </Field>
    );
};