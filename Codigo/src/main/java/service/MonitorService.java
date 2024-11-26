package service;

import java.sql.SQLException;
import java.util.List;

import dao.MonitorDAO;
import model.Monitoria;

public class MonitorService{
    
    private MonitorDAO monitorDAO = new MonitorDAO();


    public boolean registrarMonitor(Monitoria monitor){

        try{

            monitorDAO.adicionarMonitor(monitor);
            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public boolean deletarMonitor(int id){

        try{

            return monitorDAO.removerMonitor(id);

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public List<Monitoria> getMonitoresOnline() throws Exception{

        return monitorDAO.getOnline();

    }

    public List<Monitoria> getMonitoresOffline() throws Exception{

        return monitorDAO.getOffline();

    }

    public boolean setHorarios(int id, String entrada, String saida) throws Exception{

        try{

            monitorDAO.setHorarios(id, entrada, saida);
            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public boolean ficarOnline(int id_monitor, String horario) throws SQLException{

        try{

            return monitorDAO.ficarOnline(id_monitor, horario);

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public boolean ficarOffline(int id_monitor) throws SQLException{

        try{

            monitorDAO.ficarOffline(id_monitor);
            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public boolean alterarSala(int id_monitor,int sala) throws SQLException{

        try{

            monitorDAO.alterarSala(id_monitor, sala);
            return true;

        }catch(Exception e){

            e.printStackTrace();
            return false;

        }

    }

    public int getSala(int id) throws Exception{

        return monitorDAO.getSala(id);

    }

    public boolean getOnline(int id) throws Exception{

        return monitorDAO.getOnline(id);

    }

    public int getMateriaMonitor(int id) throws Exception{

        return monitorDAO.getMateriaMonitor(id);

    }

    public boolean alterarFoto(int id, String url1, String url2) throws Exception{

        return monitorDAO.alterarFoto(id, url1, url2);

    }

    public List<String> getFotos(int id) throws Exception{

        return monitorDAO.getFotos(id);

    }

}
    



    


