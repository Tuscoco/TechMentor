package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.EventoService;

public class EventoController {
    
    private EventoService eventoService;
    Gson gson = new Gson();


    public EventoController(EventoService eventoService){

        this.eventoService = eventoService;

    }

    public void setup(){}

}
