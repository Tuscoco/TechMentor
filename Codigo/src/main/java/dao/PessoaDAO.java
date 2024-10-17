package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Pessoa;

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

    public int getTipoUsuario(int id) throws SQLException{

        int tipo = -1;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT tipo_usuario FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                tipo = result.getInt("tipo_usuario");

            }

        }

        return tipo;

    }

    public boolean alterarTipoUsuario(int id, int novoTipo) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET tipo_usuario = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, novoTipo);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public boolean alterarSenha(String senha, int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET senha = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, senha);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public boolean alterarEmail(String email, int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public boolean alterarNome(String nome, int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET nome = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nome);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public boolean alterarFoto(int id, InputStream foto, long tamanhoFoto) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET foto = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setBinaryStream(1, foto, tamanhoFoto);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public InputStream getFoto(int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT foto FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getBinaryStream("foto");

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return null;

    }

    public boolean removerFoto(int id) throws SQLException{

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "DELETE foto FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

}
