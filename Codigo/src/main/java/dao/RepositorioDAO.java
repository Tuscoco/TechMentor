package dao;

import model.Repositorio;
import java.sql.*;
import java.util.*;

public class RepositorioDAO {
 
    private String url = "jdbc:postgresql://localhost:5432/techmentor";
    private String user = "";
    private String password = "";


    public void salvarRepositorio(Repositorio repositorio) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO repositorio (id, nome, link, descricao, cod_materia) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, repositorio.getNome());
            pstmt.setString(3, repositorio.getLink());
            pstmt.setString(4, repositorio.getDescricao());
            pstmt.setInt(5, repositorio.getIdMateria());

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

    public List<Repositorio> getTodos() throws SQLException{

        List<Repositorio> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM repositorio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Repositorio repositorio = new Repositorio(result.getInt("id"), result.getString("name"), result.getString("link"), result.getString("descricao"), result.getInt("cod_materia"));
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

                Repositorio repositorio = new Repositorio(result.getInt("id"), result.getString("name"), result.getString("link"), result.getString("descricao"), result.getInt("cod_materia"));
                lista.add(repositorio);

            }

        }

        return lista;

    }
    
}
