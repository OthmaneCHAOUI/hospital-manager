package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Test class for the Doctor class.
 */
class DoctorTest {

	/**
	 * Test the Doctor class constructors and getters/setters.
	 * This test ensures that the Doctor class correctly initializes its fields
	 * and that the getters and setters work as expected.
	 * It also verifies that the fields can accept null values, as no validation
	 * is currently implemented in the Doctor class.
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
	 * Test the Doctor class constructors and getters/setters.
	 * This test ensures that the Doctor class correctly initializes its fields
	 * and that the getters and setters work as expected.
	 * It also verifies that the fields can accept null values, as no validation
	 * is currently implemented in the Doctor class.
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
	 * Test the Doctor class constructors and getters/setters.
	 * This test ensures that the Doctor class correctly initializes its fields
	 * and that the getters and setters work as expected.
	 * It also verifies that the fields can accept null values, as no validation
	 * is currently implemented in the Doctor class.
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
	 * Test the Doctor class constructors and getters/setters.
	 * This test ensures that the Doctor class correctly initializes its fields
	 * and that the getters and setters work as expected.
	 * It also verifies that the fields can accept null values, as no validation
	 * is currently implemented in the Doctor class.
	 */
	@Test
	@DisplayName("Setters should update doctor fields")
	void settersShouldUpdateDoctorFields() {
		Doctor doctor = new Doctor();

		doctor.setId(2);
		doctor.setName("Brown");
		doctor.setFirstName("Lucas");
		doctor.setNationalId("DOC-TEST-002");
		doctor.setSpecialty("Dermatology");
		doctor.setClinicName("Derm Plus Clinic");
		doctor.setPhone("0612345002");
		doctor.setClinicAddress("8 Republic Avenue, Manchester");
		doctor.setPassword("doctor2");

		assertAll(
				() -> assertEquals(2, doctor.getId()),
				() -> assertEquals("Brown", doctor.getName()),
				() -> assertEquals("Lucas", doctor.getFirstName()),
				() -> assertEquals("DOC-TEST-002", doctor.getNationalId()),
				() -> assertEquals("Dermatology", doctor.getSpecialty()),
				() -> assertEquals("Derm Plus Clinic", doctor.getClinicName()),
				() -> assertEquals("0612345002", doctor.getPhone()),
				() -> assertEquals("8 Republic Avenue, Manchester", doctor.getClinicAddress()),
				() -> assertEquals("doctor2", doctor.getPassword()));
	}

	/**
	 * Test the Doctor class constructors and getters/setters.
	 * This test ensures that the Doctor class correctly initializes its fields
	 * and that the getters and setters work as expected.
	 * It also verifies that the fields can accept null values, as no validation
	 * is currently implemented in the Doctor class.
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