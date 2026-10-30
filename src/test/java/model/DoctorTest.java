package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for {@link Doctor}.
 * @author OthmaneCHAOUI
 */
@DisplayName("Doctor Class Unit Tests")
class DoctorTest {

	/**
	 * Setup method to run before all tests in this class.
	 * @throws Exception if any error occurs during setup
	 */
	@BeforeAll
	static void setupBeforeAll() throws Exception {
		System.out.println("-------------------------------------------------");
		System.out.println("Setting up Doctor Tests");
	}

	/**
	 * Teardown method to run after all tests in this class.
	 * @throws Exception
	 */
	@AfterAll
	static void tearDownAfterAll() throws Exception {
		System.out.println("Tearing down Doctor Tests");
		System.out.println("-------------------------------------------------");
	}
	
	/**
	 * Test method for {@link Doctor#Doctor()}.
	 * This test verifies that the no-args constructor of the Doctor class creates an object with default values.
	 */
	@Test
	@DisplayName("No-args constructor should create an empty doctor")
	void noArgsConstructorShouldCreateEmptyDoctor() {
		Doctor doctor = new Doctor();

		assertAll(
				() -> assertEquals(0, doctor.getId()),
				() -> assertNull(doctor.getName()),
				() -> assertNull(doctor.getFirstName()),
				() -> assertNull(doctor.getNationalId()),
				() -> assertNull(doctor.getSpecialty()),
				() -> assertNull(doctor.getClinicName()),
				() -> assertNull(doctor.getPhone()),
				() -> assertNull(doctor.getClinicAddress()),
				() -> assertNull(doctor.getPassword()));
	}

	/**
	 * Test method for {@link Doctor#Doctor(int, String, String, String)}.
	 * This test ensures that the basic constructor correctly initializes the basic fields of the Doctor class.
	 */
	@Test
	@DisplayName("Basic constructor should set only the basic doctor fields")
	void basicConstructorShouldSetBasicFields() {
		Doctor doctor = new Doctor(
				1,
				"Smith",
				"Olivia",
				"Cardiology");

		assertAll(
				() -> assertEquals(1, doctor.getId()),
				() -> assertEquals("Smith", doctor.getName()),
				() -> assertEquals("Olivia", doctor.getFirstName()),
				() -> assertEquals("Cardiology", doctor.getSpecialty()),
				() -> assertNull(doctor.getNationalId()),
				() -> assertNull(doctor.getClinicName()),
				() -> assertNull(doctor.getPhone()),
				() -> assertNull(doctor.getClinicAddress()),
				() -> assertNull(doctor.getPassword()));
	}

	/**
	 * Test method for {@link Doctor#Doctor(String, String, String, String, String, String, String, String)}.
	 * This test ensures that the full constructor correctly initializes all fields of the Doctor class.
	 */
	@Test
	@DisplayName("Full constructor should set all doctor fields")
	void fullConstructorShouldSetAllFields() {
		Doctor doctor = new Doctor(
				"Smith",
				"Olivia",
				"DOC-TEST-001",
				"Cardiology",
				"Heart Care Clinic",
				"0612345001",
				"12 Victoria Street, London",
				"doctor1");

		assertAll(
				() -> assertEquals("Smith", doctor.getName()),
				() -> assertEquals("Olivia", doctor.getFirstName()),
				() -> assertEquals("DOC-TEST-001", doctor.getNationalId()),
				() -> assertEquals("Cardiology", doctor.getSpecialty()),
				() -> assertEquals("Heart Care Clinic", doctor.getClinicName()),
				() -> assertEquals("0612345001", doctor.getPhone()),
				() -> assertEquals("12 Victoria Street, London", doctor.getClinicAddress()),
				() -> assertEquals("doctor1", doctor.getPassword()));
	}

	/**
	 * Test method to verify that the Doctor class fields can accept null values.
	 */
	@Test
	@DisplayName("Doctor fields should accept null values because no validation exists yet")
	void doctorFieldsShouldAcceptNullValues() {
		Doctor doctor = new Doctor();

		doctor.setName(null);
		doctor.setFirstName(null);
		doctor.setNationalId(null);
		doctor.setSpecialty(null);
		doctor.setClinicName(null);
		doctor.setPhone(null);
		doctor.setClinicAddress(null);
		doctor.setPassword(null);

		assertAll(
				() -> assertNull(doctor.getName()),
				() -> assertNull(doctor.getFirstName()),
				() -> assertNull(doctor.getNationalId()),
				() -> assertNull(doctor.getSpecialty()),
				() -> assertNull(doctor.getClinicName()),
				() -> assertNull(doctor.getPhone()),
				() -> assertNull(doctor.getClinicAddress()),
				() -> assertNull(doctor.getPassword()));
	}
}