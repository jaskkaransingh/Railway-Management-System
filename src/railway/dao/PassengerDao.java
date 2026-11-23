package railway.dao;

import railway.database.DBConnection;
import railway.model.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class PassengerDao {

    public int addPassenger(Passenger passenger) {

        String query = """
                INSERT INTO passengers (name, age, gender)
                VALUES (?, ?, ?)
                """;

        try (
            Connection con = DBConnection.getConnection();	
            PreparedStatement ps = con.prepareStatement(
                    query,
                    PreparedStatement.RETURN_GENERATED_KEYS
            )
        ) {

            ps.setString(1, passenger.getName());
            ps.setInt(2, passenger.getAge());
            ps.setString(3, passenger.getGender());

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    public Passenger getPassengerById(int passengerId) {

        String query = """
                SELECT *
                FROM passengers
                WHERE passenger_id = ?
                """;

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(query)
        ) {

            ps.setInt(1, passengerId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Passenger passenger = new Passenger(
                            rs.getInt("passenger_id"),
                            rs.getString("name"),
                            rs.getInt("age"),
                            rs.getString("gender")
                    );

                    return passenger;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public int addPassenger(Connection con, Passenger passenger) {

        String sql = "INSERT INTO passengers (name, age, gender) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, passenger.getName());
            ps.setInt(2, passenger.getAge());
            ps.setString(3, passenger.getGender());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}