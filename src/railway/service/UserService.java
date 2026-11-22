package railway.service;

import java.sql.SQLException;

import railway.dao.UserDao;
import railway.model.User;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public boolean registerUser(String name, String email, String password) throws SQLException {

        
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty!");
            return false;
        }

        
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty!");
            return false;
        }

        if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email address!");
            return false;
        }

        
        if (password == null || password.length() < 6) {
            System.out.println("Password must be at least 6 characters!");
            return false;
        }

        
        if (userDao.emailExists(email)) {
            System.out.println("Email already registered!");
            return false;
        }

        
        User user = new User(name, email, password);

       
        return userDao.registerUser(user);
    }


    public User loginUser(String email, String password) throws SQLException {

       
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            System.out.println("Email and password are required!");
            return null;
        }

        // Fetch user from database
        User user = userDao.getUserByEmail(email);

        
        if (user == null) {
            System.out.println("User does not exist!");
            return null;
        }

        
        if (!user.getPasswordHash().equals(password)) {
            System.out.println("Incorrect password!");
            return null;
        }

        
        return user;
    }
}