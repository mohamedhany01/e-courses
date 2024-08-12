import { Link, useParams } from "react-router-dom/cjs/react-router-dom.min";

const ArtDescription = ({ galleries, prevPath }) => {

    const { artId } = useParams();
    const { images } = galleries.find(g => g.id === parseInt(artId));

    return (
        <>
            <h3>Art Description</h3>
            <Link to={prevPath}>
                Back to Gallery
            </Link>

            <ul>
                {
                    images.map(({ imageid, baseimageurl }) => (
                        <li key={imageid}>
                            <img src={baseimageurl} width="450px" alt={`Image_${imageid}`} />
                        </li>
                    ))
                }
            </ul>

            <Link to={prevPath}>
                Back to Gallery
            </Link>
        </>
    )
}

export default ArtDescription;
