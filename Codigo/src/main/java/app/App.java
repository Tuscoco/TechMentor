package app;

import static spark.Spark.*;
import controller.*;
import service.*;

public class App 
{

    public static void main( String[] args )
    {

        port(4567);

        staticFiles.externalLocation("src/main/resources/public");

        PessoaService pessoaService = new PessoaService();
        MateriaService materiaService = new MateriaService();
        AtendimentoService atendimentoService = new AtendimentoService();
        MonitorService monitorService = new MonitorService();
        PontoService pontoService = new PontoService();
        EventoService eventoService = new EventoService();
        RepositorioService repositorioService = new RepositorioService();


        PessoaController pessoa = new PessoaController(pessoaService);
        MateriaController materia = new MateriaController(materiaService);
        AtendimentoController atendimento = new AtendimentoController(atendimentoService);
        MonitorController monitor = new MonitorController(monitorService);
        PontoController ponto = new PontoController(pontoService);
        EventoController evento = new EventoController(eventoService);
        RepositorioController repositorio = new RepositorioController(repositorioService);

        get("/", (req, res) -> {
            res.redirect("html/logCad.html");
            return null;
        });        


        pessoa.setup();
        materia.setup();
        atendimento.setup();
        monitor.setup();
        ponto.setup();
        evento.setup();
        repositorio.setup();

    }

}
