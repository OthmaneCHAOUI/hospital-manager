package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationDAO
{
    public static List<Medication> getMedications(String nationalIdPatient, int idDoctor) {
        return new MedicationDAO().getMedicationsByPatientAndDoctor(nationalIdPatient, idDoctor);
    }

    public List<Medication> getMedicationsByPatientAndDoctor(String nationalIdPatient, int idDoctor) {
        List<Medication> medications = new ArrayList<>();
        String query = "SELECT * FROM Medication WHERE patient_national_id = ? AND id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nationalIdPatient);
            pstmt.setInt(2, idDoctor);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Medication m = new Medication(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("patient_national_id"),
                    rs.getString("name"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getInt("duration"),
                    rs.getString("description")
                );
                medications.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medications;
    }

     public static void delete(int id) {
    String sql = "DELETE FROM Medication WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("The medication was deleted successfully for this patient.");
        } else {
            System.out.println("An error occurred; please try again.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void editMedication(Medication medication) throws SQLException{
        String sql = "UPDATE Medication SET name=?, start_date=?, description=?,duration=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, medication.getName());
            pstmt.setDate(2, java.sql.Date.valueOf(medication.getStartDate()));
            pstmt.setString(3, medication.getDescription());
            pstmt.setInt(4, medication.getDuration());
            pstmt.setInt(5, medication.getId());

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
    public static void insert(Medication medication, String nationalIdPatient, int idDoctor) throws SQLException {
    String sql = "INSERT INTO Medication (name, start_date, duration, description, patient_national_id, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, medication.getName());
        pstmt.setDate(2, java.sql.Date.valueOf(medication.getStartDate()));
        pstmt.setInt(3, medication.getDuration());
        pstmt.setString(4, medication.getDescription());
        pstmt.setString(5, nationalIdPatient);
        pstmt.setInt(6, idDoctor);

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Medication inserted successfully.");
        } else {
            System.out.println("Failed to insert medication.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}