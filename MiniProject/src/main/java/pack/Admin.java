package pack;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/upload")
@MultipartConfig
public class Admin extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String jdbcUrl = "jdbc:mysql://localhost:3306/org?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String dbUsername = "root";
        String dbPassword = "abhi@0806";

        String productName = request.getParameter("product-name");
        String price = request.getParameter("price");
        String description = request.getParameter("description");
        Part filePart = request.getPart("upload");
        String fileName = filePart.getSubmittedFileName();
        String uploadPath = "path_to_upload_directory" + File.separator + fileName;

        // Ensure the upload directory exists
        File uploadDir = new File("path_to_upload_directory");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try (InputStream fileContent = filePart.getInputStream();
             OutputStream os = new FileOutputStream(new File(uploadPath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileContent.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        Connection con = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String sql = "INSERT INTO datahouse (product_name, price, description, upload_image) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, productName);
            pst.setString(2, price);
            pst.setString(3, description);
            pst.setString(4, fileName);
            pst.executeUpdate();

            // Redirect to Logout.html after successful upload
            response.sendRedirect("Logout.jsp");

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
