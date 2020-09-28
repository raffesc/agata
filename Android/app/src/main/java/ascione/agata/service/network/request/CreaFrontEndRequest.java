package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class CreaFrontEndRequest {

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("id_owner")
    private String id_owner;

    @SerializedName("nome")
    private String nome;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    public CreaFrontEndRequest(String id_project, String id_owner, String nome, String status, String type) {
        this.id_project = id_project;
        this.id_owner = id_owner;
        this.nome = nome;
        this.status = status;
        this.type = type;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
