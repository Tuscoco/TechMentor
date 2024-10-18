package service;

import java.io.InputStream;

import dao.PessoaDAO;
import model.Pessoa;
//import org.mindrot.jbcrypt.BCrypt;

public class PessoaService {
    
    private PessoaDAO pessoaDAO = new PessoaDAO();


    public boolean registrarPessoa(Pessoa pessoa){

        try{

            //String cripto = BCrypt.hashpw(pessoa.getSenha(), BCrypt.gensalt());
            
            //pessoa.setSenha(cripto);

            pessoaDAO.registrarPessoa(pessoa);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public boolean loginPessoa(Pessoa pessoa) throws Exception{

        //pessoa.setSenha(BCrypt.hashpw(pessoa.getSenha(), BCrypt.gensalt()));

        return pessoaDAO.loginPessoa(pessoa);

    }

    public int getTipoUsuario(Pessoa pessoa) throws Exception{

        return pessoaDAO.getTipoUsuario(pessoa);

    }

    public boolean alterarTipoUsuario(int idAlterador, int idAlvo, int novoTipo) throws Exception{

        int tipoAlterador = pessoaDAO.getTipoUsuario(idAlterador);
        int tipoAlvo = pessoaDAO.getTipoUsuario(idAlvo);

        if(tipoAlterador == 0 || (tipoAlterador == 1 && novoTipo > 1) || (tipoAlterador == 2 && novoTipo > 2)){

            if(tipoAlvo > tipoAlterador){

                return pessoaDAO.alterarTipoUsuario(idAlvo, novoTipo);

            }

        }

        return false;

    }

    public boolean alterarSenha(String senha, int id) throws Exception{

        return pessoaDAO.alterarSenha(senha, id);

    }

    public boolean alterarEmail(String email, int id) throws Exception{

        return pessoaDAO.alterarEmail(email, id);

    }

    public boolean alterarNome(String nome, int id) throws Exception{

        return pessoaDAO.alterarNome(nome, id);

    }

    public boolean alterarFoto(int id, InputStream foto, long tamanhoFoto) throws Exception{

        return pessoaDAO.alterarFoto(id, foto, tamanhoFoto);

    }

    public InputStream getFoto(int id) throws Exception{

        return pessoaDAO.getFoto(id);

    }

    public boolean removerFoto(int id) throws Exception{

        return pessoaDAO.removerFoto(id);

    }

    public String getNome(int id) throws Exception{

        return pessoaDAO.getNome(id);

    }

    public String getEmail(int id) throws Exception{

        return pessoaDAO.getEmail(id);

    }

}
