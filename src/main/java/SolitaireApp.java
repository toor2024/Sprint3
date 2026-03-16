import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * JavaFX GUI for Peg Solitaire. This class handles only the user interface
 * and delegates all game logic to the GameLogic class.
 */
public class SolitaireApp extends Application {

    private GameLogic gameLogic;
    private GridPane boardGrid;
    private TextField sizeField;
    private ToggleGroup typeGroup;
    private Label statusLabel;
    private int selectedRow = -1;
    private int selectedCol = -1;

    @Override
    public void start(Stage stage) {
        gameLogic = new GameLogic();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));

        // Top: Board size input
        HBox topBox = new HBox(10);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setPadding(new Insets(0, 0, 10, 0));
        Label sizeLabel = new Label("Board size");
        sizeField = new TextField("7");
        sizeField.setPrefWidth(50);
        topBox.getChildren().addAll(sizeLabel, sizeField);
        root.setTop(topBox);

        // Left: Board type radio buttons
        VBox leftBox = new VBox(10);
        leftBox.setPadding(new Insets(0, 15, 0, 0));
        Label typeLabel = new Label("Board Type");
        typeGroup = new ToggleGroup();

        RadioButton englishBtn = new RadioButton("English");
        englishBtn.setToggleGroup(typeGroup);
        englishBtn.setSelected(true);
        englishBtn.setUserData(BoardType.ENGLISH);

        RadioButton hexagonBtn = new RadioButton("Hexagon");
        hexagonBtn.setToggleGroup(typeGroup);
        hexagonBtn.setUserData(BoardType.HEXAGON);

        RadioButton diamondBtn = new RadioButton("Diamond");
        diamondBtn.setToggleGroup(typeGroup);
        diamondBtn.setUserData(BoardType.DIAMOND);

        leftBox.getChildren().addAll(typeLabel, englishBtn, hexagonBtn, diamondBtn);
        root.setLeft(leftBox);

        // Center: Board display
        boardGrid = new GridPane();
        boardGrid.setAlignment(Pos.CENTER);
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);
        root.setCenter(boardGrid);

        // Right: New Game button
        VBox rightBox = new VBox(10);
        rightBox.setPadding(new Insets(0, 0, 0, 15));
        rightBox.setAlignment(Pos.TOP_CENTER);
        Button newGameBtn = new Button("New Game");
        newGameBtn.setOnAction(e -> startNewGame());
        rightBox.getChildren().add(newGameBtn);
        root.setRight(rightBox);

        // Bottom: Status message
        statusLabel = new Label("Select board size and type, then click New Game.");
        statusLabel.setPadding(new Insets(10, 0, 0, 0));
        root.setBottom(statusLabel);

        Scene scene = new Scene(root, 620, 580);
        stage.setTitle("Peg Solitaire");
        stage.setScene(scene);
        stage.show();
    }

    private void startNewGame() {
        int size;
        try {
            size = Integer.parseInt(sizeField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid board size. Enter an odd number >= 5.");
            return;
        }

        BoardType type = (BoardType) typeGroup.getSelectedToggle().getUserData();

        if (!Board.isValidSize(size, type)) {
            statusLabel.setText("Invalid board size. Enter an odd number >= 5.");
            return;
        }

        gameLogic.newGame(size, type);
        selectedRow = -1;
        selectedCol = -1;
        updateBoard();
        statusLabel.setText("Game started! Click a peg to select, then click a hole to move.");
    }

    private void updateBoard() {
        boardGrid.getChildren().clear();
        Board board = gameLogic.getBoard();
        if (board == null) return;

        int size = board.getSize();
        double cellSize = Math.min(400.0 / size, 45);
        double radius = cellSize * 0.38;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                int state = board.getCellState(r, c);
                if (state == Board.INVALID) {
                    Region spacer = new Region();
                    spacer.setPrefSize(cellSize, cellSize);
                    boardGrid.add(spacer, c, r);
                    continue;
                }

                StackPane cell = new StackPane();
                cell.setPrefSize(cellSize, cellSize);

                Circle circle = new Circle(radius);
                if (state == Board.PEG) {
                    if (r == selectedRow && c == selectedCol) {
                        circle.setFill(Color.RED);
                    } else {
                        circle.setFill(Color.DARKBLUE);
                    }
                    circle.setStroke(Color.BLACK);
                } else {
                    circle.setFill(Color.WHITE);
                    circle.setStroke(Color.GRAY);
                }

                final int row = r;
                final int col = c;
                cell.setOnMouseClicked(e -> handleCellClick(row, col));

                cell.getChildren().add(circle);
                boardGrid.add(cell, c, r);
            }
        }
    }

    private void handleCellClick(int row, int col) {
        if (gameLogic.isGameOver()) {
            statusLabel.setText("Game over! Click New Game to play again.");
            return;
        }

        Board board = gameLogic.getBoard();
        if (board == null) return;

        int state = board.getCellState(row, col);

        if (selectedRow == -1) {
            if (state == Board.PEG) {
                selectedRow = row;
                selectedCol = col;
                updateBoard();
                statusLabel.setText("Peg selected at (" + row + "," + col
                    + "). Click a hole to move.");
            }
        } else {
            if (state == Board.PEG) {
                selectedRow = row;
                selectedCol = col;
                updateBoard();
                statusLabel.setText("Peg selected at (" + row + "," + col
                    + "). Click a hole to move.");
            } else if (state == Board.EMPTY) {
                if (gameLogic.makeMove(selectedRow, selectedCol, row, col)) {
                    selectedRow = -1;
                    selectedCol = -1;
                    updateBoard();
                    if (gameLogic.isGameOver()) {
                        if (gameLogic.hasWon()) {
                            statusLabel.setText("You won! Rating: "
                                + gameLogic.getRating());
                        } else {
                            statusLabel.setText("Game over. No more moves. Pegs left: "
                                + board.getPegCount() + ". Rating: "
                                + gameLogic.getRating());
                        }
                    } else {
                        statusLabel.setText("Move applied. Pegs remaining: "
                            + board.getPegCount());
                    }
                } else {
                    statusLabel.setText("Invalid move. Select a peg and jump to a hole.");
                    selectedRow = -1;
                    selectedCol = -1;
                    updateBoard();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
