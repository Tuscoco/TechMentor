package model;

public class Materia implements Comparable<Materia>{
    
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


    @Override
    public int compareTo(Materia outra) {
        // TODO Auto-generated method stub
        return this.nome.compareTo(outra.getNome());
    }

}
