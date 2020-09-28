package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class BugRequest {

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("id_category")
    private String id_category;

    @SerializedName("title")
    private String title;

    @SerializedName("descrizione")
    private String descrizione;

    public String getId_project() {
        return id_project;
    }

    public void setId_project(String id_project) {
        this.id_project = id_project;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
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
}
