package model;

public class Monitoria {
    
    private int id_monitor;
    private int id_materia;
    private boolean online;
    private int sala = 1101;
    private boolean materia_principal;


    public Monitoria(int id_monitor, int id_materia,int sala, boolean materia_principal) {

        this.id_monitor = id_monitor;
        this.id_materia = id_materia;
        this.online = false;
        this.materia_principal = materia_principal;
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

    

    public void setIsMateriaPrincipal(boolean materia_principal) {
    this.materia_principal = materia_principal;
    }


    public boolean getIsMateriaprincipal() {
        return materia_principal;
    }


    

}


