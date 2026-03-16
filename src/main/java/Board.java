/**
 * Represents the Solitaire game board.
 * Manages the grid state for English, Hexagon, and Diamond board types.
 * Cell values: -1 = invalid (no cell), 0 = empty hole, 1 = peg.
 */
public class Board {

    public static final int INVALID = -1;
    public static final int EMPTY = 0;
    public static final int PEG = 1;

    private final int[][] grid;
    private final int size;
    private final BoardType type;

    public Board(int size, BoardType type) {
        if (!isValidSize(size, type)) {
            throw new IllegalArgumentException(
                "Invalid board size: " + size + " for type: " + type);
        }
        this.size = size;
        this.type = type;
        this.grid = new int[size][size];
        initializeBoard();
    }

    private Board(int[][] grid, int size, BoardType type) {
        this.size = size;
        this.type = type;
        this.grid = new int[size][size];
        for (int r = 0; r < size; r++) {
            System.arraycopy(grid[r], 0, this.grid[r], 0, size);
        }
    }

    private void initializeBoard() {
        int center = size / 2;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (isBoardPosition(r, c)) {
                    grid[r][c] = PEG;
                } else {
                    grid[r][c] = INVALID;
                }
            }
        }
        grid[center][center] = EMPTY;
    }

    private boolean isBoardPosition(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return false;
        }
        int center = size / 2;
        switch (type) {
            case ENGLISH:
                return isEnglishPosition(row, col);
            case DIAMOND:
                return isDiamondPosition(row, col, center);
            case HEXAGON:
                return isHexagonPosition(row, col);
            default:
                return false;
        }
    }

    private boolean isEnglishPosition(int row, int col) {
        int cornerSize = (size - 3) / 2;
        if (row < cornerSize && col < cornerSize) return false;
        if (row < cornerSize && col >= size - cornerSize) return false;
        if (row >= size - cornerSize && col < cornerSize) return false;
        if (row >= size - cornerSize && col >= size - cornerSize) return false;
        return true;
    }

    private boolean isDiamondPosition(int row, int col, int center) {
        return Math.abs(row - center) + Math.abs(col - center) <= center;
    }

    private boolean isHexagonPosition(int row, int col) {
        int cornerThreshold = size / 3;
        if (row + col < cornerThreshold) return false;
        if (row + (size - 1 - col) < cornerThreshold) return false;
        if ((size - 1 - row) + col < cornerThreshold) return false;
        if ((size - 1 - row) + (size - 1 - col) < cornerThreshold) return false;
        return true;
    }

    public int getCellState(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return INVALID;
        }
        return grid[row][col];
    }

    public void setCellState(int row, int col, int state) {
        grid[row][col] = state;
    }

    public boolean isValidCell(int row, int col) {
        return getCellState(row, col) != INVALID;
    }

    public int getPegCount() {
        int count = 0;
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (grid[r][c] == PEG) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getSize() {
        return size;
    }

    public BoardType getType() {
        return type;
    }

    public Board copy() {
        return new Board(grid, size, type);
    }

    public static boolean isValidSize(int size, BoardType type) {
        return size >= 5 && size % 2 != 0;
    }
}
