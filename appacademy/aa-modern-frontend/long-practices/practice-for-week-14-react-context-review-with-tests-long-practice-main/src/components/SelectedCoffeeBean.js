import { useContext } from "react";
import { CoffeeContext } from "../context/CoffeeContext";

const SelectedCoffeeBean = () => {

  const { coffeeBean } = useContext(CoffeeContext);

  const { name } = coffeeBean;

  return (
    <div className="selected-coffee">
      <h2>Current Selection: {name} </h2>
    </div>
  );
}

export default SelectedCoffeeBean;