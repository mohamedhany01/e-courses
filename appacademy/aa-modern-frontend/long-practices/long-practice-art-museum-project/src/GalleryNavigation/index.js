import { NavLink } from "react-router-dom/cjs/react-router-dom.min";
import "./GalleryNavigation.css";

const GalleryNavigation = ({ seed }) => {

    const { records } = seed;

    return (
        <>
            <h1>Galleries</h1>
            <nav>
                <ul>
                    {
                        records.map(
                            ({ id, name }) => {
                                return (
                                    <li key={id}>
                                        <NavLink to={`/galleries/${id}`}>
                                            {name}
                                        </NavLink>
                                    </li>
                                )
                            }
                        )
                    }
                </ul>
            </nav>
        </>
    )
}

export default GalleryNavigation;