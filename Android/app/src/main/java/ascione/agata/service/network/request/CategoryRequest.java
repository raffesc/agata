package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class CategoryRequest {

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("nome")
    private String nome;

    public CategoryRequest(String id_project, String nome) {
        this.id_project = id_project;
        this.nome = nome;
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
