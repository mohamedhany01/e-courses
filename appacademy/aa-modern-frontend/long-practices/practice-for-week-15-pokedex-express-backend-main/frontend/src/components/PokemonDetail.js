import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import PokemonItems from './PokemonItems';
import EditPokemonForm from './EditPokemonForm';
import ItemForm from './ItemForm';
import { getOnePokemon } from '../store/pokemon';

const PokemonDetail = () => {
    const dispatch = useDispatch();
    const { pokemonId } = useParams();
    const pokemon = useSelector(state => state.pokemon[pokemonId]);
    const [showEditPokeForm, setShowEditPokeForm] = useState(false);
    const [showAddItemsForm, setshowAddItemsForm] = useState(false);
    const [editItemId, setEditItemId] = useState(null);
    // console.log(pokemon);

    useEffect(() => {
        dispatch(getOnePokemon(pokemonId));
    }, [dispatch, pokemonId]);

    useEffect(() => {
        setShowEditPokeForm(false);
        setEditItemId(null);
    }, [pokemonId]);

    if (!pokemon || !pokemon.moves) {
        return null;
    }

    let content = null;

    if (editItemId) {
        content = (
            <ItemForm
                id={editItemId}
                hideForm={() => setEditItemId(null)}
                addNewItem={false}
            />
        );
    } else if (showEditPokeForm && pokemon.captured) {
        content = (
            <EditPokemonForm
                pokemon={pokemon}
                hideForm={() => setShowEditPokeForm(false)}
            />
        );
    } else if (showAddItemsForm) {
        content = (
            <ItemForm
                id={pokemon.id}
                hideForm={() => setshowAddItemsForm(false)}
                addNewItem={true}
            />
        );
    } else {
        content = (
            <div className="pokemon-detail-lists">
                <div>
                    <h2>Information</h2>
                    <ul>
                        <li>
                            <b>No</b> {pokemon.no}
                        </li>
                        <li>
                            <b>Type</b> {pokemon.type}
                        </li>
                        <li>
                            <b>Attack</b> {pokemon.attack}
                        </li>
                        <li>
                            <b>Defense</b> {pokemon.defense}
                        </li>
                        <li>
                            <b>Moves</b>
                            <ul>
                                {pokemon.moves && pokemon.moves.map((move, i) => (
                                    <li key={move + i}>{move}</li>
                                ))}
                            </ul>
                        </li>
                    </ul>
                </div>
                <div>
                    <h2>
                        Items
                        <button onClick={() => setshowAddItemsForm(true)}> + </button>
                    </h2>
                    <table>
                        <thead>
                            <tr>
                                <th></th>
                                <th>Name</th>
                                <th>Happiness</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            <PokemonItems pokemon={pokemon} setEditItemId={setEditItemId} />
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }

    return (
        <div className="pokemon-detail">
            <div className={`pokemon-detail-image-background`}>
                <div
                    className="pokemon-detail-image"
                    style={{ backgroundImage: `url('${pokemon.imageUrl}')` }}
                ></div>
                <div>
                    <h1 className="bigger">{pokemon.name}</h1>
                    {(!showEditPokeForm && pokemon.captured) && (
                        <button onClick={() => setShowEditPokeForm(true)}>Edit</button>
                    )}
                </div>

            </div>
            {content}
        </div>
    );
};

export default PokemonDetail;
