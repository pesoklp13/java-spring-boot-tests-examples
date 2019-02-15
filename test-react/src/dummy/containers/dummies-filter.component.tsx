import * as React from "react";
import {DummySourceSystem} from "../../api/dummy/dummy.model";

export interface DummiesFilterProps {
    filterDummies: (sourceSystem: DummySourceSystem) => void
}

export class DummiesFilterComponentRenderer extends React.Component<DummiesFilterProps> {

    constructor(props: DummiesFilterProps) {
        super(props);

        this.state = {};
    }

    public render(): React.ReactNode {
        return undefined;
    }
}