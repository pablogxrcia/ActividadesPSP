package com.example.cronometromejoradoo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

public class ContadorTiempo extends Application {

    private TextField inputField;
    private ProgressBar progressBar;
    private Label tiempoLabel;
    private Button iniciarButton;
    private Button cancelarButton;
    private VBox root;
    private boolean temaOscuro = false; // Estado del tema
    private int tiempoTotal;
    private int tiempoActual;
    private boolean contando;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Contador de Tiempo");

        root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Label instruccionLabel = new Label("Introduce el tiempo en segundos:");
        inputField = new TextField();
        progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(200);
        tiempoLabel = new Label("Tiempo: 00:00:00");
        iniciarButton = new Button("Iniciar");
        cancelarButton = new Button("Cancelar");
        cancelarButton.setDisable(true);
        Button temaButton = new Button("Cambiar Tema");

        // Añadir el botón de cambio de tema
        temaButton.setOnAction(e -> cambiarTema());

        root.getChildren().addAll(instruccionLabel, inputField, progressBar, tiempoLabel, iniciarButton, cancelarButton, temaButton);

        iniciarButton.setOnAction(e -> iniciarContador());
        cancelarButton.setOnAction(e -> cancelarContador());

        Scene scene = new Scene(root, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

        aplicarTemaClaro(); // Iniciar con tema claro
    }

    private void iniciarContador() {
        try {
            tiempoTotal = Integer.parseInt(inputField.getText());
            if (tiempoTotal <= 0) {
                throw new NumberFormatException();
            }
            tiempoActual = 0;
            contando = true;
            progressBar.setProgress(0);
            iniciarButton.setDisable(true);
            cancelarButton.setDisable(false);
            inputField.setDisable(true);

            new Thread(() -> {
                while (contando && tiempoActual < tiempoTotal) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        break;
                    }
                    tiempoActual++;
                    Platform.runLater(this::actualizarUI);
                }
                if (tiempoActual >= tiempoTotal) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Tiempo completado");
                        alert.setHeaderText(null);
                        alert.setContentText("¡El tiempo ha finalizado!");
                        alert.showAndWait();
                        reiniciarUI();
                    });
                }
            }).start();
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, introduce un número válido mayor que cero.");
            alert.showAndWait();
        }
    }

    private void cancelarContador() {
        contando = false;
        reiniciarUI();
    }

    private void actualizarUI() {
        int horas = tiempoActual / 3600;
        int minutos = (tiempoActual % 3600) / 60;
        int segundos = tiempoActual % 60;
        double progreso = (double) tiempoActual / tiempoTotal;

        progressBar.setProgress(progreso);
        tiempoLabel.setText(String.format("Tiempo: %02d:%02d:%02d", horas, minutos, segundos));
    }

    private void reiniciarUI() {
        iniciarButton.setDisable(false);
        cancelarButton.setDisable(true);
        inputField.setDisable(false);
        progressBar.setProgress(0);
        tiempoLabel.setText("Tiempo: 00:00:00");
    }

    // Método para cambiar entre temas
    private void cambiarTema() {
        if (temaOscuro) {
            aplicarTemaClaro();
        } else {
            aplicarTemaOscuro();
        }
        temaOscuro = !temaOscuro;
    }

    // Métodos para aplicar tema claro
    private void aplicarTemaClaro() {
        root.setStyle("-fx-background-color: white;");
        tiempoLabel.setStyle("-fx-text-fill: black;");
        inputField.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        iniciarButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
        cancelarButton.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
    }

    // Métodos para aplicar tema oscuro
    private void aplicarTemaOscuro() {
        root.setStyle("-fx-background-color: #2b2b2b;");
        tiempoLabel.setStyle("-fx-text-fill: white;");
        inputField.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        iniciarButton.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
        cancelarButton.setStyle("-fx-background-color: #444444; -fx-text-fill: white;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
