package dao;

import model.Repositorio;
import java.sql.*;
import java.util.*;
import config.*;

public class RepositorioDAO {

    public void salvarRepositorio(Repositorio repositorio) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "INSERT INTO repositorio (id, nome, link) VALUES (?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setString(2, repositorio.getNome());
            pstmt.setString(3, repositorio.getLink());

            pstmt.executeUpdate();

        }

    }

    public void deletarRepositorio(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "DELETE FROM repositorio WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

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

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT * FROM repositorio";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Repositorio repositorio = new Repositorio(result.getInt("id"), result.getString("nome"), result.getString("link"));
                lista.add(repositorio);

            }

        }

        lista = ordenar(lista);

        return lista;

    }

    public List<Repositorio> ordenar(List<Repositorio> lista){

        int n = lista.size();


        for(int i = 1;i < n;i++){

            Repositorio temp = lista.get(i);
            int j = i - 1;

            while((j >= 0) && (lista.get(j).getNome().compareTo(temp.getNome()) > 0)){

                lista.set(j + 1, lista.get(j));
                j--;

            }

            lista.set(j + 1, temp);

        }

        return lista;

    }
    
}
