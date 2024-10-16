package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Monitoria;

public class MonitorDAO {

    private static final String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private static final String user = "tech";
    private static final String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";

    public void adicionarMonitor(Monitoria monitor) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO monitoria (id_monitor, id_materia, sala, materia_principal) VALUES (?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, monitor.getIdMonitor());
            pstmt.setInt(2, monitor.getIdMateria());
            pstmt.setInt(3, 1101);
            pstmt.setBoolean(4, monitor.getIsMateriaprincipal());

            pstmt.executeUpdate();
        }
    }

    public void removerMonitor(Monitoria monitor) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM monitoria WHERE id_monitor = ? AND id_materia = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, monitor.getIdMonitor());
            pstmt.setInt(2, monitor.getIdMateria());
            pstmt.executeUpdate();
        }
    }

    public List<Monitoria> getTodos() throws SQLException {
        List<Monitoria> monitores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM monitoria";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                monitores.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
                        result.getInt("sala"), result.getBoolean("materia_principal")));

            }
        }
        return monitores;
    }

    public List<Monitoria> getMonitor(int id) throws SQLException {
        List<Monitoria> monitor = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();


            while (result.next()) {
                monitor.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
                        result.getInt("sala"), result.getBoolean("materia_principal")));

            }
        }
        return monitor;
    }


    public List<Monitoria> getMonitorPorMateria(int id) throws SQLException {
        List<Monitoria> monitores = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM monitoria WHERE id_materia = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                monitores.add(new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"),
                        result.getInt("sala"), result.getBoolean("materia_principal")));

            }
        }
        return monitores;
    }

    public void ficarOnline(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE monitoria SET online = true WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void alterarSala(int sala, int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE monitoria SET sala = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sala);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void ficarOffline(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "UPDATE monitoria SET online = false, sala = 1101 WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

