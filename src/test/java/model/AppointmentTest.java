package model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Appointment}.
 */
@DisplayName("Appointment Class Unit Tests")
class AppointmentTest {

    @Test
    @DisplayName("No-args constructor should create an empty appointment")
    void noArgsConstructorShouldCreateEmptyAppointment() {
        Appointment appointment = new Appointment();

        assertAll(
                () -> assertEquals(0, appointment.getId()),
                () -> assertEquals(0, appointment.getIdDoctor()),
                () -> assertNull(appointment.getNationalIdPatient()),
                () -> assertNull(appointment.getDateAppointment()),
                () -> assertNull(appointment.getTime()),
                () -> assertNull(appointment.getReason()),
                () -> assertNull(appointment.getStatus()));
    }

    @Test
    @DisplayName("Full constructor should set all appointment fields")
    void fullConstructorShouldSetAllFields() {
        LocalDate date = LocalDate.of(2026, 11, 19);
        LocalTime time = LocalTime.of(10, 30);
        Appointment appointment = new Appointment(
                7, 12, "PAT-001", date, time, "Follow-up", "Scheduled");

        assertAll(
                () -> assertEquals(7, appointment.getId()),
                () -> assertEquals(12, appointment.getIdDoctor()),
                () -> assertEquals("PAT-001", appointment.getNationalIdPatient()),
                () -> assertEquals(date, appointment.getDateAppointment()),
                () -> assertEquals(time, appointment.getTime()),
                () -> assertEquals("Follow-up", appointment.getReason()),
                () -> assertEquals("Scheduled", appointment.getStatus()));
    }

    @Test
    @DisplayName("Setters should update every appointment field")
    void settersShouldUpdateEveryField() {
        Appointment appointment = new Appointment();
        LocalDate date = LocalDate.of(2027, 1, 15);
        LocalTime time = LocalTime.of(14, 45);

        appointment.setId(42);
        appointment.setDoctorId(8);
        appointment.setNationalIdPatient("PAT-042");
        appointment.setDateAppointment(date);
        appointment.setTime(time);
        appointment.setReason("Consultation");
        appointment.setStatus("Confirmed");

        assertAll(
                () -> assertEquals(42, appointment.getId()),
                () -> assertEquals(8, appointment.getIdDoctor()),
                () -> assertEquals("PAT-042", appointment.getNationalIdPatient()),
                () -> assertEquals(date, appointment.getDateAppointment()),
                () -> assertEquals(time, appointment.getTime()),
                () -> assertEquals("Consultation", appointment.getReason()),
                () -> assertEquals("Confirmed", appointment.getStatus()));
    }
}
