package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EndpointResponse {

    @SerializedName("records")
    private List<EndpointRecordResponse> records;

    public EndpointResponse(List<EndpointRecordResponse> records) {
        this.records = records;
    }

    public List<EndpointRecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<EndpointRecordResponse> records) {
        this.records = records;
    }
}
