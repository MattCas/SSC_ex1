import java.sql.*;
import java.util.Scanner;

public class DbInterface {
	static String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/uni1";
	static Connection conn = null;
	PreparedStatement pstmt;
	
	public static void main(String[] args) {
		setupConn();
		

	}

	public static void setupConn (){
		try 
		{
			//Setup connection
			conn = DriverManager.getConnection(dbName, "mxc471", "steclike" );	    
			// load the JDBC driver
			System.setProperty("jdbc.drivers", "org.postgresql.Driver") ;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		//Handle connection
		if (conn != null)
		{
			System.out.println("Database Accessed successfully");
		}
		else
		{
			System.out.println("Connection failed!");
		}

	}
	@SuppressWarnings("deprecation")
	public void registerStudent () throws SQLException{
		//Define required variables
		int sid;
		String title;
		String forename;
		String famName;
		Date dob;
		
		//Create scanner to read Student data in from cmd
		Scanner s = new Scanner(System.in);
		//Prompt user for parameters and read in
		System.out.println("Enter a Student ID number");
		sid = s.nextInt();
		System.out.println("Enter the Student's title");
		title = s.nextLine();
		System.out.println("Enter the Student's forename");
		forename = s.nextLine();
		System.out.println("Enter the Student's family name");
		famName = s.nextLine();
		System.out.println("Enter the Student's date of birth in the format YYYY/MM/DD");
		dob = (Date)s.next()
		//Create appropriate statement
		pstmt = conn.prepareStatement(
				"INSERT INTO Student (studentID,titleID,forename,familyName,dateOfBirth)" +
				"VALUES (?, ?, ?, ?, ?)");
		pstmt.clearParameters() ;
		pstmt.setInt(1, sid) ;
		pstmt.setInt(2, /* statement query SELECT titleID FROM Titles WHERE titleString = title */);
		pstmt.setString(3, forename);
		pstmt.setString(4, famName) ;
		pstmt.setDate(5, dob);
		pstmt.executeUpdate() ;


	}

}
