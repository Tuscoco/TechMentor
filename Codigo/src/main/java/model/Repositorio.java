package model;

public class Repositorio {
    
    private int cod_materia;
    private String nome;
    private String link;
    private String descricao;


    public Repositorio(int cod_materia, String nome, String link) {

        this.cod_materia = cod_materia;
        this.nome = nome;
        this.link = link;

    }


    public void setDescricao(String texto) {
        this.descricao = texto;
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
