package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class FrontEndCreateResponse {

    @SerializedName("id")
    private String id;


    @SerializedName("id_project")
    private String id_project;

    @SerializedName("nome")
    private String nome;


    @SerializedName("id_owner")
    private String id_owner;


    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;


    public FrontEndCreateResponse(String id, String id_project, String nome, String id_owner, String status, String type) {
        this.id = id;
        this.id_project = id_project;
        this.nome = nome;
        this.id_owner = id_owner;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
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
