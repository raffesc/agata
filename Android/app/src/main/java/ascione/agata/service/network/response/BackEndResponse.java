package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BackEndResponse {

    @SerializedName("records")
    private List<BackEndRecordResponse> records;

    public List<BackEndRecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<BackEndRecordResponse> records) {
        this.records = records;
    }
}
