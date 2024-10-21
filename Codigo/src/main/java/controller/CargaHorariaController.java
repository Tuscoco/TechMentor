package controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.CargaHoraria;
import service.Carga_horariaService;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;


public class CargaHorariaController {
    
    private Carga_horariaService carga_horariaService;
    Gson gson = new Gson();


    public CargaHorariaController(Carga_horariaService carga_horariaService){

        this.carga_horariaService = carga_horariaService;

    }

    public void setup(){


        get("/getCargaHorariaAluno", (req,res) ->{
            res.type("application/json");
            JsonObject json = gson.fromJson(req.body(), JsonObject.class);
            int idMonitor = json.get("id_monitor").getAsInt();


            try{
                
                List<CargaHoraria> lista = carga_horariaService.getCargaHorariaAluno(idMonitor);

                return new Gson().toJson(lista);

            }catch(Exception e){

                res.status(500);
                e.printStackTrace();
                return "{\"message\":\"Erro no servidor ao buscar a carga horária.\"}";

            }
        });


        post("/adicionarCarga",(req,res) -> {
            CargaHoraria cargaHoraria = gson.fromJson(req.body(), CargaHoraria.class);
            boolean success = carga_horariaService.adicionarCargarHoraria(cargaHoraria);

            if(success){

                return gson.toJson("Carga horária adicionada!");

            }else{

                return gson.toJson("Falha ao adicionar carga horária!");

            }

        });

        delete("/deleteCargaAluno",(req,res)->{
            CargaHoraria cargaHoraria = gson.fromJson(req.body(), CargaHoraria.class);
            boolean success = carga_horariaService.deletarCargarHorariaAluno(cargaHoraria);

            if(success){

                return gson.toJson("Carga horária removida!");

            }else{

                return gson.toJson("Falha ao remover carga horária!");

            }
        });

        delete("/deleteCargaDia",(req,res)->{
            CargaHoraria cargaHoraria = gson.fromJson(req.body(), CargaHoraria.class);
            boolean success = carga_horariaService.deletarCargarHorariaDia(cargaHoraria);

            if(success){

                return gson.toJson("Carga horária removida!");

            }else{

                return gson.toJson("Falha ao remover carga horária!");

            }
        });

        }

        
    }


