package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class DesignImageResponse {

    @SerializedName("status_code")
    private Integer status_code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DesignImageDataResponse data;

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DesignImageDataResponse getData() {
        return data;
    }

    public void setData(DesignImageDataResponse data) {
        this.data = data;
    }
}
