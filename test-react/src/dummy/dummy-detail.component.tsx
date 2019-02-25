import {Dummy, DummySourceSystem} from "../api/dummy/dummy.model";
import * as React from "react";

export interface DummyDetailComponentProps {
    dummy: Dummy
}

export const DummyDetailComponent = (props: DummyDetailComponentProps) => {
    const {dummy} = props;
    return (
        <div className="detail">
            <div className="name">Name: {dummy.name}</div>
            <div className="system">System: {dummy.sourceSystem}</div>
            <div className="common-info">Common info: {dummy.furtherInformation}</div>
            <div className="specific-info">{
                dummy.sourceSystem === DummySourceSystem.INTERNAL
                    ? dummy.internalInformation
                    : dummy.externalInformation
            }
            </div>
        </div>
    );
};