package dao;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Pessoa;
import config.*;
import java.util.*;

public class PessoaDAO {

    private List<Pessoa> cachePessoas2;
    private List<Pessoa> cachePessoas3;

    public void registrarPessoa(Pessoa pessoa) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String senhaCripto = BCrypt.hashpw(pessoa.getSenha(), BCrypt.gensalt());
            String sql = "INSERT INTO pessoa (id, nome, email, senha, tipo_usuario, foto) VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pessoa.getId());
            pstmt.setString(2, pessoa.getNome());
            pstmt.setString(3, pessoa.getEmail());
            pstmt.setString(4, senhaCripto);
            pstmt.setInt(5, pessoa.getTipoUsuario());
            pstmt.setString(6, pessoa.getFoto());

            pstmt.executeUpdate();

            atualizarCache();

        }

    }

    public boolean loginPessoa(Pessoa pessoa) throws SQLException{

        boolean autenticado = false;

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT senha FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pessoa.getId());

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                String senhaCripto = result.getString("senha");
                autenticado = BCrypt.checkpw(pessoa.getSenha(), senhaCripto);

            }

        }

        return autenticado;

    }

    public int getTipoUsuario(int id) throws SQLException{

        int tipo = -1;

        try(Connection conn = DataBaseConnection.getConnection()){

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

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE pessoa SET tipo_usuario = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, novoTipo);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            atualizarCache();

            return alterado > 0;

        }

    }

    public boolean alterarSenha(String senha, int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String senhaCripto = BCrypt.hashpw(senha, BCrypt.gensalt());
            String sql = "UPDATE pessoa SET senha = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, senhaCripto);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public boolean alterarEmail(String email, int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE pessoa SET email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, email);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public String getEmail(int id) throws SQLException{

        String email = null;

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT email FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                email = result.getString("email");

            }

        }

        return email;

    }

    public boolean alterarNome(String nome, int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE pessoa SET nome = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nome);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            atualizarCache();

            return alterado > 0;

        }

    }

    public String getNome(int id) throws SQLException{

        String nome = null;

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT nome FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                nome = result.getString("nome");

            }

        }

        return nome;

    }

    public boolean alterarFoto(int id, String url) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE pessoa SET foto = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, url);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public String getFoto(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT foto FROM pessoa WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet result = pstmt.executeQuery();

            if(result.next()){

                return result.getString("foto");

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return null;

    }

    public boolean removerFoto(int id) throws SQLException{

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "UPDATE pessoa SET foto = NULL WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public List<Pessoa> getUsuarios(int tipo) throws SQLException{

        List<Pessoa> lista = new ArrayList<>();

        try(Connection conn = DataBaseConnection.getConnection()){

            String sql = "SELECT id, nome FROM pessoa WHERE tipo_usuario = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, tipo);
            
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Pessoa pessoa = new Pessoa(result.getInt("id"),result.getString("nome"), tipo);
                lista.add(pessoa);

            }

        }

        return lista;

    }

    public List<Pessoa> getPessoas(int tipo) throws SQLException{

        if(tipo == 2){

            if(cachePessoas2 == null){

                cachePessoas2 = getUsuarios(tipo);

            }

            return cachePessoas2;

        }else if(tipo == 3){

            if(cachePessoas3 == null){

                cachePessoas3 = getUsuarios(tipo);

            }

            return cachePessoas3;

        }

        return null;

    }

    public void atualizarCache() throws SQLException{

        cachePessoas2 = getUsuarios(2);
        cachePessoas3 = getUsuarios(3);

    }

}
