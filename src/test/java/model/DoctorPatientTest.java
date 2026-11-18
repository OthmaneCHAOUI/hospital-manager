package model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link DoctorPatient}.
 */
@DisplayName("DoctorPatient Class Unit Tests")
class DoctorPatientTest {

    @Test
    @DisplayName("No-args constructor should create an empty doctor patient")
    void noArgsConstructorShouldCreateEmptyDoctorPatient() {
        DoctorPatient patient = new DoctorPatient();

        assertAll(
                () -> assertEquals(0, patient.getId()),
                () -> assertEquals(0, patient.getIdDoctor()),
                () -> assertNull(patient.getName()),
                () -> assertNull(patient.getFirstName()),
                () -> assertNull(patient.getBirthDate()),
                () -> assertNull(patient.getNationalId()),
                () -> assertNull(patient.getCity()),
                () -> assertNull(patient.getPhone()),
                () -> assertNull(patient.getAddress()),
                () -> assertNull(patient.getGender()));
    }

    @Test
    @DisplayName("Full constructor should set all doctor patient fields")
    void fullConstructorShouldSetAllFields() {
        LocalDate birthDate = LocalDate.of(1990, 4, 12);
        DoctorPatient patient = new DoctorPatient(
                7, 12, "Doe", "Jane", birthDate, "PAT-001",
                "London", "0612345001", "12 Victoria Street", "Female");

        assertAll(
                () -> assertEquals(7, patient.getId()),
                () -> assertEquals(12, patient.getIdDoctor()),
                () -> assertEquals("Doe", patient.getName()),
                () -> assertEquals("Jane", patient.getFirstName()),
                () -> assertEquals(birthDate, patient.getBirthDate()),
                () -> assertEquals("PAT-001", patient.getNationalId()),
                () -> assertEquals("London", patient.getCity()),
                () -> assertEquals("0612345001", patient.getPhone()),
                () -> assertEquals("12 Victoria Street", patient.getAddress()),
                () -> assertEquals("Female", patient.getGender()));
    }

    @Test
    @DisplayName("Create constructor should set patient details without an id or national id")
    void createConstructorShouldSetPatientDetails() {
        LocalDate birthDate = LocalDate.of(1995, 8, 20);
        DoctorPatient patient = new DoctorPatient(
                "Smith", "Alex", birthDate, "Paris", "0612345002",
                "8 Main Street", "Male");

        assertAll(
                () -> assertEquals(0, patient.getId()),
                () -> assertEquals(0, patient.getIdDoctor()),
                () -> assertEquals("Smith", patient.getName()),
                () -> assertEquals("Alex", patient.getFirstName()),
                () -> assertEquals(birthDate, patient.getBirthDate()),
                () -> assertNull(patient.getNationalId()),
                () -> assertEquals("Paris", patient.getCity()),
                () -> assertEquals("0612345002", patient.getPhone()),
                () -> assertEquals("8 Main Street", patient.getAddress()),
                () -> assertEquals("Male", patient.getGender()));
    }

    @Test
    @DisplayName("Create constructor with national id should set all patient details")
    void createConstructorWithNationalIdShouldSetPatientDetails() {
        LocalDate birthDate = LocalDate.of(1988, 2, 3);
        DoctorPatient patient = new DoctorPatient(
                "Brown", "Taylor", birthDate, "PAT-002", "Madrid",
                "0612345003", "4 Oak Avenue", "Non-binary");

        assertAll(
                () -> assertEquals(0, patient.getId()),
                () -> assertEquals(0, patient.getIdDoctor()),
                () -> assertEquals("Brown", patient.getName()),
                () -> assertEquals("Taylor", patient.getFirstName()),
                () -> assertEquals(birthDate, patient.getBirthDate()),
                () -> assertEquals("PAT-002", patient.getNationalId()),
                () -> assertEquals("Madrid", patient.getCity()),
                () -> assertEquals("0612345003", patient.getPhone()),
                () -> assertEquals("4 Oak Avenue", patient.getAddress()),
                () -> assertEquals("Non-binary", patient.getGender()));
    }

    @Test
    @DisplayName("Setters should update every doctor patient field")
    void settersShouldUpdateEveryField() {
        DoctorPatient patient = new DoctorPatient();
        LocalDate birthDate = LocalDate.of(2000, 10, 31);

        patient.setId(42);
        patient.setDoctorId(8);
        patient.setName("Johnson");
        patient.setFirstName("Chris");
        patient.setBirthDate(birthDate);
        patient.setNationalId("PAT-042");
        patient.setCity("Rome");
        patient.setPhone("0612345004");
        patient.setAddress("20 Elm Road");
        patient.setGender("Other");

        assertAll(
                () -> assertEquals(42, patient.getId()),
                () -> assertEquals(8, patient.getIdDoctor()),
                () -> assertEquals("Johnson", patient.getName()),
                () -> assertEquals("Chris", patient.getFirstName()),
                () -> assertEquals(birthDate, patient.getBirthDate()),
                () -> assertEquals("PAT-042", patient.getNationalId()),
                () -> assertEquals("Rome", patient.getCity()),
                () -> assertEquals("0612345004", patient.getPhone()),
                () -> assertEquals("20 Elm Road", patient.getAddress()),
                () -> assertEquals("Other", patient.getGender()));
    }
}
