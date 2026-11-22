package railway.dao;

import railway.database.DBConnection;
import railway.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {


    public boolean registerUser(User user) throws SQLException {

        String query = """
                INSERT INTO users (name, email, password_hash, role)
                VALUES (?, ?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getRole());

            return ps.executeUpdate() > 0;
        }
    }



    public User getUserByEmail(String email) throws SQLException {

        String query = "SELECT * FROM users WHERE email = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                    );
                }
            }
        }

        return null;
    }



    public boolean emailExists(String email) throws SQLException {

        String query = "SELECT user_id FROM users WHERE email = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}