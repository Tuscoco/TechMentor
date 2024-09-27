package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.MateriaService;

public class MateriaController {
    
    private MateriaService materiaService;
    Gson gson = new Gson();


    public MateriaController(MateriaService materiaService){

        this.materiaService = materiaService;

    }

    public void setup(){}

}
