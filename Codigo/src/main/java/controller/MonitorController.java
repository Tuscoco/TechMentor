package controller;

import static spark.Spark.*;
import com.google.gson.Gson;
import service.MonitorService;

public class MonitorController {
    
    private MonitorService monitorService;
    Gson gson = new Gson();


    public MonitorController(MonitorService monitorService){

        this.monitorService = monitorService;

    }

    public void setup(){}

}
