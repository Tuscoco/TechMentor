package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Materia;

public class MateriaDAO {
    
    private static final String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private static final String user = "tech";
    private static final String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


    public List<Materia> exibirMaterias() throws SQLException{

        List<Materia> materias = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){
            
            String sql = "SELECT * FROM materia";
            PreparedStatement materia_pstmt = conn.prepareStatement(sql);
            ResultSet result = materia_pstmt.executeQuery();
            while (result.next()) {
               Materia materia = new Materia(result.getInt("id_materia"),result.getString("nome"));
                materias.add(materia);
            }
        }

        return materias;
    }

    public static Materia findMateria(int id) throws SQLException{
        Materia materia;
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            
            String sql = "SELECT * FROM materia WHERE id_materia = ?";
            PreparedStatement materia_pstmt = conn.prepareStatement(sql);
            materia_pstmt.setInt(1, id);
            ResultSet result = materia_pstmt.executeQuery();
            materia = new Materia(result.getInt("id_materia"),result.getString("nome") );

            
        }
        return materia;
    }

    public static List<Materia> findMateriaByName(String name) throws SQLException{
        List<Materia> materias = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            
            String sql = "SELECT * FROM materia WHERE nome = ?";
            PreparedStatement materia_pstmt = conn.prepareStatement(sql);
            materia_pstmt.setString(1, "name");
            ResultSet result = materia_pstmt.executeQuery();
            while(result.next()){
                materias.add(new Materia(result.getInt("id_materia"),result.getString("nome") ));
            }

            
        }
        return materias;
    }

}
