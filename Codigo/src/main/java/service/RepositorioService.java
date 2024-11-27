package service;

import dao.RepositorioDAO;
import model.Repositorio;
import java.sql.SQLException;
import java.util.*;

public class RepositorioService {
    
    RepositorioDAO repositorioDAO = new RepositorioDAO();


    public boolean salvarRepositorio(Repositorio repositorio){

        try{

            repositorioDAO.salvarRepositorio(repositorio);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public boolean deletarRepositorio(int id){

        try{

            repositorioDAO.deletarRepositorio(id);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public List<Repositorio> getTodos() throws SQLException{

        return repositorioDAO.getRepos();

    }

}
