package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Atendimento;
import config.*;

public class AtendimentoDAO {
 
    private String url;
    private String user;
    private String password;

    public AtendimentoDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }


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
