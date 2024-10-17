package game2048;

import java.util.Formatter;
import java.util.Iterator;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author StreamAzure
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

        board.setViewingPerspective(side);

        int scoreSum = 0;
        // 默认为向上移动
        // 注意默认棋盘方向下，最底下一行 row = 0，最右侧一列 col = 3
        for (int col = 0; col < board.size(); col ++){
            int res = handleCol(col);
            if (res != -1){
                changed = true;
            }
            if (res > 1) {
                scoreSum += res;
            }
        }
        board.setViewingPerspective(Side.NORTH);
        score += scoreSum;

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    private int handleCol(int col) {
        int colScore = 0;
        int changed = -1;
        boolean[] merged = {false, false, false, false};
        for (int i = board.size() - 1; i >= 0; i--) {
            // 从最顶上的 tile 开始向下考虑
            Tile nowTile = board.tile(col, i);
            if (nowTile == null) {
                continue;
            }
            int nearestTile = getNearestTile(i, col);
            if (nearestTile == -1 && i != board.size() - 1) {
                // 不是最顶上的方块，且上面没有方块，直接移动到最顶上
                board.move(col, board.size() - 1, nowTile);
                changed = 1;
            } // 如果是最顶上的方块，不考虑
            else if (nearestTile != -1) {
                // 上面有方块
                Tile otherTile = board.tile(col, nearestTile);
                if (otherTile.value() == nowTile.value() && !merged[nearestTile]) {
                    // 值相同，且目标位置没有发生过合并，压上去合并
                    if (board.move(col, nearestTile, nowTile)){
                        colScore += board.tile(col, nearestTile).value();
                        merged[nearestTile] = true;
                    }
                } else {
                    // 值不同，不紧邻，则中间有空位，贴在 otherTile 的下一行
                    board.move(col, nearestTile - 1, nowTile);
                }
                changed = 1;
            }
        }
        return colScore > 0 ? colScore : changed;
    }

    private int getNearestTile(int row, int col){
        // 向上方向最近的 tile 的所在行
        // 若返回 -1， 则向上方向无 tile
        for (int i = row + 1; i < board.size(); i++) {
            if (board.tile(col, i) != null) {
                return i;
            }
        }
        return -1;
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
        for (Tile tile : b) {
            if (tile == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (Tile tile : b) {
            if (tile != null && tile.value() == MAX_PIECE){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        for (Tile tile : b){
            if (tile == null) {
                return true;
            }
            int[] dx = {0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0};
            for (int i = 0; i < 4; i++){
                int nx = tile.col() + dx[i];
                int ny = tile.row() + dy[i];
                if (nx >= b.size() || ny >= b.size() || nx < 0 || ny < 0){
                    continue;
                }
                if (b.tile(nx, ny) != null){
                  if (b.tile(nx, ny ).value() == tile.value()){
                      return true;
                  }
                } else {
                    return true;
                }
            }
        }
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
