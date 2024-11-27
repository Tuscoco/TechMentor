package model;

public class Pessoa {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private int tipo_usuario = 3;
    private String foto = "https://res.cloudinary.com/deycrrjpb/image/upload/v1732706559/mzyq3m5groxsx42hshrd.jpg";


    public Pessoa(){}
    
    public Pessoa(int id, String nome, String email, String senha) {

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;

    }

    public Pessoa(int id, String nome, int tipo_usuario){

        this.id = id;
        this.nome = nome;
        this.tipo_usuario = tipo_usuario;

    }

    public Pessoa(int id, String senha) {

        this.id = id;
        this.senha = senha;

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


    public int getTipoUsuario() {
        return tipo_usuario;
    }


    public void setTipoUsuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }


    public String getFoto(){
        return foto;
    }

}
