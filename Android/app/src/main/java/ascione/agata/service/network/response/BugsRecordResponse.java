package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class BugsRecordResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("id_category")
    private String id_category;

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("title")
    private String title;

    @SerializedName("descrizione")
    private String descrizione;

    @SerializedName("nome_category")
    private String nome_category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNome_category() {
        return nome_category;
    }

    public void setNome_category(String nome_category) {
        this.nome_category = nome_category;
    }
}
