package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Pessoa;
import model.Monitoria;
import service.MonitorService;
import service.PessoaService;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.*;

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
            int tipoantigo = pessoaService.getTipoUsuario(idAlvo);

            try{

                boolean success = pessoaService.alterarTipoUsuario(idAlterador, idAlvo, novoTipo);

                if(success){

                    if(novoTipo == 2){

                        int idMateria = json.get("idMateria").getAsInt();
                        
                        Monitoria monitor = new Monitoria(idAlvo, idMateria);

                        MonitorService monitorService = new MonitorService();

                        boolean success2 = monitorService.registrarMonitor(monitor);

                        if(success2){

                            return gson.toJson("Monitor Adicionado!");

                        }else{

                            return gson.toJson("Erro ao adicionar monitor!");

                        }

                    }else{

                        if(tipoantigo == 2){
                            
                            MonitorService monitorService = new MonitorService();

                            boolean success3 = monitorService.deletarMonitor(idAlvo);

                            if(success3){

                                return gson.toJson("Monitor removido!");

                            }else{

                                return gson.toJson("Falha ao remover monitor!");

                            }

                        }

                    }

                }else{

                    res.status(403);
                    return gson.toJson("Não tem permissão!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro ao alterar!");

            }

            return req;

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

        post("/alterarnome", (req, res) -> {

            res.type("application/json");

            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            String nome = json.get("nome").getAsString();
            int id = json.get("id").getAsInt();

            try{

                boolean success = pessoaService.alterarNome(nome, id);

                if(success){

                    res.status(200);
                    return gson.toJson("Nome alterado!");

                }else{

                    res.status(403);
                    return gson.toJson("Erro ao alterar nome!");

                }

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return gson.toJson("Erro!");

            }

        });

        post("/salvarfoto", (req, res) -> {

            res.type("application/json");

            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            int id = Integer.parseInt(req.queryParams("id"));
            Part filePart = req.raw().getPart("foto");

            if(filePart != null){

                try(InputStream inputStream = filePart.getInputStream()){

                    long tamanhoFoto = filePart.getSize();
                    
                    boolean sucesso = pessoaService.alterarFoto(id, inputStream, tamanhoFoto);
                    
                    if(sucesso){

                        res.status(200);
                        return "Foto atualizada com sucesso!";

                    }else{

                        res.status(500);
                        return "Erro ao atualizar a foto no banco de dados.";

                    }

                }catch(Exception e){

                    e.printStackTrace();
                    res.status(500);
                    return "Erro ao processar a foto.";

                }

            }else{

                res.status(400);
                return "Nenhuma foto foi enviada.";

            }

        });

        get("/tipousuario/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            try{

                int tipoUsuario = pessoaService.getTipoUsuario(id);

                if(tipoUsuario != -1){

                    res.status(200);

                    return gson.toJson(Integer.toString(tipoUsuario));

                }else{

                    res.status(404);

                    return gson.toJson("Pessoa não encontrada");

                }

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Ocorreu um erro");

            }

        });

        get("/mostrarfoto/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));
            InputStream fotoStream = pessoaService.getFoto(id);

            if(fotoStream != null){

                byte[] fotoBytes = fotoStream.readAllBytes();

                String fotoB64 = Base64.getEncoder().encodeToString(fotoBytes);

                String jsonRes = "{\"foto\":\"" + fotoB64 + "\"}";

                res.status(200);
                return jsonRes;

            }else{

                res.status(404);
                return "{\"erro\": \"Foto não encontrada.\"}";

            }

        });

        get("/mostrarnome/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            String nome = pessoaService.getNome(id);

            if(nome != null){

                res.status(200);

                return gson.toJson(nome);

            }else{

                res.status(404);

                return gson.toJson("Erro ao procurar o nome!");

            }

        });

        get("/mostraremail/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            String email = pessoaService.getEmail(id);

            if(email != null){

                res.status(200);

                return gson.toJson(email);

            }else{

                res.status(404);

                return gson.toJson("Erro ao procurar o email!");

            }

        });

        get("/mostrarusuarios/:tipo", (req, res) -> {

            res.type("application/json");

            try{

                int tipo = Integer.parseInt(req.params(":tipo"));

                List<Pessoa> lista = pessoaService.getUsuarios(tipo);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);

                return "{\"message\":\"Erro no servidor ao buscar os usuarios.\"}";

            }

        });

        delete("/removerfoto/:id", (req, res) -> {

            res.type("application/json");

            int id = Integer.parseInt(req.params(":id"));

            boolean success = pessoaService.removerFoto(id);

            if(success){

                return gson.toJson("Foto removida com sucesso!");

            }else{

                return gson.toJson("Erro ao alterar foto!");

            }

        });

    }

}
