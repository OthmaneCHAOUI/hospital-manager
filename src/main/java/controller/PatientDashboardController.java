package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import main.java.model.*;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Displays patient information and keeps the medical records panels
 * synchronized with the selected doctor.
 */
public class PatientDashboardController {
    @FXML
    private AnchorPane profilePane;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView profileImage;
    @FXML
    private VBox mainVBox;
    @FXML
    private MenuButton doctorMenu;
    @FXML
    private Hyperlink linkMedications;
    @FXML
    private Hyperlink linkTests;
    @FXML
    private Hyperlink linkAppointments;

    @FXML
    private TextField nameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private MenuButton genderMenu;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button saveButton;
    @FXML
    private ImageView closeIcon;

    private String selectedSexe;

    private Patient patient;
    private Doctor currentDoctor;

    public void initialize() {
        linkMedications.setOnAction(e -> loadMedicationsTable());
        linkTests.setOnAction(e -> loadTestsTable());
        linkAppointments.setOnAction(e -> loadAppointmentTable());
        profileImage.setOnMouseClicked(this::showProfileEdit);
        usernameLabel.setOnMouseClicked(this::showProfileEdit);
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        usernameLabel.setText(patient.getName() + " " + patient.getFirstName());
        try {
            for (Doctor doc : DoctorPatientDAO.getDoctorsByPatientNationalId(patient.getNationalId())) {
                MenuItem item = new MenuItem(doc.getName() + " " + doc.getFirstName());
                item.setOnAction(e -> {
                    currentDoctor = doc;
                    loadMedicationsTable();
                    doctorMenu.setText(currentDoctor.getName() + " " + currentDoctor.getFirstName());
                });
                doctorMenu.getItems().add(item);
            }
            if (!doctorMenu.getItems().isEmpty()) {
                currentDoctor = DoctorPatientDAO.getDoctorsByPatientNationalId(patient.getNationalId()).get(0);
                doctorMenu.setText(currentDoctor.getName() + " " + currentDoctor.getFirstName());
                loadMedicationsTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMedicationsTable() {
        try {
            List<Medication> list = new MedicationDAO().getMedicationsByPatientAndDoctor(patient.getNationalId(),
                    currentDoctor.getId());
            TableView<Medication> table = new TableView<>();

            TableColumn<Medication, String> colName = new TableColumn<>("Name");
            colName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));

            TableColumn<Medication, String> colDescription = new TableColumn<>("Description");
            colDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));

            TableColumn<Medication, String> colDateDebut = new TableColumn<>("Start date");
            colDateDebut.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStartDate().toString()));

            TableColumn<Medication, String> colPeriode = new TableColumn<>("Period (days)");
            colPeriode.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getDuration())));

            // table.getColumns().addAll(colName, colDescription, colDateDebut, colPeriode);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadTestsTable() {
        try {
            List<MedicalTest> list = new MedicalTestDAO().getTestsByPatientAndDoctor(patient.getNationalId(),
                    currentDoctor.getId());
            TableView<MedicalTest> table = new TableView<>();

            TableColumn<MedicalTest, String> colType = new TableColumn<>("Test type");
            colType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTestType()));

            TableColumn<MedicalTest, String> colResult = new TableColumn<>("Result");
            colResult.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getResult()));

            TableColumn<MedicalTest, String> colDate = new TableColumn<>("Test date");
            colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTestDate().toString()));

            TableColumn<MedicalTest, String> colDoctorType = new TableColumn<>("Doctor type");
            colDoctorType.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDoctorType()));

            TableColumn<MedicalTest, String> colStatus = new TableColumn<>("Status");
            colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

            // table.getColumns().addAll(colType, colResult, colDate, colDoctorType,
            // colStatus);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAppointmentTable() {
        try {
            List<Appointment> list = new AppointmentDAO().getAppointmentByPatientAndDoctor(patient.getNationalId(),
                    currentDoctor.getId());
            TableView<Appointment> table = new TableView<>();

            TableColumn<Appointment, String> colDate = new TableColumn<>("Date");
            colDate.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDateAppointment().toString()));

            TableColumn<Appointment, String> colHeure = new TableColumn<>("Time");
            colHeure.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime().toString()));

            TableColumn<Appointment, String> colRaison = new TableColumn<>("Reason");
            colRaison.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getReason()));

            TableColumn<Appointment, String> colStatus = new TableColumn<>("Status");
            colStatus.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

            // table.getColumns().addAll(colDate, colHeure, colRaison, colStatus);
            table.getItems().addAll(list);

            refreshTable(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void refreshTable(TableView<?> table) {
        mainVBox.getChildren()
                .removeIf(node -> node instanceof TableView || (node instanceof VBox && node != mainVBox));
        mainVBox.getChildren().add(table);
    }

    @FXML
    public void hideProfileEdit(MouseEvent event) {
        profilePane.setVisible(false);
        profilePane.setDisable(true);
    }

    @FXML
    public void showProfileEdit(MouseEvent event) {
        profilePane.setVisible(true);
        profilePane.setDisable(false);

        nameField.setText(patient.getName());
        firstNameField.setText(patient.getFirstName());
        cityField.setText(patient.getCity());
        emailField.setText(patient.getEmail());
        phoneField.setText(patient.getPhone());
        addressField.setText(patient.getAddress());
        birthDatePicker.setValue(patient.getDateNaissance());
        selectedSexe = patient.getGender();
        genderMenu.setText(selectedSexe);
        passwordField.setText(patient.getPassword());

        saveButton.setOnAction(e -> {
            patient.setName(nameField.getText());
            patient.setFirstName(firstNameField.getText());
            patient.setCity(cityField.getText());
            patient.setEmail(emailField.getText());
            patient.setPhone(phoneField.getText());
            patient.setAddress(addressField.getText());
            patient.setDateNaissance(birthDatePicker.getValue());
            patient.setGender(selectedSexe);
            patient.setPassword(passwordField.getText());

            new PatientDAO().updatePatient(patient);
            usernameLabel.setText(patient.getName());
            profilePane.setDisable(true);
            profilePane.setVisible(false);
            loadMedicationsTable();
        });

    }

    @FXML
    public void handleGenderSelection(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        selectedSexe = item.getText();
        genderMenu.setText(selectedSexe);
    }
}