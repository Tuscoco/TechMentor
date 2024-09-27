package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.AlunoService;

public class AlunoController {
    
    private AlunoService alunoService;
    Gson gson = new Gson();


    public AlunoController(AlunoService alunoService){

        this.alunoService = alunoService;

    }

    public void setup(){}

}
