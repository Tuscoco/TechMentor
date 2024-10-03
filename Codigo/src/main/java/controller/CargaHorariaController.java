package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.Carga_horariaService;

public class CargaHorariaController {
    
    private Carga_horariaService carga_horariaService;
    Gson gson = new Gson();


    public CargaHorariaController(Carga_horariaService carga_horariaService){

        this.carga_horariaService = carga_horariaService;

    }

    public void setup(){}

}
