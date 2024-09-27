package model;

public class Coordenador {
    
    private int cod_coordenador;
    private int id;


    public Coordenador(int cod_coordenador, int id) {

        this.cod_coordenador = cod_coordenador;
        this.id = id;

    }


    public int getCod_coordenador() {
        return cod_coordenador;
    }


    public int getId() {
        return id;
    }

}
