package prm.example.project.models.request_object;

public class SignUpObject {
    private String address;
    private String birthDate;
    private String email;
    private boolean gender;
    private String name;
    private String password;
    private String username;

    public SignUpObject() {
    }

    public SignUpObject(String address, String birthDate, String email, boolean gender, String name, String password, String username) {
        this.address = address;
        this.birthDate = birthDate;
        this.email = email;
        this.gender = gender;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "SignUpObject{" +
                "address='" + address + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
