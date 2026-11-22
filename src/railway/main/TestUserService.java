package railway.main;

import railway.service.UserService;

import java.sql.SQLException;

import railway.model.User;

public class TestUserService {

    public static void main(String[] args) throws SQLException {

        UserService service = new UserService();

        
        boolean registered = service.registerUser(
                "Rahul",
                "rahul123@gmail.com",
                "rahul123"
        );

        System.out.println("Registration: " + registered);


        
        User user = service.loginUser(
                "rahul123@gmail.com",
                "rahul123"
        );

        if (user != null) {
            System.out.println("\nLogin Successful!");
            System.out.println("Welcome " + user.getName());
        } else {
            System.out.println("\nLogin Failed!");
        }
    }
}