package controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Medication;
import model.MedicationDAO;
import model.Appointment;
import model.AppointmentDAO;
import model.MedicalTest;
import model.MedicalTestDAO;

/**
 * Manages the doctor’s patient records for medications, tests, and
 * appointments.
 */
public class DoctorMTRController {

    @FXML
    private TableColumn<Medication, Void> Medication_column_action;

    @FXML
    private TableColumn<Medication, LocalDate> Medication_column_date;

    @FXML
    private TableColumn<Medication, String> Medication_column_description;

    @FXML
    private TableColumn<Medication, Integer> Medication_column_id;

    @FXML
    private TableColumn<Medication, String> Medication_column_name;

    @FXML
    private TableColumn<Medication, String> Medication_column_duration;

    @FXML
    private TableColumn<Appointment, Void> Appointment_column_action;

    @FXML
    private TableColumn<Appointment, LocalDate> Appointment_column_date;

    @FXML
    private TableColumn<Appointment, LocalTime> Appointment_column_time;

    @FXML
    private TableColumn<Appointment, Integer> Appointment_column_id;

    @FXML
    private TableColumn<Appointment, String> Appointment_column_reason;

    @FXML
    private TableColumn<Appointment, String> Appointment_column_status;

    @FXML
    private TableColumn<MedicalTest, Void> Test_column_action;

    @FXML
    private TableColumn<MedicalTest, LocalDate> Test_column_date;

    @FXML
    private TableColumn<MedicalTest, Integer> Test_column_id;

    @FXML
    private TableColumn<MedicalTest, String> Test_column_name;

    @FXML
    private TableColumn<MedicalTest, String> Test_column_result;

    @FXML
    private TableColumn<MedicalTest, String> Test_column_status;

    @FXML
    private TableColumn<MedicalTest, String> Test_column_type;

    @FXML
    private Button addMedicationButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button addTestButton;

    @FXML
    private Button exportMedicationButton;

    @FXML
    private Button exportAppointmentButton;

    @FXML
    private Button exportMedicalTestButton;

    @FXML
    private Button medicationsButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button appointmentsButton;

    @FXML
    private Button testsButton;

    @FXML
    private TextField medicationSearchField;

    @FXML
    private TextField appointmentSearchField;

    @FXML
    private TextField medicalTestSearchField;

    @FXML
    private Text doctorNameField;

    @FXML
    private AnchorPane interfaceMedication;

    @FXML
    private AnchorPane interfaceAppointment;

    @FXML
    private AnchorPane interfaceTest;

    @FXML
    private TableView<Medication> medicationTable;

    private ObservableList<Medication> medicationList;

    @FXML
    private TableView<MedicalTest> medicalTestTable;

    private ObservableList<MedicalTest> testList;

    @FXML
    private TableView<Appointment> appointmentTable;

    private ObservableList<Appointment> appointmentList;

    @FXML
    public void initialize() {
        try {
            Medication_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            Medication_column_date.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            Medication_column_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            Medication_column_duration.setCellValueFactory(new PropertyValueFactory<>("duration"));

            Test_column_name.setCellValueFactory(new PropertyValueFactory<>("testType"));
            Test_column_date.setCellValueFactory(new PropertyValueFactory<>("testDate"));
            Test_column_type.setCellValueFactory(new PropertyValueFactory<>("doctorType"));
            Test_column_result.setCellValueFactory(new PropertyValueFactory<>("result"));
            Test_column_status.setCellValueFactory(new PropertyValueFactory<>("status"));

            Appointment_column_date.setCellValueFactory(new PropertyValueFactory<>("dateAppointment"));
            Appointment_column_time.setCellValueFactory(new PropertyValueFactory<>("time"));
            Appointment_column_reason.setCellValueFactory(new PropertyValueFactory<>("reason"));
            Appointment_column_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            addActionButtonsToTableMedication();
            addActionButtonsToTableAppointment();
            addActionButtonsToTableTest();
            medicationSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterMedications(newValue);
            });
            appointmentSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterAppointment(newValue);
            });
            medicalTestSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterMedicalTest(newValue);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterMedications(String keyword) {
        FilteredList<Medication> filteredList = new FilteredList<>(medicationList, medication -> {
            if (keyword == null || keyword.isEmpty())
                return true;
            String lowerKeyword = keyword.toLowerCase();

            return medication.getName().toLowerCase().contains(lowerKeyword) ||
                    medication.getDescription().toLowerCase().contains(lowerKeyword) ||
                    String.valueOf(medication.getDuration()).toLowerCase().contains(lowerKeyword) ||
                    medication.getStartDate().toString().contains(lowerKeyword);
        });

        medicationTable.setItems(filteredList);
    }

    private void filterAppointment(String keyword) {
        FilteredList<Appointment> filteredList = new FilteredList<>(appointmentList, rendez -> {
            if (keyword == null || keyword.isEmpty())
                return true;
            String lowerKeyword = keyword.toLowerCase();

            return rendez.getReason().toLowerCase().contains(lowerKeyword) ||
                    rendez.getStatus().toLowerCase().contains(lowerKeyword) ||
                    rendez.getDateAppointment().toString().contains(lowerKeyword) ||
                    rendez.getTime().toString().contains(lowerKeyword);
        });

        appointmentTable.setItems(filteredList);
    }

    private void filterMedicalTest(String keyword) {
        FilteredList<MedicalTest> filteredList = new FilteredList<>(testList, test -> {
            if (keyword == null || keyword.isEmpty())
                return true;
            String lowerKeyword = keyword.toLowerCase();

            return test.getTestType().toLowerCase().contains(lowerKeyword) ||
                    test.getDoctorType().toLowerCase().contains(lowerKeyword) ||
                    test.getResult().toLowerCase().contains(lowerKeyword) ||
                    test.getStatus().toLowerCase().contains(lowerKeyword) ||
                    test.getTestDate().toString().contains(lowerKeyword);
        });

        medicalTestTable.setItems(filteredList);
    }

    private void loadMedications() {
        medicationList = FXCollections.observableArrayList(MedicationDAO.getMedications(nationalIdPatient, idDoctor));
        medicationTable.setItems(medicationList);
    }

    private void loadAppointment() {
        appointmentList = FXCollections.observableArrayList(AppointmentDAO.getAppointment(nationalIdPatient, idDoctor));
        appointmentTable.setItems(appointmentList);
    }

    private void loadMedicalTest() {
        testList = FXCollections.observableArrayList(MedicalTestDAO.getTests(nationalIdPatient, idDoctor));
        medicalTestTable.setItems(testList);
    }

    @FXML
    private Text patientName;

    @FXML
    void handleExport(ActionEvent event) {

    }

    @FXML
    void handleSettings(ActionEvent event) {

    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    private boolean rdLoaded = false;
    private boolean medicationLoaded = true;
    private boolean testLoaded = false;
    private int inter = 1;

    @FXML
    void switchToAppointment(ActionEvent event) {

        if (!rdLoaded) {
            loadAppointment();
            rdLoaded = true;
        }
        inter = 3;
        switchForm(3);
    }

    @FXML
    void switchToMedication(ActionEvent event) {

        if (!medicationLoaded) {
            loadMedications();
            medicationLoaded = true;
        }
        inter = 1;
        switchForm(1);
    }

    @FXML
    void switchToMedicalTest(ActionEvent event) {

        if (!testLoaded) {
            loadMedicalTest();
            testLoaded = true;
        }
        inter = 2;
        switchForm(2);
    }

    private int idDoctor;
    private String nationalIdPatient;
    private String namePatient;
    private String firstNamePatient;

    public void setDoctorId(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public void setNationalIdPatient(String nationalIdPatient) {
        this.nationalIdPatient = nationalIdPatient;
    }

    public void setPatientName(String namePatient) {
        this.namePatient = namePatient;
    }

    public void setPatientFirstName(String firstNamePatient) {
        this.firstNamePatient = firstNamePatient;
        String res = "";
        res = namePatient + " " + firstNamePatient;
        patientName.setText(res);
        loadMedications();
    }

    public void switchForm(int choix) {
        switch (choix) {
            case 1:
                interfaceMedication.setVisible(true);
                interfaceAppointment.setVisible(false);
                interfaceTest.setVisible(false);
                break;
            case 2:
                interfaceTest.setVisible(true);
                interfaceMedication.setVisible(false);
                interfaceAppointment.setVisible(false);
                break;
            case 3:
                interfaceAppointment.setVisible(true);
                interfaceTest.setVisible(false);
                interfaceMedication.setVisible(false);
                break;
        }
    }

    private void addActionButtonsToTableMedication() {
        Callback<TableColumn<Medication, Void>, TableCell<Medication, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Medication, Void> call(final TableColumn<Medication, Void> param) {
                return new TableCell<>() {
                    private final Button btnVoir = new Button();
                    private final Button btnSupprimer = new Button();
                    private final HBox pane = new HBox(35);

                    {
                        ImageView iconVoir = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/edit.png")));
                        iconVoir.setFitHeight(18);
                        iconVoir.setFitWidth(18);
                        btnVoir.setGraphic(iconVoir);
                        btnVoir.setStyle("-fx-background-color: transparent;");

                        ImageView iconSupprimer = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/delete.png")));
                        iconSupprimer.setFitHeight(18);
                        iconSupprimer.setFitWidth(18);
                        btnSupprimer.setGraphic(iconSupprimer);
                        btnSupprimer.setStyle("-fx-background-color: transparent;");

                        pane.getChildren().addAll(btnVoir, btnSupprimer);

                        btnVoir.setOnAction(event -> {
                            Medication medication = getTableView().getItems().get(getIndex());
                            voirMedication(medication);
                        });

                        btnSupprimer.setOnAction(event -> {
                            Medication medication = getTableView().getItems().get(getIndex());
                            deleteMedication(medication);
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

        Medication_column_action.setCellFactory(cellFactory);
    }

    private void addActionButtonsToTableTest() {
        Callback<TableColumn<MedicalTest, Void>, TableCell<MedicalTest, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MedicalTest, Void> call(final TableColumn<MedicalTest, Void> param) {
                return new TableCell<>() {
                    private final Button btnVoir = new Button();
                    private final Button btnSupprimer = new Button();
                    private final HBox pane = new HBox(35);

                    {
                        ImageView iconVoir = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/info.png")));
                        iconVoir.setFitHeight(18);
                        iconVoir.setFitWidth(18);
                        btnVoir.setGraphic(iconVoir);
                        btnVoir.setStyle("-fx-background-color: transparent;");

                        ImageView iconSupprimer = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/delete.png")));
                        iconSupprimer.setFitHeight(18);
                        iconSupprimer.setFitWidth(18);
                        btnSupprimer.setGraphic(iconSupprimer);
                        btnSupprimer.setStyle("-fx-background-color: transparent;");

                        pane.getChildren().addAll(btnVoir, btnSupprimer);

                        btnVoir.setOnAction(event -> {
                            MedicalTest medicalTest = getTableView().getItems().get(getIndex());
                            voirMedicalTest(medicalTest);
                        });

                        btnSupprimer.setOnAction(event -> {
                            MedicalTest medicalTest = getTableView().getItems().get(getIndex());
                            deleteMedicalTest(medicalTest);
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

        Test_column_action.setCellFactory(cellFactory);
    }

    private void addActionButtonsToTableAppointment() {
        Callback<TableColumn<Appointment, Void>, TableCell<Appointment, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Appointment, Void> call(final TableColumn<Appointment, Void> param) {
                return new TableCell<>() {
                    private final Button btnVoir = new Button();
                    private final Button btnSupprimer = new Button();
                    private final HBox pane = new HBox(35);

                    {
                        ImageView iconVoir = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/info.png")));
                        iconVoir.setFitHeight(18);
                        iconVoir.setFitWidth(18);
                        btnVoir.setGraphic(iconVoir);
                        btnVoir.setStyle("-fx-background-color: transparent;");

                        ImageView iconSupprimer = new ImageView(
                                new Image(getClass().getResourceAsStream("/resources/delete.png")));
                        iconSupprimer.setFitHeight(18);
                        iconSupprimer.setFitWidth(18);
                        btnSupprimer.setGraphic(iconSupprimer);
                        btnSupprimer.setStyle("-fx-background-color: transparent;");

                        pane.getChildren().addAll(btnVoir, btnSupprimer);

                        btnVoir.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            voirAppointment(appointment);
                        });

                        btnSupprimer.setOnAction(event -> {
                            Appointment appointment = getTableView().getItems().get(getIndex());
                            deleteAppointment(appointment);
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

        Appointment_column_action.setCellFactory(cellFactory);
    }

    private void deleteMedication(Medication medication) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the medication " + medication.getName() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                MedicationDAO.delete(medication.getId());
                loadMedications(); // reload the list after deletion
            }
        });
    }

    private void voirMedication(Medication medication) {

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit medication for " + "" + namePatient);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        TextField nameInput = new TextField();
        nameInput.setText(medication.getName());

        DatePicker startDate = new DatePicker();
        startDate.setValue(medication.getStartDate());

        TextField tfduration = new TextField();
        tfduration.setText(String.valueOf(medication.getDuration()));

        TextField tfDescription = new TextField();
        tfDescription.setText(medication.getDescription());

        Button btnAjouter = new Button("Add");
        Button btnAnnuler = new Button("Cancel");

        // Spacing between elements in the HBox
        HBox hbox1 = new HBox(10, nameInput, startDate);
        HBox hbox2 = new HBox(10, tfduration, tfDescription);
        HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

        // Padding for each HBox (internal spacing)
        hbox1.setPadding(new Insets(5));
        hbox2.setPadding(new Insets(5));
        hbox3.setPadding(new Insets(10));

        // Margin (outer spacing) example: add margin around nameInput and cityInput in
        // hbox1
        HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
        HBox.setMargin(tfduration, new Insets(0, 10, 0, 0));
        HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
            if (nameInput.getText().isEmpty() || startDate.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields (name, start date).");
                alert.showAndWait();
                return;
            }

            try {
                Medication newmedication = new Medication();
                newmedication.setName(nameInput.getText());
                newmedication.setStartDate(startDate.getValue()); // LocalDate
                newmedication.setDuration(Integer.parseInt(tfduration.getText()));
                newmedication.setDescription(tfDescription.getText());
                newmedication.setId(medication.getId());

                MedicationDAO.editMedication(medication); // DAO call
                dialogStage.close();
                loadMedications(); // Reload the table

            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL error");
                alert.setHeaderText("Error adding medication");
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

    private void deleteMedicalTest(MedicalTest medicalTest) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the medical test " + medicalTest.getTestType() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                MedicalTestDAO.delete(medicalTest.getId());
                loadMedicalTest(); // reload the list after deletion
            }
        });
    }

    private void voirMedicalTest(MedicalTest medicalTest) {

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit medical test for " + "" + namePatient);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        TextField nameInput = new TextField();
        nameInput.setText(medicalTest.getTestType());

        DatePicker startDate = new DatePicker();
        startDate.setValue(medicalTest.getTestDate());

        TextField tfResult = new TextField();
        tfResult.setText(medicalTest.getResult());

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("pending", "completed");
        statusComboBox.setValue(medicalTest.getStatus());
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("internal", "external");
        typeComboBox.setValue(medicalTest.getDoctorType());

        Button btnAjouter = new Button("Update");
        Button btnAnnuler = new Button("Cancel");

        // Spacing between elements in the HBox
        HBox hbox1 = new HBox(10, nameInput, startDate);
        HBox hbox2 = new HBox(10, tfResult, statusComboBox, typeComboBox);
        HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

        // Padding for each HBox (internal spacing)
        hbox1.setPadding(new Insets(5));
        hbox2.setPadding(new Insets(5));
        hbox3.setPadding(new Insets(10));

        // Margin (outer spacing) example: add margin around nameInput and cityInput in
        // hbox1
        HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
        HBox.setMargin(tfResult, new Insets(0, 10, 0, 0));
        HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
            if (nameInput.getText().isEmpty() || startDate.getValue() == null || statusComboBox.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields (name, test date, and status).");
                alert.showAndWait();
                return;
            }

            try {
                MedicalTest newmedicalTest = new MedicalTest();
                newmedicalTest.setTestType(nameInput.getText());
                newmedicalTest.setTestDate(startDate.getValue()); // LocalDate
                newmedicalTest.setResult(tfResult.getText());
                newmedicalTest.setStatus(statusComboBox.getValue());
                newmedicalTest.setDoctorType(typeComboBox.getValue());
                newmedicalTest.setId(medicalTest.getId());

                MedicalTestDAO.editTest(newmedicalTest); // DAO call
                dialogStage.close();
                loadMedicalTest(); // Reload the table

            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL error");
                alert.setHeaderText("Error adding medical test");
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

    private void deleteAppointment(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete confirmation");
        alert.setHeaderText(null);
        alert.setContentText(
                "Are you sure you want to delete the appointment dated " + appointment.getDateAppointment() + "?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                AppointmentDAO.delete(appointment.getId());
                loadAppointment(); // reload the list after deletion
            }
        });
    }

    private void voirAppointment(Appointment appointment) {

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit appointment for " + "" + namePatient);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);

        DatePicker date = new DatePicker();
        date.setValue(appointment.getDateAppointment());

        TextField tfRaisont = new TextField();
        tfRaisont.setText(appointment.getReason());
        TextField timeInput = new TextField();
        timeInput.setText(String.valueOf(appointment.getTime()));

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("absent", "upcoming", "present");
        statusComboBox.setValue(appointment.getStatus());

        Button btnAjouter = new Button("Add");
        Button btnAnnuler = new Button("Cancel");

        // Spacing between elements in the HBox
        HBox hbox1 = new HBox(10, date, timeInput);
        HBox hbox2 = new HBox(10, tfRaisont, statusComboBox);
        HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

        // Padding for each HBox (internal spacing)
        hbox1.setPadding(new Insets(5));
        hbox2.setPadding(new Insets(5));
        hbox3.setPadding(new Insets(10));

        // Margin (outer spacing) example: add margin around nameInput and cityInput in
        // hbox1
        HBox.setMargin(date, new Insets(0, 10, 0, 0)); // right margin of 10px
        HBox.setMargin(tfRaisont, new Insets(0, 10, 0, 0));
        HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
            if (date.getValue() == null || statusComboBox.getValue() == null || timeInput.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing fields");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all required fields (date, time, and status).");
                alert.showAndWait();
                return;
            }

            try {
                Appointment newappointment = new Appointment();
                newappointment.setDateAppointment(date.getValue());
                newappointment.setReason(tfRaisont.getText()); // LocalDate
                newappointment.setTime(LocalTime.parse(timeInput.getText()));
                newappointment.setStatus(statusComboBox.getValue());
                newappointment.setId(appointment.getId());

                AppointmentDAO.editTest(appointment); // DAO call
                dialogStage.close();
                loadAppointment(); // Rechargement de la table

            } catch (SQLException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL error");
                alert.setHeaderText("Error updating appointment");
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
    public void handleAdd(ActionEvent event) {

        if (inter == 1) {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add medication");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);

            TextField nameInput = new TextField();
            nameInput.setPromptText("Name");

            DatePicker startDate = new DatePicker();
            startDate.setPromptText("Start date");

            TextField tfduration = new TextField();
            tfduration.setPromptText("Period (integer)");

            TextField tfDescription = new TextField();
            tfDescription.setPromptText("Description");

            Button btnAjouter = new Button("Add");
            Button btnAnnuler = new Button("Cancel");

            // Spacing between elements in the HBox
            HBox hbox1 = new HBox(10, nameInput, startDate);
            HBox hbox2 = new HBox(10, tfduration, tfDescription);
            HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

            // Padding for each HBox (internal spacing)
            hbox1.setPadding(new Insets(5));
            hbox2.setPadding(new Insets(5));
            hbox3.setPadding(new Insets(10));

            // Margin (outer spacing) example: add margin around nameInput and cityInput in
            // hbox1
            HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
            HBox.setMargin(tfduration, new Insets(0, 10, 0, 0));
            HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

            VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
                if (nameInput.getText().isEmpty() || startDate.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Missing fields");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all required fields (name, start date).");
                    alert.showAndWait();
                    return;
                }

                try {
                    Medication medication = new Medication();
                    medication.setName(nameInput.getText());
                    medication.setStartDate(startDate.getValue()); // LocalDate
                    medication.setDuration(Integer.parseInt(tfduration.getText()));
                    medication.setDescription(tfDescription.getText());

                    MedicationDAO.insert(medication, nationalIdPatient, idDoctor); // DAO call
                    dialogStage.close();
                    loadMedications(); // Reload the table

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL error");
                    alert.setHeaderText("Error adding medication");
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
        } else if (inter == 2) {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add medical test");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);

            TextField nameInput = new TextField();
            nameInput.setPromptText("Name");

            DatePicker startDate = new DatePicker();
            startDate.setPromptText("Test date");

            TextField tfResult = new TextField();
            tfResult.setPromptText("Result");

            ComboBox<String> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().addAll("pending", "completed");
            statusComboBox.setPromptText("Status");
            ComboBox<String> typeComboBox = new ComboBox<>();
            typeComboBox.getItems().addAll("internal", "external");
            typeComboBox.setPromptText("Doctor type");

            Button btnAjouter = new Button("Add");
            Button btnAnnuler = new Button("Cancel");

            // Spacing between elements in the HBox
            HBox hbox1 = new HBox(10, nameInput, startDate);
            HBox hbox2 = new HBox(10, tfResult, statusComboBox, typeComboBox);
            HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

            // Padding for each HBox (internal spacing)
            hbox1.setPadding(new Insets(5));
            hbox2.setPadding(new Insets(5));
            hbox3.setPadding(new Insets(10));

            // Margin (outer spacing) example: add margin around nameInput and cityInput in
            // hbox1
            HBox.setMargin(nameInput, new Insets(0, 10, 0, 0)); // right margin of 10px
            HBox.setMargin(tfResult, new Insets(0, 10, 0, 0));
            HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

            VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
                if (nameInput.getText().isEmpty() || startDate.getValue() == null
                        || statusComboBox.getValue() == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Missing fields");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all required fields (name, test date, and status).");
                    alert.showAndWait();
                    return;
                }

                try {
                    MedicalTest medicalTest = new MedicalTest();
                    medicalTest.setTestType(nameInput.getText());
                    medicalTest.setTestDate(startDate.getValue()); // LocalDate
                    medicalTest.setResult(tfResult.getText());
                    medicalTest.setStatus(statusComboBox.getValue());
                    medicalTest.setDoctorType(typeComboBox.getValue());

                    MedicalTestDAO.insert(medicalTest, nationalIdPatient, idDoctor); // DAO call
                    dialogStage.close();
                    loadMedicalTest(); // Reload the table

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL error");
                    alert.setHeaderText("Error adding medical test");
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
        } else {
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add appointment");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setResizable(false);

            DatePicker date = new DatePicker();
            date.setPromptText("Appointment date");

            TextField tfRaisont = new TextField();
            tfRaisont.setPromptText("Reason");
            TextField timeInput = new TextField();
            timeInput.setPromptText("Time (format x:x)");

            ComboBox<String> statusComboBox = new ComboBox<>();
            statusComboBox.getItems().addAll("absent", "upcoming", "present");
            statusComboBox.setPromptText("Status");

            Button btnAjouter = new Button("Add");
            Button btnAnnuler = new Button("Cancel");

            // Spacing between elements in the HBox
            HBox hbox1 = new HBox(10, date, timeInput);
            HBox hbox2 = new HBox(10, tfRaisont, statusComboBox);
            HBox hbox3 = new HBox(15, btnAjouter, btnAnnuler);

            // Padding for each HBox (internal spacing)
            hbox1.setPadding(new Insets(5));
            hbox2.setPadding(new Insets(5));
            hbox3.setPadding(new Insets(10));

            // Margin (outer spacing) example: add margin around nameInput and cityInput in
            // hbox1
            HBox.setMargin(date, new Insets(0, 10, 0, 0)); // right margin of 10px
            HBox.setMargin(tfRaisont, new Insets(0, 10, 0, 0));
            HBox.setMargin(btnAjouter, new Insets(0, 10, 0, 0));

            VBox vbox = new VBox(15, hbox1, hbox2, hbox3);

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
                if (date.getValue() == null || statusComboBox.getValue() == null || timeInput.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Missing fields");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill in all required fields (date, time, and status).");
                    alert.showAndWait();
                    return;
                }

                try {
                    Appointment appointment = new Appointment();
                    appointment.setDateAppointment(date.getValue());
                    appointment.setReason(tfRaisont.getText()); // LocalDate
                    appointment.setTime(LocalTime.parse(timeInput.getText()));
                    appointment.setStatus(statusComboBox.getValue());

                    AppointmentDAO.insert(appointment, nationalIdPatient, idDoctor); // DAO call
                    dialogStage.close();
                    loadAppointment(); // Reload the table

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL error");
                    alert.setHeaderText("Error adding appointment");
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
    }

}
