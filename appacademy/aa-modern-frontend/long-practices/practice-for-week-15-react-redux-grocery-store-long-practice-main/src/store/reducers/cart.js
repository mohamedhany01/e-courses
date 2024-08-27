const ADD = 'cart/ADD_ITEM';
const REMOVE = 'cart/REMOVE_ITEM';
const PURCHASE = 'cart/PURCHASE';
const COUNT_UP = "cart/COUNT_UP";
const COUNT_DOWN = "cart/COUNT_DOWN";

export const addProduce = (id) => {
    return {
        type: ADD,
        payload: { id }
    };
};

export const removeProduce = (id) => {
    return {
        type: REMOVE,
        payload: { id }
    };
};

export const purchase = () => {
    return {
        type: PURCHASE
    };
};

export const countUp = (id) => {
    return {
        type: COUNT_UP,
        payload: { id }
    }
}

export const countDown = (id) => {
    return {
        type: COUNT_DOWN,
        payload: { id }
    }
}

const defaultState = {
    items: {},
}

const countPlusOne = (state, id) => {
    const newCount = state.items[id] ? state.items[id].count + 1 : 1;

    return newCount;
}

const countMminusOne = (currentCount) => {

    return currentCount - 1;
}

const deleteItem = (state, id) => {
    const { [id]: _, ...newCart } = state.items;

    return newCart;
}

export const getAllCartItems = ({ produceState, cartState }) => {
    return Object.values(cartState.items)
        .map(item => {
            return {
                ...item,
                ...produceState[item.id]
            };
        });
};

export default function produceReducer(state = defaultState, { type, payload }) {
    switch (type) {
        case ADD:
            return {
                ...state,
                items: {
                    ...state.items,
                    [payload.id]: {
                        id: payload.id,
                        count: countPlusOne(state, payload.id)
                    }
                }
            };
        case REMOVE:
            return {
                ...state,
                items: deleteItem(state, payload.id)
            };
        case PURCHASE:
            return defaultState;
        case COUNT_UP:
            return {
                ...state,
                items: {
                    ...state.items,
                    [payload.id]: {
                        id: payload.id,
                        count: countPlusOne(state, payload.id)
                    }
                }
            };
        case COUNT_DOWN:

            const hasCount = state.items[payload.id];

            if (hasCount) {
                const currentCount = state.items[payload.id].count;

                if (currentCount > 1) {
                    return {
                        ...state,
                        items: {
                            ...state.items,
                            [payload.id]: {
                                id: payload.id,
                                count: countMminusOne(currentCount)
                            }
                        }
                    };
                } else {
                    return {
                        ...state,
                        items: deleteItem(state, payload.id)
                    };
                }
            }

            return state;
        default:
            return state;
    }
}