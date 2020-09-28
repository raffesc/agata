package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class FrontEndAttributeRequest {

    @SerializedName("id_front_end")
    private String id_front_end;

    @SerializedName("value")
    private String value;

    @SerializedName("priv")
    private String priv;

    @SerializedName("type")
    private String type;

    public FrontEndAttributeRequest(String id_front_end, String value, String priv, String type) {
        this.id_front_end = id_front_end;
        this.value = value;
        this.priv = priv;
        this.type = type;
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
