package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import config.*;
import java.util.List;

import model.Materia;

public class MateriaDAO {
    
    private String url;
    private String user;
    private String password;

    public MateriaDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }


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
        //Collections.sort(materias);
        return materias;
    }

    public Materia findMateriaById(int id) throws SQLException{
        Materia materia = null;
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            
            String sql = "SELECT * FROM materia WHERE id_materia = ?";
            PreparedStatement materia_pstmt = conn.prepareStatement(sql);
            materia_pstmt.setInt(1, id);
            ResultSet result = materia_pstmt.executeQuery();
            
            if(result.next()){

                materia = new Materia(result.getInt("id_materia"),result.getString("nome"));

            }

            
        }
        return materia;
    }

    public List<Materia> findMateriaByName(String name) throws SQLException{
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
