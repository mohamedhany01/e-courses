import produceData from '../../mockData/produce.json';

const POPULATE = 'produce/POPULATE';
const TOGGLE_LIKE = 'produce/TOGGLE_LIKE';

export const populateProduce = () => {
    return {
        type: POPULATE,
        payload: produceData
    };
};

export const toggleLike = (id) => {
    return {
        type: TOGGLE_LIKE,
        payload: {
            id
        }
    };
};

export const getAllProduce = (state) => Object.values(state.produceState);

export default function produceReducer(state = {}, { type, payload }) {
    switch (type) {
        case POPULATE:
            const newState = {};
            payload.forEach(produce => {
                newState[produce.id] = produce;
            });
            return newState;
        case TOGGLE_LIKE:
            const newItem = state[payload.id];
            newItem.liked = !newItem.liked;

            return {
                ...state,
            }
        default:
            return state;
    }
}