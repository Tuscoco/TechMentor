package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Atendimento;

public class AtendimentoDAO {
 
    private static final String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private static final String user = "tech";
    private static final String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


    public void salvarAtendimento(Atendimento atendimento) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO atendimento (id, id_monitor, id_aluno, id_materia, data, tema_duvida, descricao, duvida_sanada) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setInt(2, atendimento.getIdMonitor());
            pstmt.setInt(3, atendimento.getIdAluno());
            pstmt.setInt(4, atendimento.getIdMateria());
            pstmt.setString(5, atendimento.getData());
            pstmt.setString(6, atendimento.getTemaDuvida());
            pstmt.setString(7, atendimento.getDescricao());
            pstmt.setBoolean(8, atendimento.isDuvidaSanada());

            pstmt.executeUpdate();

        }

    }

    public int getNextId() throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT MAX(id) AS max_id FROM atendimento";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getInt("max_id") + 1;

            }

        }

        return 1;

    }

    public List<Atendimento> buscarAtendimentos(int id_monitor) throws SQLException{

        List<Atendimento> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM atendimento WHERE id_monitor = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id_monitor);

            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Atendimento atendimento = new Atendimento(result.getInt("id"), result.getInt("id_monitor"), result.getInt("id_aluno"), result.getInt("id_materia"), result.getString("data"), result.getString("tema_duvida"), result.getString("descricao"), result.getBoolean("duvida_sanada"));
                lista.add(atendimento);

            }

        }

        return lista;

    }
    
}
