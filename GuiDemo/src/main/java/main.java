import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setPadding(new Insets(20));

        // Text requirement
        Label title = new Label("GUI Demo (Sprint 0) - JavaFX");
        title.setLayoutX(20);
        title.setLayoutY(20);

        // Lines requirement
        Line line1 = new Line(20, 60, 360, 60);
        line1.setStroke(Color.BLACK);
        Line line2 = new Line(20, 60, 20, 180);
        line2.setStroke(Color.BLACK);

        // Checkbox requirement
        CheckBox cb = new CheckBox("Enable option");
        cb.setLayoutX(20);
        cb.setLayoutY(80);

        // Radio buttons requirement
        ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Choice A");
        rb1.setToggleGroup(group);
        rb1.setLayoutX(20);
        rb1.setLayoutY(120);

        RadioButton rb2 = new RadioButton("Choice B");
        rb2.setToggleGroup(group);
        rb2.setLayoutX(20);
        rb2.setLayoutY(150);

        root.getChildren().addAll(title, line1, line2, cb, rb1, rb2);

        Scene scene = new Scene(root, 400, 220);
        stage.setTitle("Sprint 0 JavaFX GUI Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
