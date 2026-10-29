package model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link Medication}.
 * @author OthmaneCHAOUI
 */
@DisplayName("Medication Class Unit Tests")
public class MedicationTest {
    /**
     * Set up the test environment before all tests.
     * @throws Exception if any error occurs during setup
     */
    @BeforeAll 
    static void setupBeforeAll() throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("Start of Medication Tests");
        System.out.println("-------------------------------------------------");
    }

    /**
     * Clean up the test environment after all tests.
     * @throws Exception if any error occurs during cleanup
     */
    @AfterAll
    static void tearDownAfterAll() throws Exception {
        System.out.println("-------------------------------------------------");
        System.out.println("End of Medication Tests");
        System.out.println("-------------------------------------------------");
    }

    /**
     * Test method for {@link Medication#Medication()}.
     */
    @Test
    @DisplayName("No-args constructor should create an empty medication")
    void noArgsConstructorShouldCreateEmptyMedication() {
        Medication medication = new Medication();

        assertAll(
                () -> assertEquals(0, medication.getId()),
                () -> assertEquals(0, medication.getIdDoctor()),
                () -> assertNull(medication.getNationalIdPatient()),
                () -> assertNull(medication.getName()),
                () -> assertNull(medication.getStartDate()),
                () -> assertEquals(0, medication.getDuration()),
                () -> assertNull(medication.getDescription()));
    }

    /**
     * Test method for the constructor used when loading a medication with its
     * patient and doctor relationships.
     */
    @Test
    @DisplayName("Full constructor should set all medication fields")
    void fullConstructorShouldSetAllFields() {
        LocalDate startDate = LocalDate.of(2026, 10, 29);
        Medication medication = new Medication(
                7,
                12,
                "PAT-001",
                "Amoxicillin",
                startDate,
                10,
                "Take after meals");

        assertAll(
                () -> assertEquals(7, medication.getId()),
                () -> assertEquals(12, medication.getIdDoctor()),
                () -> assertEquals("PAT-001", medication.getNationalIdPatient()),
                () -> assertEquals("Amoxicillin", medication.getName()),
                () -> assertEquals(startDate, medication.getStartDate()),
                () -> assertEquals(10, medication.getDuration()),
                () -> assertEquals("Take after meals", medication.getDescription()));
    }

    /**
     * Test method for {@link Medication#Medication(String, LocalDate, int, String)}.
     */
    @Test
    @DisplayName("Create constructor should set medication details")
    void createConstructorShouldSetMedicationDetails() {
        LocalDate startDate = LocalDate.of(2026, 11, 1);
        Medication medication = new Medication(
                "Ibuprofen",
                startDate,
                5,
                "Take twice daily");

        assertAll(
                () -> assertEquals(0, medication.getId()),
                () -> assertEquals(0, medication.getIdDoctor()),
                () -> assertNull(medication.getNationalIdPatient()),
                () -> assertEquals("Ibuprofen", medication.getName()),
                () -> assertEquals(startDate, medication.getStartDate()),
                () -> assertEquals(5, medication.getDuration()),
                () -> assertEquals("Take twice daily", medication.getDescription()));
    }

    /**
     * Test method for {@link Medication#Medication(int, String, LocalDate, int, String)}.
     */
    @Test
    @DisplayName("Edit constructor should set the medication id and details")
    void editConstructorShouldSetMedicationIdAndDetails() {
        LocalDate startDate = LocalDate.of(2026, 12, 1);
        Medication medication = new Medication(
                21,
                "Paracetamol",
                startDate,
                3,
                "As needed");

        assertAll(
                () -> assertEquals(21, medication.getId()),
                () -> assertEquals(0, medication.getIdDoctor()),
                () -> assertNull(medication.getNationalIdPatient()),
                () -> assertEquals("Paracetamol", medication.getName()),
                () -> assertEquals(startDate, medication.getStartDate()),
                () -> assertEquals(3, medication.getDuration()),
                () -> assertEquals("As needed", medication.getDescription()));
    }

    /**
     * Test method for all Medication setters and their corresponding getters.
     */
    @Test
    @DisplayName("Setters should update every medication field")
    void settersShouldUpdateEveryField() {
        Medication medication = new Medication();
        LocalDate startDate = LocalDate.of(2027, 1, 15);

        medication.setId(42);
        medication.setDoctorId(8);
        medication.setNationalIdPatient("PAT-042");
        medication.setName("Aspirin");
        medication.setStartDate(startDate);
        medication.setDuration(14);
        medication.setDescription("One tablet daily");

        assertAll(
                () -> assertEquals(42, medication.getId()),
                () -> assertEquals(8, medication.getIdDoctor()),
                () -> assertEquals("PAT-042", medication.getNationalIdPatient()),
                () -> assertEquals("Aspirin", medication.getName()),
                () -> assertEquals(startDate, medication.getStartDate()),
                () -> assertEquals(14, medication.getDuration()),
                () -> assertEquals("One tablet daily", medication.getDescription()));
    }
}
