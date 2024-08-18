const SetCoffeeBean = ({ coffeeBeans }) => {
  return (
    <div className="set-coffee-bean">
      <h2>Select a Coffee Bean</h2>
      <select
        name="coffee-bean"
      >
        {coffeeBeans.map(bean => (
          <option
            key={bean.id}
            value={bean.id}
          >
            {bean.name}
          </option>
        ))}
      </select>
    </div>
  );
}

export default SetCoffeeBean;