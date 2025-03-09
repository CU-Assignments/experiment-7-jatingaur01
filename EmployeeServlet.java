import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Employee Search</h2>");
        out.println("<form method='post'>");
        out.println("Enter Employee ID: <input type='text' name='empId'><br>");
        out.println("<input type='submit' value='Search'>");
        out.println("</form>");
        out.println("<h2>Employee List</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees_db", "Nikhil", "Nikhil040");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM employees");
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name") + "</td><td>" + rs.getString("department") + "</td></tr>");
            }
            out.println("</table>");

            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }

        out.println("</body></html>");
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String empId = request.getParameter("empId");

        out.println("<html><body>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employees_db", "Nikhil", "Nikhil040");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM employees WHERE id = ?");
            ps.setInt(1, Integer.parseInt(empId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Employee Details</h2>");
                out.println("ID: " + rs.getInt("id") + "<br>");
                out.println("Name: " + rs.getString("name") + "<br>");
                out.println("Department: " + rs.getString("department") + "<br>");
            } else {
                out.println("<h2>No Employee Found with ID " + empId + "</h2>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }

        out.println("<br><a href='EmployeeServlet'>Back</a>");
        out.println("</body></html>");
        out.close();
    }
}
