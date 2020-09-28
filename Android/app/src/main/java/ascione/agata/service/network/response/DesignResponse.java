package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DesignResponse {

    @SerializedName("records")
    private List<DesignRecordResponse> records;

    public List<DesignRecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<DesignRecordResponse> records) {
        this.records = records;
    }
}
