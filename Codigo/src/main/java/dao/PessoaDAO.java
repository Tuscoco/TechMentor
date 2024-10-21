package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Pessoa;
import config.*;
import java.util.*;

public class PessoaDAO {
    
    private String url;
    private String user;
    private String password;

    public PessoaDAO(){

        Config config = new Config("config/config.properties");
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

    }


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

    public String getEmail(int id) throws SQLException{

        String email = null;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

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

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "UPDATE pessoa SET nome = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, nome);
            pstmt.setInt(2, id);

            int alterado = pstmt.executeUpdate();

            return alterado > 0;

        }

    }

    public String getNome(int id) throws SQLException{

        String nome = null;

        try(Connection conn = DriverManager.getConnection(url, user, password)){

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

    public List<Pessoa> getAlunos() throws SQLException{

        List<Pessoa> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id, nome, tipo_usuario FROM pessoa WHERE tipo_usuario > 1";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Pessoa pessoa = new Pessoa(result.getInt("id"),result.getString("nome"), result.getInt("tipo_usuario"));
                lista.add(pessoa);

            }

        }

        lista = ordenar(lista);

        return lista;

    }

    public List<Pessoa> getTodos() throws SQLException{

        List<Pessoa> lista = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(url, user, password)){

            String sql = "SELECT id, nome, tipo_usuario FROM pessoa WHERE tipo_usuario > 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            ResultSet result = pstmt.executeQuery();

            while(result.next()){

                Pessoa pessoa = new Pessoa(result.getInt("id"),result.getString("nome"), result.getInt("tipo_usuario"));
                lista.add(pessoa);

            }

        }

        lista = ordenar(lista);

        return lista;

    }

    public List<Pessoa> ordenar(List<Pessoa> lista){

        int n = lista.size();


        for(int i = 1;i < n;i++){

            Pessoa temp = lista.get(i);
            int j = i - 1;

            while((j >= 0) && (lista.get(j).getNome().compareTo(temp.getNome()) > 0)){

                lista.set(j + 1, lista.get(j));
                j--;

            }

            lista.set(j + 1, temp);

        }

        return lista;

    }

}
