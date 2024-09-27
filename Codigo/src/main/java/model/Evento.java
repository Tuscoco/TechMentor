package model;

public class Evento {
    
    private String local;
    private String data_hora;
    private String nome;
    private int materia;
    
    
	public Evento(String local, String data_hora, String nome, int materia) {

		this.local = local;
		this.data_hora = data_hora;
		this.nome = nome;
		this.materia = materia;

	}


	public String getLocal() {
		return local;
	}


	public String getData_hora() {
		return data_hora;
	}


	public String getNome() {
		return nome;
	}


	public int getMateria() {
		return materia;
	}

}
