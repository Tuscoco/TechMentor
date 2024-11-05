package dao;

import model.Evento;
import java.sql.*;
import java.util.*;
import config.*;

public class EventoDAO {

    public void salvarEvento(Evento evento) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "INSERT INTO evento (id, nome, local, data, hora, id_materia) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, evento.getNome());
            pstmt.setString(3, evento.getLocal());
            pstmt.setString(4, evento.getData());
            pstmt.setString(5, evento.getHora());
            pstmt.setInt(6, evento.getMateria());

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT MAX(id) AS max_id FROM evento";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getInt("max_id") + 1;

            }

        }

        return 1;

    }

    public List<Evento> buscarTodos() throws SQLException{

        List<Evento> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT * FROM evento";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Evento evento = new Evento(result.getInt("id"), result.getString("nome"), result.getString("local"), result.getString("data"), result.getString("hora"), result.getInt("id_materia"));
                lista.add(evento);

            }

        }

        return lista;

    }

    public List<Evento> buscarEventosDoDia(String data) throws SQLException{

        List<Evento> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT * FROM evento WHERE data = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, data);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Evento evento = new Evento(result.getInt("id"), result.getString("nome"), result.getString("local"), result.getString("data"), result.getString("hora"), result.getInt("id_materia"));
                lista.add(evento);

            }

        }

        return lista;

    }

    public boolean removerEvento(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "DELETE FROM evento WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

}
