package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.*;
import model.Ponto;

public class PontoDAO {

    public boolean baterPonto(Ponto ponto) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "INSERT INTO ponto (id_monitor, data) VALUES (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, ponto.getIdMonitor());
            pstmt.setString(2, ponto.getData());

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public List<Ponto> buscarPontos(int id) throws SQLException{

        List<Ponto> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT * FROM ponto WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Ponto ponto = new Ponto(result.getInt("id_monitor"), result.getString("data"));
                lista.add(ponto);

            }

        }

        return lista;

    }

    public List<Ponto> buscarPeloMes(int id, int mes) throws SQLException{

        List<Ponto> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT * FROM ponto WHERE id_monitor = ? AND EXTRACT(MONTH FROM TO_DATE(data, 'DD/MM/YYYY')) = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);
            pstmt.setInt(2, mes);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Ponto ponto = new Ponto(result.getInt("id_monitor"), result.getString("data"));
                lista.add(ponto);

            }

        }

        return lista;

    }
    
}
    


