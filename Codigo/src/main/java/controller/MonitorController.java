package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Monitoria;
import service.MonitorService;
import static spark.Spark.*;
import model.Ponto;
import service.PontoService;


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

        get("/buscarmateriamonitor/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));
            
            try{

                int materia = monitorService.getMateriaMonitor(id);

                if(materia != -1){

                    res.status(200);

                    return gson.toJson(Integer.toString(materia));

                }else{

                    res.status(404);

                    return gson.toJson("Monitor não encontrado");

                }

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Ocorreu um erro");

            }

        });

        post("/sethorarios/:id", (req, res) -> {

            res.type("application/json");

            try{

                JsonObject json = gson.fromJson(req.body(), JsonObject.class);
                int id = Integer.parseInt(req.params(":id"));
                String entrada = json.get("entrada").getAsString();
                String saida = json.get("saida").getAsString();

                boolean success = monitorService.setHorarios(id, entrada, saida);

                if(success){

                    return gson.toJson("Horários atualizados com sucesso!");

                }else{

                    return gson.toJson("Não foi possível alterar os horários!");

                }

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Erro interno ao processar a requisição!");

            }

        });

        post("/ficaronline/:id", (req, res) -> {

            try{

                int idMonitor = Integer.parseInt(req.params(":id"));
                LocalTime horario = LocalTime.now();
                String horarioString = horario.format(DateTimeFormatter.ofPattern("HH:mm"));

                boolean success = monitorService.ficarOnline(idMonitor, horarioString);

                if(success){

                    LocalDate hoje = LocalDate.now();
                    DateTimeFormatter formatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    String data = hoje.format(formatada);

                    Ponto ponto = new Ponto(Integer.parseInt(req.params(":id")), data);

                    PontoService pontoService = new PontoService();

                    boolean successPonto = pontoService.baterPonto(ponto);

                    if(successPonto){

                        return gson.toJson("Monitor Online e ponto batido com sucesso!");

                    }else{

                        res.status(500);

                        return gson.toJson("Erro ao bater ponto!");

                    }

                }else{

                    return gson.toJson("Erro ao mudar o status do monitor!");

                }

            }catch(Exception e){

                return gson.toJson("Erro interno ao processar a requisição!");

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

        post("/salvarfotomonitor/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            String url1 = json.get("foto1").getAsString();
            String url2 = json.get("foto2").getAsString();

            try{
                
                boolean sucesso = monitorService.alterarFoto(id, url1, url2);
                
                if(sucesso){

                    res.status(200);
                    return "Fotos atualizadas com sucesso!";

                }else{

                    res.status(500);
                    return "Erro ao atualizar as fotos no banco de dados.";

                }

            }catch(Exception e){

                e.printStackTrace();
                res.status(500);
                return "Erro ao processar as fotos.";

            }

        });

        get("/mostrarfotomonitor/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            try{

                List<String> lista = monitorService.getFotos(id);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar as fotos.\"}";

            }

        });

        get("/mostrarhorarios/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            try{

                List<String> lista = monitorService.getHorarios(id);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar as fotos.\"}";

            }

        });

    }

}
