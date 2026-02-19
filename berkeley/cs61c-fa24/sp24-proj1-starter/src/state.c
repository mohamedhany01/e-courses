#include "state.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "snake_utils.h"

/* Helper function definitions */
static void set_board_at(game_state_t *state, unsigned int row, unsigned int col, char ch);
static bool is_tail(char c);
static bool is_head(char c);
static bool is_snake(char c);
static char body_to_tail(char c);
static char head_to_body(char c);
static unsigned int get_next_row(unsigned int cur_row, char c);
static unsigned int get_next_col(unsigned int cur_col, char c);
static void find_head(game_state_t *state, unsigned int snum);
static char next_square(game_state_t *state, unsigned int snum);
static void update_tail(game_state_t *state, unsigned int snum);
static void update_head(game_state_t *state, unsigned int snum);

/* Task 1 */
game_state_t *create_default_state()
{
  const unsigned int COLS_COUNT = 20;
  const unsigned int ROWS_COUNT = 18;
  const char WALL = '#';
  const char FLOOR = ' ';

  game_state_t *gs = (game_state_t *)malloc(sizeof(game_state_t));

  if (gs == NULL)
  {
    perror("Error allocating game state");
    exit(1);
  }

  gs->num_snakes = 1;
  gs->snakes = (snake_t *)malloc(gs->num_snakes * sizeof(snake_t));

  if (gs->snakes == NULL)
  {
    perror("Error allocating a snake");
    exit(1);
  }

  gs->num_rows = ROWS_COUNT;

  gs->board = (char **)malloc(gs->num_rows * sizeof(char *));

  if (gs->board == NULL)
  {
    perror("Error allocating game state");
    exit(1);
  }

  for (size_t i = 0; i < gs->num_rows; i++)
  {
    gs->board[i] = (char *)malloc((COLS_COUNT + 1) * sizeof(char));

    if (gs->board[i] == NULL)
    {
      perror("Error allocating row");

      for (size_t j = 0; j < i; j++)
      {
        free(gs->board[j]);
      }

      free(gs->board);
      free(gs->snakes);
      free(gs);

      exit(1);
    }

    for (size_t j = 0; j < COLS_COUNT; j++)
    {
      bool isWall = (((i >= 1 && i <= COLS_COUNT - 2) && (j == 0 || j == COLS_COUNT - 1)) ||
                     i == 0 ||
                     i == gs->num_rows - 1);
      if (isWall)
      {
        gs->board[i][j] = WALL;
      }
      else
      {
        gs->board[i][j] = FLOOR;
      }
    }

    gs->board[i][COLS_COUNT] = '\0';
  }

  // Hardcoded snake
  gs->board[2][2] = 'd';
  gs->board[2][3] = '>';
  gs->board[2][4] = 'D';
  gs->board[2][9] = '*';

  gs->snakes[0].head_row = 2;
  gs->snakes[0].head_col = 4;

  gs->snakes[0].tail_row = 2;
  gs->snakes[0].tail_col = 2;

  gs->snakes[0].live = true;

  return gs;
}

/* Task 2 */
void free_state(game_state_t *state)
{
  if (state == NULL)
    return;

  for (size_t i = 0; i < state->num_rows; i++)
  {
    free(state->board[i]);
  }
  free(state->snakes);
  free(state);
}

/* Task 3 */
void print_board(game_state_t *state, FILE *fp) {
  // TODO: Implement this function.
  return;
}

/*
  Saves the current state into filename. Does not modify the state object.
  (already implemented for you).
*/
void save_board(game_state_t *state, char *filename) {
  FILE *f = fopen(filename, "w");
  print_board(state, f);
  fclose(f);
}

/* Task 4.1 */

/*
  Helper function to get a character from the board
  (already implemented for you).
*/
char get_board_at(game_state_t *state, unsigned int row, unsigned int col) { return state->board[row][col]; }

/*
  Helper function to set a character on the board
  (already implemented for you).
*/
static void set_board_at(game_state_t *state, unsigned int row, unsigned int col, char ch) {
  state->board[row][col] = ch;
}

/*
  Returns true if c is part of the snake's tail.
  The snake consists of these characters: "wasd"
  Returns false otherwise.
*/
static bool is_tail(char c) {
  // TODO: Implement this function.
  return true;
}

/*
  Returns true if c is part of the snake's head.
  The snake consists of these characters: "WASDx"
  Returns false otherwise.
*/
static bool is_head(char c) {
  // TODO: Implement this function.
  return true;
}

/*
  Returns true if c is part of the snake.
  The snake consists of these characters: "wasd^<v>WASDx"
*/
static bool is_snake(char c) {
  // TODO: Implement this function.
  return true;
}

/*
  Converts a character in the snake's body ("^<v>")
  to the matching character representing the snake's
  tail ("wasd").
*/
static char body_to_tail(char c) {
  // TODO: Implement this function.
  return '?';
}

/*
  Converts a character in the snake's head ("WASD")
  to the matching character representing the snake's
  body ("^<v>").
*/
static char head_to_body(char c) {
  // TODO: Implement this function.
  return '?';
}

/*
  Returns cur_row + 1 if c is 'v' or 's' or 'S'.
  Returns cur_row - 1 if c is '^' or 'w' or 'W'.
  Returns cur_row otherwise.
*/
static unsigned int get_next_row(unsigned int cur_row, char c) {
  // TODO: Implement this function.
  return cur_row;
}

/*
  Returns cur_col + 1 if c is '>' or 'd' or 'D'.
  Returns cur_col - 1 if c is '<' or 'a' or 'A'.
  Returns cur_col otherwise.
*/
static unsigned int get_next_col(unsigned int cur_col, char c) {
  // TODO: Implement this function.
  return cur_col;
}

/*
  Task 4.2

  Helper function for update_state. Return the character in the cell the snake is moving into.

  This function should not modify anything.
*/
static char next_square(game_state_t *state, unsigned int snum) {
  // TODO: Implement this function.
  return '?';
}

/*
  Task 4.3

  Helper function for update_state. Update the head...

  ...on the board: add a character where the snake is moving

  ...in the snake struct: update the row and col of the head

  Note that this function ignores food, walls, and snake bodies when moving the head.
*/
static void update_head(game_state_t *state, unsigned int snum) {
  // TODO: Implement this function.
  return;
}

/*
  Task 4.4

  Helper function for update_state. Update the tail...

  ...on the board: blank out the current tail, and change the new
  tail from a body character (^<v>) into a tail character (wasd)

  ...in the snake struct: update the row and col of the tail
*/
static void update_tail(game_state_t *state, unsigned int snum) {
  // TODO: Implement this function.
  return;
}

/* Task 4.5 */
void update_state(game_state_t *state, int (*add_food)(game_state_t *state)) {
  // TODO: Implement this function.
  return;
}

/* Task 5.1 */
char *read_line(FILE *fp) {
  // TODO: Implement this function.
  return NULL;
}

/* Task 5.2 */
game_state_t *load_board(FILE *fp) {
  // TODO: Implement this function.
  return NULL;
}

/*
  Task 6.1

  Helper function for initialize_snakes.
  Given a snake struct with the tail row and col filled in,
  trace through the board to find the head row and col, and
  fill in the head row and col in the struct.
*/
static void find_head(game_state_t *state, unsigned int snum) {
  // TODO: Implement this function.
  return;
}

/* Task 6.2 */
game_state_t *initialize_snakes(game_state_t *state) {
  // TODO: Implement this function.
  return NULL;
}
