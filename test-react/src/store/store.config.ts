import {DummyReducer, DummyState} from "./reducers/dummy/dummy.reducer";
import {applyMiddleware, combineReducers, createStore} from "redux";
import thunk from "redux-thunk";

export interface AppState {
    dummies: DummyState
}

export const store = createStore(
    combineReducers({dummies: DummyReducer}),
    applyMiddleware(thunk)
);