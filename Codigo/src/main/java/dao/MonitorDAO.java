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

    private List<Monitoria> cacheMonitoresOnline;
    private List<Monitoria> cacheMonitoresOffline;

    public void adicionarMonitor(Monitoria monitor) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "INSERT INTO monitoria (id_monitor, id_materia, online) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, monitor.getIdMonitor());
            pstmt.setInt(2, monitor.getIdMateria());
            pstmt.setBoolean(3, false);

            pstmt.executeUpdate();

            atualizarCacheMonitores();

        }

    }

    public boolean removerMonitor(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "DELETE FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            int removido = pstmt.executeUpdate();

            atualizarCacheMonitores();

            return removido > 0;
            
        }
    }

    public List<Monitoria> getMonitoresOnline() throws SQLException{

        List<Monitoria> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT id_monitor, id_materia, sala, entrada, saida FROM monitoria WHERE online = true";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Monitoria monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"), result.getInt("sala"), result.getString("entrada"), result.getString("saida"));
                lista.add(monitor);

            }

        }

        return lista;

    }

    public List<Monitoria> getMonitoresOffline() throws SQLException{

        List<Monitoria> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT id_monitor, id_materia, sala, entrada, saida FROM monitoria WHERE online IS DISTINCT FROM true";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Monitoria monitor = new Monitoria(result.getInt("id_monitor"), result.getInt("id_materia"), result.getInt("sala"), result.getString("entrada"), result.getString("saida"));
                lista.add(monitor);

            }

        }

        return lista;

    }

    public void setHorarios(int id, String entrada, String saida) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET entrada = ?, saida = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, entrada);
            pstmt.setString(2, saida);
            pstmt.setInt(3, id);

            pstmt.executeUpdate();

        }

    }

    public boolean ficarOnline(int id, String horario) throws SQLException{
    
        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET online = true, sala = 1101 WHERE id_monitor = ? AND CAST(? AS TIME) BETWEEN CAST(entrada AS TIME) AND CAST(saida AS TIME)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setString(2, horario);

            int alterado = pstmt.executeUpdate();

            atualizarCacheMonitores();

            return alterado > 0;

        }

    }

    public void ficarOffline(int id) throws SQLException{
    
        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET online = false, sala = NULL WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            atualizarCacheMonitores();

        }

    }

    public void alterarSala(int id, int sala) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE monitoria SET sala = ? WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, sala);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

            atualizarCacheMonitores();

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

    public List<String> getHorarios(int id) throws SQLException{

        List<String> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT entrada, saida FROM monitoria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                lista.add(result.getString("entrada"));
                lista.add(result.getString("saida"));

            }

        }

        return lista;

    }

    public List<Monitoria> getOnline() throws SQLException{

        if(cacheMonitoresOnline == null){

            cacheMonitoresOnline = getMonitoresOnline();

        }

        return cacheMonitoresOnline;

    }

    public List<Monitoria> getOffline() throws SQLException{

        if(cacheMonitoresOffline == null){

            cacheMonitoresOffline = getMonitoresOffline();

        }

        return cacheMonitoresOffline;

    }

    public void atualizarCacheMonitores() throws SQLException{

        cacheMonitoresOnline = getMonitoresOnline();
        cacheMonitoresOffline = getMonitoresOffline();

    }

}

