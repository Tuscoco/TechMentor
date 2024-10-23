package service;

import dao.EventoDAO;
import model.Evento;
import java.sql.SQLException;
import java.util.*;

public class EventoService {
    
    private EventoDAO eventoDAO = new EventoDAO();


    public boolean salvarEvento(Evento evento){

        try{

            eventoDAO.salvarEvento(evento);

            return true;

        }catch(Exception e){

            e.printStackTrace();

            return false;

        }

    }

    public List<Evento> buscarTodos() throws SQLException{

        return eventoDAO.buscarTodos();

    }

    public List<Evento> buscarEventosDoDia(String data) throws SQLException{

        return eventoDAO.buscarEventosDoDia(data);

    }

    public boolean removerEvento(int id) throws Exception{

        return eventoDAO.removerEvento(id);

    }

}
