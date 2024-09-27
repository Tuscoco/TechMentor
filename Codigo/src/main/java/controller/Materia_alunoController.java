package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.Materia_alunoService;

public class Materia_alunoController {
    
    private Materia_alunoService materia_alunoService;
    Gson gson = new Gson();


    public Materia_alunoController(Materia_alunoService materia_alunoService){

        this.materia_alunoService = materia_alunoService;

    }

    public void setup(){}

}
