const Screen = require("./screen");
const Cursor = require("./cursor");

class TTT {

  constructor() {

    this.playerTurn = "O";

    this.grid = [[' ',' ',' '],
                 [' ',' ',' '],
                 [' ',' ',' ']]

    this.cursor = new Cursor(3, 3);

    // Initialize a 3x3 tic-tac-toe grid
    Screen.initialize(3, 3);
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


    // Check horizontal
    for (let i = 0; i < grid.length; i++) {
      let winner = grid[i][0];

      if (winner === ' ') { continue }

      let counter = 0;

      for (let j = 0; j < grid.length; j++) {
        const currentTile = grid[i][j];

        if (winner == currentTile) {
          counter++;
        }

        if (counter === 3) {
          return winner;
        }

      }
    }


    // Check vertical
    for (let i = 0; i < grid.length; i++) {
      let winner = grid[0][i];

      if (winner === ' ') { continue }

      let counter = 0;

      for (let j = 0; j < grid.length; j++) {
        const currentTile = grid[j][i];

        if (winner == currentTile) {
          counter++;
        }

        if (counter === 3) {
          return winner;
        }

      }
    }


    // Check diagonal | Left
    let winner = grid[0][0]; // A winner reference
    let counter = 1;

    for (let i = 1; i < grid.length; i++) {
      const currentTile = grid[i][i];

      if (winner === currentTile && winner !== ' ') { counter++; }

      if (counter === 3) {
        return winner;
      }
    }


    // Check diagonal | Right
    if (
      (
        grid[0][2] === grid[1][1] &&
        grid[0][2] !== ' ' &&
        grid[1][1] !== ' '
      ) && (
        grid[1][1] === grid[2][0] &&
        grid[1][1] !== ' ' &&
        grid[2][0] !== ' '
      )
    ) {

      return grid[0][2]; // We can return any tile from the three ones
    }


    // Check emptiness
    for (let i = 0; i < grid.length; i++) {
      for (let j = 0; j < grid.length; j++) {
        const currentTile = grid[i][j];

        if (currentTile === ' ') return false;

      }
    }

    return 'T'
  }

  setChar(charOnGrid) {
    const [x, y] = [this.cursor.row, this.cursor.col];

    const currentTile = Screen.grid[x][y];

    // Prevent overwrite a value (e.g. X and O)
    if (currentTile !== ' ') return;

    Screen.setGrid(x, y, charOnGrid);

    // Check status on each move on case of win
    const isWon = TTT.checkWin(Screen.grid);

    if (isWon) {
      TTT.endGame(isWon);
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

module.exports = TTT;
