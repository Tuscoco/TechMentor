package service;

import java.util.List;
import dao.PessoaDAO;
import model.Pessoa;

public class PessoaService {
    
    private PessoaDAO pessoaDAO = new PessoaDAO();


    public boolean registrarPessoa(Pessoa pessoa){

        try{

            pessoaDAO.registrarPessoa(pessoa);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public boolean loginPessoa(Pessoa pessoa) throws Exception{

        return pessoaDAO.loginPessoa(pessoa);

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

    public int getTipoUsuario(int id) throws Exception{

        return pessoaDAO.getTipoUsuario(id);

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

    public boolean alterarFoto(int id, String url) throws Exception{

        return pessoaDAO.alterarFoto(id, url);

    }

    public String getFoto(int id) throws Exception{

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

    public List<Pessoa> getUsuarios(int tipo) throws Exception{

        return pessoaDAO.getUsuarios(tipo);

    }

}
