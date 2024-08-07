import { NavLink, Route, Switch } from "react-router-dom/cjs/react-router-dom.min";
import MovieDetails from "../MovieDetails";

function Movies({ movies }) {

  return (
    <div className='comp orange'>
      <h1>Movies Component</h1>
      <nav>
        <ul>
          {movies.map(({ id, title }) => <li key={id}><NavLink to={`/movies/${id}`}>{title}</NavLink></li>)}
        </ul>
      </nav>
      <Switch>
        <Route path="/movies/:movieId">
          <MovieDetails movies={movies} />
        </Route>
      </Switch>
    </div>
  );
}

export default Movies;