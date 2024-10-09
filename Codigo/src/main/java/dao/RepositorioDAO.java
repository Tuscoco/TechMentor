package dao;

import model.Repositorio;
import java.sql.*;
import java.util.*;

public class RepositorioDAO {
 
    private String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private String user = "tech";
    private String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


    public void salvarRepositorio(Repositorio repositorio) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO repositorio (id, nome, link) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, repositorio.getNome());
            pstmt.setString(3, repositorio.getLink());

            pstmt.executeUpdate();

        }

    }

    public void deletarRepositorio(Repositorio repositorio) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "DELETE FROM repositorio WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, repositorio.getId());

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT MAX(id) AS max_id FROM repositorio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getInt("max_id") + 1;

            }

        }

        return 1;

    }

    public List<Repositorio> getTodos() throws SQLException{

        List<Repositorio> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM repositorio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Repositorio repositorio = new Repositorio(result.getInt("id"), result.getString("name"), result.getString("link"));
                lista.add(repositorio);

            }

        }

        return lista;

    }

    public List<Repositorio> getAlguns(int cod_materia) throws SQLException{

        List<Repositorio> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM repositorio WHERE cod_materia = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, cod_materia);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Repositorio repositorio = new Repositorio(result.getInt("id"), result.getString("name"), result.getString("link"));
                lista.add(repositorio);

            }

        }

        return lista;

    }
    
}
