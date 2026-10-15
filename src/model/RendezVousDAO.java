package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles appointment read and write operations for the clinic application.
 */
public class RendezVousDAO {
    public static List<RendezVous> getRendezVous(String cnie, int idDoctor) {
        return new RendezVousDAO().getRendezVousByPatientAndDoctor(cnie, idDoctor);
    }
    
    public List<RendezVous> getRendezVousByPatientAndDoctor(String cnie, int idDoctor) {
        List<RendezVous> rdvs = new ArrayList<>();
        String sql = "SELECT * FROM Rendez_vous WHERE cnie_patient = ? AND id_doctor = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cnie);
            stmt.setInt(2, idDoctor);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RendezVous r = new RendezVous(
                    rs.getInt("id"),
                    rs.getInt("id_doctor"),
                    rs.getString("cnie_patient"),
                    rs.getDate("date_rendez_vous").toLocalDate(),
                    rs.getTime("heure").toLocalTime(),
                    rs.getString("raison"),
                    rs.getString("statut")
                );
                rdvs.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rdvs;
    }
    
    
    public static void delete(int id) {
    String sql = "DELETE FROM Rendez_vous WHERE id = ?";
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
     public static void editerTest(RendezVous rendezVous)  throws SQLException{
        String sql = "UPDATE Rendez_vous SET date_rendez_vous=? , heure=? , raison=? , statut=?  WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, java.sql.Date.valueOf(rendezVous.getDateRendezVous()));
            pstmt.setTime(2, java.sql.Time.valueOf(rendezVous.getHeure()));
            pstmt.setString(3, rendezVous.getRaison());
            pstmt.setString(4, rendezVous.getStatut());
            pstmt.setInt(5, rendezVous.getId());
            
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
    public static void inserer(RendezVous rendezVous, String cniePatient, int idDoctor) throws SQLException {
    String sql = "INSERT INTO Rendez_vous (date_rendez_vous, heure, raison, statut, cnie_patient, id_doctor) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setDate(1, Date.valueOf(rendezVous.getDateRendezVous()));
        pstmt.setTime(2, Time.valueOf(rendezVous.getHeure()));
        pstmt.setString(3, rendezVous.getRaison());
        pstmt.setString(4, rendezVous.getStatut());
        pstmt.setString(5, cniePatient);
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
