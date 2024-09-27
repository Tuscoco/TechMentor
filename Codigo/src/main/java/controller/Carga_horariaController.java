package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.Carga_horariaService;

public class Carga_horariaController {
    
    private Carga_horariaService carga_horariaService;
    Gson gson = new Gson();


    public Carga_horariaController(Carga_horariaService carga_horariaService){

        this.carga_horariaService = carga_horariaService;

    }

    public void setup(){}

}
