import java.util.ArrayList;
import java.util.List;

/**
 * Contains all game rules and logic for Peg Solitaire, separated from the GUI.
 * Handles move validation, move execution, and game-over detection.
 */
public class GameLogic {

    private static final int[][] DIRECTIONS = {
        {-2, 0}, {2, 0}, {0, -2}, {0, 2}
    };

    private Board board;
    private boolean gameOver;
    private boolean won;

    public GameLogic() {
        this.board = null;
        this.gameOver = false;
        this.won = false;
    }

    public void newGame(int size, BoardType type) {
        this.board = new Board(size, type);
        this.gameOver = false;
        this.won = false;
    }

    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (board == null) return false;

        if (board.getCellState(fromRow, fromCol) != Board.PEG) return false;
        if (board.getCellState(toRow, toCol) != Board.EMPTY) return false;

        int dr = toRow - fromRow;
        int dc = toCol - fromCol;
        boolean validDirection = false;
        for (int[] dir : DIRECTIONS) {
            if (dir[0] == dr && dir[1] == dc) {
                validDirection = true;
                break;
            }
        }
        if (!validDirection) return false;

        int jumpedRow = (fromRow + toRow) / 2;
        int jumpedCol = (fromCol + toCol) / 2;
        if (board.getCellState(jumpedRow, jumpedCol) != Board.PEG) return false;

        return true;
    }

    public boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (!isValidMove(fromRow, fromCol, toRow, toCol)) return false;

        int jumpedRow = (fromRow + toRow) / 2;
        int jumpedCol = (fromCol + toCol) / 2;

        board.setCellState(fromRow, fromCol, Board.EMPTY);
        board.setCellState(jumpedRow, jumpedCol, Board.EMPTY);
        board.setCellState(toRow, toCol, Board.PEG);

        checkGameOver();
        return true;
    }

    public List<Move> getValidMoves() {
        List<Move> moves = new ArrayList<>();
        if (board == null) return moves;
        int size = board.getSize();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.getCellState(r, c) == Board.PEG) {
                    for (int[] dir : DIRECTIONS) {
                        int toR = r + dir[0];
                        int toC = c + dir[1];
                        if (isValidMove(r, c, toR, toC)) {
                            moves.add(new Move(r, c, toR, toC));
                        }
                    }
                }
            }
        }
        return moves;
    }

    private void checkGameOver() {
        if (board == null) return;
        if (getValidMoves().isEmpty()) {
            gameOver = true;
            won = (board.getPegCount() == 1);
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWon() {
        return won;
    }

    public Board getBoard() {
        return board;
    }

    public String getRating() {
        if (board == null) return "";
        int pegs = board.getPegCount();
        if (pegs == 1) return "Outstanding";
        if (pegs == 2) return "Very Good";
        if (pegs == 3) return "Good";
        return "Average";
    }
}
