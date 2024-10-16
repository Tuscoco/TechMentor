package model;


public class Atendimento {
    
    private int id;
	private int id_monitor;
    private int id_aluno;
	private int id_materia;
    private String data;
    private String tema_duvida;
    private String descricao;
    private boolean duvida_sanada;


    public Atendimento(int id, int id_monitor, int id_aluno, int id_materia, String data, String tema_duvida, String descricao, boolean duvida_sanada) {

        this.id = id;
		this.id_monitor = id_monitor;
        this.id_aluno = id_aluno;
		this.id_materia = id_materia;
        this.data = data;
        this.tema_duvida = tema_duvida;
        this.descricao = descricao;
        this.duvida_sanada = duvida_sanada;

    }

	public Atendimento(int id_monitor, int id_aluno, int id_materia, String data, String tema_duvida, String descricao, boolean duvida_sanada) {

		this.id_monitor = id_monitor;
        this.id_aluno = id_aluno;
		this.id_materia = id_materia;
        this.data = data;
        this.tema_duvida = tema_duvida;
        this.descricao = descricao;
        this.duvida_sanada = duvida_sanada;

    }


	public int getId() {
		return id;
	}


	public int getIdMonitor() {
		return id_monitor;
	}


	public int getIdAluno() {
		return id_aluno;
	}


	public String getData() {
		return data;
	}


	public int getIdMateria() {
		return id_materia;
	}


	public String getTemaDuvida() {
		return tema_duvida;
	}


	public String getDescricao() {
		return descricao;
	}


	public boolean isDuvidaSanada() {
		return duvida_sanada;
	}

}
