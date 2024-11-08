import { configureStore } from "@reduxjs/toolkit";

import ProductReducer from "./reducers/Product";

export const STORE = configureStore({
    reducer: {
        product: ProductReducer
    },
});