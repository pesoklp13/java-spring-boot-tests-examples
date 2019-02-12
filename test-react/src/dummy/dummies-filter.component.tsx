import * as React from "react";
import {DummyState} from "../store/reducers/dummy/dummy.reducer";
import {Dummy, DummySourceSystem} from "../api/dummy/dummy.model";
import {connect} from "react-redux";
import {Action} from "redux";
import {getDummyDetail} from "../store/actions/dummy/dummy-async.creators";
import {ThunkDispatch} from "redux-thunk";

export interface DummiesFilterProps {
    handleClick: () => void
}

class DummiesFilterComponentRenderer extends React.Component<DummiesFilterProps,DummyState> {

    constructor(props: any) {
        super(props);
        this.state = {
            selectedDummy: {
                id: 0,
                name: "jezko",
                sourceSystem: DummySourceSystem.INTERNAL,
                furtherInformation: "info"
            }
        };
    }


    setState<K extends keyof DummyState>(state: ((prevState: Readonly<DummyState>, props: Readonly<DummiesFilterProps>) => (Pick<DummyState, K> | DummyState | null)) | Pick<DummyState, K> | DummyState | null, callback?: () => void): void {
        console.log("setState called");
        super.setState(state);
    }

    public render(): React.ReactNode {
        console.log("rendering");
        const dummy: Dummy = this.state.selectedDummy as Dummy;

        return (
            <div>
                {dummy.name ? dummy.name : "unknown"}
                <div onClick={() => {this.props.handleClick()}}>
                    tak ma klikni kua
                </div>
            </div>
        );
    }
}

export const DummiesFilterComponent = connect(null,
    (dispatch: ThunkDispatch<DummyState, any, Action>) => {
        return {
            handleClick: () => {
                console.log("clicked");
                dispatch(getDummyDetail(1)).then();
            }
        }
    }
)(DummiesFilterComponentRenderer);