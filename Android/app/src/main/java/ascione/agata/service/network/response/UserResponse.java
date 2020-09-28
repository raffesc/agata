package ascione.agata.service.network.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("userToken")
    private String userToken;

    @SerializedName("username")
    private String username;

    @SerializedName("image")
    private String image;

    @SerializedName("nome")
    private String nome;

    @SerializedName("cognome")
    private String cognome;

    @SerializedName("age")
    private String age;

    @SerializedName("contract")
    private String contract;

    @SerializedName("id")
    private String id;

    public UserResponse(String email, String password, String userToken, String username, String image, String nome, String cognome, String age, String contract, String id) {
        this.email = email;
        this.password = password;
        this.userToken = userToken;
        this.username = username;
        this.image = image;
        this.nome = nome;
        this.cognome = cognome;
        this.age = age;
        this.contract = contract;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

