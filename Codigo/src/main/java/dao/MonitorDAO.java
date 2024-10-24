package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.*;
import model.Monitoria;

public class MonitorDAO{

    private String url;
    private String user;
    private String password;

    public MonitorDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }

    public void adicionarMonitor(Monitoria monitor) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO monitoria (id_monitor, id_materia, online) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, monitor.getIdMonitor());
            pstmt.setInt(2, monitor.getIdMateria());
            pstmt.setBoolean(3, false);

            pstmt.executeUpdate();
        }

    }

    public boolean removerMonitor(int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "DELETE FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            int removido = pstmt.executeUpdate();

            return removido > 0;
            
        }
    }

    public List<Monitoria> getMonitoresOnline() throws SQLException{

        List<Monitoria> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id_monitor, id_materia FROM monitoria WHERE online = true";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Monitoria monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"));
                lista.add(monitor);

            }

        }

        return lista;

    }

    public List<Monitoria> getMonitoresOffline() throws SQLException{

        List<Monitoria> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id_monitor, id_materia FROM monitoria WHERE online = false";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Monitoria monitor = new Monitoria(result.getInt("id"), result.getInt("id_materia"));
                lista.add(monitor);

            }

        }

        return lista;

    }

    public void ficarOnline(int id) throws SQLException{
    
        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE monitoria SET online = true, sala = 1101 WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        }

    }

    public void ficarOffline(int id) throws SQLException{
    
        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE monitoria SET online = false WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        }

    }

    public void alterarSala(int id, int sala) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE monitoria SET sala = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, sala);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

        }

    }

    public int getSala(int id) throws SQLException{

        int sala = 0;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT sala FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                sala = result.getInt("sala");

            }

        }

        return sala;

    }

    public boolean getOnline(int id) throws SQLException{

        boolean online = false;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT online FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                online = result.getBoolean("online");

            }

        }

        return online;

    }

    public int getMateriaMonitor(int id) throws SQLException{

        int materia = -1;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id_materia FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                materia = result.getInt("id_materia");

            }

        }

        return materia;

    }

}

