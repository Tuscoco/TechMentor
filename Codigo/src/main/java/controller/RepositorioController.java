package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.RepositorioService;

public class RepositorioController {
    
    private RepositorioService repositorioService;
    Gson gson = new Gson();


    public RepositorioController(RepositorioService repositorioService){

        this.repositorioService = repositorioService;

    }

    public void setup(){}

}
