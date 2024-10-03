package model;

public class Monitoria {
    
    private int id_monitor;
    private int id_materia;
    private boolean online;


    public Monitoria(int id_monitor, int id_materia) {

        this.id_monitor = id_monitor;
        this.id_materia = id_materia;
        this.online = false;

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
