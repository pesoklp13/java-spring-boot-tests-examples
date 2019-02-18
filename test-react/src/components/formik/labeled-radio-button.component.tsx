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
                ({field, form}: FieldProps) => (
                    <label>
                        <input type={"radio"} {...props} onChange={field.onChange} checked={form.values[props.name] === props.value}/>
                        {props.value}
                    </label>
                )
            }
        </Field>
    );
};