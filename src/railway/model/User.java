package railway.model;

public class User {

    private int userId;
    private String name;
    private String email;
    private String passwordHash;
    private String role;

    public User(int userId, String name, String email,
                String passwordHash, String role) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(String name, String email, String passwordHash) {

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = "USER";
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}