package modelo;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.net.URL;

public class IdeiaModelo implements Serializable {
    private String Id;
    private String nomeuser;
    private String conteudo;
    private String IdEM;
    private String imguser; // vai armazenar o identificador do recurso
    private String descricao;


    public IdeiaModelo(){

    }


    public IdeiaModelo(String nomeuser, String conteudo, String imguser, String descricao, String Id) {
        this.nomeuser = nomeuser;
        this.conteudo = conteudo;
        this.imguser = imguser;
        this.descricao = descricao;
        this.Id = Id;


    }




// métodos getters e setters


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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

    public String getImguser() {
        return imguser;
    }

    public void setImguser(String imguser) {
        this.imguser = imguser;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String imgpub) {
        this.descricao = imgpub;
    }
}



