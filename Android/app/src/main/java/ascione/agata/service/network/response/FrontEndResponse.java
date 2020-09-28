package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FrontEndResponse {


    @SerializedName("model")
    private List<FrontEndRecordResponse> model;

    @SerializedName("view")
    private List<FrontEndRecordResponse> view;

    @SerializedName("controller")
    private List<FrontEndRecordResponse> controller;

    public List<FrontEndRecordResponse> getModel() {
        return model;
    }

    public void setModel(List<FrontEndRecordResponse> model) {
        this.model = model;
    }

    public List<FrontEndRecordResponse> getView() {
        return view;
    }

    public void setView(List<FrontEndRecordResponse> view) {
        this.view = view;
    }

    public List<FrontEndRecordResponse> getController() {
        return controller;
    }

    public void setController(List<FrontEndRecordResponse> controller) {
        this.controller = controller;
    }
}
