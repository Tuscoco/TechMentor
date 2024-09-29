package model;

public class Repositorio {
    
    private int id;
    private String nome;
    private String link;
    private String descricao;
    private int cod_materia;


    public Repositorio(int id, String nome, String link, String descricao, int cod_materia) {

        this.id = id;
        this.nome = nome;
        this.link = link;
        this.descricao = descricao;
        this.cod_materia = cod_materia;

    }


    public int getId() {
        return id;
    }
    
    
    public int getCod_materia() {
        return cod_materia;
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
