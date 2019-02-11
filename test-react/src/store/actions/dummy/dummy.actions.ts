import {Action} from "redux";
import {Dummy} from "../../../api/dummy/dummy.model";

export const DUMMY_SEARCH_ALL = "@dummy/SEARCH_ALL";
export const DUMMY_FIND_BY_SOURCE_SYSTEM = "@dummy/FIND_BY_SOURCE_SYSTEM";
export const DUMMY_DETAIL = "@dummy/DETAIL";

export interface SearchAllDummies extends Action<string> {
    type: "@dummy/SEARCH_ALL",
    payload: Dummy[]
}

export interface FindDummiesBySourceSystem extends Action<string> {
    type: "@dummy/FIND_BY_SOURCE_SYSTEM",
    payload: Dummy[]
}

export interface GetDummyDetail extends Action<string> {
    type: "@dummy/DETAIL",
    payload: Dummy
}

export type DummyActions = SearchAllDummies | FindDummiesBySourceSystem | GetDummyDetail;