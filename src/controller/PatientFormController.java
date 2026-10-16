package controller;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import model.Patient;
import model.PatientDAO;

/**
 * Provides the form used to create a new patient account.
 */
public class PatientFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField nationalIdField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button createButton;

    private PatientDAO patientDAO;

    @FXML
    public void initialize(){
        patientDAO = new PatientDAO();
    }

    @FXML
    public void addPatient() throws SQLException {
        boolean hasError = false;

        nameField.setStyle("");
        firstNameField.setStyle("");
        nationalIdField.setStyle("");
        passwordField.setStyle("");

        if (nameField.getText().trim().isEmpty()) {
            nameField.setPromptText("Name is required");
            nameField.setStyle("-fx-prompt-text-fill: red;");
            nameField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (firstNameField.getText().trim().isEmpty()) {
            firstNameField.setPromptText("First name is required");
            firstNameField.setStyle("-fx-prompt-text-fill: red;");
            firstNameField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (nationalIdField.getText().trim().isEmpty()) {
            nationalIdField.setPromptText("National ID is required");
            nationalIdField.setStyle("-fx-prompt-text-fill: red;");
            nationalIdField.getStyleClass().add("field-error");
            hasError = true;
        }
        if (passwordField.getText().trim().isEmpty()) {
            passwordField.setPromptText("Password is required");
            passwordField.setStyle("-fx-prompt-text-fill: red;");
            passwordField.getStyleClass().add("field-error");
            hasError = true;
        }

        if (hasError) {
            return;
        }

        if (patientDAO.trouverParCnie(nationalIdField.getText()) != null) {
            nationalIdField.clear();
            nationalIdField.setPromptText("National ID already in use");
            nationalIdField.setStyle("-fx-prompt-text-fill: red;");
            nationalIdField.getStyleClass().add("field-error");
            return;
        }

        Patient patient = new Patient(
            nameField.getText(),
            firstNameField.getText(),
            nationalIdField.getText(),
            passwordField.getText()
        );
        patientDAO.addPatient(patient);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Patient added successfully.");

        nameField.clear();
        firstNameField.clear();
        nationalIdField.clear();
        passwordField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToLoginView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login_view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
