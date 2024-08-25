import { Route, Switch } from 'react-router-dom';
import SingleArticle from '../SingleArticle';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { loadArticles } from '../../store/articleReducer';
import { NavLink } from 'react-router-dom/cjs/react-router-dom';

const ArticleList = () => {

  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(loadArticles())
  }, [dispatch]);

  const articles = useSelector(state => state.articleState.entries);

  return (
    <div>
      <h1>Article List</h1>
      <ol>
        {articles.map(({ id, title }) => (
          <li key={id}><NavLink to={`/article/${id}`}>{title}</NavLink></li>
        ))}
      </ol>

      <Switch>
        <Route path='/article/:id'>
          <SingleArticle />
        </Route>
      </Switch>
    </div>
  );
};

export default ArticleList;