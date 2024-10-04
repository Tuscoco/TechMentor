package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.MateriaAlunoService;

public class MateriaAlunoController {
    
    private MateriaAlunoService materia_alunoService;
    Gson gson = new Gson();


    public MateriaAlunoController(MateriaAlunoService materia_alunoService){

        this.materia_alunoService = materia_alunoService;

    }

    public void setup(){}

}
