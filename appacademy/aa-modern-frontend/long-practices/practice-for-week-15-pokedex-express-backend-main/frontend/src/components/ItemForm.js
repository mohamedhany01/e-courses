import { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { addItem, editItem } from '../store/items';

const ItemForm = ({ id, hideForm, addNewItem }) => {
    const dispatch = useDispatch();

    let itemToAdd = useSelector(state => state.pokemon[id]);

    const itemToChange = useSelector(state => state.items[id])

    const [happiness, setHappiness] = useState(addNewItem ? "" : itemToChange.happiness);
    const [price, setPrice] = useState(addNewItem ? "" : itemToChange.price);
    const [name, setName] = useState(addNewItem ? "" : itemToChange.name);

    const updateName = (e) => setName(e.target.value);
    const updateHappiness = (e) => setHappiness(e.target.value);
    const updatePrice = (e) => setPrice(e.target.value);

    const handleUpdateSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            ...itemToChange,
            name,
            happiness,
            price
        };

        let returnedItem = await dispatch(editItem(payload))
        if (returnedItem) {
            hideForm();
        }
    };

    const handleAddSubmit = async (e) => {
        e.preventDefault();

        const payload = {
            happiness: parseInt(happiness),
            name,
            price: parseInt(price),
        };

        let returnedItem = await dispatch(addItem(payload, itemToAdd.id))
        if (returnedItem) {
            hideForm();
        }

    }

    const handleCancelClick = (e) => {
        e.preventDefault();
        hideForm();
    };

    return (
        <section className="edit-form-holder centered middled">
            <form className="item-form" onSubmit={addNewItem ? handleAddSubmit : handleUpdateSubmit}>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={updateName}
                />
                <input
                    type="number"
                    placeholder="Happiness"
                    min="0"
                    max="100"
                    required
                    value={happiness}
                    onChange={updateHappiness}
                />
                <input
                    type="number"
                    placeholder="Price"
                    required
                    value={price}
                    onChange={updatePrice}
                />
                <button type="submit">{addNewItem ? "Add" : "Update"} Item</button>
                <button type="button" onClick={handleCancelClick}>Cancel</button>
            </form>
        </section>
    );
};

export default ItemForm;
