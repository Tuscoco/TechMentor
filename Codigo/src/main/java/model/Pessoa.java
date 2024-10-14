package model;

public class Pessoa {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String imagem;
    private int tipo_usuario;


    public Pessoa(){}
    
    public Pessoa(int id, String nome, String email, String senha, int tipo_usuario) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo_usuario = tipo_usuario;

    }

    public Pessoa(int id, String senha) {

        this.id = id;
        this.senha = senha;

    }


    public void setImagem(String url) {
        this.imagem = url;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
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


    public void setSenha(String senha){
        this.senha = senha;
    }


    public String getImagem() {
        return imagem;
    }


    public int getTipoUsuario() {
        return tipo_usuario;
    }

}
