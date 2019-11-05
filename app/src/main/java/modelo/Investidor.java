package modelo;

public class Investidor {

    private String nome;
    private String email;
    private String senha;
    private int patrimoio;
    private String tipo;
    private String id;

    public Investidor() {
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

    public int getPatrimoio() {
        return patrimoio;
    }

    public void setPatrimoio(int patrimoio) {
        this.patrimoio = patrimoio;
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
