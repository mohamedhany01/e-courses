import bulbasaur from "./images/bulbasaur.jpg";
import "./Showcase.css";

function Showcase() {
  const favPokemon = "Bulbasaur";
  const pokeCharacteristics = {
    type: "move",
    move: "Vine Whip",
  };

  return (
    <div className="pokInfo">
      <h1>{favPokemon}'s Showcase Component</h1>
      <img
        src={bulbasaur}
        alt={favPokemon}
        style={{
          width: "200px",
          borderRadius: "50%",
        }}
      />
      <h2>
        Bulbasaur's type is{" "}
        <span className="move">{pokeCharacteristics.type}</span> and one of
        their moves is <span className="type">{pokeCharacteristics.move}</span>
      </h2>
    </div>
  );
}

export default Showcase;
