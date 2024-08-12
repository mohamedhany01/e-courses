import './UseState.css';
import { useState } from 'react';

const UseState = () => {
  const [theme, setTheme] = useState('light');

  const [count, setCount] = useState(0);

  return (
    <div className={`state ${theme}`} >
      <h1>UseState Component</h1>
      <button onClick={() => setTheme(theme === 'light' ? 'dark' : 'light')}>Toggle Theme</button>
      <button onClick={() => setTheme('dark')}>Dark</button>
      <button onClick={() => setTheme('light')}>Light</button>
      <h2>{count}</h2>
      <button onClick={() => setCount((prev) => prev + 1)}>
        Increment
      </button>
      <button onClick={() => setCount((prev) => prev - 1)}>
        Decrement
      </button>
    </div>
  );
};

export default UseState;
