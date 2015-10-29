import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class DbInterface {
	static String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/uni1";
	static Connection conn = null;
	static PreparedStatement pstmt;
	static Statement query;
	static int sid = 0;

	public static void main(String[] args) {
		setupConn();
		//registerStudent();
		//addTutor();
		studentReport();
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
	public static void registerStudent (){
		//Define required variables
		int title = 0;		
		String forename = null;
		String famName = null;
		Date dob;

		//Create scanner to read Student data in from cmd
		Scanner s = new Scanner(System.in);
		//Prompt user for parameters and read in... whiles make sure you enter the right type
		//SID
		System.out.println("Enter a Student ID number");
		while (!s.hasNextInt()){
			System.out.println("Only insert a number");
			s.nextLine();
		}
		sid = s.nextInt();
		//Titles
		System.out.println("Enter the Student's title as a number as follows: 1 for Mr, 2 for Miss, 3 for Ms, 4 for Mrs, 5 for Dr, 6 for Professor");
		while (!s.hasNextInt() /*&& (s.nextInt() < 6)  && (s.nextInt() > 0))*/){
			System.out.println("Only insert a number between 1 and 6");
		}
		title = s.nextInt();		
		//Forename
		System.out.println("Enter the Student's forename");
		s.nextLine();
		forename = s.nextLine();
		//Family name
		System.out.println("Enter the Student's family name");
		famName = s.nextLine();
		//Date of birth
		System.out.println("Enter the Student's date of birth in the format YYYY-MM-DD");
		dob = Date.valueOf(s.nextLine());
		//Create appropriate statements
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO Student (studentID,titleID,forename,familyName,dateOfBirth)" +
					"VALUES (?, ?, ?, ?, ?)");
			pstmt.clearParameters() ;
			pstmt.setInt(1, sid) ;
			pstmt.setInt(2, title);
			pstmt.setString(3, forename);
			pstmt.setString(4, famName) ;
			pstmt.setDate(5, dob);
			pstmt.executeUpdate() ;
			System.out.println("Stedent registered successfully");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		s.close();
	}
	/*public static boolean isTitle(String s)
	{
		String[] titles = {"Mr", "Ms", "Miss", "Mrs", "Dr", "Professor"};  
		return (Arrays.asList(titles).contains(s));
	}
	 */
	public static void addTutor(){
		//Create scanner to read Student data in from cmd
		Scanner s = new Scanner(System.in);
		int lecID;
		//SID
		System.out.println("Enter a Student ID number");
		while (!s.hasNextInt()){
			System.out.println("Only insert a number");
			s.nextLine();
		}
		sid = s.nextInt();
		//LecturerID
		System.out.println("Enter a Lecturer ID number");
		while (!s.hasNextInt()){
			System.out.println("Only insert a number");
			s.nextLine();
		}
		lecID = s.nextInt();
		try {
			pstmt = conn.prepareStatement(
					"INSERT INTO Tutor (studentID,lecturerID)" +
					"VALUES (?, ?)");
			pstmt.clearParameters() ;
			pstmt.setInt(1, sid) ;
			pstmt.setInt(2, lecID);
			pstmt.executeUpdate() ;
			System.out.println("Tutor " + lecID + " added successfully for student " + sid);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		s.close();
	}
	public static void studentReport(){
		Scanner s = new Scanner(System.in);
		ResultSet rs;
		System.out.println("Enter a Student ID number to generate a report");
		while (!s.hasNextInt()){
			System.out.println("Only insert a number");
			s.nextLine();
		}
		sid = s.nextInt();
		System.out.println("** Start of student report **");
		try {
			//Get Title, Name, Surname, dob and StudentID From Student
			pstmt = conn.prepareStatement(
					"SELECT (SELECT titleString FROM Titles WHERE Titles.titleID = Student.titleID), Student.foreName, Student.familyName, Student.dateOfBirth, Student.studentID FROM Student WHERE studentID = " +
							sid);
			rs = pstmt.executeQuery();
			ResultSetMetaData metad = rs.getMetaData();
			while (rs.next()){
				for (int i = 1; i <= metad.getColumnCount(); i++ ){
					System.out.println(rs.getString(i));
				}
			}
			//Get year of study and registration type
			pstmt = conn.prepareStatement(
					"SELECT yearOfStudy, (SELECT description FROM RegistrationType WHERE RegistrationType.registrationTypeID = StudentRegistration.registrationTypeID) FROM StudentRegistration WHERE studentID = " +
							sid);
			rs = pstmt.executeQuery();
			metad = rs.getMetaData();
			while (rs.next()){
				for (int i = 1; i <= metad.getColumnCount(); i++ ){
					System.out.println(rs.getString(i));
				}
			}
			//Get contact details
			pstmt = conn.prepareStatement(
					"SELECT eMailAddress, postalAddress FROM StudentContact WHERE studentID = " +
							sid);
			rs = pstmt.executeQuery();
			metad = rs.getMetaData();
			while (rs.next()){
				for (int i = 1; i <= metad.getColumnCount(); i++ ){
					System.out.println(rs.getString(i));
				}
			}
			//Get next of kin contact details
			pstmt = conn.prepareStatement(
					"SELECT NextOfKinContact.name , NextOfKinContact.eMailAddress, NextOfKinContact.postalAddress FROM NextOfKinContact WHERE studentID = " +
							sid);
			rs = pstmt.executeQuery();
			metad = rs.getMetaData();
			while (rs.next()){
				for (int i = 1; i <= metad.getColumnCount(); i++ ){
					System.out.println(rs.getString(i));
				}
			}
			//Get tutor details
			pstmt = conn.prepareStatement(
					"SELECT foreName, familyName FROM Lecturer WHERE lecturerID = (SELECT Tutor.lecturerID FROM Tutor WHERE studentID = " +
							sid + ")");
			rs = pstmt.executeQuery() ;
			metad = rs.getMetaData();
			while (rs.next()){
				for (int i = 1; i <= metad.getColumnCount(); i++ ){
					System.out.println(rs.getString(i));
				}
			}
			System.out.println("** End of student report **");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		s.close();
	}
}

