package service;

import dao.AtendimentoDAO;
import model.Atendimento;

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

}
