import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperations {
    public static void insertData(String username, String password, String contactNumber, String email) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CakeShopDB", "your_username", "your_password");
            String query = "INSERT INTO users (username, password, contact_number, email) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, contactNumber);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
            pstmt.close();
            con.close();
        } catch (SQLException e) {
        }
    }
}
