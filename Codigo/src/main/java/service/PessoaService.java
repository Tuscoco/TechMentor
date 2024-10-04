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

}
