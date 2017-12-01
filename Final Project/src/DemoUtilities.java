import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DemoUtilities {


/**
 * Server Information
 */
public final static String user = "root";
public final static String pswd = "root"; 
public final static String DBname = "Website"; 
public final static String Driver ="com.mysql.jdbc.Driver";
public final static String connectionURL ="jdbc:mysql://localhost:8000/";

protected static ArrayList <String>companynames= new ArrayList<String>();

/**
 * Loader txt file information 
 */
public static String fileLoc ="'C:/Users/Mishuk/Documents/SQL Printout/Flights.txt'";	
public static String Loader = "LOAD DATA LOCAL INFILE "+fileLoc
+" into table Flights columns terminated by ' '";

/************************************
*load table
*/
public void loadInfo(Statement s)
{
	try {
		s.executeUpdate("delete from flights");
		s.executeUpdate(Loader);
	} catch (SQLException e) {
		System.out.println(e);
	}
}
/********************************
 * Load Company Names
 */
public void loadCompanies(Statement s) {
	try {
		ResultSet rs = s.executeQuery("Select Company_name from company_info");
		while (rs.next()) {
			companynames.add(rs.getString(1));
		}
	} catch (SQLException e) {
		System.out.println(e);
	}
}

}






