package model;

public class Evento {
    
    private int id;
	private String local;
    private String data_hora;
    private String nome;
    private int materia;
    
    
	public Evento(int id, String nome, String local, String data_hora, int materia) {

		this.id = id;
		this.local = local;
		this.data_hora = data_hora;
		this.nome = nome;
		this.materia = materia;

	}


	public int getId() {
		return id;
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
