package main.java.model;

import java.sql.*;

public class PatientDAO {
    public void addPatient(Patient patient) {
        String query = "INSERT INTO Patient (name, firstName, nationalId, password) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setString(3, patient.getNationalId());
            pstmt.setString(4, patient.getPassword());

            int rs = pstmt.executeUpdate();
            if (rs > 0) {
                System.out.println("Patient added successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Patient trouverParCnie(String nationalId) throws SQLException {
        String query = "SELECT * FROM Patient WHERE nationalId = ?";
        Patient patient = null;
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nationalId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patient(rs.getString("name"), rs.getString("firstName"), rs.getString("nationalId"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public static Patient checkPatientByCniePassword(String nationalId, String password) {
        String query = "SELECT * FROM Patient WHERE nationalId = ? AND password = ?";
        Patient patient = null;

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nationalId);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setFirstName(rs.getString("firstName"));
                patient.setNationalId(rs.getString("nationalId"));
                patient.setPassword(rs.getString("password"));
                patient.setDateNaissance(
                        rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null);
                patient.setCity(rs.getString("city"));
                patient.setEmail(rs.getString("email"));
                patient.setPhone(rs.getString("phone"));
                patient.setAddress(rs.getString("address"));
                patient.setGender(rs.getString("gender"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    public void updatePatient(Patient patient) {
        String query = "UPDATE Patient SET name=?, firstName=?, city=?, email=?, address=?, phone=?, birth_date=?, password=? WHERE nationalId=?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getFirstName());
            pstmt.setString(3, patient.getCity());
            pstmt.setString(4, patient.getEmail());
            pstmt.setString(5, patient.getAddress());
            pstmt.setString(6, patient.getPhone());
            if (patient.getDateNaissance() != null)
                pstmt.setDate(7, Date.valueOf(patient.getDateNaissance()));
            else
                pstmt.setNull(7, java.sql.Types.DATE);
            pstmt.setString(8, patient.getPassword());
            pstmt.setString(9, patient.getNationalId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}