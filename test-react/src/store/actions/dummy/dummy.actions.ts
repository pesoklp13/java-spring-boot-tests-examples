import {Action} from "redux";
import {DummySourceSystem} from "../../../api/dummy/dummy.model";

export const DUMMY_SEARCH_ALL = "@dummy/SEARCH_ALL";
export const DUMMY_FIND_BY_SOURCE_SYSTEM = "@dummy/FIND_BY_SOURCE_SYSTEM";
export const DUMMY_DETAIL = "@dummy/DETAIL";

export interface SearchAllDummies extends Action<string> {
    type: "@dummy/SEARCH_ALL"
}

export interface FindDummiesBySourceSystem extends Action<string> {
    type: "@dummy/FIND_BY_SOURCE_SYSTEM",
    payload: DummySourceSystem
}

export interface GetDummyDetail extends Action<string> {
    type: "@dummy/DETAIL",
    payload: number
}

export type DummyActions = SearchAllDummies | FindDummiesBySourceSystem | GetDummyDetail;