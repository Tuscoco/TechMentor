package service;

import dao.MateriaDAO;
import model.Materia;

public class MateriaService {
    
    private MateriaDAO materiaDAO = new MateriaDAO();

    public Materia findMateriaById(int id) throws Exception{

        return materiaDAO.findMateriaById(id);

    }

}
