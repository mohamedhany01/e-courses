import './ClimateStats.css';

function ClimateStats() {

  return (
    <div className="climate-stats">
      <div className="temperature">
        Temperature {"x"}°F
      </div>
      <div className="humidity">
        Humidity {"y"}%
      </div>
    </div>
  )
}

export default ClimateStats;