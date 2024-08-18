import ReactSlider from "react-slider";
import "./Hygrometer.css";
import { useClimate } from "../../context/ClimateContext";
import { useEffect, useState } from "react";

function Hygrometer() {

  const { humidity, setHumidity } = useClimate();
  const [actualHumidity, setActualHumidity] = useState(humidity);
  const [desiredHumidity, setDesiredHumidity] = useState(humidity);

  // Update desired humidity when it changes
  useEffect(() => {
    setDesiredHumidity(humidity);
  }, [humidity]);

  // Gradually change actualHumidity to match desiredHumidity
  useEffect(() => {
    if (actualHumidity === desiredHumidity) return; // Exit if humiditys are equal

    const intervalId = setInterval(() => {
      setActualHumidity(prevTemp => {
        if (prevTemp < desiredHumidity) {
          return Math.min(prevTemp + 2, desiredHumidity);
        } else if (prevTemp > desiredHumidity) {
          return Math.max(prevTemp - 2, desiredHumidity);
        }
        return prevTemp;
      });

    }, 1000); // Update every second

    // Cleanup function to clear the interval
    return () => clearInterval(intervalId);
  }, [desiredHumidity, actualHumidity]);

  return (
    <section>
      <h2>Hygrometer</h2>
      <div className="actual-humid">Actual Humidity: {actualHumidity}%</div>
      <ReactSlider
        value={humidity}
        onAfterChange={(val) => setHumidity(val)}
        className="hygrometer-slider"
        thumbClassName="hygrometer-thumb"
        trackClassName="hygrometer-track"
        ariaLabel={"Hygrometer"}
        orientation="vertical"
        min={0}
        max={100}
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

export default Hygrometer;