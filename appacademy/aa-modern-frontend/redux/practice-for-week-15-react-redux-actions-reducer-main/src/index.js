import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import configureStore from './store';
import './index.css';
import { loadArticles } from './store/articleReducer';

const store = configureStore();

/**
   This will put the store and loadArticles "on the window", 
  effectively making them global variables that you can access 
  from your browser's console (or anywhere else in your application). 
  While this can be very useful when debugging, it can also create issues 
  if you end up accidentally--i.e., without realizing it--accessing things 
  on the window in other files. So it is generally a good idea to take things 
  off the window once you are done testing them. In particular you NEVER want 
  to leave things on the window in production, hence the conditional wrapping.
*/
if (process.env.NODE_ENV !== 'production') {
  window.store = store;
  window.loadArticles = loadArticles;
}

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);