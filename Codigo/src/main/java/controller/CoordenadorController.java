package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.CoordenadorService;

public class CoordenadorController {
    
    private CoordenadorService coordenadorService;
    Gson gson = new Gson();


    public CoordenadorController(CoordenadorService coordenadorService){

        this.coordenadorService = coordenadorService;

    }

    public void setup(){}

}
