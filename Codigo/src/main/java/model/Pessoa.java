package model;

public class Pessoa {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String imagem;


    public Pessoa(int id, String nome, String email, String senha) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;

    }


    public void setImagem(String url) {
        this.imagem = url;
    }


    public int getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public String getEmail() {
        return email;
    }


    public String getSenha() {
        return senha;
    }


    public String getImagem() {
        return imagem;
    }

}
