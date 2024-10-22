package model;


public class Evento {
    
    private int id;
	private String local;
    private String data;
	private String hora;
    private String nome;
    private int materia;
    
    
	public Evento(int id, String nome, String local, String data, String hora, int materia) {

		this.id = id;
		this.local = local;
		this.data = data;
		this.hora = hora;
		this.nome = nome;
		this.materia = materia;

	}

	public Evento(String nome, String local, String data, int materia) {

		this.local = local;
		this.data = data;
		this.nome = nome;
		this.materia = materia;

	}


	public int getId() {
		return id;
	}


	public String getLocal() {
		return local;
	}


	public String getData() {
		return data;
	}


	public void setData(String data) {
		this.data = data;
	}


	public String getHora() {
		return hora;
	}


	public void setHora(String hora) {
		this.hora = hora;
	}


	public String getNome() {
		return nome;
	}


	public int getMateria() {
		return materia;
	}

}
