package dao;

import model.Atendimento;
import java.sql.*;

public class AtendimentoDAO {
 
    private String url = "jdbc:postgresql://localhost:5432/techmentor";
    private String user = "";
    private String password = "";


    public void salvarAtendimento(Atendimento atendimento) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO atendimento (id, id_monitor, id_aluno, cod_materia, data, tema_duvida, descricao, duvida_sanada) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, getNextId());
            pstmt.setInt(2, atendimento.getIdMonitor());
            pstmt.setInt(3, atendimento.getIdAluno());
            pstmt.setInt(4, atendimento.getIdMateria());
            pstmt.setDate(5, atendimento.getData());
            pstmt.setString(6, atendimento.getTemaDuvida());
            pstmt.setString(7, atendimento.getDescricao());
            pstmt.setBoolean(8, atendimento.isDuvidaSanada());

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
    
}
