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

@WebServlet("/DiningCountServlet")
public class DinningCountServlet extends HttpServlet {

PrintWriter out;

public DinningCountServlet() {
super();
}

public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html");
out=response.getWriter();
String dinning_date=request.getParameter("dinning_date").trim();
int boys=Integer.parseInt(request.getParameter("boys").trim());
int girls=Integer.parseInt(request.getParameter("girls").trim());
int medical_staff=Integer.parseInt(request.getParameter("medical_staff").trim());
int mess_staff=Integer.parseInt(request.getParameter("mess_staff").trim());
int sports=Integer.parseInt(request.getParameter("sports").trim());
int events=Integer.parseInt(request.getParameter("events").trim());
int parents=Integer.parseInt(request.getParameter("parents").trim());
store_data(dinning_date, boys, girls, medical_staff, mess_staff, sports, events, parents);
}

public void store_data(String dinning_date, int boys, int girls, int medical_staff, int mess_staff, int sports, int events, int parents) {
try {
Class.forName("com.mysql.jdbc.Driver");
Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hmms", "root", "");
PreparedStatement pstmt=con.prepareStatement("INSERT INTO dinning_count_status (`dinning_date`, `boys`, `girls`, `medical_staff`, `mess_staff`, `sports`, `events`, `parents`) VALUES (?,?,?,?,?,?,?,?)");
pstmt.setString(1, dinning_date);
pstmt.setInt(2, boys);
pstmt.setInt(3, girls);
pstmt.setInt(4, medical_staff);
pstmt.setInt(5, mess_staff);
pstmt.setInt(6, sports);
pstmt.setInt(7, events);
pstmt.setInt(8, parents);
int i=pstmt.executeUpdate();
if(i==1)
out.println("Record inserted");
pstmt.close(); con.close();
} catch(Exception e) {
out.println(e.toString());
e.printStackTrace();
}
}
}

