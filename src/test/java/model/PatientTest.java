package model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Patient}.
 * @author OthmaneCHAOUI
 */
@DisplayName ("PatientTest")
public class PatientTest {

    /**
     * Set up the test environment before all tests.
     */
    @BeforeAll 
    public static void setUpBeforeAll() {
        System.out.println("------------------------------------------------");
        System.out.println("Setting up PatientTest...");
    }

    /**
     * Tear down the test environment after all tests.
     */
    @AfterAll 
    public static void tearDownAfterAll() {
        System.out.println("Tearing down PatientTest...");
        System.out.println("------------------------------------------------");
    }

    /**
     * Test method for {@link Patient#Patient()}.
     * This test ensures that the no-args constructor correctly initializes an empty Patient object.
     */
    @Test
    @DisplayName("Test Patient class constructors and getters/setters")
    public void testPatientConstructorsAndGettersSetters() {
        // Test the no-args constructor
        Patient patient1 = new Patient();
        assertAll(
            () -> assertEquals(0, patient1.getId()),
            () -> assertNull(patient1.getName()),   
            () -> assertNull(patient1.getFirstName()),
            () -> assertNull(patient1.getNationalId()),
            () -> assertNull(patient1.getPassword()),
            () -> assertNull(patient1.getCity()),
            () -> assertNull(patient1.getPhone()),
            () -> assertNull(patient1.getAddress()),
            () -> assertNull(patient1.getGender()),
            () -> assertNull(patient1.getAccountCreated()),
            () -> assertNull(patient1.getEmail()),
            () -> assertNull(patient1.getBirthDate())
        );
    }


    /**
     * Test method for {@link Patient#Patient(int, String, String, String, String, String, String, String, String, String, LocalDate)}.
     * This test ensures that the constructor with arguments correctly initializes the fields of the Patient class.
     */
    @Test
    @DisplayName("Test Patient class constructors and getters/setters")
    public void testPatientConstructorsAndGettersSettersWithArgs() {
        // Test the constructor with arguments
        Patient patient2 = new Patient("Doe", "John", "123456789", "password", 
            "New York", "123-456-7890", "123 Main St", "Male", "2023-01-01", 
            "john.doe@example.com", java.time.LocalDate.of(1990, 1, 1));
            
        assertAll(
            () -> assertEquals("Doe", patient2.getName()),
            () -> assertEquals("John", patient2.getFirstName()),
            () -> assertEquals("123456789", patient2.getNationalId()),
            () -> assertEquals("password", patient2.getPassword()),
            () -> assertEquals("New York", patient2.getCity()),
            () -> assertEquals("123-456-7890", patient2.getPhone()),
            () -> assertEquals("123 Main St", patient2.getAddress()),
            () -> assertEquals("Male", patient2.getGender()),
            () -> assertEquals("2023-01-01", patient2.getAccountCreated()),
            () -> assertEquals("john.doe@example.com", patient2.getEmail()),
            () -> assertEquals(java.time.LocalDate.of(1990, 1, 1), patient2.getBirthDate())
        );
    }   
}