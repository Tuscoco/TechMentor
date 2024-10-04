package service;

import dao.AtendimentoDAO;
import model.Atendimento;
import java.sql.SQLException;
import java.util.*;

public class AtendimentoService {
    
    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();


    public boolean salvarAtendimento(Atendimento atendimento){

        try{

            atendimentoDAO.salvarAtendimento(atendimento);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public List<Atendimento> buscarAtendimentos(int id_monitor) throws SQLException{

        return atendimentoDAO.buscarAtendimentos(id_monitor);

    }

}
