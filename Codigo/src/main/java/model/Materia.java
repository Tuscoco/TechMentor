package model;

public class Materia {
    
    private int cod_materia;
    private String nome;


    public Materia(int cod_materia, String nome) {

        this.cod_materia = cod_materia;
        this.nome = nome;

    }


    public int getCod_materia(){
        return cod_materia;
    }


    public String getNome() {
        return nome;
    }

}
