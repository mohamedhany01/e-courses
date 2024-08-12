import { Route, Switch } from "react-router-dom/cjs/react-router-dom.min";
import GalleryNavigation from "./GalleryNavigation";
import GalleryView from "./GalleryView";

import harvardArt from "./data/harvardArt";
import Home from "./Home";

function App() {
  return (
    <>
      <GalleryNavigation seed={harvardArt} />

      <Switch>
        <Route exact path="/">
          <Home />
        </Route>
        <Route path="/galleries/:galleryId">
          <GalleryView seed={harvardArt} />
        </Route>
        <Route>
          <h1>Page Not Found</h1>
        </Route>
      </Switch>
    </>
  );
}

export default App;
