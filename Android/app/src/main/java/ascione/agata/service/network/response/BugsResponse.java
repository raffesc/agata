package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BugsResponse {

    @SerializedName("records")
    private List<BugsRecordResponse> records;

    public BugsResponse() {
        this.records = new ArrayList<>();
    }

    public List<BugsRecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<BugsRecordResponse> records) {
        this.records = records;
    }
}
