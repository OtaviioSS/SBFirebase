package modelo;

public class IdeiaModelo {
    private String nomeuser;
    private String conteudo;
    private String IdEM;
    private int imguser; // vai armazenar o identificador do recurso
    private int imgpub; // vai armazenar o identificador do recurso


    public IdeiaModelo(){

    }


    public IdeiaModelo(String nomeuser, String conteudo, int imguser, int imgpub) {
        this.nomeuser = nomeuser;
        this.conteudo = conteudo;
        this.imguser = imguser;
        this.imgpub = imgpub;


    }
// métodos getters e setters


    public String getIdEM() {
        return IdEM;
    }

    public void setIdEM(String idEM) {
        IdEM = idEM;
    }

    public String getNomeuser() {
        return this.nomeuser;
    }

    public void setNomeuser(String nomeuser) {
        this.nomeuser = nomeuser;
    }

    public String getConteudo()      {
        return this.conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getImguser() {
        return this.imguser;
    }

    public void setImguser(int imguser) {
        this.imguser = imguser;
    }

    public int getImgpub() {
        return this.imgpub;
    }

    public void setImgpub(int imgpub) {
        this.imgpub = imgpub;
    }
}



