package prm.example.project.models.request_data;

import com.google.gson.annotations.SerializedName;

public class LoginGoogleData {
    @SerializedName("username")
    private String username;
    @SerializedName("token")
    private String accessToken;


    public LoginGoogleData() {
    }

    public LoginGoogleData(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
