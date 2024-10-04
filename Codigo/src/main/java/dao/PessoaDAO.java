package dao;

import model.Pessoa;
import java.sql.*;

public class PessoaDAO {
    
    private String url = "jdbc:postgresql://localhost:5432/techmentor";
    private String user = "";
    private String password = "";


    public void registrarPessoa(Pessoa pessoa) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "INSERT INTO pessoa (id, nome, email, senha, tipo_usuario) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pessoa.getId());
            pstmt.setString(2, pessoa.getNome());
            pstmt.setString(3, pessoa.getEmail());
            pstmt.setString(4, pessoa.getSenha());
            pstmt.setInt(5, pessoa.getTipoUsuario());

            pstmt.executeUpdate();

        }

    }

}
