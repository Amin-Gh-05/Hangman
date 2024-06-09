package hangman.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseManager {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/hangman";
    private static final String USER = "postgres";
    private static final String PASSWORD = "2005tmsv";

    private static Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    public static void createUser(String username, String password, String name) {
        try (Connection conn = connect()) {
            String query = "INSERT INTO user_info (name, username, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createGame(String username, String word, int wrongGuesses, int time, boolean win) {
        UUID game_id = UUID.randomUUID();

        try (Connection conn = connect()) {
            String query = "INSERT INTO game_info (game_id, word, wrong_guesses, win, time, username) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setObject(1, game_id);
            ps.setString(2, word);
            ps.setInt(3, wrongGuesses);
            ps.setBoolean(4, win);
            ps.setInt(5, time);
            ps.setString(6, username);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static User readUser(String username) throws SQLException {
        Connection conn = connect();
        String query = "SELECT * FROM user_info WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        conn.close();

        if (rs.next()) {
            return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name")
            );
        }

        return null;
    }

    public static Boolean findUser(String username) throws SQLException {
        Connection conn = connect();
        String query = "SELECT * FROM user_info WHERE username = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        conn.close();

        return rs.next();
    }

    public static List<Game> readGame() throws SQLException {
        List<Game> games = new ArrayList<>();

        Connection conn = connect();
        String query = "SELECT * FROM game_info";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        conn.close();

        while (rs.next()) {
            Game game = new Game(
                    (UUID) rs.getObject("game_id"),
                    rs.getString("username"),
                    rs.getString("word"),
                    rs.getInt("wrong_guesses"),
                    rs.getInt("time"),
                    rs.getBoolean("win")
            );
            games.add(game);
        }

        return games;
    }
}