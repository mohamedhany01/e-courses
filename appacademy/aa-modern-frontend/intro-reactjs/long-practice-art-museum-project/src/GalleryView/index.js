import { Route, Switch, useParams, useRouteMatch } from "react-router-dom/cjs/react-router-dom.min";
import ArtImageTile from "../ArtImageTile";
import ArtDescription from "../ArtDescription";

const GalleryView = ({ seed }) => {

    const { galleryId } = useParams();

    const { records } = seed;

    const { id, name, objects, theme } = records.find(gallery => gallery.id === parseInt(galleryId));

    const { url } = useRouteMatch();
    
    return (
        <>
            <h2>{name} | {id}</h2>

            <Switch>
                <Route exact path={url}>
                    {objects.map(art => (
                        <ArtImageTile key={art.id} art={art} theme={theme} />
                    ))}
                </Route>
                <Route path={`${url}/art/:artId`}>
                    <ArtDescription galleries={objects} prevPath={url} />
                </Route>
            </Switch>

        </>
    )
}

export default GalleryView;