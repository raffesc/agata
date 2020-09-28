package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class DesignRecordResponse {


    @SerializedName("id")
    private String id;

    @SerializedName("id_category")
    private String id_category;

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("nome")
    private String nome;

    @SerializedName("nome_category")
    private String nome_category;

    @SerializedName("descrizione")
    private String descrizione;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_category() {
        return nome_category;
    }

    public void setNome_category(String nome_category) {
        this.nome_category = nome_category;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
