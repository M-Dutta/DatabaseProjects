import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class driver extends DemoUtilities {
	public static void main (String [] args) {
		Functions demo = new Functions();
		try
		{
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
			@SuppressWarnings("unused")
			Statement stmt;
			Connection con;
			try {
				con = DriverManager.getConnection(connectionURL+
						DBname,user,pswd);
				stmt=con.createStatement();
			} catch (SQLException e) {
				System.out.println(e);
			}
		
	
	}
}
