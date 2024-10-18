package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import config.*;
import model.CargaHoraria;

public class CargaHorariaDAO {
    
    private String url;
    private String user;
    private String password;

    public CargaHorariaDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }


    public void adicionarCargarHoraria(CargaHoraria cargaHoraria) throws SQLException{
        try(Connection conn = DriverManager.getConnection(url, user, password)){
           String sql = "INSERT INTO cargaHoraria (id_monitor, id_materia, dia_semana, horario_entrada,horario_saida) VALUES (?,?,?,?,?)";

            Time sqlTimeEntrada = Time.valueOf(cargaHoraria.getHorarioE());
            Time sqlTimeSaida = Time.valueOf(cargaHoraria.getHorarioS());
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,cargaHoraria.getIdMonitor());
            pstmt.setInt(2,cargaHoraria.getIdMonitor());
            pstmt.setString(3,cargaHoraria.getDia());
            pstmt.setTime(4,sqlTimeEntrada);
            pstmt.setTime(5,sqlTimeSaida);
            pstmt.executeUpdate();
        }
    }
    public void deletarCargarHorariaDia(CargaHoraria cargaHoraria) throws SQLException{
        try(Connection conn = DriverManager.getConnection(url, user, password)){
           String sql = "DELETE FROM cargaHoraria WHERE id_monitor = ? AND id_materia=? AND dia_semana = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,cargaHoraria.getIdMonitor());
            pstmt.setInt(2,cargaHoraria.getIdMateria());
            pstmt.setString(3,cargaHoraria.getDia());
            pstmt.executeUpdate();
        }
    }


    public void deletarCargarHorariaAluno(CargaHoraria cargaHoraria) throws SQLException{
        try(Connection conn = DriverManager.getConnection(url, user, password)){
           String sql = "DELETE FROM cargaHoraria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,cargaHoraria.getIdMonitor());
            pstmt.executeUpdate();
        }
    }

    public List<CargaHoraria> getCargaHorariaAluno(int id) throws SQLException{
        List<CargaHoraria> cargaHoraria = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM carga_horaria WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                cargaHoraria.add(new CargaHoraria(result.getInt("id_monitor"), result.getInt("id_materia"), result.getString("dia_semana"), result.getTime("horario_entrada").toLocalTime(), result.getTime("horario_saida").toLocalTime()));
            }
            
        }
        return cargaHoraria;
    }


    
    }
    


