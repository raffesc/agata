package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BackEndCreateAttributeResponse {


    @SerializedName("records")
    private List<BackEndAttributeResponse> attributes;

    public List<BackEndAttributeResponse> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<BackEndAttributeResponse> attributes) {
        this.attributes = attributes;
    }
}
