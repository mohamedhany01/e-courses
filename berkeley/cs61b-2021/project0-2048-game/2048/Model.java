package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        /*
        * Game logic "Moving/Merging"

        - Start scanning the whole grid/board top-left to bottom "Loop"
		   0,3 | 1,3 | 2,3 | 3,3
		   0,2 | 1,2 | 2,2 | 3,2
		   0,1 | 1,1 | 2,1 | 3,1
		   0,0 | 1,0 | 2,0 | 3,0


		Moving Task: "First Loop"

		- Process only the "tile" with value

			- Get the "Row Indicator" which is "3" (Look at the indexed above).

			- Get the "Current Row Indicator" which is changeable

			- Scan each "tile" column by column from top-left to bottom "Loop"

				- Scan negative boundaries using "Row Indicator and Current Row Indicator" and skip them

				- Get the valid tile "inside the boundaries" and check if not "null" tile.

				- Go to the next tile from top-left to bottom

			- When out of the loop check the boundaries again for "Row Indicator" to move the current "tile" up to its above tile with "null" value.

		Merging Task: "Second Loop"

		- Get current "tile" from top-left to bottom in the board "We will check it against nulls later "

		- Check current row boundaries against negatives

		- Get the next "tile" below the previous "tile"

		- Check both of them against "nulls" before merging

		- Get both value from them "upper tile and below tile"

		- Merge if they are equal "Merging Process"

			- Move the below tile to the one in the top

			- Increase the score

			Merging The Remain "tiles" Up Process "Loop"

			- Start looping from the "tile" below

			- Get the current "tile"

			- Check it against "nulls" and skip if any

			- Check the row of current "tile" from it goes out of boundaries

			- Move current "tile" to up
        * */

        // Task #4: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        board.setViewingPerspective(side);
        final int BORDER_SIZE = board.size();
        for (int column = 0; column < BORDER_SIZE; column++) {
            for (int row = BORDER_SIZE - 1; row >= 0; row--) {
                Tile tile = board.tile(column, row);

                // Just process tile with value
                if (tile != null) {
                    int tempRowIndicator = BORDER_SIZE - 1;
                    int currentRow = row;

                    while (true) {

                        // Check if tempRowIndicator or currentRow out of boundaries
                        if (tempRowIndicator < 0 || currentRow < 0) {
                            break;
                        }

                        Tile tileInTheUpper = board.tile(column, tempRowIndicator);

                        // Break if the the row got negative or found a null tile on the top while go throw
                        if (tileInTheUpper == null) {
                            break;
                        }

                        // Move to the next row from top to down
                        tempRowIndicator--;
                    }

                    // Just in case row indicator went out of the valid board boundaries
                    if (tempRowIndicator >= row && tempRowIndicator < BORDER_SIZE && tempRowIndicator >= 0) {
                        board.move(column, tempRowIndicator, tile);
                        changed = true;
                    }
                }
            }

            for (int row = BORDER_SIZE - 1; row >= 0; row--) {
                Tile currentValidTile = board.tile(column, row);

                if (row - 1 < 0) {
                    break;
                }

                Tile nextValidTile = board.tile(column, row - 1);
                if (currentValidTile != null && nextValidTile != null) {
                    int currentTileValue = currentValidTile.value();
                    int nextTileValue = nextValidTile.value();

                    if (currentTileValue == nextTileValue) {

                        // Move bottom next tile and merge it with the upper one
                        board.move(column, row, nextValidTile);
                        score += currentValidTile.value() * 2;
                        for (int i = (nextValidTile.row() - 1); i >= 0; i--) {
                            Tile currentRestTile = board.tile(column, i);

                            if (currentRestTile == null) {
                                break;
                            }

                            if (i < BORDER_SIZE) {
                                board.move(column, i + 1, currentRestTile);
                            }
                        }
                        changed = true;
                    }

                }
            }
        }
        board.setViewingPerspective(side.NORTH);
        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
		
        // Task #1: Fill in this function.
        final int boardSize = b.size();
        final boolean emptyTile = true;
		
        /*
         * The board is scanned from bottom left
         * side using columns then rows one by one
         * */

        // Loop over the whole grid and search for an empty space
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                if (b.tile(col, row) == null) {
                    return emptyTile;
                }
            }
        }

        // Else all grid is full
        return !emptyTile;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        
		// Task #2: Fill in this function.
        final int boardSize = b.size();
        final boolean maxTile = true;

        // Loop over the whole grid and search for the max value
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                if (b.tile(col, row) != null && b.tile(col, row).value() == MAX_PIECE) {
                    return maxTile;
                }
            }
        }

        // Else no max value in the board
        return !maxTile;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // Task #3: Fill in this function.
        final int boardSize = b.size();
        final boolean emptyTile = true;
        final boolean equalTiles = true;

        // Kernels for the four directions UP, RIGHT, DOWN, LEFT
        final int[] xKernel = {
			0,
			1,
			0,
			-1
        };
        final int[] yKernel = {
			1,
			0,
			-1,
			0
        };

        // Loop over the whole grid and search for an empty space
        if (emptySpaceExists(b)) {
            return emptyTile;
        }

        /*
         * Loop over the whole grid and check the
         * four neighbors (UP, DOWN, LEFT, RIGHT)
         * for each tile in the grid/board
         * */
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {

                int currentTileValue = b.tile(col, row).value();

                // Check the boundaries for each tile in the board
                for (int fourDirectionsIndex = 0; fourDirectionsIndex < boardSize; fourDirectionsIndex++) {
                    int currentXOffset = col + xKernel[fourDirectionsIndex];
                    int currentYOffset = row + yKernel[fourDirectionsIndex];

                    // Skip board boundaries overflow by ignoring invalid offsets
                    if (currentXOffset < 0 || currentYOffset < 0 || currentXOffset > boardSize - 1 || currentYOffset > boardSize - 1) {
                        continue;
                    }

                    int currentNeighbourTileValue = b.tile(currentXOffset, currentYOffset).value();
                    if (currentTileValue == currentNeighbourTileValue) {
                        // Debugging
                        /*System.out.print("Found a valid move [" + currentTileValue + ", " + currentNeighbourTileValue + "]" + " in the indexes: ");
                        System.out.print("(" + col + ", " + row + ")" + "(" + currentXOffset + ", " + currentYOffset + ")");*/
                        return equalTiles;
                    }
                }
            }
        }

        // No valid moves
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
