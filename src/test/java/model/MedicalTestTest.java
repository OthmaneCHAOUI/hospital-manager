package model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;

/**
 * Test class for {@link MedicalTest}.
 * @author OthmaneCHAOUI
 */
@DisplayName("MedicalTest Class Unit Tests")
public class MedicalTestTest {
    /**
     * Sets up the test class before all tests are run.
     * @throws Exception
     */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        System.out.println("-----------------------------------------------------");
        System.out.println("Setting up MedicalTestTest class...");
    }
    
    /**
     * Tears down the test class after all tests have been run.
     * @throws Exception
     */
    @AfterAll
    public static void tearDownAfterClass() throws Exception {
        System.out.println("Tearing down MedicalTestTest class...");
        System.out.println("-----------------------------------------------------");
    }

    /**
     * Test method for {@link MedicalTest#MedicalTest()}.
     * This test verifies that the no-args constructor of the MedicalTest class creates an object
     */
    @Test
    @DisplayName("no-args constructor should create an empty MedicalTest object")
    public void testNoArgsConstructorShouldCreateEmptyMedicalTest() {
        MedicalTest medicalTest = new MedicalTest();

        assertNotNull(medicalTest, "MedicalTest object should not be null");
        assertEquals(0, medicalTest.getId(), "Default id should be 0");
        assertEquals(0, medicalTest.getIdDoctor(), "Default idDoctor should be 0");
        assertNull(medicalTest.getNationalIdPatient(), "Default nationalIdPatient should be null");
        assertNull(medicalTest.getTestType(), "Default testType should be null");
        assertNull(medicalTest.getResult(), "Default result should be null");
        assertNull(medicalTest.getDoctorType(), "Default doctorType should be null");
        assertNull(medicalTest.getStatus(), "Default status should be null");
        
    }


    /**
     * Test method for {@link MedicalTest#MedicalTest(int, int, String, String, String, localDateTime, String, String)}.
     * This test verifies that the valued constructor of the MedicalTest class creates an object with specified
     */
    @Test
    @DisplayName ("Valued constructor should create a MedicalTest object with specified values")
    public void ValuedConstructorShouldCreateMedicalTestWithValues() {
        int id = 1;
        int idDoctor = 2;
        String nationalIdPatient = "123456789";
        String testType = "Blood Test";
        String result = "Positive";
        LocalDate testDate = LocalDate.of(2024, 6, 1);
        String doctorType = "General Practitioner";
        String status = "Completed";

        assertAll(
                () -> {
                    MedicalTest medicalTest = new MedicalTest(id, idDoctor, nationalIdPatient, testType, result, testDate, doctorType, status);
                    assertEquals(id, medicalTest.getId());
                    assertEquals(idDoctor, medicalTest.getIdDoctor());
                    assertEquals(nationalIdPatient, medicalTest.getNationalIdPatient());
                    assertEquals(testType, medicalTest.getTestType());
                    assertEquals(result, medicalTest.getResult());
                    assertEquals(doctorType, medicalTest.getDoctorType());
                    assertEquals(status, medicalTest.getStatus());
                }
        );
    }

    /**
     * Test method for getters and setters of the MedicalTest class.
     * This test verifies that the getters and setters of the MedicalTest class work correctly.
     */
    @Test
    @DisplayName("Getters and Setters should work correctly")
    public void gettersAndSettersShouldWorkCorrectly() {
        MedicalTest medicalTest = new MedicalTest();

        medicalTest.setId(1);
        medicalTest.setDoctorId(2);
        medicalTest.setNationalIdPatient("123456789");
        medicalTest.setTestType("Blood Test");
        medicalTest.setResult("Positive");
        medicalTest.setTestDate(LocalDate.of(2024, 6, 1));
        medicalTest.setDoctorType("General Practitioner");
        medicalTest.setStatus("Completed");

        assertAll(
                () -> assertEquals(1, medicalTest.getId()),
                () -> assertEquals(2, medicalTest.getIdDoctor()),
                () -> assertEquals("123456789", medicalTest.getNationalIdPatient()),
                () -> assertEquals("Blood Test", medicalTest.getTestType()),
                () -> assertEquals("Positive", medicalTest.getResult()),
                () -> assertEquals(LocalDate.of(2024, 6, 1), medicalTest.getTestDate()),
                () -> assertEquals("General Practitioner", medicalTest.getDoctorType()),
                () -> assertEquals("Completed", medicalTest.getStatus())
        );
    }
}