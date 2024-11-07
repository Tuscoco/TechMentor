package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.*;
import model.Monitoria;

public class MonitorDAO{

    public void adicionarMonitor(Monitoria monitor) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "INSERT INTO monitoria (id_monitor, id_materia, online) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, monitor.getIdMonitor());
            pstmt.setInt(2, monitor.getIdMateria());
            pstmt.setBoolean(3, false);

            pstmt.executeUpdate();
        }

    }

    public boolean removerMonitor(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "DELETE FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            int removido = pstmt.executeUpdate();

            return removido > 0;
            
        }
    }

    public List<Monitoria> getMonitoresOnline() throws SQLException{

        List<Monitoria> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

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

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT id_monitor, id_materia FROM monitoria WHERE online IS DISTINCT FROM true";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Monitoria monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"));
                lista.add(monitor);

            }

        }

        return lista;

    }

    public void ficarOnline(int id) throws SQLException{
    
        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET online = true, sala = 1101 WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        }

    }

    public void ficarOffline(int id) throws SQLException{
    
        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET online = false WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        }

    }

    public void alterarSala(int id, int sala) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET sala = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, sala);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

        }

    }

    public int getSala(int id) throws SQLException{

        int sala = 0;

        try(Connection conn = DataBaseConnection.getConnection()){

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

        try(Connection conn = DataBaseConnection.getConnection()){

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

        try(Connection conn = DataBaseConnection.getConnection()){

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

    public boolean alterarFoto(int id, String url1, String url2) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET foto1 = ?, foto2 = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, url1);
            pstmt.setString(2, url2);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public List<String> getFotos(int id) throws SQLException{

        List<String> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT foto1, foto2 FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                lista.add(result.getString("foto1"));
                lista.add(result.getString("foto2"));

            }

        }

        return lista;

    }

}

