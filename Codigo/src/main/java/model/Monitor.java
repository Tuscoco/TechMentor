package model;

public class Monitor {
    
    private int matricula;
    private int materia;
    private boolean esta_online;


    public Monitor(int matricula, int materia, boolean esta_online) {

        this.matricula = matricula;
        this.materia = materia;
        this.esta_online = esta_online;

    }


    public int getMatricula() {
        return matricula;
    }


    public int getMateria() {
        return materia;
    }


    public boolean isOnline() {
        return esta_online;
    }

}
