package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Pessoa;
import service.PessoaService;

public class PessoaController {
    
    private PessoaService pessoaService;
    Gson gson = new Gson();


    public PessoaController(PessoaService pessoaService){

        this.pessoaService = pessoaService;

    }

    public void setup(){

        post("/registrarpessoa", (req, res) -> {

            res.type("application/json");

            Pessoa pessoa = gson.fromJson(req.body(), Pessoa.class);
            boolean success = pessoaService.registrarPessoa(pessoa);

            if(success){

                return gson.toJson("Usuario registrado!");

            }else{

                return gson.toJson("Falha ao registrar usuario!");

            }

        });

    }

}
