import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DemoUtilities {


/**
 * Server Information
 * Note that localhost:8000/ value will vary depending on your MySQL local server 
 */
public final static String user = "root";
public final static String pswd = "root"; 
public final static String DBname = "Website?useSSL=false"; 
public final static String Driver ="com.mysql.jdbc.Driver";
public final static String connectionURL ="jdbc:mysql://localhost:8000/";

protected static ArrayList <String>companynames= new ArrayList<String>();

/**
 * Loader txt file information 
 * Paste the location for flight_data.text  <- the file that landon updated  
 */
public static String fileLoc ="'C:/Users/Mishuk/Documents/SQL Printout/Flights.txt'";	
public static String Loader = "LOAD DATA LOCAL INFILE "+fileLoc
+" into table Flights columns terminated by ' '";

/************************************
*loads Company Names not already on our company_info Table
*No need to touch
*/
public void loadInfo(Statement s)
{
	try {
		s.executeUpdate("delete from flights");
		s.executeUpdate(Loader);
	} catch (SQLException e) {
		System.out.println("loadInfo "+e);
	}
}
/********************************
 * Puts Company information from the Flight Table
 * Note that Only the Company Name will be loaded.
 * We have to add rest of the information Manually.
 * company info has 3 fields: name, website and ratings. 
 * This will only fill Name.  
 */
public void loadCompanies(Statement s) {
	try {
		ResultSet rs = s.executeQuery("Select Company_name from company_info");
		while (rs.next()) {
			companynames.add(rs.getString(1));
		}
	} catch (SQLException e) {
		System.out.println("load Companies "+e);
	}
}

}







