package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Evento;
import service.EventoService;
import java.util.*;

public class EventoController {
    
    private EventoService eventoService;
    Gson gson = new Gson();


    public EventoController(EventoService eventoService){

        this.eventoService = eventoService;

    }

    public void setup(){

        post("/salvarevento", (req, res) -> {

            res.type("application/json");

            Evento evento = gson.fromJson(req.body(), Evento.class);
            boolean success = eventoService.salvarEvento(evento);

            if(success){

                return gson.toJson("Evento salvo!");

            }else{

                return gson.toJson("Falha ao enviar evento!");

            }

        });

        get("/mostrarevento", (req, res) -> {

            res.type("application/json");

            List<Evento> lista = eventoService.getTodos();

            return new Gson().toJson(lista);

        });

    }

}
