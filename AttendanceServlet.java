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

@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String studentId = request.getParameter("studentId");
        String studentName = request.getParameter("studentName");
        String attendanceDate = request.getParameter("attendanceDate");
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_db", "root", "password");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO attendance (student_id, student_name, date, status) VALUES (?, ?, ?, ?)");
            ps.setString(1, studentId);
            ps.setString(2, studentName);
            ps.setString(3, attendanceDate);
            ps.setString(4, status);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<h2>Attendance Recorded Successfully</h2>");
            } else {
                out.println("<h2>Failed to Record Attendance</h2>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        }

        out.println("<br><a href='attendance.jsp'>Back</a>");
        out.close();
    }
}
