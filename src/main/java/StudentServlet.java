import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public StudentServlet() {
        super();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        PreparedStatement ps = null;
        response.setContentType("text/html");

        String rollno = request.getParameter("rollno");
        String name = request.getParameter("name");
        String branch = request.getParameter("branch");

        String query = "INSERT INTO `student_tbl`(`rollno`, `name`, `branch`) VALUES (?,?,?)";
        PrintWriter pw = response.getWriter();

        try {
            String url = "jdbc:mysql://localhost:3306/sms_db";
            String db_user = "root";
            String db_pass = "";

            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            con = DriverManager.getConnection(url, db_user, db_pass);

            // Prepare the statement
            ps = con.prepareStatement(query);
            ps.setString(1, rollno);
            ps.setString(2, name);
            ps.setString(3, branch);

            // Execute the query
            int r = ps.executeUpdate();
            if (r == 1) {
                pw.println("Record inserted into student_tbl successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("Error: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
                if (pw != null) pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
