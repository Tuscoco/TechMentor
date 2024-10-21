package controller;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Monitoria;
import service.MonitorService;
import static spark.Spark.*;



public class MonitorController{
    
    private MonitorService monitorService;
    Gson gson = new Gson();


    public MonitorController(MonitorService monitorService){

        this.monitorService = monitorService;

    }

    public void setup(){

        get("/mostrarMonitoresOnline", (req, res) -> {

            res.type("application/json");

            try{
                
                List<Monitoria> lista = monitorService.getMonitoresOnline();

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os monitores.\"}";

            }

        });

        get("/mostrarMonitoresOffline", (req, res) -> {

            res.type("application/json");

            try{
                

                List<Monitoria> lista = monitorService.getMonitoresOffline();

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os monitores.\"}";

            }

        });

        get("/mostrarsala/:id", (req, res) -> {

            try{

                int id = Integer.parseInt(req.params(":id"));

                int sala = monitorService.getSala(id);

                return gson.toJson(Integer.toString(sala));

            }catch(Exception e){

                res.status(500);
                return gson.toJson("Erro ao buscar a sala!");

            }

        });

        get("/mostraronline/:id", (req, res) -> {

            try{

                int id = Integer.parseInt(req.params(":id"));

                boolean online = monitorService.getOnline(id);

                if(online){

                    return gson.toJson("1");

                }else{

                    return gson.toJson("0");

                }

            }catch(Exception e){

                res.status(500);
                return gson.toJson("Erro ao buscar status!");

            }

        });

        post("/ficaronline/:id", (req, res) -> {

            try{

                int idMonitor = Integer.parseInt(req.params(":id"));
                boolean success = monitorService.ficarOnline(idMonitor);

                if(success){

                    return gson.toJson("Monitor está online!");

                }else{

                    return gson.toJson("Erro ao mudar o status do monitor!");

                }

            }catch(Exception e){

                return gson.toJson("Erro interno ao processar a requisição.");

            }

        });

        post("/ficaroffline/:id", (req, res) -> {

            try{

                int idMonitor = Integer.parseInt(req.params(":id"));
                boolean success = monitorService.ficarOffline(idMonitor);

                if(success){

                    return gson.toJson("Monitor está offline!");

                }else{

                    return gson.toJson("Erro ao mudar o status do monitor!");

                }

            }catch(Exception e){

                return gson.toJson("Erro interno ao processar a requisição.");

            }

        });

        post("/mudarsala/:id", (req, res) -> {

            res.type("application/json");

            try{

                int idMonitor = Integer.parseInt(req.params(":id"));
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int sala = json.get("sala").getAsInt();

                boolean success = monitorService.alterarSala(idMonitor, sala);

                if(success){

                    return gson.toJson("Sala alterada com sucesso!");

                }else{

                    return gson.toJson("Erro ao mudar a sala!");

                }

            }catch(Exception e){

                return gson.toJson("Erro interno ao processar a requisição.");

            }
        
        });

    }

}
