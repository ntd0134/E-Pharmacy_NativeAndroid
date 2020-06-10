package prm.example.project.models.request_object;

public class LoginGoogleObject {
    String idToken;

    public LoginGoogleObject() {
    }

    public LoginGoogleObject(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
