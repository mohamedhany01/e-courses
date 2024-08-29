const LOAD_ARTICLES = 'article/loadArticles';
const ADD_ARTICLE = 'article/addArticle';

export const loadArticles = (articles) => {
  return {
    type: LOAD_ARTICLES,
    articles
  };
};

export const addArticle = (article) => {
  return {
    type: ADD_ARTICLE,
    article
  };
};

export const fetchArticles = () => {
  return async function (dispatch) {
    const res = await fetch("/api/articles");
    const articles = await res.json();

    dispatch(loadArticles(articles));

    return articles;
  }
}

export const writeArticle = (article) => async (dispatch) => {
  const { title, imageUrl, body } = article;

  if (title.trim() !== '') {
    const res = await fetch('/api/articles', {
      method: "POST",
      headers: new Headers({
        "Content-Type": "application/json",
      }),
      body: JSON.stringify({
        title,
        imageUrl,
        body
      })
    });

    const { ok, statusText } = res;

    if (ok) {
      const newArticle = await res.json();
      dispatch(addArticle(newArticle));

      return article;
    } else {
      console.log(res);
      throw new Error("Something went wrong! " + statusText)
    }
  } else {
    alert("Article's title cannot be empty!");
  }

  return {};
}

const initialState = { entries: [], isLoading: true };

const articleReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_ARTICLES:
      return { ...state, entries: [...action.articles] };
    case ADD_ARTICLE:
      return { ...state, entries: [...state.entries, action.article] };
    default:
      return state;
  }
};

export default articleReducer;