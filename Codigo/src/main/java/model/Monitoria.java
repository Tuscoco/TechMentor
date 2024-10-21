package model;

public class Monitoria {
    
    private int id_monitor;
    private int id_materia;
    private boolean online;
    private int sala = 1101;


    public Monitoria(int id_monitor, int id_materia,int sala) {

        this.id_monitor = id_monitor;
        this.id_materia = id_materia;
        this.online = false;
        this.sala = sala;

    }

    public Monitoria(int id_monitor, int id_materia){

        this.id_monitor = id_monitor;
        this.id_materia = id_materia;

    }


    public int getSala() {
        return sala;
    }


    public void setSala(int sala) {
        this.sala = sala;
    }


    public int getIdMonitor() {
        return id_monitor;
    }


    public int getIdMateria() {
        return id_materia;
    }


    public void setOnline(boolean online) {
        this.online = online;
    }


    public boolean isOnline() {
        return online;
    
    }

}


