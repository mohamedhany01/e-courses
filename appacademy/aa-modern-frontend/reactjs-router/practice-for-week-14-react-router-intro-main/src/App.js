import Home from './components/Home';
import Stocks from './components/Stocks';
import Movies from './components/Movies';
import { Route, Switch } from 'react-router-dom/cjs/react-router-dom';

function App() {
  return (
    <div className='main'>
      <h1>App Component</h1>

      <Switch>
        <Route path="/" exact="/">
          <Home />
        </Route>

        <Route path="/stocks">
          <Stocks />
        </Route>

        <Route path="/movies">
          <Movies />
        </Route>

        <Route>
          <h1>Page Not Found</h1>
        </Route>
      </Switch>

    </div>
  );
}

export default App;