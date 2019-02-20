import * as React from "react";
import {connect} from "react-redux";
import {Dummy} from "../../api/dummy/dummy.model";
import {AppState} from "../../store/store.config";
import {ThunkDispatch} from "redux-thunk";
import {Action} from "redux";
import {getDummyDetail} from "../../store/actions/dummy/dummy-async.creators";

export interface DummiesListProps {
    getDetail: (id: number) => void
    dummies?: Dummy[] | undefined,
    dummy?: Dummy | undefined
}

export class DummiesListComponent extends React.Component<DummiesListProps> {

    constructor(props: DummiesListProps){
        super(props);

        this.state = {};
    }

    public render(): React.ReactNode {
        return null;
    }
}

export const mapStateToProps = (state: AppState) => {
    return {
        dummies: state.dummies.filteredDummies,
        dummy: state.dummies.selectedDummy
    }
};

export const mapDispatchToProps = (dispatch: ThunkDispatch<AppState, any, Action>) => {
    return {
        getDetail: (id: number) => {
            dispatch(getDummyDetail(id)).then();
        }
    }
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(DummiesListComponent)