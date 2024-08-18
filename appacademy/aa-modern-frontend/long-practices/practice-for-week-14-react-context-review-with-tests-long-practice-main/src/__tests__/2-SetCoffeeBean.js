import React from 'react';
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import '@testing-library/jest-dom';
import App from '../App';
import SetCoffeeBean from '../components/SetCoffeeBean';
import CoffeeProvider from '../context/CoffeeContext';
import coffeeBeans from '../mockData/coffeeBeans.json';

describe('SetCoffeeBean', () => {
  const mockFn = jest.fn();
  const useContextSpy = jest.spyOn(React, 'useContext');

  it ('should be invoked by the App component', () => {
    useContextSpy.mockImplementation(() => ({
      coffeeBean: { id: 5, name: 'Set Bean' },
      setCoffeeBeanId: mockFn,
    }));

    render(<App />);
    
    // Use regex since not checking for whole string
    expect(screen.getByRole('heading', { name: /Select a Coffee Bean/ })).toBeInTheDocument();
    // Use `combobox` to grab the `select` input
    expect(screen.getByRole('combobox')).toBeInTheDocument();
  });

  it ('should update coffeeBeanId when new coffee bean is selected from dropdown', () => {
    const setCoffeeBeanId = mockFn;
    useContextSpy.mockImplementationOnce(() => ({
      coffeeBean: 
      {
        "id": "3",
        "name": "Liberica"
      },
      setCoffeeBeanId: setCoffeeBeanId
    }));

    render (<SetCoffeeBean coffeeBeans={coffeeBeans} />);
    
    userEvent.selectOptions(
      screen.getByRole('combobox'),
      screen.getByRole('option', { name: 'Liberica'})
    )
    expect(setCoffeeBeanId).toHaveBeenCalledWith("3");
    
    userEvent.selectOptions(
      screen.getByRole('combobox'),
      screen.getByRole('option', { name: 'Excelsa'})
    )
    expect(setCoffeeBeanId).toHaveBeenCalledWith("4");
  });

  it ('should cause SelectedCoffeeBean to update when a new coffee bean is selected (Integration test)', () => {
    useContextSpy.mockRestore(); // Enables test to run actual useContext
    render(
      <CoffeeProvider>
        <App />
      </CoffeeProvider >
    );

    userEvent.selectOptions(
      screen.getByRole('combobox'),
      screen.getByRole('option', { name: 'Excelsa'})
    )
    expect(screen.getByRole('heading', { name: 'Current Selection: Excelsa' })).toBeInTheDocument();
  });  
});