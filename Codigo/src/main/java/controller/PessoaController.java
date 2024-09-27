package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.PessoaService;

public class PessoaController {
    
    private PessoaService pessoaService;
    Gson gson = new Gson();


    public PessoaController(PessoaService pessoaService){

        this.pessoaService = pessoaService;

    }

    public void setup(){}

}
