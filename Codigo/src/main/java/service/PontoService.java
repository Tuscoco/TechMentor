package service;

import java.util.List;
import dao.PontoDAO;
import model.Ponto;

public class PontoService {
    
    private PontoDAO pontoDAO = new PontoDAO();

    public boolean baterPonto(Ponto ponto) throws Exception{

        return pontoDAO.baterPonto(ponto);

    }

    public List<Ponto> buscarPontos(int id) throws Exception{

        return pontoDAO.buscarPontos(id);

    }

    public List<Ponto> buscarPeloMes(int id, int mes) throws Exception{

        return pontoDAO.buscarPeloMes(id, mes);

    }

}
