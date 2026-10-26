package controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DoctorDAO;
import model.Doctor;

public class DoctorFormController {

    @FXML
    private Button createButton;

    @FXML
    private TextField f_clinicAddress;

    @FXML
    private TextField f_nationalId;

    @FXML
    private TextField f_password;

    @FXML
    private TextField f_name;

    @FXML
    private TextField f_clinic_name;

    @FXML
    private TextField f_firstName;

    @FXML
    private TextField f_specialty;

    @FXML
    private TextField f_phone;

    @FXML
    private Button backButton;

    @FXML
    private Label statusLabel;

    @FXML
    public void goToLoginView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleCreate(ActionEvent event) throws SQLException {
        boolean hasError = false;

        if (f_name.getText().trim().isEmpty()) {
            f_name.setPromptText("Name is required");
            f_name.setStyle("-fx-prompt-text-fill: red;");
            f_name.getStyleClass().add("field-error");
            hasError = true;
        }
        if (f_firstName.getText().trim().isEmpty()) {
            f_firstName.setPromptText("First name is required");
            f_firstName.setStyle("-fx-prompt-text-fill: red;");
            f_firstName.getStyleClass().add("field-error");
            hasError = true;
        }
        if (f_specialty.getText().trim().isEmpty()) {
            f_specialty.setPromptText("Specialty is required");
            f_specialty.setStyle("-fx-prompt-text-fill: red;");
            f_specialty.getStyleClass().add("field-error");
            hasError = true;
        }
        if (f_password.getText().trim().isEmpty()) {
            f_password.setPromptText("Password is required");
            f_password.setStyle("-fx-prompt-text-fill: red;");
            f_password.getStyleClass().add("field-error");
            hasError = true;
        }
        if (f_nationalId.getText().trim().isEmpty()) {
            f_nationalId.setPromptText("National ID is required");
            f_nationalId.setStyle("-fx-prompt-text-fill: red;");
            f_nationalId.getStyleClass().add("field-error");
            hasError = true;
        }
        if (f_clinic_name.getText().trim().isEmpty()) {
            f_clinic_name.setPromptText("Clinic name is required");
            f_clinic_name.setStyle("-fx-prompt-text-fill: red;");
            f_clinic_name.getStyleClass().add("field-error");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        Doctor d = new Doctor(
                f_name.getText(),
                f_firstName.getText(),
                f_nationalId.getText(),
                f_specialty.getText(),
                f_clinic_name.getText(),
                f_phone.getText(),
                f_clinicAddress.getText(),
                f_password.getText());
        System.out.println(f_name.getText() + f_firstName.getText() + f_nationalId.getText() + f_specialty.getText()
                + f_clinic_name.getText() + f_phone.getText() + f_clinicAddress.getText() + f_password.getText());

        int etat = DoctorDAO.insert(d);

        if (etat == 1) {
            statusLabel.setStyle("-fx-opacity: 1.0; -fx-text-fill: green;");
            statusLabel.setText("Account created successfully!");

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login_view.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            pause.play();
        } else if (etat == -1) {
            statusLabel.setStyle("-fx-opacity: 1.0; -fx-text-fill: red;");
            statusLabel.setText("An account already exists with this national ID.");
        } else {
            statusLabel.setStyle("-fx-opacity: 1.0; -fx-text-fill: red;");
            statusLabel.setText("Error creating the account. Please try again.");
        }
    }
}
