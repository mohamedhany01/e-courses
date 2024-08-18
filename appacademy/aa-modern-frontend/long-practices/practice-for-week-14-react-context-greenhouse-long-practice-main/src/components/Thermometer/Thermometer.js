import ReactSlider from "react-slider";
import './Thermometer.css';
import { useClimate } from "../../context/ClimateContext";
import { useEffect, useState } from "react";

function Thermometer() {

  const { temperature, setTemperature } = useClimate();
  const [actualTemperature, setActualTemperature] = useState(temperature);
  const [desiredTemperature, setDesiredTemperature] = useState(temperature);

  // Update desired temperature when it changes
  useEffect(() => {
    setDesiredTemperature(temperature);
  }, [temperature]);

  // Gradually change actualTemperature to match desiredTemperature
  useEffect(() => {
    if (actualTemperature === desiredTemperature) return; // Exit if temperatures are equal

    const intervalId = setInterval(() => {
      setActualTemperature(prevTemp => {
        if (prevTemp < desiredTemperature) {
          return Math.min(prevTemp + 1, desiredTemperature);
        } else if (prevTemp > desiredTemperature) {
          return Math.max(prevTemp - 1, desiredTemperature);
        }
        return prevTemp;
      });

    }, 1000); // Update every second

    // Cleanup function to clear the interval
    return () => clearInterval(intervalId);
  }, [desiredTemperature, actualTemperature]);

  return (
    <section>
      <h2>Thermometer</h2>
      <div className="actual-temp">Actual Temperature: {actualTemperature}°F</div>
      <ReactSlider
        value={temperature}
        onAfterChange={(val) => setTemperature(val)}
        className="thermometer-slider"
        thumbClassName="thermometer-thumb"
        trackClassName="thermometer-track"
        ariaLabel={"Thermometer"}
        orientation="vertical"
        min={0}
        max={120}
        renderThumb={(props, state) => <div {...props}>{state.valueNow}</div>}
        renderTrack={(props, state) => (
          <div {...props} index={state.index}></div>
        )}
        invert
        pearling
        minDistance={1}
      />
    </section>
  );
}

export default Thermometer;