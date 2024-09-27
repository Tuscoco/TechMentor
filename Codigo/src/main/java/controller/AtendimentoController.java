package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.AtendimentoService;

public class AtendimentoController {
    
    private AtendimentoService atendimentoService;
    Gson gson = new Gson();


    public AtendimentoController(AtendimentoService atendimentoService){

        this.atendimentoService = atendimentoService;

    }

    public void setup(){}

}
