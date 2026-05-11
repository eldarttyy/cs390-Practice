import javafx.application.Application;
import javafx.application.Platform;
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
    
    // Function to integrate: sin(x)
    private double f(double x) {
        return Math.sin(x);
    }

    private VBox buildUi() {
        VBox root = new VBox();
        root.setPadding(new Insets(10, 10, 10, 10));

        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10, 0, 10, 0));
        Button startButton = new Button("Start");
        TextField inputField = new TextField();
        inputField.setPromptText("upper limit (b), default PI");
        inputField.setPrefWidth(120);
        TextField resultField = new TextField();
        resultField.setPrefWidth(160);
        resultField.setEditable(false);
        resultField.setPromptText("result");

        topBox.getChildren().addAll(startButton, inputField, resultField);

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
        // Build UI and keep references to controls
        VBox root = buildUi();
        // Find controls (simple lookup by traversal)
        Button startButton = (Button) ((HBox) root.getChildren().get(0)).getChildren().get(0);
        TextField inputField = (TextField) ((HBox) root.getChildren().get(0)).getChildren().get(1);
        TextField resultField = (TextField) ((HBox) root.getChildren().get(0)).getChildren().get(2);

        startButton.setOnAction(evt -> {
            startButton.setDisable(true);
            progressBar.setProgress(0);
            resultField.setText("");

            Thread worker = new Thread(() -> {
                // Integration limits
                double a = 0.0;
                double b = Math.PI;
                String in = inputField.getText();
                if (in != null && !in.isBlank()) {
                    try {
                        b = Double.parseDouble(in.trim());
                    } catch (NumberFormatException ex) {
                        final String msg = "invalid input";
                        Platform.runLater(() -> {
                            resultField.setText(msg);
                            progressBar.setProgress(0);
                            startButton.setDisable(false);
                        });
                        return;
                    }
                }
                // number of subdivisions (large to take time)
                int n = 1 << 22; // about 4 million
                double h = (b - a) / n;
                double result = (f(a) + f(b)) / 2.0;

                int updateChunk = Math.max(1, n / 200); // ~200 UI updates
                for (int i = 1; i < n; i++) {
                    double x = a + i * h;
                    result += f(x);

                    if (i % updateChunk == 0) {
                        final double progress = i / (double) n;
                        Platform.runLater(() -> progressBar.setProgress(progress));
                    }
                }

                result = result * h;

                final String out = String.format("%.10f", result);
                Platform.runLater(() -> {
                    resultField.setText(out);
                    progressBar.setProgress(1.0);
                    startButton.setDisable(false);
                });
            });
            worker.setDaemon(true);
            worker.start();
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Integration UI");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
