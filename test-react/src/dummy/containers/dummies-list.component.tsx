import * as React from "react";
import {connect} from "react-redux";
import {Dummy} from "../../api/dummy/dummy.model";
import {AppState} from "../../store/store.config";
import {ThunkDispatch} from "redux-thunk";
import {Action} from "redux";
import {getDummyDetail} from "../../store/actions/dummy/dummy-async.creators";
import classNames from "classnames";
import Modal from "react-modal";
import {closeDummyDetail} from "../../store/actions/dummy/dummy.creators";
import {DummyDetailComponent} from "../dummy-detail.component";

export interface DummiesListProps {
    getDetail: (id: number) => void,
    closeDetail?: () => void,
    dummies?: Dummy[] | undefined,
    dummy?: Dummy | undefined
}

export class DummiesListComponent extends React.Component<DummiesListProps> {

    constructor(props: DummiesListProps) {
        super(props);

        this.state = {};
    }

    public render(): React.ReactNode {
        return (
            <div>
                {this.props.dummies ? this.props.dummies.map((dummy: Dummy) => this.renderRow(dummy)) : "No dummies selected"}
                {this.props.dummy ? this.renderModal() : null}
            </div>
        );
    }

    private renderModal() {
        return (
            <Modal isOpen={!!this.props.dummy} onAfterClose={this.props.closeDetail}>
                <DummyDetailComponent dummy={this.props.dummy as Dummy}/>
            </Modal>
        );
    }

    public renderRow(dummy: Dummy) {
        return (
            <div className={classNames("row")} key={dummy.id}>
                <span className="name">{dummy.name}</span> | <span className="sourceSystem">{dummy.sourceSystem}</span> |
                <button onClick={() => this.props.getDetail(dummy.id)} type="button">Show detail</button>
            </div>
        );
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
        },
        closeDetail: () => {
            dispatch(closeDummyDetail());
        }
    }
};

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(DummiesListComponent)