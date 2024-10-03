package dao;

import model.Evento;
import java.sql.*;
import java.util.*;

public class EventoDAO {
    
    private String url = "jdbc:postgresql://localhost:5432/techmentor";
    private String user = "";
    private String password = "";


    public void salvarEvento(Evento evento) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO evento (id, nome, local, data_hora, cod_materia) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, evento.getNome());
            pstmt.setString(3, evento.getLocal());
            pstmt.setDate(4, evento.getData());
            pstmt.setInt(5, evento.getMateria());

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELEXT MAX(id) AS max_id FROM repositorio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getInt("max_id") + 1;

            }

        }

        return 1;

    }

    public List<Evento> getTodos() throws SQLException{

        List<Evento> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM evento";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Evento evento = new Evento(result.getInt("id"), result.getString("nome"), result.getString("local"), result.getDate("data_hora"), result.getInt("materia"));
                lista.add(evento);

            }

        }

        return lista;

    }

}
