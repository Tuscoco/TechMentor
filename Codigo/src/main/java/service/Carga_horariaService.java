package service;

import java.sql.SQLException;
import java.util.List;

import dao.CargaHorariaDAO;
import model.CargaHoraria;

public class Carga_horariaService {
    
    private CargaHorariaDAO carga_horariaDAO = new CargaHorariaDAO();

    public boolean adicionarCargarHoraria(CargaHoraria cargaHoraria)throws SQLException{
        try {
            carga_horariaDAO.adicionarCargarHoraria(cargaHoraria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarCargarHorariaAluno(CargaHoraria cargaHoraria)throws SQLException{
        try {
            carga_horariaDAO.deletarCargarHorariaAluno(cargaHoraria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletarCargarHorariaDia(CargaHoraria cargaHoraria)throws SQLException{
        try {
            carga_horariaDAO.deletarCargarHorariaDia(cargaHoraria);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<CargaHoraria> getCargaHorariaAluno(int id) throws SQLException{
        return carga_horariaDAO.getCargaHorariaAluno(id);
    }

}
