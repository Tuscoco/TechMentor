package service;

import dao.PessoaDAO;
import model.Pessoa;
import org.mindrot.jbcrypt.BCrypt;

public class PessoaService {
    
    private PessoaDAO pessoaDAO = new PessoaDAO();


    public boolean registrarPessoa(Pessoa pessoa){

        try{

            pessoa.setSenha(BCrypt.hashpw(pessoa.getSenha(), BCrypt.gensalt()));

            pessoaDAO.registrarPessoa(pessoa);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public boolean loginPessoa(Pessoa pessoa) throws Exception{

        pessoa.setSenha(BCrypt.hashpw(pessoa.getSenha(), BCrypt.gensalt()));

        return pessoaDAO.loginPessoa(pessoa);

    }

    public int getTipoUsuario(Pessoa pessoa) throws Exception{

        return pessoaDAO.getTipoUsuario(pessoa);

    }

}
