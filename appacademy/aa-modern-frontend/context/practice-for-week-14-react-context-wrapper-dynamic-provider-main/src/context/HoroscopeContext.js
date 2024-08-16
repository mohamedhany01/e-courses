import { createContext, useState } from 'react';

import horoscopes from "../data/horoscopes";

export const HoroscopeContext = createContext();


export const HoroscopeProvider = props => {

    const [currentSign, setCurrentSign] = useState("Virgo");

    return (
        <HoroscopeContext.Provider value={{ sign: horoscopes[currentSign], setCurrentSign }}>
            {props.children}
        </HoroscopeContext.Provider>
    )
}

