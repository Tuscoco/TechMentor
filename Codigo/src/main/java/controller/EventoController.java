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

            List<Evento> lista = eventoService.buscarTodos();

            return new Gson().toJson(lista);

        });

        get("/mostrareventosdodia/:data", (req, res) -> {

            res.type("application/json");

            String data = req.params(":data");

            List<Evento> lista = eventoService.buscarEventosDoDia(data);

            return new Gson().toJson(lista);

        });

        delete("/removerevento/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            try{

                boolean success = eventoService.removerEvento(id);

                if(success){

                    res.status(200);
                    return gson.toJson("Evento removido!");

                }else{

                    res.status(403);
                    return gson.toJson("Erro ao remover evento!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro!");

            }

        });

    }

}
