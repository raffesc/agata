package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class FrontEndAttributeResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("id_front_end")
    private String id_front_end;

    @SerializedName("value")
    private String value;

    @SerializedName("priv")
    private String priv;

    @SerializedName("type")
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_front_end() {
        return id_front_end;
    }

    public void setId_front_end(String id_front_end) {
        this.id_front_end = id_front_end;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
}
