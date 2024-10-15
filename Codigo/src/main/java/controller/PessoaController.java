package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

        post("/alterartipousuario", (req, res) -> {

            res.type("application/json");

            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            int idAlterador = json.get("idAlterador").getAsInt();
            int idAlvo = json.get("idAlvo").getAsInt();
            int novoTipo = json.get("novoTipo").getAsInt();

            try{

                boolean success = pessoaService.alterarTipoUsuario(idAlterador, idAlvo, novoTipo);

                if(success){

                    res.status(200);
                    return gson.toJson("Tipo de usuario alterado!");

                }else{

                    res.status(403);
                    return gson.toJson("Não tem permissão!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro ao alterar!");

            }

        });

        post("/alterarsenha", (req, res) -> {

            res.type("application/json");

            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            String senha = json.get("senha").getAsString();
            int id = json.get("id").getAsInt();

            try{

                boolean success = pessoaService.alterarSenha(senha, id);

                if(success){

                    res.status(200);
                    return gson.toJson("Senha alterada!");

                }else{

                    res.status(403);
                    return gson.toJson("Erro ao alterar senha!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro!");

            }

        });

        post("/alteraremail", (req, res) -> {

            res.type("application/json");

            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            String email = json.get("email").getAsString();
            int id = json.get("id").getAsInt();

            try{

                boolean success = pessoaService.alterarEmail(email, id);

                if(success){

                    res.status(200);
                    return gson.toJson("Email alterado!");

                }else{

                    res.status(403);
                    return gson.toJson("Erro ao alterar Email!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro!");

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
