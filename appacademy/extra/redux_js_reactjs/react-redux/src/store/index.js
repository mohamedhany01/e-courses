import { legacy_createStore, combineReducers } from "redux";
import { productReducer } from "./reducers/Product";

const reducers = combineReducers({
    product: productReducer
})

export const STORE = legacy_createStore(reducers);