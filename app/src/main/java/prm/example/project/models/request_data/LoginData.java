package prm.example.project.models.request_data;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("username")
    private String username;
    @SerializedName("token")
    private String token;

    public LoginData(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }
}
