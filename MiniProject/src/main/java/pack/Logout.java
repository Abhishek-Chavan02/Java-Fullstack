package pack;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/logout")
public class Logout extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String jdbcUrl = "jdbc:mysql://localhost:3306/org?useSSL=false&serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "abhi@0806";

        HttpSession session = request.getSession(false);

        if (session != null) {
            String username = (String) session.getAttribute("username");

            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

                String updateSql = "UPDATE customer SET isLogin = 0 WHERE uname = ?";
                pst = con.prepareStatement(updateSql);
                pst.setString(1, username);
                pst.executeUpdate();

                // Retrieve data from the datahouse table
                String querySql = "SELECT * FROM datahouse";
                pst = con.prepareStatement(querySql);
                rs = pst.executeQuery();

                // Store the result set data in a list
                List<Product> productList = new ArrayList<>();
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setDescription(rs.getString("description"));

                    // Convert hexadecimal image data to binary
                    String hexImageData = rs.getString("upload_image");
                    byte[] binaryImageData = hexToBytes(hexImageData);
                    product.setUploadImage(binaryImageData);

                    product.setCreatedAt(rs.getString("created_at"));
                    productList.add(product);
                }

                // Store the product list in the request
                request.setAttribute("productList", productList);

                // Forward to the JSP page
                RequestDispatcher dispatcher = request.getRequestDispatcher("Logout.jsp");
                dispatcher.forward(request, response);

                // Invalidate session after forwarding
                session.invalidate();

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                PrintWriter out = response.getWriter();
                out.println("Error: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            response.sendRedirect("index.html");
        }
    }

    // Helper method to convert hexadecimal string to bytes
    private byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                                 + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }
}
