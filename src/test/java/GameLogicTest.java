import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class GameLogicTest {

    private GameLogic logic;

    @BeforeEach
    void setUp() {
        logic = new GameLogic();
    }

    @Test
    void newGame_createsBoard() {
        logic.newGame(7, BoardType.ENGLISH);
        assertNotNull(logic.getBoard());
        assertFalse(logic.isGameOver());
        assertFalse(logic.hasWon());
    }

    @Test
    void validMove_isAccepted() {
        logic.newGame(7, BoardType.ENGLISH);
        // (1,3) has peg, (2,3) has peg, (3,3) is empty → valid jump
        assertTrue(logic.isValidMove(1, 3, 3, 3));
    }

    @Test
    void invalidMove_toOccupiedCell_isRejected() {
        logic.newGame(7, BoardType.ENGLISH);
        // (0,2) peg → (2,2) peg: destination occupied
        assertFalse(logic.isValidMove(0, 2, 2, 2));
    }

    @Test
    void invalidMove_wrongDistance_isRejected() {
        logic.newGame(7, BoardType.ENGLISH);
        // Distance of 1, not 2
        assertFalse(logic.isValidMove(2, 3, 3, 3));
    }

    @Test
    void invalidMove_diagonal_isRejected() {
        logic.newGame(7, BoardType.ENGLISH);
        // Diagonal moves are not allowed
        assertFalse(logic.isValidMove(2, 2, 4, 4));
    }

    @Test
    void invalidMove_fromEmptyCell_isRejected() {
        logic.newGame(7, BoardType.ENGLISH);
        // Center (3,3) is empty
        assertFalse(logic.isValidMove(3, 3, 5, 3));
    }

    @Test
    void makeMove_updatesBoardCorrectly() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        // Move (1,3) → (3,3) jumping over (2,3)
        assertTrue(logic.makeMove(1, 3, 3, 3));
        assertEquals(Board.EMPTY, board.getCellState(1, 3));
        assertEquals(Board.EMPTY, board.getCellState(2, 3));
        assertEquals(Board.PEG, board.getCellState(3, 3));
    }

    @Test
    void makeMove_reducesPegCount() {
        logic.newGame(7, BoardType.ENGLISH);
        int before = logic.getBoard().getPegCount();
        logic.makeMove(1, 3, 3, 3);
        assertEquals(before - 1, logic.getBoard().getPegCount());
    }

    @Test
    void invalidMove_isNotExecuted() {
        logic.newGame(7, BoardType.ENGLISH);
        int before = logic.getBoard().getPegCount();
        assertFalse(logic.makeMove(0, 0, 2, 2));
        assertEquals(before, logic.getBoard().getPegCount());
    }

    @Test
    void getValidMoves_returnsMovesAtStart() {
        logic.newGame(7, BoardType.ENGLISH);
        List<Move> moves = logic.getValidMoves();
        assertFalse(moves.isEmpty());
    }

    @Test
    void gameOver_winDetectedWith1PegLeft() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        int size = board.getSize();
        // Clear all pegs
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.getCellState(r, c) == Board.PEG) {
                    board.setCellState(r, c, Board.EMPTY);
                }
            }
        }
        // Set up: peg at (2,3), peg at (3,3), empty at (4,3)
        // After move, 1 peg remains → win
        board.setCellState(2, 3, Board.PEG);
        board.setCellState(3, 3, Board.PEG);
        board.setCellState(4, 3, Board.EMPTY);

        logic.makeMove(2, 3, 4, 3);
        assertTrue(logic.isGameOver());
        assertTrue(logic.hasWon());
    }

    @Test
    void gameOver_lossDetectedWithMultiplePegsNoMoves() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        int size = board.getSize();
        // Clear all pegs
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.getCellState(r, c) == Board.PEG) {
                    board.setCellState(r, c, Board.EMPTY);
                }
            }
        }
        // Set up: peg at (2,3), peg at (3,3), empty at (4,3)
        // Also an isolated peg at (0,2) → after move, 2 pegs remain, no moves
        board.setCellState(2, 3, Board.PEG);
        board.setCellState(3, 3, Board.PEG);
        board.setCellState(4, 3, Board.EMPTY);
        board.setCellState(0, 2, Board.PEG);

        logic.makeMove(2, 3, 4, 3);
        assertTrue(logic.isGameOver());
        assertFalse(logic.hasWon());
    }

    @Test
    void getRating_outstanding() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        clearBoard(board);
        board.setCellState(3, 3, Board.PEG);
        assertEquals("Outstanding", logic.getRating());
    }

    @Test
    void getRating_veryGood() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        clearBoard(board);
        board.setCellState(3, 3, Board.PEG);
        board.setCellState(0, 2, Board.PEG);
        assertEquals("Very Good", logic.getRating());
    }

    @Test
    void getRating_good() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        clearBoard(board);
        board.setCellState(3, 3, Board.PEG);
        board.setCellState(0, 2, Board.PEG);
        board.setCellState(0, 3, Board.PEG);
        assertEquals("Good", logic.getRating());
    }

    @Test
    void getRating_average() {
        logic.newGame(7, BoardType.ENGLISH);
        Board board = logic.getBoard();
        clearBoard(board);
        board.setCellState(3, 3, Board.PEG);
        board.setCellState(0, 2, Board.PEG);
        board.setCellState(0, 3, Board.PEG);
        board.setCellState(0, 4, Board.PEG);
        assertEquals("Average", logic.getRating());
    }

    @Test
    void diamondBoard_validMoveWorks() {
        logic.newGame(5, BoardType.DIAMOND);
        // Center (2,2) is empty; (0,2) peg, (1,2) peg → valid jump
        assertTrue(logic.isValidMove(0, 2, 2, 2));
        assertTrue(logic.makeMove(0, 2, 2, 2));
    }

    @Test
    void hexagonBoard_initializesCorrectly() {
        logic.newGame(7, BoardType.HEXAGON);
        Board board = logic.getBoard();
        assertEquals(36, board.getPegCount());
        assertEquals(Board.EMPTY, board.getCellState(3, 3));
    }

    @Test
    void newGame_resetsGameState() {
        logic.newGame(7, BoardType.ENGLISH);
        logic.makeMove(1, 3, 3, 3);
        int pegsAfterMove = logic.getBoard().getPegCount();
        // Start a new game; board should be fresh
        logic.newGame(7, BoardType.ENGLISH);
        assertEquals(32, logic.getBoard().getPegCount());
        assertFalse(logic.isGameOver());
    }

    private void clearBoard(Board board) {
        int size = board.getSize();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (board.getCellState(r, c) == Board.PEG) {
                    board.setCellState(r, c, Board.EMPTY);
                }
            }
        }
    }
}
