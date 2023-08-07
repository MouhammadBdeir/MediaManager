package oniline.itlogic.mediamanager.model;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter und Setter für username und password
    // ...

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Optional: Weitere Hilfsmethoden oder Validierungen
}
