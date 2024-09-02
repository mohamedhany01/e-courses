import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { deleteItem, getPokemonItems } from "../store/items";

const PokemonItems = ({ pokemon, setEditItemId }) => {
    const dispatch = useDispatch();

    const items = useSelector((state) => {
        if (!pokemon.items) return null;
        return pokemon.items.map(itemId => state.items[itemId]);
    });

    useEffect(() => {
        dispatch(getPokemonItems(pokemon.id));
    }, [dispatch]);

    if (!items) {
        return null;
    }

    return items.map((item) => (
        <tr key={item.id}>
            <td>
                <img
                    className="item-image"
                    alt={item.imageUrl}
                    src={`${item.imageUrl}`}
                />
            </td>
            <td>{item.name}</td>
            <td className="centered">{item.happiness}</td>
            <td className="centered">${item.price}</td>
            {pokemon.captured && (
                <td className="centered">
                    <button onClick={() => setEditItemId(item.id)}>
                        Edit
                    </button>
                </td>
            )}
            {pokemon.captured && (
                <td className="centered">
                    <button onClick={() => dispatch(deleteItem({
                        id: item.id,
                        pokemonId: pokemon.id
                    }))}>
                        Delete
                    </button>
                </td>

            )}
        </tr>
    ));
};

export default PokemonItems;
