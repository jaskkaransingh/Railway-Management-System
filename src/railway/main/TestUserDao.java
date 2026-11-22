package railway.main;

import java.sql.SQLException;

import railway.dao.UserDao;
import railway.model.User;

public class TestUserDao {

    public static void main(String[] args) throws SQLException {

        UserDao dao = new UserDao();

        String email = "jaskaran123@gmail.com";

        // Test 1: Check whether email exists
        boolean exists = dao.emailExists(email);

        System.out.println("Email exists: " + exists);


        // Test 2: Fetch user by email
        User user = dao.getUserByEmail(email);

        if (user != null) {
            System.out.println("\nUser Found!");
            System.out.println("ID: " + user.getUserId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Role: " + user.getRole());
        } else {
            System.out.println("User not found!");
        }
    }
}