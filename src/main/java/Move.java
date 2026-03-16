/**
 * Represents a single peg move in the Solitaire game.
 * A move consists of jumping a peg from a source position over an adjacent peg
 * into an empty hole two positions away.
 */
public class Move {

    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;
    private final int jumpedRow;
    private final int jumpedCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.jumpedRow = (fromRow + toRow) / 2;
        this.jumpedCol = (fromCol + toCol) / 2;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }

    public int getJumpedRow() {
        return jumpedRow;
    }

    public int getJumpedCol() {
        return jumpedCol;
    }
}
