import { useClimate } from '../../context/ClimateContext';
import './ClimateStats.css';

function ClimateStats() {

  const { temperature, humidity } = useClimate();

  return (
    <div className="climate-stats">
      <div className="temperature">
        Temperature {temperature}°F
      </div>
      <div className="humidity">
        Humidity {humidity}%
      </div>
    </div>
  )
}

export default ClimateStats;