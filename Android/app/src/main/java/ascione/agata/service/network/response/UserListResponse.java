package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {

    @SerializedName("records")
    private List<UserListRecordResponse> records;

    public List<UserListRecordResponse> getRecords() {
        return records;
    }

    public void setRecords(List<UserListRecordResponse> records) {
        this.records = records;
    }
}
