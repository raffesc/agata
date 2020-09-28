package ascione.agata.service.network.response;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

public class DesignImageDataResponse {

    @SerializedName("size")
    private Integer size;

    @SerializedName("file_name")
    private String file_name;

    @SerializedName("type")
    private String type;

    @SerializedName("resolution")
    private String resolution;

    @SerializedName("thumb")
    private Boolean thumb;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Boolean getThumb() {
        return thumb;
    }

    public void setThumb(Boolean thumb) {
        this.thumb = thumb;
    }
}
