import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntegrationUi extends Application {

    private ProgressBar progressBar;

    private VBox buildUi() {
        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));

        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10, 0, 10, 0));
        Button startButton = new Button("Start");
        TextField resultField = new TextField();
        resultField.setPrefWidth(200);

        topBox.getChildren().addAll(startButton, resultField);

        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER);
        progressBar = new ProgressBar();
        progressBar.setProgress(0);

        bottomBox.getChildren().add(progressBar);

        root.getChildren().addAll(topBox, bottomBox);
        return root;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(buildUi());
        stage.setScene(scene);
        stage.setTitle("Integration UI");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
