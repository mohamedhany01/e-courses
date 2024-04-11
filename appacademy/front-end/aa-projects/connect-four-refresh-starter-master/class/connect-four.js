const Screen = require("./screen");
const Cursor = require("./cursor");

class ConnectFour {

  constructor() {

    this.playerTurn = "O";

    this.grid = [[' ', ' ', ' ', ' ', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ', ' '],
    [' ', ' ', ' ', ' ', ' ', ' ', ' ']]

    this.cursor = new Cursor(6, 7);

    // Initialize a 6x7 connect-four grid
    Screen.initialize(6, 7);
    Screen.setGridlines(true);

    // Navigation
    Screen.addCommand('up', 'up', this.cursor.up.bind(this.cursor));
    Screen.addCommand('right', 'right', this.cursor.right.bind(this.cursor));
    Screen.addCommand('down', 'down', this.cursor.down.bind(this.cursor));
    Screen.addCommand('left', 'left', this.cursor.left.bind(this.cursor));

    // Player's movements
    Screen.addCommand('x', 'set (X)', this.setChar.bind(this, 'X'));
    Screen.addCommand('o', 'set (O)', this.setChar.bind(this, 'O'));

    // Set color to the active til in the grid
    this.cursor.resetBackgroundColor();
    this.cursor.setBackgroundColor();

    Screen.render();
  }

  static checkWin(grid) {

    const WIN_COUNT = 4;

    // Check horizontal
    for (let i = 0; i < grid.length; i++) {

      let counter = 0;
      let prevWinner = null;

      for (let j = 0; j < grid[i].length; j++) {

        let winner = grid[i][j];

        if (winner === ' ') {
          counter = 0;
          continue;
        }

        // In case of O becoming X or vice versa, need to reset the counter
        // We need the 4 in the row to count it as a valid winner
        // Else we need to reset the counter
        if (prevWinner !== winner) { counter = 0 };

        prevWinner = winner;

        const currentTile = grid[i][j];

        if (winner == currentTile) {

          counter++;
        }

        if (counter === WIN_COUNT) {
          return winner;
        }
      }
    }

    // Check vertical
    for (let i = 0; i < grid[0].length; i++) {

      let counter = 0;
      let prevWinner = null;

      for (let j = 0; j < grid.length; j++) {

        let winner = grid[j][i];

        if (winner === ' ') {
          counter = 0;
          continue;
        }

        // In case of O becoming X or vice versa, need to reset the counter
        // We need the 4 in the row to count it as a valid winner
        // Else we need to reset the counter
        if (prevWinner !== winner) { counter = 0 };

        prevWinner = winner;

        const currentTile = grid[j][i];

        if (winner == currentTile) {

          counter++;
        }

        if (counter === WIN_COUNT) {

          return winner;
        }
      }
    }

    // Check diagonal | Left
    for (let i = 0; i < grid.length; i++) {

      let counter = 0;

      for (let j = 0; j < grid[i].length; j++) {

        let winner = grid[i][j];
        let prevWinner = null;

        if (winner === ' ') { continue }

        // We found a win case continuo testing the rest of the diagonal
        let k = i;
        let l = j;

        let inValidBoundaries = k < grid.length && l < grid[i].length;

        while (inValidBoundaries) {

          const currentTile = grid[k][l];

          // In case of O becoming X or vice versa, need to reset the counter
          // We need the 4 in the row to count it as a valid winner
          // Else we need to reset the counter
          if (prevWinner !== currentTile) { counter = 0; };

          prevWinner = currentTile;

          if (winner == currentTile) {
            counter++;
          }

          if (counter === WIN_COUNT) {

            return winner;
          }

          k++;
          l++;

          inValidBoundaries = k < grid.length && l < grid[i].length;
        }
      }
    }


    // Check diagonal | Right
    for (let i = 0; i < grid.length; i++) {

      let counter = 0;

      for (let j = 0; j < grid[i].length; j++) {

        let winner = grid[i][j];
        let prevWinner = null;

        if (winner === ' ') { continue }

        // We found a win case continuo testing the rest of the diagonal
        let k = i;
        let l = j;

        let inValidBoundaries = k < grid.length && l > 0;

        while (inValidBoundaries) {

          const currentTile = grid[k][l];

          // In case of O becoming X or vice versa, need to reset the counter
          // We need the 4 in the row to count it as a valid winner
          // Else we need to reset the counter
          if (prevWinner !== currentTile) { counter = 0; };

          prevWinner = currentTile;

          if (winner == currentTile) {
            counter++;
          }

          if (counter === WIN_COUNT) {

            return winner;
          }

          k++;
          l--;

          inValidBoundaries = k < grid.length && l >= 0;
        }
      }
    }

    // Check emptiness
    for (let i = 0; i < grid.length; i++) {
      for (let j = 0; j < grid[i].length; j++) {
        const currentTile = grid[i][j];

        if (currentTile === ' ') return false;

      }
    }

    return 'T';
  }

  setChar(charOnGrid) {
    const [x, y] = [this.cursor.row, this.cursor.col];

    const currentTile = Screen.grid[x][y];

    // Prevent overwrite a value (e.g. X and O)
    if (currentTile !== ' ') return;

    Screen.setGrid(x, y, charOnGrid);

    // Check status on each move on case of win
    const isWon = ConnectFour.checkWin(Screen.grid);

    if (isWon) {
      ConnectFour.endGame(isWon);
    }

    Screen.render();
  }

  static endGame(winner) {
    if (winner === 'O' || winner === 'X') {
      Screen.setMessage(`Player ${winner} wins!`);
    } else if (winner === 'T') {
      Screen.setMessage(`Tie game!`);
    } else {
      Screen.setMessage(`Game Over`);
    }
    Screen.render();
    Screen.quit();
  }

}

module.exports = ConnectFour;
