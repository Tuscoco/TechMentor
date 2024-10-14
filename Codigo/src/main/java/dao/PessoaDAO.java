package dao;

import model.Pessoa;
import java.sql.*;

public class PessoaDAO {
    
    private String url = "jdbc:postgresql://dpg-cs35gut6l47c73ea2a70-a.oregon-postgres.render.com:5432/techmentor_g8ly";
    private String user = "tech";
    private String password = "g1ZBH8AkXqgoSHDDpVSPhnpwF47r0Dx3";


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

    public boolean loginPessoa(Pessoa pessoa) throws SQLException{

        boolean autenticado = false;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT * FROM pessoa WHERE id = ? AND senha = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pessoa.getId());
            pstmt.setString(2, pessoa.getSenha());

            ResultSet result = pstmt.executeQuery();

            autenticado = result.next();

        }

        return autenticado;

    }

    public int getTipoUsuario(Pessoa pessoa) throws SQLException{

        int tipo = -1;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT tipo_usuario FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pessoa.getId());

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                tipo = result.getInt("tipo_usuario");

            }

        }

        return tipo;

    }

}
