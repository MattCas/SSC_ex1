import java.sql.*;
import java.util.Random;

public class Populator {
	static String dbName = "jdbc:postgresql://dbteach2.cs.bham.ac.uk/uni1";
	static Connection conn = null;

	public static void main(String[] args) throws SQLException{
		truncate();
		populate();
	}
	@SuppressWarnings("deprecation")
	public static void populate() throws SQLException{
		
		PreparedStatement pstmt;

		Random r = new Random();

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

		try
		{
			//int count;

			// Execute inserts for each table
			// Titles
			pstmt = conn.prepareStatement(
					"INSERT INTO Titles (titleID, titleString)" +
					"VALUES (?, ?)") ;
			//Mr
			pstmt.clearParameters() ;
			pstmt.setInt(1, 1) ;
			pstmt.setString(2, "Mr") ;
			pstmt.executeUpdate() ;
			//Miss
			pstmt.clearParameters() ;
			pstmt.setInt(1, 2) ;
			pstmt.setString(2, "Miss") ;
			pstmt.executeUpdate() ;
			//Ms
			pstmt.clearParameters() ;
			pstmt.setInt(1, 3) ;
			pstmt.setString(2, "Ms") ;
			pstmt.executeUpdate() ;
			//Mrs
			pstmt.clearParameters() ;
			pstmt.setInt(1, 4) ;
			pstmt.setString(2, "Mrs") ;
			pstmt.executeUpdate() ;
			//Dr
			pstmt.clearParameters() ;
			pstmt.setInt(1, 5) ;
			pstmt.setString(2, "Dr") ;
			pstmt.executeUpdate() ;
			//Professor
			pstmt.clearParameters() ;
			pstmt.setInt(1, 6) ;
			pstmt.setString(2, "Professor") ;
			pstmt.executeUpdate() ;

			System.out.println("Added 6 titles to Titles relation");

			// Student
			pstmt = conn.prepareStatement(
					"INSERT INTO Student (studentID,titleID,forename,familyName,dateOfBirth)" +
					"VALUES (?, ?, ?, ?, ?)") ;
			int c = 0;
			for (int i = 1; i < 101; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setInt(2, 1+ r.nextInt(6)); //random int between 1 and 6 for titles
				pstmt.setString(3, "Forename" + i) ;
				pstmt.setString(4, "FamilyName" + i) ;
				pstmt.setDate(5, new Date(60, 0, 1));
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to Student relation");

			// Lecturer
			pstmt = conn.prepareStatement(
					"INSERT INTO Lecturer (lecturerID,titleID,forename,familyName)" +
					"VALUES (?, ?, ?, ?)") ;
			c=0;
			for (int i = 1; i < 11; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setInt(2, 1+ r.nextInt(6)); //random int between 1 and 6 for titles
				pstmt.setString(3, "LecturerForename" + i) ;
				pstmt.setString(4, "LecturerFamilyName" + i) ;
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to Lecturer relation");

			// RegistrationTypeID

			pstmt = conn.prepareStatement(
					"INSERT INTO RegistrationType (registrationTypeID, description)" +
					"VALUES (?, ?)") ;
			//Normal
			pstmt.clearParameters() ;
			pstmt.setInt(1, 1) ;
			pstmt.setString(2, "Normal") ;
			pstmt.executeUpdate() ;
			//Repeating
			pstmt.clearParameters() ;
			pstmt.setInt(1, 2) ;
			pstmt.setString(2, "Repeating") ;
			pstmt.executeUpdate() ;
			//External
			pstmt.clearParameters() ;
			pstmt.setInt(1, 3) ;
			pstmt.setString(2, "External") ;
			pstmt.executeUpdate() ;

			System.out.println("Added 3 registration types to RegistrationType relation");

			// StudentRegistration
			pstmt = conn.prepareStatement(
					"INSERT INTO StudentRegistration (studentID,yearOfStudy,registrationTypeID)" +
					"VALUES (?, ?, ?)") ;
			c = 0;
			for (int i = 1; i < 101; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setInt(2, 1+ r.nextInt(10)); //random int between 1 and 10 for year of study
				pstmt.setInt(3, 1+ r.nextInt(3)); //random int between 1 and 3 for year of study
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to StudentRegistration relation");

			// StudentContact
			pstmt = conn.prepareStatement(
					"INSERT INTO StudentContact (studentID,eMailAddress, postalAddress)" +
					"VALUES (?, ?, ?)") ;
			c = 0;
			for (int i = 1; i < 101; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setString(2, "StudentEmail" + i); 
				pstmt.setString(3, "StudentPostalAddress" + i); 
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to StudentContact relation");	
			
			// NextOfKinContact
			pstmt = conn.prepareStatement(
					"INSERT INTO NextOfKinContact (studentID,name,eMailAddress, postalAddress)" +
					"VALUES (?, ?, ?, ?)") ;
			c = 0;
			for (int i = 1; i < 101; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setString(2, "NextOfKinName" + i);
				pstmt.setString(3, "NextOfKineMail" + i); 
				pstmt.setString(4, "NextOfKinPostalAddress" + i); 
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to NextOfKinContact relation");		
			
			// LecturerContact
			pstmt = conn.prepareStatement(
					"INSERT INTO LecturerContact (LecturerID,office,eMailAddress)" +
					"VALUES (?, ?, ?)") ;
			c = 0;
			for (int i = 1; i < 11; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setString(2, "Office" + i);
				pstmt.setString(3, "eMailAddress" + i); 
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to LecturerContact relation");
			
			// Tutor
			
			pstmt = conn.prepareStatement(
					"INSERT INTO Tutor (StudentID, LecturerID)" +
					"VALUES (?, ?)") ;
			c = 0;
			for (int i = 1; i < 101; i++) {
				pstmt.clearParameters() ;
				pstmt.setInt(1, i) ;
				pstmt.setInt(2, 1 + r.nextInt(10));
				pstmt.executeUpdate() ;
				c++;
			}
			System.out.println("Added " + c + " random entries to Tutor relation");
						
			System.out.println("Population of database " + dbName + " is complete");
		}
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{			
				e.printStackTrace();
			}
		}
	}
	public static void truncate()
	{
		try
		{
			//Setup connection
			conn = DriverManager.getConnection(dbName, "mxc471", "steclike" );	    
			// load the JDBC driver
			System.setProperty("jdbc.drivers", "org.postgresql.Driver") ;

			Statement stmt ;
			stmt = conn.createStatement() ;
			stmt.executeUpdate("TRUNCATE TABLE Student CASCADE");
			stmt.executeUpdate("TRUNCATE TABLE Lecturer CASCADE");
			stmt.executeUpdate("TRUNCATE TABLE Titles CASCADE");	
			stmt.executeUpdate("TRUNCATE TABLE RegistrationType CASCADE");	
			stmt.executeUpdate("TRUNCATE TABLE StudentRegistration CASCADE");	
			stmt.executeUpdate("TRUNCATE TABLE StudentContact CASCADE");
			stmt.executeUpdate("TRUNCATE TABLE NextOfKinContact CASCADE");
			stmt.executeUpdate("TRUNCATE TABLE LecturerContact CASCADE");
			stmt.executeUpdate("TRUNCATE TABLE Tutor CASCADE");
			
			System.out.println("Cleared all tables");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		finally 
		{
			try 
			{
				conn.close();
			} 
			catch (SQLException e) 
			{			
				e.printStackTrace();
			}
		}

	}

}
