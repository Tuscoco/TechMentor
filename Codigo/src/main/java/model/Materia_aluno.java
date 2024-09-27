package model;

public class Materia_aluno {
    
    private int cod_materia;
    private int matricula;


    public Materia_aluno(int cod_materia, int matricula) {

        this.cod_materia = cod_materia;
        this.matricula = matricula;

    }


    public int getCod_materia() {
        return cod_materia;
    }


    public int getMatricula(){
        return matricula;
    }

}
