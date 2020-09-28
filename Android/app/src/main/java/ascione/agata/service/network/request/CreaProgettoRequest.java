package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class CreaProgettoRequest {

    @SerializedName("id_owner")
    private String id_owner;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("title")
    private String title;

    @SerializedName("icon")
    private String icon;

    @SerializedName("main_color")
    private String main_color;

    public CreaProgettoRequest(String id_owner, String id_user, String title, String icon, String main_color) {
        this.id_owner = id_owner;
        this.id_user = id_user;
        this.title = title;
        this.icon = icon;
        this.main_color = main_color;
    }
}
