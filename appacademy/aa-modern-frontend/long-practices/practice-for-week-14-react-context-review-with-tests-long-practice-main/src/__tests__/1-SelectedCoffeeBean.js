import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import { useContext } from 'react';
import App from '../App';
import SelectedCoffeeBean from '../components/SelectedCoffeeBean';

jest.mock('react', () => ({
  ...jest.requireActual('react'), // use actual React for everything but useContext
  useContext: jest.fn()
}));

describe('SelectedCoffeeBean', () => {
  const mockFn = jest.fn();

  it ('should be invoked by the App component', () => {
    useContext.mockImplementation(() => ({
      coffeeBean: { id: 5, name: 'Set Bean' },
      setCoffeeBeanId: mockFn
    }));

    render(<App />);
    // Use regex since not checking for whole string
    expect(screen.getByRole('heading', { name: /Current Selection/ })).toBeInTheDocument();
  });
  
  it('should render the name of the selected coffee in the `h2` heading', () => {
    useContext.mockReturnValueOnce({
      coffeeBean: { id: 5, name: 'Set Bean' },
      setCoffeeBeanId: mockFn
    }).mockReturnValueOnce({
      coffeeBean: { id: 5, name: 'Vanilla' },
      setCoffeeBeanId: mockFn
    });

    const { rerender } = render(<SelectedCoffeeBean />);
    expect(screen.getByRole('heading', { name: 'Current Selection: Set Bean' })).toBeInTheDocument();
    expect(screen.queryByRole('heading', { name: 'Current Selection: Vanilla' })).not.toBeInTheDocument();
    
    rerender(<SelectedCoffeeBean />);
    expect(screen.getByRole('heading', { name: 'Current Selection: Vanilla' })).toBeInTheDocument();
    expect(screen.queryByRole('heading', { name: 'Current Selection: Set Bean' })).not.toBeInTheDocument();
  });
});