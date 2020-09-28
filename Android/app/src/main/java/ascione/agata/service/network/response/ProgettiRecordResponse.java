package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class ProgettiRecordResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("id_owner")
    private String id_owner;

    @SerializedName("title")
    private String title;

    @SerializedName("id_users")
    private String id_users;

    @SerializedName("icon")
    private String icon;

    @SerializedName("main_color")
    private String main_color;

    @SerializedName("published")
    private String published;

    @SerializedName("num_like")
    private String num_like;

    @SerializedName("ranking")
    private String ranking;

    public ProgettiRecordResponse(String id, String id_owner, String title, String id_users, String icon, String main_color, String published, String num_like, String ranking) {
        this.id = id;
        this.id_owner = id_owner;
        this.title = title;
        this.id_users = id_users;
        this.icon = icon;
        this.main_color = main_color;
        this.published = published;
        this.num_like = num_like;
        this.ranking = ranking;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_owner() {
        return id_owner;
    }

    public void setId_owner(String id_owner) {
        this.id_owner = id_owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId_users() {
        return id_users;
    }

    public void setId_users(String id_users) {
        this.id_users = id_users;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain_color() {
        return main_color;
    }

    public void setMain_color(String main_color) {
        this.main_color = main_color;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getNum_like() {
        return num_like;
    }

    public void setNum_like(String num_like) {
        this.num_like = num_like;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
