package pack;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/register")
public class Register extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String jdbcUrl = "jdbc:mysql://localhost:3306/org?useSSL=false&serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "abhi@0806";

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection con = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO customer (name, lname, email, mobile, uname, password, isLogin) VALUES (?, ?, ?, ?, ?, ?, 0)";
            pst = con.prepareStatement(sql);
            pst.setString(1, firstName);
            pst.setString(2, lastName);
            pst.setString(3, email);
            pst.setString(4, mobile);
            pst.setString(5, username);
            pst.setString(6, password);
            int rowCount = pst.executeUpdate();

            if (rowCount > 0) {
                out.println("<html><body>");
                out.println("<h1>Registration Successful</h1>");
                out.println("<p>Thank you for registering. You can now <a href='index.html#login'>login</a>.</p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h1>Registration Failed</h1>");
                out.println("<p>Sorry, there was an error processing your request. Please try again later.</p>");
                out.println("</body></html>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
