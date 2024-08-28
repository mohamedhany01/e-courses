// constant to avoid debugging typos
const GET_ALL_TWEETS = 'tweet/getAllTweets';

//regular action creator
const loadTweets = (tweets) => {
  return {
    type: GET_ALL_TWEETS,
    tweets
  };
};

export const postTweet = (data) => async (dispatch) => {
  const { content } = data;

  if (content.trim() !== '') {
    const res = await fetch('/api/tweets', {
      method: "POST",
      headers: new Headers({
        "Content-Type": "application/json",
      }),
      body: JSON.stringify(data)
    });

    const { ok, statusText } = res;

    if (ok) {
      dispatch(getAllTweets());
    } else {
      console.log(res);
      throw new Error("Something went wrong! " + statusText)
    }
  } else {
    alert('Tweet cannot be empty!');
  }

}

// thunk action creator
export const getAllTweets = () => async (dispatch) => {
  const response = await fetch('/api/tweets');

  if (response.ok) {
    const data = await response.json();

    dispatch(loadTweets(data));
    return data;
  }
};

// state object
const initialState = {};

// reducer
const tweetsReducer = (state = initialState, action) => {
  switch (action.type) {
    case GET_ALL_TWEETS: {
      const newState = {};
      action.tweets.forEach((tweet) => (newState[tweet.id] = tweet));
      return newState;
    }
    default:
      return state;
  }
};

export default tweetsReducer;