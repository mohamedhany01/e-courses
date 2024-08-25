import articles from '../data/data.json';

const LOAD_ARTICLES = 'article/loadArticles';

const initialState = { entries: [], isLoading: true };

const articleReducer = (state = initialState, { type, payload }) => {
    switch (type) {
        case LOAD_ARTICLES:
            return { ...state, entries: [...payload] };
        default:
            return state;
    }
};

export const loadArticles = () => {
    return {
        type: LOAD_ARTICLES,
        payload: articles
    };
};

export default articleReducer;