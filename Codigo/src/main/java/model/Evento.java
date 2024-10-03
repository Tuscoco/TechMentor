package model;

import java.sql.Date;

public class Evento {
    
    private int id;
	private String local;
    private Date data;
    private String nome;
    private int materia;
    
    
	public Evento(int id, String nome, String local, Date data, int materia) {

		this.id = id;
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


	public Date getData() {
		return data;
	}


	public String getNome() {
		return nome;
	}


	public int getMateria() {
		return materia;
	}

}
