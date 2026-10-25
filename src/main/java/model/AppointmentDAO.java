package main.java.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles appointment read and write operations for the clinic application.
 */
public class AppointmentDAO {
    public static List<Appointment> getAppointment(String nationalId, int idDoctor) {
        return new AppointmentDAO().getAppointmentByPatientAndDoctor(nationalId, idDoctor);
    }

    public List<Appointment> getAppointmentByPatientAndDoctor(String nationalId, int idDoctor) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment WHERE patient_national_id = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nationalId);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Appointment r = new Appointment(
                        rs.getInt("id"),
                        rs.getInt("id_doctor"),
                        rs.getString("patient_national_id"),
                        rs.getDate("appointment_date").toLocalDate(),
                        rs.getTime("time").toLocalTime(),
                        rs.getString("reason"),
                        rs.getString("status"));
                appointments.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    public static void delete(int id) {
        String sql = "DELETE FROM Appointment WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The appointment was deleted successfully for this patient.");
            } else {
                System.out.println("An error occurred; please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editTest(Appointment appointment) throws SQLException {
        String sql = "UPDATE Appointment SET appointment_date=? , time=? , reason=? , status=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(appointment.getDateAppointment()));
            pstmt.setTime(2, java.sql.Time.valueOf(appointment.getTime()));
            pstmt.setString(3, appointment.getReason());
            pstmt.setString(4, appointment.getStatus());
            pstmt.setInt(5, appointment.getId());

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("The patient was reassigned to this doctor successfully.");
            } else {
                System.out.println("No patient was found with this national ID and doctor ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insert(Appointment appointment, String nationalIdPatient, int idDoctor) throws SQLException {
        String sql = "INSERT INTO Appointment (appointment_date, time, reason, status, patient_national_id, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(appointment.getDateAppointment()));
            pstmt.setTime(2, Time.valueOf(appointment.getTime()));
            pstmt.setString(3, appointment.getReason());
            pstmt.setString(4, appointment.getStatus());
            pstmt.setString(5, nationalIdPatient);
            pstmt.setInt(6, idDoctor);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Appointment added successfully!");
            } else {
                System.out.println("Failed to insert appointment.");
            }
        }
    }

}
