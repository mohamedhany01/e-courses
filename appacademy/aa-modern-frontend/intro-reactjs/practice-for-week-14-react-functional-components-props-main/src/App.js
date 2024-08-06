import Showcase from "./Showcase";
import BaseStats from "./BaseStats";
import "./App.css";

const baseStats = {
  hp: 45,
  attack: 49,
  defense: 49,
  spAttack: 65,
  spDef: 65,
  speed: 45,
};

const handleClick = () => {
  alert(
    `Special Stats\n\tSpecial Attack: ${baseStats.spAttack}\n\tSpecial Defense: ${baseStats.spDef}`
  );
};

function App() {
  return (
    <div className="main-wrapper background">
      <Showcase />
      <BaseStats stats={baseStats} clicker={handleClick} />
    </div>
  );
}

export default App;
