import { Link, useRouteMatch } from "react-router-dom/cjs/react-router-dom.min";

const ArtImageTile = ({ art, theme }) => {
    const { id } = art;
    const { baseimageurl, imageid } = art.images[0];

    const { url } = useRouteMatch();

    return (
        <>
            <h3>{theme}</h3>
            <Link to={`${url}/art/${id}`}>
                <img src={baseimageurl} width={"200px"} alt={`Image_${imageid}`} />
            </Link>
        </>
    )
}

export default ArtImageTile;