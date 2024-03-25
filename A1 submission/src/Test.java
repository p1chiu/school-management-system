import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.util.Properties;

class calGPA {
    public static void main (String args[]) throws SQLException, IOException
    {
        Properties prop = new Properties();

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch (ClassNotFoundException x)
        {
            System.out.println("Driver could not be loaded.");
        }
        String dbacct, passwrd, name;
        char grade;
        int credit;
        String connectionString = "jdbc:oracle:thin:@//localhost:1521/freepdb1";

        Scanner scanner = new Scanner(System.in);

//        System.out.println("Enter database account: ");
//        dbacct = scanner.nextLine();
//        System.out.println("Enter password: ");
//        passwrd = scanner.nextLine();
        try (InputStream input = new FileInputStream("database.properties")) {
            prop.load(input);
            dbacct = prop.getProperty("database.user");
            passwrd = prop.getProperty("database.password");
        }
        Connection conn = DriverManager.getConnection(connectionString, dbacct, passwrd);

        String stmt1 = "select G.Grade, C.Credit_hours " +
                "from STUDENT S, GRADE_REPORT G, SECTION SEC, COURSE C " +
                "where G.Student_number=S.Student_number AND " +
                "G.Section_identifier=SEC.Section_identifier AND " +
                "SEC.Course_number=C.Course_number AND S.Name=?";

        PreparedStatement p = conn.prepareStatement(stmt1);
        System.out.println("Please enter your name: ");
        name = scanner.nextLine();
        p.clearParameters();
        p.setString(1, name);
        ResultSet r = p.executeQuery();

        while (r.next()) {
            // Assuming Grade and Credit_hours are of type CHAR and INTEGER respectively
            char grader = r.getString("Grade").charAt(0); // Assuming Grade is CHAR
            int creditHours = r.getInt("Credit_hours");  // Assuming Credit_hours is INTEGER

            System.out.println("Grade: " + grader + ", Credit Hours: " + creditHours);
        }

        p.clearParameters();
        p.setString(1, name);
        ResultSet r2 = p.executeQuery();

        double count=0, sum=0, avg=0;
        while(r2.next())
        {
            grade = r2.getString("Grade").charAt(0);
            credit = r2.getInt("Credit_hours");
            switch (grade)
            {
                case 'A': sum=sum+(4*credit); count=count+1; break;
                case 'B': sum=sum+(3*credit); count=count+1; break;
                case 'C': sum=sum+(2*credit); count=count+1; break;
                case 'D': sum=sum+(1*credit); count=count+1; break;
                case 'F': sum=sum+(0*credit); count=count+1; break;
                default: System.out.println("This grade "+grade+" will not be calculated."); break;
            }
        };
        avg = sum/count;
        System.out.println("Student named "+name+" has a grade point average "+avg+".");
        r.close();
    }
}