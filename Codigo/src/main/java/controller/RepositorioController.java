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

        get("/mostrarrepositorio", (req, res) -> {

            res.type("application/json");

            List<Repositorio> lista = repositorioService.getTodos();

            return new Gson().toJson(lista);

        });

        get("/mostrarrepositoriofiltrado", (req, res) -> {

            res.type("application/json");

            int cod_materia = gson.fromJson(req.body(), int.class);

            List<Repositorio> lista = repositorioService.getAlguns(cod_materia);

            return new Gson().toJson(lista);

        });

    }

}
