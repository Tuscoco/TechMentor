package model;

public class Repositorio {
    
    private int id;
    private String nome;
    private String link;
    private String descricao;
    private int id_materia;


    public Repositorio(int id, String nome, String link) {

        this.id = id;
        this.nome = nome;
        this.link = link;

    }

    /*public Repositorio(String nome, String link){

        this.nome = nome;
        this.link = link;

    }*/


    public int getId() {
        return id;
    }
    
    
    public int getIdMateria() {
        return id_materia;
    }


    public String getNome() {
        return nome;
    }


    public String getLink() {
        return link;
    }


    public String getDescricao() {
        return descricao;
    }

}
