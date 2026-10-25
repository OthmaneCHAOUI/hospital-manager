package main.java.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorPatientDAO {
    public static List<Doctor> getDoctorsByPatientNationalId(String nationalId) throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = """
                    SELECT d.* FROM Doctor d
                    JOIN DoctorPatient dp ON d.id = dp.id_doctor
                    WHERE dp.nationalId = ?
                """;
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nationalId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Doctor doc = new Doctor();
                doc.setId(rs.getInt("id"));
                doc.setName(rs.getString("name"));
                doc.setFirstName(rs.getString("firstName"));
                doc.setNationalId(rs.getString("nationalId"));
                doc.setSpecialty(rs.getString("specialty"));
                doc.setClinicName(rs.getString("clinic_name"));
                doc.setPhone(rs.getString("phone"));
                doc.setClinicAddress(rs.getString("clinic_address"));
                doc.setPassword(rs.getString("password"));
                doctors.add(doc);
            }
        }
        return doctors;
    }

    public static void insert(DoctorPatient patient, int idDoctor) {
        String sql = "INSERT INTO DoctorPatient (name, firstName, birth_date, nationalId, city, phone, address, gender, id_doctor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setDate(3, java.sql.Date.valueOf(patient.getDateNaissance()));
            pstmt.setString(4, patient.getNationalId());
            pstmt.setString(5, patient.getCity());
            pstmt.setString(6, patient.getPhone());
            pstmt.setString(7, patient.getAddress());
            pstmt.setString(8, patient.getGender());
            pstmt.setInt(9, idDoctor);

            pstmt.executeUpdate();

            System.out.println("Patient added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DoctorPatient> getPatients(int idDoctor) throws SQLException {
        List<DoctorPatient> doctorPatients = new ArrayList<>();
        String sql = "SELECT name,firstName,birth_date,nationalId,city,phone,address,gender FROM DoctorPatient WHERE id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoctor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DoctorPatient doctorPatient = new DoctorPatient(
                        rs.getString("name"),
                        rs.getString("firstName"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("nationalId"),
                        rs.getString("city"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("gender"));
                doctorPatients.add(doctorPatient);
            }
        }
        return doctorPatients;
    }

    public static void update(DoctorPatient newDoctorPatient, String nationalIdPatient, int idDoctor) {
        String sql = "UPDATE DoctorPatient SET name=?, firstName=?, birth_date=?,city=?,phone=?,address=?,gender=? WHERE nationalId=? AND id_doctor=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newDoctorPatient.getName());
            pstmt.setString(2, newDoctorPatient.getFirstName());
            pstmt.setDate(3, java.sql.Date.valueOf(newDoctorPatient.getDateNaissance()));
            pstmt.setString(4, newDoctorPatient.getCity());
            pstmt.setString(5, newDoctorPatient.getPhone());
            pstmt.setString(6, newDoctorPatient.getAddress());
            pstmt.setString(7, newDoctorPatient.getGender());
            pstmt.setString(8, nationalIdPatient);
            pstmt.setInt(9, idDoctor);

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

    public static void delete(String nationalIdPatient, int idDoctor) {
        String sql = "DELETE FROM DoctorPatient WHERE nationalId = ? AND id_doctor = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nationalIdPatient);
            pstmt.setInt(2, idDoctor);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("The patient was removed from this doctor successfully.");
            } else {
                System.out.println("No patient was found with this national ID and doctor ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}