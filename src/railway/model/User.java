
package railway.model;

/**
 * User model - represents one row of the USERS table.
 *
 * It only holds data (fields + getters/setters).
 * Database operations belong to UserDAO.
 * Business logic belongs to UserService.
 */
public class User {

    private int userId;
    private String name;
    private String email;
    private String mobile;
    private String passwordHash;
    private String role;

    // Empty constructor for DAO
    public User() {
    }

    // Constructor for a new user
    public User(String name, String email, String mobile,
                String passwordHash, String role) {

        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Constructor for an existing user
    public User(int userId, String name, String email,
                String mobile, String passwordHash, String role) {

        this(name, email, mobile, passwordHash, role);
        this.userId = userId;
    }

    // Constructor retained from teammate's implementation
    public User(int userId, String name, String email,
                String passwordHash, String role) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Constructor retained from teammate's implementation
    public User(String name, String email, String passwordHash) {

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = "USER";
    }

    // Getters and setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Check whether the user is an administrator
    public boolean isAdmin() {
        return "ADMIN".equals(role);
    }

    // Password hash is intentionally excluded
    @Override
    public String toString() {
        return "User{id=" + userId
                + ", name='" + name
                + "', email='" + email
                + "', mobile='" + mobile
                + "', role='" + role + "'}";
    }
}
