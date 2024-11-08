const ACTIONS = {
    ADD: "ADD_PRODUCT",
    CHECKOUT: "CHECKOUT_PRODUCTS"
}


function addProduct(payload) {
    return {
        type: ACTIONS.ADD,
        payload: payload
    }
}

export { ACTIONS, addProduct };