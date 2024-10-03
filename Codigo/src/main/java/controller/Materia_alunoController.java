package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.MateriaAlunoService;

public class Materia_alunoController {
    
    private MateriaAlunoService materia_alunoService;
    Gson gson = new Gson();


    public Materia_alunoController(MateriaAlunoService materia_alunoService){

        this.materia_alunoService = materia_alunoService;

    }

    public void setup(){}

}
