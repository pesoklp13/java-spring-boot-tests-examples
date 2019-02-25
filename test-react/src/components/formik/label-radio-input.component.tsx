import {FieldProps, FormikProps} from "formik";
import classNames from "classnames";
import * as React from "react";

type Overwrite<T1, T2> = {
    [P in Exclude<keyof T1, keyof T2>]: T1[P]
} & T2;

type FormMap = { [key: string]: any };

type PartialFieldProps = Overwrite<FieldProps<FormMap>, {
    form?: FormikProps<FormMap>,
    value: any,
    label?: string
}>

export const LabelRadioInput = (props: PartialFieldProps) => {
    return (
        <label>
            <input
                name={props.field.name}
                type="radio"
                value={props.value}
                onChange={props.field.onChange}
                onBlur={props.field.onBlur}
                className={classNames("radio-button")}
            />
            {props.label ? props.label : props.value}
        </label>
    );
};