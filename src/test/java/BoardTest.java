import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void englishBoardSize7_hasCorrectPegCount() {
        Board board = new Board(7, BoardType.ENGLISH);
        // 7x7=49, minus 4 corners of 2x2 (16 cells) = 33 valid, minus 1 center = 32
        assertEquals(32, board.getPegCount());
    }

    @Test
    void englishBoardSize7_centerIsEmpty() {
        Board board = new Board(7, BoardType.ENGLISH);
        assertEquals(Board.EMPTY, board.getCellState(3, 3));
    }

    @Test
    void englishBoardSize7_cornersAreInvalid() {
        Board board = new Board(7, BoardType.ENGLISH);
        assertEquals(Board.INVALID, board.getCellState(0, 0));
        assertEquals(Board.INVALID, board.getCellState(0, 1));
        assertEquals(Board.INVALID, board.getCellState(1, 0));
        assertEquals(Board.INVALID, board.getCellState(1, 1));
        assertEquals(Board.INVALID, board.getCellState(0, 5));
        assertEquals(Board.INVALID, board.getCellState(0, 6));
        assertEquals(Board.INVALID, board.getCellState(5, 0));
        assertEquals(Board.INVALID, board.getCellState(6, 6));
    }

    @Test
    void englishBoardSize5_hasCorrectPegCount() {
        Board board = new Board(5, BoardType.ENGLISH);
        // 5x5=25, minus 4 corner cells (1x1 each) = 21 valid, minus 1 center = 20
        assertEquals(20, board.getPegCount());
    }

    @Test
    void diamondBoardSize5_hasCorrectPegCount() {
        Board board = new Board(5, BoardType.DIAMOND);
        // Diamond: |r-2|+|c-2|<=2 gives 13 valid cells, minus 1 center = 12
        assertEquals(12, board.getPegCount());
    }

    @Test
    void diamondBoardSize7_centerIsEmpty() {
        Board board = new Board(7, BoardType.DIAMOND);
        assertEquals(Board.EMPTY, board.getCellState(3, 3));
    }

    @Test
    void diamondBoardSize5_cornersAreInvalid() {
        Board board = new Board(5, BoardType.DIAMOND);
        assertEquals(Board.INVALID, board.getCellState(0, 0));
        assertEquals(Board.INVALID, board.getCellState(0, 1));
        assertEquals(Board.INVALID, board.getCellState(4, 4));
    }

    @Test
    void hexagonBoardSize7_hasCorrectPegCount() {
        Board board = new Board(7, BoardType.HEXAGON);
        // Hex with cornerThreshold=2: 12 invalid cells, 49-12=37 valid, minus 1 = 36
        assertEquals(36, board.getPegCount());
    }

    @Test
    void hexagonBoardSize7_cornersAreInvalid() {
        Board board = new Board(7, BoardType.HEXAGON);
        assertEquals(Board.INVALID, board.getCellState(0, 0));
        assertEquals(Board.INVALID, board.getCellState(0, 6));
        assertEquals(Board.INVALID, board.getCellState(6, 0));
        assertEquals(Board.INVALID, board.getCellState(6, 6));
    }

    @Test
    void invalidSize_throwsException() {
        assertThrows(IllegalArgumentException.class,
            () -> new Board(4, BoardType.ENGLISH));
        assertThrows(IllegalArgumentException.class,
            () -> new Board(3, BoardType.ENGLISH));
    }

    @Test
    void boardCopy_isIndependent() {
        Board original = new Board(7, BoardType.ENGLISH);
        Board copy = original.copy();
        copy.setCellState(3, 3, Board.PEG);
        assertEquals(Board.EMPTY, original.getCellState(3, 3));
        assertEquals(Board.PEG, copy.getCellState(3, 3));
    }

    @Test
    void isValidSize_rejectsEvenNumbers() {
        assertFalse(Board.isValidSize(6, BoardType.ENGLISH));
        assertFalse(Board.isValidSize(8, BoardType.DIAMOND));
    }

    @Test
    void isValidSize_rejectsTooSmall() {
        assertFalse(Board.isValidSize(3, BoardType.ENGLISH));
        assertFalse(Board.isValidSize(1, BoardType.DIAMOND));
    }

    @Test
    void isValidSize_acceptsValidSizes() {
        assertTrue(Board.isValidSize(5, BoardType.ENGLISH));
        assertTrue(Board.isValidSize(7, BoardType.ENGLISH));
        assertTrue(Board.isValidSize(9, BoardType.HEXAGON));
        assertTrue(Board.isValidSize(5, BoardType.DIAMOND));
    }

    @Test
    void getCellState_outOfBounds_returnsInvalid() {
        Board board = new Board(7, BoardType.ENGLISH);
        assertEquals(Board.INVALID, board.getCellState(-1, 0));
        assertEquals(Board.INVALID, board.getCellState(0, -1));
        assertEquals(Board.INVALID, board.getCellState(7, 0));
        assertEquals(Board.INVALID, board.getCellState(0, 7));
    }

    @Test
    void englishBoardSize9_hasCorrectPegCount() {
        Board board = new Board(9, BoardType.ENGLISH);
        // 9x9=81, minus 4 corners of 3x3 (36 cells) = 45 valid, minus 1 center = 44
        assertEquals(44, board.getPegCount());
    }
}
