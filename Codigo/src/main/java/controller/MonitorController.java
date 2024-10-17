package controller;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.Monitoria;
import service.MonitorService;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;



public class MonitorController {
    
    private MonitorService monitorService;
    Gson gson = new Gson();


    public MonitorController(MonitorService monitorService){

        this.monitorService = monitorService;

    }

    public void setup(){
        post("/registrarMonitor", (req, res) -> {

            res.type("application/json");

            Monitoria monitor = gson.fromJson(req.body(), Monitoria.class);
            boolean success = monitorService.registrarMonitor(monitor);

            if(success){

                return gson.toJson("Monitor adicionado!");

            }else{

                return gson.toJson("Falha ao adicionar monitor!");

            }

        });


        delete("/removerMonitor", (req, res) -> {

            res.type("application/json");
        
            try {
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int idMonitor = json.get("id_monitor").getAsInt();
                int idMateria = json.get("id_materia").getAsInt();
        
                Monitoria monitor = monitorService.getMonitorEMateria(idMonitor, idMateria);
        
                if (monitor == null) {
                    return gson.toJson("Monitor não encontrado!");
                }
        
                boolean success = monitorService.deletarMonitor(monitor);
        
                if (success) {
                    return gson.toJson("Monitor removido com sucesso!");
                } else {
                    return gson.toJson("Erro ao remover o monitor!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return gson.toJson("Erro interno ao processar a requisição.");
            }
        
        });
        

        get("/mostrarMonitores", (req, res) -> {

            res.type("application/json");

            try{
                

                List<Monitoria> lista = monitorService.getTodos();

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os monitores.\"}";

            }

        });

        get("/monitorPorMateria" , (req,res) -> {
            res.type("application/json");


            try{
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);

                int idMateria = json.get("id_materia").getAsInt();

                List<Monitoria> lista = monitorService.getMonitorPorMateria(idMateria);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os monitores.\"}";

            }

        });


        get("/monitorPorMateria" , (req,res) -> {
            res.type("application/json");


            try{
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);

                int idMateria = json.get("id_materia").getAsInt();

                List<Monitoria> lista = monitorService.getMonitorPorMateria(idMateria);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os monitores.\"}";

            }

        });


        post("/ficarOnline", (req,res) ->{
            res.type("application/json");

            try {
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int idMonitor = json.get("id_monitor").getAsInt();
                boolean success = monitorService.ficarOffline(idMonitor);

                if (success) {
                    return gson.toJson("Monitor está offline!");
                } else {
                    return gson.toJson("Erro ao mudar o status do monitor!");
                }
            } catch (Exception e) {
                return gson.toJson("Erro interno ao processar a requisição.");
            }
        });

        post("/ficarOffline", (req,res) ->{
            res.type("application/json");

            try {
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int idMonitor = json.get("id_monitor").getAsInt();
                boolean success = monitorService.ficarOffline(idMonitor);

                if (success) {
                    return gson.toJson("Monitor está offline!");
                } else {
                    return gson.toJson("Erro ao mudar o status do monitor!");
                }
            } catch (Exception e) {
                return gson.toJson("Erro interno ao processar a requisição.");
            }
        });

        delete("/mudarSala", (req, res) -> {

            res.type("application/json");
        
            try {
                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int idMonitor = json.get("id_monitor").getAsInt();
                int sala = json.get("sala").getAsInt();
        
                List<Monitoria> monitor = monitorService.getMonitor(idMonitor);
        
                if (monitor.isEmpty()) {
                    return gson.toJson("Monitor não encontrado!");
                }
        
                boolean success = monitorService.alterarSala(idMonitor,sala);
        
                if (success) {
                    return gson.toJson("Sala alterada com sucesso!");
                } else {
                    return gson.toJson("Erro ao mudar sala!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return gson.toJson("Erro interno ao processar a requisição.");
            }
        
        });



        




    }



}
