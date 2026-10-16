package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalTestDAO {
    public static List<MedicalTest> getTests(String nationalId, int idDoctor) {
        return new MedicalTestDAO().getTestsByPatientAndDoctor(nationalId, idDoctor);
    }

    public List<MedicalTest> getTestsByPatientAndDoctor(String nationalId, int idDoctor) {
        List<MedicalTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM MedicalTest WHERE patient_national_id = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nationalId);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                MedicalTest t = new MedicalTest(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("patient_national_id"),
                    rs.getString("test_type"),
                    rs.getString("result"),
                    rs.getDate("test_date").toLocalDate(),
                    rs.getString("doctor_type"),
                    rs.getString("status")
                );
                tests.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

     public static void delete(int id) {
    String sql = "DELETE FROM MedicalTest WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("The medical test was deleted successfully for this patient.");
        } else {
            System.out.println("An error occurred; please try again.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
     public static void editTest(MedicalTest medicalTest) throws SQLException {
        String sql = "UPDATE MedicalTest SET test_type=?, test_date=?, result=?,doctor_type=?,status=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, medicalTest.getTestType());
            pstmt.setDate(2, java.sql.Date.valueOf(medicalTest.getTestDate()));
            pstmt.setString(3, medicalTest.getResult());
            pstmt.setString(4, medicalTest.getDoctorType());
            pstmt.setString(5, medicalTest.getStatus());
            pstmt.setInt(6, medicalTest.getId());

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
    public static void insert(MedicalTest medicalTest, String nationalIdPatient, int idDoctor)  throws SQLException{
    String sql = "INSERT INTO MedicalTest (test_type, result, test_date, doctor_type, status, patient_national_id, id_doctor) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, medicalTest.getTestType());
        pstmt.setString(2, medicalTest.getResult());
        pstmt.setDate(3, java.sql.Date.valueOf(medicalTest.getTestDate()));
        pstmt.setString(4, medicalTest.getDoctorType());
        pstmt.setString(5, medicalTest.getStatus());
        pstmt.setString(6, nationalIdPatient);
        pstmt.setInt(7, idDoctor);

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("Medical test added successfully!");
        } else {
            System.out.println("Failed to add medical test.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
