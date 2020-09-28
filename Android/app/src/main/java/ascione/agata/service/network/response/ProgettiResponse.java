package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProgettiResponse {

    @SerializedName("records")
    private List<ProgettiRecordResponse> records;

    public List<ProgettiRecordResponse> getRecords() {
        return records;
    }

    public ProgettiResponse() {
        this.records = new ArrayList<>();
    }

    public void setRecords(List<ProgettiRecordResponse> records) {
        this.records = records;
    }
}
