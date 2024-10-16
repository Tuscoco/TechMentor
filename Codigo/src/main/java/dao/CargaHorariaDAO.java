package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.CargaHoraria;

public class CargaHorariaDAO {
    
    private static final String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private static final String user = "tech";
    private static final String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


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
    


