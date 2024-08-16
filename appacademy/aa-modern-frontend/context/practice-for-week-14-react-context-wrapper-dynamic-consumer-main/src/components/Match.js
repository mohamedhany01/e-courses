import { useContext, useState } from "react";
import { HoroscopeContext } from "../context/HoroscopeContext";

const Match = () => {
    const { sign } = useContext(HoroscopeContext);

    const [match, setMatch] = useState(false);

    console.log(sign);
    return (
        <>
            <button onClick={() => setMatch(!match)}>Match</button>
            {match && (
                <div>Match: {sign.match}</div>
            )}
        </>
    );
};

export default Match;