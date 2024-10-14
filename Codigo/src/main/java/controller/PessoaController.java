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

        post("/loginpessoa", (req, res) -> {

            res.type("application/json");

            Pessoa pessoa = gson.fromJson(req.body(), Pessoa.class);

            boolean success = pessoaService.loginPessoa(pessoa);

            if(success){

                return gson.toJson("Login concluido!");

            }else{

                return gson.toJson("Falha no login!");

            }

        });

        get("/tipousuario/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            
            Pessoa pessoa = new Pessoa();
            pessoa.setId(id);

            try{

                int tipoUsuario = pessoaService.getTipoUsuario(pessoa);

                if(tipoUsuario != -1){

                    res.status(200);

                    return gson.toJson(new String[] {Integer.toString(tipoUsuario), "true"});

                }else{

                    res.status(404);

                    return gson.toJson(new String[] {"Pessoa não encontrada", "false"});

                }

            }catch(Exception e){

                res.status(500);

                return gson.toJson(new String[] {"Ocorreu um erro", "false"});

            }

        });

    }

}
