import { useDispatch } from "react-redux";
import { addProduce } from "../../store/reducers/cart";
import { toggleLike } from "../../store/reducers/produce";

function ProduceDetails({ produce, onAddItem }) {
  const cartItem = {};

  const dispatch = useDispatch();

  const addNewItem = () => {
    dispatch(addProduce(produce.id));
    onAddItem(true);
  }

  return (
    <li className="produce-details">
      <span>{produce.name}</span>
      <span>
        <button
          className={"like-button" + (produce.liked ? " selected" : "")}
          onClick={() => dispatch(toggleLike(produce.id))}
        >
          <i className={"fas fa-heart"} />
        </button>
        <button
          className={"plus-button" + (cartItem ? " selected" : "")} onClick={addNewItem}
        >
          <i className="fas fa-plus" />
        </button>
      </span>
    </li>
  );
}

export default ProduceDetails;