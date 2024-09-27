package model;

public class Aluno {
    
    private int id;
    private int matricula;


    public Aluno(int id, int matricula) {

        this.id = id;
        this.matricula = matricula;

    }

    public int getId() {

        return id;

    }

    public int getMatricula() {

        return matricula;

    }

}
