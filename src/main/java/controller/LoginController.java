package main.java.controller;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import main.java.model.Doctor;
import main.java.model.DoctorDAO;
import main.java.model.Patient;
import main.java.model.PatientDAO;

/**
 * Handles authentication and navigation to doctor or patient views.
 */
public class LoginController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField f_nationalId;

    @FXML
    private PasswordField f_password;

    @FXML
    private RadioButton doctorRadioButton;

    @FXML
    private RadioButton patientRadioButton;

    @FXML
    private ToggleGroup type;

    @FXML
    private Hyperlink doctorLink;

    @FXML
    private Hyperlink patientLink;

    @FXML
    private Label label_incorrect;

    @FXML
    public void goToDoctorForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/doctor_form.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToPatientForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_form.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToPatientDashboard(ActionEvent event, Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/patient_dashboard_view.fxml"));
        Parent root = loader.load();

        PatientDashboardController controller = loader.getController();
        controller.setPatient(patient);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToDoctorDashboard(ActionEvent event, Doctor doctor) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/doctor_dashboard_view.fxml"));
        Parent root = loader.load();

        DoctorDashboardController controller = (DoctorDashboardController) loader.getController();
        controller.setDoctorName(doctor.getName());
        controller.setDoctorId(doctor.getId());
        controller.setDoctor(doctor);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLogin(ActionEvent event) throws SQLException, IOException {
        if (f_nationalId.getText().trim().isEmpty() || f_password.getText().trim().isEmpty()
                || type.getSelectedToggle() == null) {
            if (f_nationalId.getText().trim().isEmpty()) {
                f_nationalId.setPromptText("National ID is required");
                f_nationalId.setStyle("-fx-prompt-text-fill : red");
                f_nationalId.getStyleClass().add("field-error");
            }
            if (f_password.getText().trim().isEmpty()) {
                f_password.setPromptText("Password is required");
                f_password.setStyle("-fx-prompt-text-fill : red");
                f_password.getStyleClass().add("field-error");
            }
            if (type.getSelectedToggle() == null) {
                doctorRadioButton.setStyle("-fx-text-fill: red; -fx-radio-color: red;");
                patientRadioButton.setStyle("-fx-text-fill: red; -fx-radio-color: red;");
            }

        } else {
            if (doctorRadioButton.isSelected()) {
                Doctor doctor = DoctorDAO.checkDoctorByCniePassword(f_nationalId.getText(), f_password.getText());
                if (doctor != null) {
                    System.out
                            .println("Login successful: Welcome Dr." + doctor.getName() + " " + doctor.getFirstName());
                    goToDoctorDashboard(event, doctor);
                } else {
                    System.out.println("Login failed");
                    label_incorrect.setStyle("-fx-opacity : 1.0");
                }
            } else {
                Patient patient = PatientDAO.checkPatientByCniePassword(f_nationalId.getText(), f_password.getText());
                if (patient != null) {
                    System.out.println("Login successful: Welcome " + patient.getName() + " " + patient.getFirstName());
                    goToPatientDashboard(event, patient);
                } else {
                    System.out.println("Login failed");
                    label_incorrect.setStyle("-fx-opacity : 1.0");
                }
            }

        }
    }
}