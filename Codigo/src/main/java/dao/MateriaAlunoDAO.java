package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import config.*;
import model.Materia;
import model.MateriaAluno;

public class MateriaAlunoDAO {
    
    private String url;
    private String user;
    private String password;

    public MateriaAlunoDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }



     public void registrarRelacao(MateriaAluno relacao) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO materia_aluno (id_aluno,id_materia) VALUES (?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, relacao.getIdAluno());
            pstmt.setInt(2, relacao.getIdMateria());
            pstmt.executeUpdate();
            

        }

    }    

    public List<Materia> exibirMateriaRelacoes(int id_aluno)throws SQLException{
    
        List<Materia> materias = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id_materia FROM materia_aluno WHERE id_aluno = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1,id_aluno);

            ResultSet result = pstmt.executeQuery();


            while(result.next()){
                String materia_sql = "SELECT * FROM materia WHERE id_materia = ?";
                PreparedStatement materia_pstmt = conn.prepareStatement(sql);
                materia_pstmt.setInt(1,result.getInt("id_materia"));
                ResultSet result_materia = materia_pstmt.executeQuery();
                //materias.add(MateriaDAO.findMateriaById(result_materia.getInt("id_materia")));

            }

        }
        Collections.sort(materias);

        return materias;

    }


     public MateriaAluno getRelacao(int id) throws SQLException{
        MateriaAluno relacao;
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            String sql = "SELECT * FROM materia_aluno WHERE id_materia = ? AND id_aluno = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet result = pstmt.executeQuery();
            relacao = new MateriaAluno(result.getInt("id_aluno"), result.getInt("id_materia"));
        }
        return relacao;
    }


    public void deletarRelacao(MateriaAluno relacao) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "DELETE FROM materia_aluno WHERE id_aluno = ? AND id_materia = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, relacao.getIdAluno());
            pstmt.setInt(2, relacao.getIdMateria());

            pstmt.executeUpdate();
            

        }

    }  


}
