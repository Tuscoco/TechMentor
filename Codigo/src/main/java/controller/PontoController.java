package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.google.gson.Gson;
import model.Ponto;
import service.PontoService;
import static spark.Spark.*;


public class PontoController {
    
    private PontoService pontoService;
    Gson gson = new Gson();


    public PontoController(PontoService carga_horariaService){

        this.pontoService = carga_horariaService;

    }

    public void setup(){

        post("/baterponto/:id", (req, res) -> {

            LocalDate hoje = LocalDate.now();
            DateTimeFormatter formatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String data = hoje.format(formatada);

            Ponto ponto = new Ponto(Integer.parseInt(req.params(":id")), data);

            boolean success = pontoService.baterPonto(ponto);

            if(success){

                return gson.toJson("Ponto realizado com sucesso!");

            }else{

                res.status(500);

                return gson.toJson("Erro ao bater ponto!");

            }

        });

        get("/buscarpontos/:id", (req, res) -> {

            try{

                int id = Integer.parseInt(req.params(":id"));

                List<Ponto> lista = pontoService.buscarPontos(id);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Não foi possível buscar os pontos!");

            }

        });

        get("/buscarpontopelomes/:id/:mes", (req, res) -> {

            try{

                int id = Integer.parseInt(req.params(":id"));
                int mes = Integer.parseInt(req.params(":mes"));

                List<Ponto> lista = pontoService.buscarPeloMes(id, mes);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Não foi possível buscar os pontos!");

            }

        });

    }
        
}


