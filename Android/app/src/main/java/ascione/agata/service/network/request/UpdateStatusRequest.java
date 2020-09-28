package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class UpdateStatusRequest {

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    public UpdateStatusRequest(String id, String status) {
        this.id = id;
        this.status = status;
    }
}
