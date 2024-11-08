import { ACTIONS } from "../actions/Product";

const productReducer = (state = [], { type, payload }) => {
    switch (type) {
        case ACTIONS.ADD:
            return [...state, payload];
        case ACTIONS.CHECKOUT:
            return [];
        default:
            return state;
    }
};

export { productReducer };