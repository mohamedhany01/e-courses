import { useState, useEffect } from 'react';
import { countDown, removeProduce } from '../../store/reducers/cart';
import { useDispatch } from 'react-redux';
import { countUp } from '../../store/reducers/cart';

function CartItem({ item }) {
  const [count, setCount] = useState(item.count);

  const dispatch = useDispatch();

  useEffect(() => {
    setCount(item.count);
  }, [item.count]);

  return (
    <li className="cart-item">
      <div className="cart-item-header">{item.name}</div>
      <div className="cart-item-menu">
        <input
          type="number"
          value={count}
          onChange={() => { }}
        />
        <button
          className="cart-item-button"
          onClick={() => dispatch(countUp(item.id))}
        >
          +
        </button>
        <button
          className="cart-item-button"
          onClick={() => dispatch(countDown(item.id))}
        >
          -
        </button>
        <button
          className="cart-item-button"
          onClick={() => dispatch(removeProduce(item.id))}
        >
          Remove
        </button>
      </div>
    </li>
  );
}

export default CartItem;