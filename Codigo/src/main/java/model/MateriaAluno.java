package model;

public class MateriaAluno {
    
    private int id_aluno;
    private int id_materia;


    public MateriaAluno(int id_aluno, int id_materia) {

        this.id_aluno = id_aluno;
        this.id_materia = id_materia;

    }


    public int getIdAluno() {
        return id_aluno;
    }


    public int getIdMateria() {
        return id_materia;
    }

}
