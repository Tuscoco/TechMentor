package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import model.Materia;
import service.MateriaService;

public class MateriaController {
    
    private MateriaService materiaService;
    Gson gson = new Gson();


    public MateriaController(MateriaService materiaService){

        this.materiaService = materiaService;

    }

    public void setup(){

        get("/mostrarmateria/:id", (req, res) -> {

            try{

                int id = Integer.parseInt(req.params(":id"));

                Materia materia = materiaService.findMateriaById(id);

                return gson.toJson(materia);

            }catch(Exception e){

                res.status(500);

                return gson.toJson("Erro ao recuperar materia!");

            }

        });

    }

}
