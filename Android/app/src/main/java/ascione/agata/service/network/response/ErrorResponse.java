package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
