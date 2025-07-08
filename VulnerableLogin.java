import java.sql.*;
import javax.servlet.http.*;

public class VulnerableLogin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "pass");
            Statement stmt = conn.createStatement();

            // 🚨 Vulnerable to SQL Injection!
            String query = "SELECT * FROM users WHERE username = '" + username +
                           "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                response.getWriter().println("Login successful!");
            } else {
                response.getWriter().println("Invalid credentials.");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
