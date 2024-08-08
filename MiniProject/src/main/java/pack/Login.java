package pack;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/submit")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String jdbcUrl = "jdbc:mysql://localhost:3306/org?useSSL=false&serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "abhi@0806";

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            
            String sql = "SELECT * FROM customer WHERE uname = ? AND password = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getInt("isLogin") == 1) {
                    out.println("<html><body>");
                    out.println("<h1>Login Failed</h1>");
                    out.println("<p>User is already logged in. Please logout first.</p>");
                    out.println("</body></html>");
                } else {
                    if ("abhi".equals(username)) {
                        response.sendRedirect("Admin.html");
                    } else {
                        String updateSql = "UPDATE customer SET isLogin = 1 WHERE uname = ?";
                        pst = con.prepareStatement(updateSql);
                        pst.setString(1, username);
                        pst.executeUpdate();

                        // Create session
                        HttpSession session = request.getSession();
                        session.setAttribute("username", username);

                        // Redirect to logout.html after successful login
                        response.sendRedirect("Logout.jsp");
                    }
                }
            } else {
                // Redirect to logout.html if login fails
                response.sendRedirect("Logout.jsp");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
