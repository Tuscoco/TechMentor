package model;

public class Repositorio {
    
    private int id;
    private String nome;
    private String link;
    private String descricao;
    private int id_materia;


    public Repositorio(int id, String nome, String link, String descricao, int id_materia) {

        this.id = id;
        this.nome = nome;
        this.link = link;
        this.descricao = descricao;
        this.id_materia = id_materia;

    }


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
