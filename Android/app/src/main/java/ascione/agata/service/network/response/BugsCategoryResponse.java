package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class BugsCategoryResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("nome")
    private String nome;

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
}
