package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Repositorio;
import service.RepositorioService;
import java.util.*;

public class RepositorioController {
    
    private RepositorioService repositorioService;
    Gson gson = new Gson();


    public RepositorioController(RepositorioService repositorioService){

        this.repositorioService = repositorioService;

    }

    public void setup(){

        post("/salvarrepositorio", (req, res) -> {

            res.type("application/json");

            Repositorio repositorio = gson.fromJson(req.body(), Repositorio.class);
            boolean success = repositorioService.salvarRepositorio(repositorio);

            if(success){

                return gson.toJson("Repositorio salvo!");

            }else{

                return gson.toJson("Falha ao salvar repositorio!"); 

            }

        });

        delete("/deletarrepositorio/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            boolean success = repositorioService.deletarRepositorio(id);

            if(success){

                return gson.toJson("Repositorio deletado!");

            }else{

                return gson.toJson("Erro ao deletar repositorio!");

            }

        });

        get("/mostrarrepositorio", (req, res) -> {

            res.type("application/json");

            try{

                List<Repositorio> lista = repositorioService.getTodos();

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                return "{\"message\":\"Erro no servidor ao buscar os repositórios.\"}";

            }

        });

    }

}
