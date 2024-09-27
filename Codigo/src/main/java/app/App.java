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
        AlunoService alunoService = new AlunoService();
        CoordenadorService coordenadorService = new CoordenadorService();
        MateriaService materiaService = new MateriaService();
        AtendimentoService atendimentoService = new AtendimentoService();
        Materia_alunoService materia_alunoService = new Materia_alunoService();
        MonitorService monitorService = new MonitorService();
        Carga_horariaService carga_horariaService = new Carga_horariaService();
        EventoService eventoService = new EventoService();
        RepositorioService repositorioService = new RepositorioService();


        PessoaController pessoa = new PessoaController(pessoaService);
        AlunoController aluno = new AlunoController(alunoService);
        CoordenadorController coordenador = new CoordenadorController(coordenadorService);
        MateriaController materia = new MateriaController(materiaService);
        AtendimentoController atendimento = new AtendimentoController(atendimentoService);
        Materia_alunoController materia_aluno = new Materia_alunoController(materia_alunoService);
        MonitorController monitor = new MonitorController(monitorService);
        Carga_horariaController carga_horaria = new Carga_horariaController(carga_horariaService);
        EventoController evento = new EventoController(eventoService);
        RepositorioController repositorio = new RepositorioController(repositorioService);


        pessoa.setup();
        aluno.setup();
        coordenador.setup();
        materia.setup();
        atendimento.setup();
        materia_aluno.setup();
        monitor.setup();
        carga_horaria.setup();
        evento.setup();
        repositorio.setup();

    }

}
