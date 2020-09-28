package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class IdRequest {

    @SerializedName("id")
    private String  id;

    public IdRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
