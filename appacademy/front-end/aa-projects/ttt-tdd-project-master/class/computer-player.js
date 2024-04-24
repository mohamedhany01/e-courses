const TTT = require("./ttt");


/*
  TODO: Not yet working well

  - I need to figure out how to evaluate blocking
  - Win in all 1000 tries test case
  - Use utilities functions
*/
class ComputerPlayer {

  static HEURISTIC_SCORE = {
    X: 1, // Assuming X is the AI agent and try to maximize
    O: -1,
    T: 0
  }

  static AGENTS = {
    AI: "X", // Assuming X is the AI agent
    Player: "O"
  }

  static getValidMoves(grid) {
    let validMoves = [];
    for (let row = 0; row < grid.length; row++) {
      for (let col = 0; col < grid[row].length; col++) {
        if (grid[row][col] === ' ') {
          validMoves.push({ row, col });
        }
      }
    }
    return validMoves;
  }

  static randomMove(grid) {
    let validMoves = this.getValidMoves(grid);
    return validMoves[Math.floor(Math.random() * validMoves.length)];
  }


  static getWinningMoves(grid, symbol) {

    // Your code here

  }

  static getSmartMove(grid, symbol) {


    let currentScore = -Infinity;
    let move;
    for (let i = 0; i < 3; i++) {
      for (let j = 0; j < 3; j++) {
        if (grid[i][j] === " ") {

          grid[i][j] = symbol;

          const oppositeAgent = symbol === ComputerPlayer.AGENTS.AI ? ComputerPlayer.AGENTS.Player : ComputerPlayer.AGENTS.AI;
          let score = ComputerPlayer.minmax(grid, oppositeAgent);

          if (score > currentScore) {

            // TODO
            // Eager to block! Why
            // score = currentScore;

            // TODO
            // Eager to win! Why
            currentScore = score;

            move = { row: i, col: j };

          }
        }
      }
    }

    return move;
  }

  static minmax(grid, agent) {


    const won = TTT.checkWin(grid);

    if (won) {
      return ComputerPlayer.HEURISTIC_SCORE[won];
    }

    if (ComputerPlayer.AGENTS.AI === agent) {
      let currentScore = -Infinity;

      for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[i].length; j++) {

          if (grid[i][j] === " ") {
            grid[i][j] = ComputerPlayer.AGENTS.AI;

            let score = ComputerPlayer.minmax(grid, ComputerPlayer.AGENTS.Player);

            grid[i][j] = " ";

            currentScore = Math.max(score, currentScore);
          }
        }
      }

      return currentScore;
    } else {
      let currentScore = Infinity;

      for (let i = 0; i < grid.length; i++) {
        for (let j = 0; j < grid[i].length; j++) {

          if (grid[i][j] === " ") {
            grid[i][j] = ComputerPlayer.AGENTS.Player;

            let score = ComputerPlayer.minmax(grid, ComputerPlayer.AGENTS.AI);

            grid[i][j] = " ";

            currentScore = Math.min(score, currentScore);
          }
        }
      }

      return currentScore;
    }
  }
}

module.exports = ComputerPlayer;
