package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class EndpointRequest {

    @SerializedName("id_project")
    private String id_project;

    @SerializedName("title")
    private String title;

    @SerializedName("descrizione")
    private String descrizione;

    public EndpointRequest(String id_project, String title, String descrizione) {
        this.id_project = id_project;
        this.title = title;
        this.descrizione = descrizione;
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
}
