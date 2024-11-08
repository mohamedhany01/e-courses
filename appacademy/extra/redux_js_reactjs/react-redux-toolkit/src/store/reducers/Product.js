import { createSlice } from "@reduxjs/toolkit";

const productSlice = createSlice({
    name: "product",
    initialState: [],
    reducers: {
        addProduct: (state = [], { payload }) => {

            state.push(payload)

            return state;
        }
    }
})

const { addProduct } = productSlice.actions;

export { addProduct };

export default productSlice.reducer;