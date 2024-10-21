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
        Carga_horariaService carga_horariaService = new Carga_horariaService();
        EventoService eventoService = new EventoService();
        RepositorioService repositorioService = new RepositorioService();


        PessoaController pessoa = new PessoaController(pessoaService);
        MateriaController materia = new MateriaController(materiaService);
        AtendimentoController atendimento = new AtendimentoController(atendimentoService);
        MonitorController monitor = new MonitorController(monitorService);
        CargaHorariaController carga_horaria = new CargaHorariaController(carga_horariaService);
        EventoController evento = new EventoController(eventoService);
        RepositorioController repositorio = new RepositorioController(repositorioService);


        pessoa.setup();
        materia.setup();
        atendimento.setup();
        monitor.setup();
        carga_horaria.setup();
        evento.setup();
        repositorio.setup();

    }

}
