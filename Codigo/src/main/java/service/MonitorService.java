package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.MonitorDAO;
import model.Monitoria;

public class MonitorService {
    
    private MonitorDAO monitorDAO = new MonitorDAO();


    public boolean registrarMonitor(Monitoria monitor){
        boolean resp;
        try {
            monitorDAO.adicionarMonitor(monitor);
            resp = true;
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }

    public boolean deletarMonitor(Monitoria monitor){
        boolean resp;
        try {
            monitorDAO.removerMonitor(monitor);
            resp = true;
        } catch (Exception e) {
            resp = false;
            e.printStackTrace();
        }
        return resp;
    }

    public List<Monitoria> getTodos() throws SQLException{
        try {
            return monitorDAO.getTodos();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    } 


    public Monitoria getMonitorEMateria(int id_monitor,int id_materia){
        try{
            return monitorDAO.getMonitorEMateria(id_monitor, id_materia);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Monitoria> getMonitor(int id_monitor){
        try{
            return monitorDAO.getMonitor(id_monitor);
        } catch(Exception e){
            e.printStackTrace();
            return new ArrayList<Monitoria>();
        }
    }


    public List<Monitoria> getMonitorPorMateria(int id_materia){

        List<Monitoria> monitor = new ArrayList<>();

        try {
            return monitorDAO.getMonitorPorMateria(id_materia);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public boolean ficarOnline(int id_monitor) throws SQLException{
        try {
            monitorDAO.ficarOnline(id_monitor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ficarOffline(int id_monitor) throws SQLException{
        try {
            monitorDAO.ficarOffline(id_monitor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean alterarSala(int id_monitor,int sala) throws SQLException{
        try {
            monitorDAO.alterarSala(sala,id_monitor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    





    




}
    



    


