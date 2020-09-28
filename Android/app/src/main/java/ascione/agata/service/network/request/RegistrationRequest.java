package ascione.agata.service.network.request;

import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

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

    public RegistrationRequest() {
    }

    public RegistrationRequest(String email, String password, String username, String image, String nome, String cognome, String age) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.image = image;
        this.nome = nome;
        this.cognome = cognome;
        this.age = age;
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
}
