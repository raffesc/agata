package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class BackEndAttributeResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("id_back_end")
    private String id_back_end;

    @SerializedName("title")
    private String title;

    @SerializedName("priv")
    private  String priv;

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private String value;

    public BackEndAttributeResponse(String id, String id_back_end, String title, String priv, String type, String value) {
        this.id = id;
        this.id_back_end = id_back_end;
        this.title = title;
        this.priv = priv;
        this.type = type;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_back_end() {
        return id_back_end;
    }

    public void setId_back_end(String id_back_end) {
        this.id_back_end = id_back_end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriv() {
        return priv;
    }

    public void setPriv(String priv) {
        this.priv = priv;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
