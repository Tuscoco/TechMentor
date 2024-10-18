package dao;

import model.Evento;
import java.sql.*;
import java.util.*;

public class EventoDAO {
    
    private String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private String user = "tech";
    private String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


    public void salvarEvento(Evento evento) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO evento (id, nome, local, data_hora, id_materia) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, evento.getNome());
            pstmt.setString(3, evento.getLocal());
            pstmt.setString(4, evento.getDataHora());
            pstmt.setInt(5, evento.getMateria());

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

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

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM evento";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Evento evento = new Evento(result.getInt("id"), result.getString("nome"), result.getString("local"), result.getString("data_hora"), result.getInt("id_materia"));
                lista.add(evento);

            }

        }

        return lista;

    }

    public List<Evento> buscarEventosDoDia(String data) throws SQLException{

        List<Evento> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM evento WHERE data_hora = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, data);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Evento evento = new Evento(result.getInt("id"), result.getString("nome"), result.getString("local"), result.getString("data_hora"), result.getInt("id_materia"));
                lista.add(evento);

            }

        }

        return lista;

    }

}
