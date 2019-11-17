package modelo;

public class Empreendedor {

    private String nome;
    private String email;
    private String senha;
    private String id;
    private String tipo;
    private String imguser;




    public String getImguser() {
        return imguser;
    }
    public void setImguser(String imguser) {
        this.imguser = imguser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
