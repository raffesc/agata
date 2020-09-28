package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ascione.agata.service.network.response.BackEndAttributeResponse;

public class BackEndAttributeRequest {

    @SerializedName("attributes")
    private List<BackEndAttributeResponse> attributes;



    public List<BackEndAttributeResponse> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<BackEndAttributeResponse> attributes) {
        this.attributes = attributes;
    }
}
