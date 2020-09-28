package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("response")
    private String response;

    public Response(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
