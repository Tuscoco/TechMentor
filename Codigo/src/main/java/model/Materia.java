package model;

public class Materia {
    
    private int id_materia;
    private String nome;


    public Materia(int id_materia, String nome) {

        this.id_materia = id_materia;
        this.nome = nome;

    }


    public int getIdMateria(){
        return id_materia;
    }


    public String getNome() {
        return nome;
    }

}
