package model;

public class Atendimento {
    
    private int id;
	private int matricula_a;
    private int matricula_m;
    private String data;
    private int cod_materia;
    private String tema_duvida;
    private String descricao;
    private boolean duvida_sanada;


    public Atendimento(int id, int matricula_a, int matricula_m, int cod_materia, String data, String tema_duvida, String descricao, boolean duvida_sanada) {

        this.id = id;
		this.matricula_a = matricula_a;
        this.matricula_m = matricula_m;
		this.cod_materia = cod_materia;
        this.data = data;
        this.tema_duvida = tema_duvida;
        this.descricao = descricao;
        this.duvida_sanada = duvida_sanada;

    }


	public int getId() {
		return id;
	}


	public int getMatricula_a() {
		return matricula_a;
	}


	public int getMatricula_m() {
		return matricula_m;
	}


	public String getData() {
		return data;
	}


	public int getCod_materia() {
		return cod_materia;
	}


	public String getTema_duvida() {
		return tema_duvida;
	}


	public String getDescricao() {
		return descricao;
	}


	public boolean isDuvida_sanada() {
		return duvida_sanada;
	}

}
