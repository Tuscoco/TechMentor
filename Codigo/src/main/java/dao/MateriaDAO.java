package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.*;

import model.Materia;

public class MateriaDAO {

    public Materia findMateriaById(int id) throws SQLException{
        Materia materia = null;
        try(Connection conn = DataBaseConnection.getConnection()){
            
            String sql = "SELECT nome FROM materia WHERE id_materia = ?";
            PreparedStatement materia_pstmt = conn.prepareStatement(sql);
            materia_pstmt.setInt(1, id);
            ResultSet result = materia_pstmt.executeQuery();
            
            if(result.next()){

                materia = new Materia(result.getString("nome"));

            }

            
        }
        return materia;
    }

}
