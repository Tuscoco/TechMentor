package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Atendimento;
import service.AtendimentoService;

public class AtendimentoController {
    
    private AtendimentoService atendimentoService;
    Gson gson = new Gson();


    public AtendimentoController(AtendimentoService atendimentoService){

        this.atendimentoService = atendimentoService;

    }

    public void setup(){

        post("/salvaratendimento", (req, res) -> {

            res.type("application/json");

            Atendimento atendimento = gson.fromJson(req.body(), Atendimento.class);
            boolean success = atendimentoService.salvarAtendimento(atendimento);

            if(success){

                return gson.toJson("Atendimento enviado!");

            }else{

                return gson.toJson("Falha ao enviar atendimento!");

            }

        });

    }

}
