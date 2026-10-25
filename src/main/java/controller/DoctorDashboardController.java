package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.model.DoctorPatient;
import main.java.model.DoctorPatientDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.model.Doctor;
import main.java.model.DoctorDAO;

/**
 * Displays the doctor dashboard and manages patient lookup and patient records.
 */
public class DoctorDashboardController {

    @FXML
    private Text doctorNameField;
    @FXML
    private Button addButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button settingsButton;

    @FXML
    private TableColumn<DoctorPatient, String> column_address;

    @FXML
    private TableColumn<DoctorPatient, Void> column_action;
    @FXML
    private TableColumn<DoctorPatient, String> column_nationalId;

    @FXML
    private TableColumn<DoctorPatient, LocalDate> column_birthDate;

    @FXML
    private TableColumn<DoctorPatient, String> column_name;

    @FXML
    private TableColumn<DoctorPatient, String> column_firstName;

    @FXML
    private TableColumn<DoctorPatient, String> column_gender;

    @FXML
    private TableColumn<DoctorPatient, String> column_phone;

    @FXML
    private TableColumn<DoctorPatient, String> column_city;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<DoctorPatient> patientTable;

    private ObservableList<DoctorPatient> patientsList;

    private int idDoctor;
    private String nameDoctor;

    public int getDoctorId() {
        return idDoctor;
    }

    public String getDoctorName() {
        return nameDoctor;
    }

    public void setDoctorName(String nameDoctor) {
        this.nameDoctor = nameDoctor;
    }

    public void setDoctorId(int idDoctor) {
        this.idDoctor = idDoctor;
        try {
            doctorNameField.setText(nameDoctor);
            loadPatients(); // ✅ Load patients here after the id is defined
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            column_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            column_birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            column_nationalId.setCellValueFactory(new PropertyValueFactory<>("nationalId"));
            column_city.setCellValueFactory(new PropertyValueFactory<>("city"));
            column_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            column_address.setCellValueFactory(new PropertyValueFactory<>("address"));
            column_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            addActionButtonsToTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addActionButtonsToTable() {
        Callback<TableColumn<DoctorPatient, Void>, TableCell<DoctorPatient, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<DoctorPatient, Void> call(final TableColumn<DoctorPatient, Void> param) {
                return new TableCell<>() {
                    private final Button btnVoir = new Button();
                    private final Button btnSupprimer = new Button();
                    private final HBox pane = new HBox(35); // 10px spacing

                    {
                        ImageView iconVoir = new ImageView(
                                new Image(getClass().getResourceAsStream("/assets/icons/info.png")));
                        iconVoir.setFitHeight(18);
                        iconVoir.setFitWidth(18);
                        btnVoir.setGraphic(iconVoir);
                        btnVoir.setStyle("-fx-background-color: transparent;");

                        ImageView iconSupprimer = new ImageView(
                                new Image(getClass().getResourceAsStream("/assets/icons/delete.png")));
                        iconSupprimer.setFitHeight(18);
                        iconSupprimer.setFitWidth(18);
                        btnSupprimer.setGraphic(iconSupprimer);
                        btnSupprimer.setStyle("-fx-background-color: transparent;");

                        pane.getChildren().addAll(btnVoir, btnSupprimer);

                        btnVoir.setOnAction(event -> {
                            DoctorPatient patient = getTableView().getItems().get(getIndex());
                            voirPatient(patient);
                        });

                        btnSupprimer.setOnAction(event -> {
                            DoctorPatient patient = getTableView().getItems().get(getIndex());
                            deletePatient(patient);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
            }
        };

        column_action.setCellFactory(cellFactory);
    }

    public void voirPatient(DoctorPatient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/doctor_mtr_view.fxml"));
            Parent root = loader.load();

            DoctorMTRController controller = loader.getController();

            controller.setDoctorId(idDoctor);
            controller.setNationalIdPatient(patient.getNationalId());
            controller.setPatientName(patient.getName());
            controller.setPatientFirstName(patient.getFirstName());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePatient(DoctorPatient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the patient " + patient.getName() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DoctorPatientDAO.delete(patient.getNationalId(), idDoctor);
                try {
                    loadPatients(); // reload the list after deletion
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadPatients() throws SQLException {
        patientsList = FXCollections.observableArrayList(DoctorPatientDAO.getPatients(idDoctor));
        patientTable.setItems(patientsList);
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add a patient");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");

        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("First name");

        DatePicker dpDateNaissance = new DatePicker();
        dpDateNaissance.setPromptText("Birth date");

        TextField nationalIdInput = new TextField();
        nationalIdInput.setPromptText("CNI");

        TextField cityInput = new TextField();
        cityInput.setPromptText("City");

        TextField phoneInput = new TextField();
        phoneInput.setPromptText("Phone");

        TextField addressInput = new TextField();
        addressInput.setPromptText("Address");

        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("M", "F");
        genderComboBox.setPromptText("Gender");

        Button btnAjouter = new Button("Add");
        Button btnAnnuler = new Button("Cancel");

        // Spacing between elements in the HBox
        HBox hbox1 = new HBox(10, nameInput, cityInput);
        HBox hbox2 = new HBox(10, firstNameInput, phoneInput);
        HBox hbox3 = new HBox(10, dpDateNaissance, addressInput);
        HBox hbox4 = new HBox(10, nationalIdInput, genderComboBox);
        HBox hbox5 = new HBox(15, btnAjouter, btnAnnuler);

        // Padding for each HBox (internal spacing)
        hbox1.setPadding(new Insets(5));
        hbox2.setPadding(new Insets(5));
        hbox3.setPadding(new Insets(5));
        hbox4.setPadding(new Insets(5));
        hbox5.setPadding(new Insets(10));

        // Margin (outer spacing) example: add margin around nameInput and cityInput in
        // hbox1
        HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
        HBox.setMargin(firstNameInput, new Insets(0, 10, 0, 0));
        HBox.setMargin(dpDateNaissance, new Insets(0, 10, 0, 0));
        HBox.setMargin(nationalIdInput, new Insets(0, 10, 0, 0));
        HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(15, hbox1, hbox2, hbox3, hbox4, hbox5);

        // Padding for the VBox (internal spacing all around)
        vbox.setPadding(new Insets(20));

        // Gray solid border, rounded corners 10px, thickness 2px
        vbox.setBorder(new Border(new BorderStroke(
                Color.GRAY,
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2))));

        btnAnnuler.setOnAction(e -> dialogStage.close());

        btnAjouter.setOnAction(e -> {
            // Check required fields
            if (nameInput.getText().isEmpty() || firstNameInput.getText().isEmpty()
                    || dpDateNaissance.getValue() == null
                    || nationalIdInput.getText().isEmpty() || genderComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing fields");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please fill in all required fields (name, first name, date, national ID, gender).");
                alert.showAndWait();
                return;
            }

            try {
                DoctorPatient patient = new DoctorPatient();
                patient.setName(nameInput.getText());
                patient.setFirstName(firstNameInput.getText());
                patient.setDateNaissance(dpDateNaissance.getValue()); // LocalDate
                patient.setNationalId(nationalIdInput.getText());
                patient.setCity(cityInput.getText());
                patient.setPhone(phoneInput.getText());
                patient.setAddress(addressInput.getText());
                patient.setGender(genderComboBox.getValue());

                // ⚠ Verify that idDoctor is properly initialized
                if (idDoctor == 0) {
                    throw new IllegalStateException("Doctor ID is not initialized.");
                }

                DoctorPatientDAO.insert(patient, idDoctor); // DAO call

                dialogStage.close();
                loadPatients(); // Reload the table

            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL error");
                alert.setHeaderText("Error adding patient");
                alert.setContentText("Details: " + ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unexpected error");
                alert.setContentText("Details: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @FXML
    void handleExport(ActionEvent event) {
        System.out.println("Export data (feature not implemented)");
    }

    private Doctor currentDoctor;

    public void setDoctor(Doctor doctor) {
        this.currentDoctor = doctor;
    }

    @FXML
    void handleSettings(ActionEvent event) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit your information");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setText(currentDoctor.getName());

        TextField tfNomCabinet = new TextField();
        tfNomCabinet.setPromptText("Clinic name");
        tfNomCabinet.setText(currentDoctor.getClinicName());

        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("First name");
        firstNameInput.setText(currentDoctor.getFirstName());

        TextField addressInput = new TextField();
        addressInput.setPromptText("Clinic address");
        addressInput.setText(currentDoctor.getClinicAddress());

        TextField nationalIdInput = new TextField();
        nationalIdInput.setPromptText("CNIE");
        nationalIdInput.setText(currentDoctor.getNationalId());

        TextField tftele = new TextField();
        tftele.setPromptText("Phone");
        tftele.setText(currentDoctor.getPhone());

        TextField tfSpec = new TextField();
        tfSpec.setPromptText("Specialty");
        tfSpec.setText(currentDoctor.getSpecialty());

        TextField tfmotPasse = new TextField();
        tfmotPasse.setPromptText("Password");
        tfmotPasse.setText(currentDoctor.getPassword());

        Button btnAjouter = new Button("Update");
        Button btnAnnuler = new Button("Cancel");

        // Spacing between elements in the HBox
        HBox hbox1 = new HBox(10, nameInput, tfNomCabinet);
        HBox hbox2 = new HBox(10, firstNameInput, addressInput);
        HBox hbox3 = new HBox(10, nationalIdInput, tftele);
        HBox hbox4 = new HBox(10, tfSpec, tfmotPasse);
        HBox hbox5 = new HBox(15, btnAjouter, btnAnnuler);

        // Padding for each HBox (internal spacing)
        hbox1.setPadding(new Insets(5));
        hbox2.setPadding(new Insets(5));
        hbox3.setPadding(new Insets(10));

        // Margin (outer spacing) example: add margin around nameInput and cityInput in
        // hbox1
        HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
        HBox.setMargin(firstNameInput, new Insets(0, 10, 0, 0));
        HBox.setMargin(nationalIdInput, new Insets(0, 10, 0, 0));
        HBox.setMargin(tfSpec, new Insets(0, 10, 0, 0));
        HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(15, hbox1, hbox2, hbox3, hbox4, hbox5);

        // Padding for the VBox (internal spacing all around)
        vbox.setPadding(new Insets(20));

        // Gray solid border, rounded corners 10px, thickness 2px
        vbox.setBorder(new Border(new BorderStroke(
                Color.GRAY,
                BorderStrokeStyle.SOLID,
                new CornerRadii(10),
                new BorderWidths(2))));

        btnAnnuler.setOnAction(e -> dialogStage.close());

        btnAjouter.setOnAction(e -> {
            // Check required fields
            if (nameInput.getText().isEmpty() || firstNameInput.getText().isEmpty() ||
                    nationalIdInput.getText().isEmpty() || tfSpec.getText().isEmpty() ||
                    tfmotPasse.getText().isEmpty() || tfNomCabinet.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing fields");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Please fill in all required fields (name, first name, clinic name, national ID, specialty, password).");
                alert.showAndWait();
                return;
            }

            try {
                Doctor newdoctor = new Doctor();
                newdoctor.setName(nameInput.getText());
                newdoctor.setFirstName(firstNameInput.getText());
                newdoctor.setPassword(tfmotPasse.getText());
                newdoctor.setClinicAddress(addressInput.getText()); // LocalDate
                newdoctor.setNationalId(nationalIdInput.getText());
                newdoctor.setClinicName(tfNomCabinet.getText());
                newdoctor.setSpecialty(tfSpec.getText());
                newdoctor.setPhone(tftele.getText());
                newdoctor.setId(idDoctor);
                doctorNameField.setText(nameInput.getText());

                DoctorDAO.update(newdoctor); // DAO call
                dialogStage.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL error");
                alert.setHeaderText("Error updating doctor information");
                alert.setContentText("Details: " + ex.getMessage());
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Unexpected error");
                alert.setContentText("Details: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<DoctorPatient> filtered = FXCollections.observableArrayList();

        for (DoctorPatient p : patientsList) {
            if (p.getName().toLowerCase().contains(keyword) ||
                    p.getFirstName().toLowerCase().contains(keyword) ||
                    p.getNationalId().toLowerCase().contains(keyword)) {
                filtered.add(p);
            }
        }

        patientTable.setItems(filtered);
    }
}