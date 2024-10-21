package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.*;

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

}
