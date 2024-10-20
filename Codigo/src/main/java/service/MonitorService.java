package service;

import java.sql.SQLException;
import java.util.ArrayList;
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

        return monitorDAO.getMonitoresOnline();

    }

    public List<Monitoria> getMonitoresOffline() throws Exception{

        return monitorDAO.getMonitoresOffline();

    }

        public boolean ficarOnline(int id_monitor) throws SQLException{

        try{

            monitorDAO.ficarOnline(id_monitor);
            return true;

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

//     public Monitoria getMonitorEMateria(int id_monitor,int id_materia){
//         try{
//             return monitorDAO.getMonitorEMateria(id_monitor, id_materia);
//         } catch(Exception e){
//             e.printStackTrace();
//             return null;
//         }
//     }

//     public List<Monitoria> getMonitor(int id_monitor){
//         try{
//             return monitorDAO.getMonitor(id_monitor);
//         } catch(Exception e){
//             e.printStackTrace();
//             return new ArrayList<Monitoria>();
//         }
//     }


//     public List<Monitoria> getMonitorPorMateria(int id_materia){

//         List<Monitoria> monitor = new ArrayList<>();

//         try {
//             return monitorDAO.getMonitorPorMateria(id_materia);
//         } catch (Exception e) {
//             return new ArrayList<>();
//         }
//     }

}
    



    


