export const LOAD_ITEMS = "items/LOAD_ITEMS";
export const UPDATE_ITEM = "items/UPDATE_ITEM";
export const REMOVE_ITEM = "items/REMOVE_ITEM";
export const ADD_ITEM = "items/ADD_ITEM";

const load = (items, pokemonId) => ({
  type: LOAD_ITEMS,
  items,
  pokemonId
});

const update = (item) => ({
  type: UPDATE_ITEM,
  item
});

const add = (item) => ({
  type: ADD_ITEM,
  item
});

const remove = (itemId, pokemonId) => ({
  type: REMOVE_ITEM,
  itemId,
  pokemonId
});

export const addItem = (data, id) => async dispatch => {
  console.log(`/api/pokemon/${id}/items`);
  const response = await fetch(`/api/pokemon/${id}/items`, {
    method: "POST",
    headers: new Headers({
      "Content-Type": "application/json"
    }),
    body: JSON.stringify(data)
  });

  if (response.ok) {
    const item = await response.json();

    dispatch(add(item));

    return item;
  }
};

export const getPokemonItems = (id) => async dispatch => {
  const response = await fetch(`/api/pokemon/${id}/items`);

  if (response.ok) {
    const pokemonItems = await response.json();
    dispatch(load(pokemonItems, id));
  }
};

export const editItem = (data) => async dispatch => {
  const response = await fetch(`/api/items/${data.id}`, {
    method: "PUT",
    headers: new Headers({
      "Content-Type": "application/json"
    }),
    body: JSON.stringify(data)
  });

  if (response.ok) {
    const item = await response.json();
    console.log(item);
    dispatch(update(item));

    return item;
  }
};

export const deleteItem = (data) => async dispatch => {
  const { id, pokemonId } = data;

  const response = await fetch(`/api/items/${id}`, {
    method: "DELETE",
    headers: new Headers({
      "Content-Type": "application/json"
    }),
    body: JSON.stringify({ id: id })
  });

  if (response.ok) {
    const itemId = await response.json();

    dispatch(remove(id, pokemonId));

    return itemId;
  }
};

const initialState = {};

const itemsReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_ITEMS:
      const newItems = {};
      action.items.forEach(item => {
        newItems[item.id] = item;
      })
      return {
        ...state,
        ...newItems
      }
    case REMOVE_ITEM:
      const newState = { ...state };
      delete newState[action.itemId];
      return newState;
    case ADD_ITEM:
      return {
        ...state,
        [action.item.id]: action.item
      }
    case UPDATE_ITEM:
      return {
        ...state,
        [action.item.id]: action.item
      };
    default:
      return state;
  }
};

export default itemsReducer;