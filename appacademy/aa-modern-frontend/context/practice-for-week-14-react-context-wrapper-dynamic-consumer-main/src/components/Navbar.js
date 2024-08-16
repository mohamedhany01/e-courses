import horoscopesObj from '../data/horoscopes';

const Navbar = () => {
  const horoscopes = Object.keys(horoscopesObj);

  return (
    <nav>
      {horoscopes.map(sign => (
        <span key={sign}>
        {sign}
        </span>
      ))}
    </nav>
  )
};

export default Navbar;