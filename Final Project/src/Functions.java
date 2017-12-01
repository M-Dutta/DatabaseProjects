import java.io.FileNotFoundException; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

/***
 * @author Mishuk
 *List of Functions (a)ListFlights (b)addUsers
 *(d) Add Company Info[addInfo] (e)removeUsers (f)removeCompany
 *(g) customUpdates (h) find User
 */
public class Functions extends DemoUtilities {

	
	
/**************************************
 * List all Flights
 * @param s
 * s is the placeholder for statement function used in the main.
 */
public void listFlights(Statement s) {
	loadInfo(s); //reload info
	String str="";
	for (int i =0; i < 80;i++ ) str+="_";
	try {
		System.out.println("The List of Flights:");
		System.out.println(str);
		
		ResultSet r = s.executeQuery("select * from flights");
		System.out.printf("|%-25s|%-14s|%-6s|%-15s|%-15s|\n","Flight Name", "Flight Number",
				"Price", "Departure Time", "Arrival Time");
		System.out.printf("|%-25s|%-14s|%-6s|%-15s|%-15s|\n",str.substring(0,25),str.substring(0,14),
				str.substring(0,6),str.substring(0,15),str.substring(0,15));
		while (r.next()) {
			System.out.printf("|%-25s|%-14s|%-6s|%-15s|%-15s|\n",r.getString(1),r.getString(2),
		r.getDouble(3), r.getTime(4), r.getTime(5));
			System.out.printf("|%-25s|%-14s|%-6s|%-15s|%-15s|\n","","","","","");
		
			if ( !companynames.contains(r.getString(1)) ) {
				companynames.add(r.getString(1));
				s.executeUpdate("Insert into company_info"+" values('"+r.getString(1)+"','No info Available','No info Available');" );
				
			}
		}
		System.out.printf("|%-25s|%-14s|%-6s|%-15s|%-15s|\n\n",str.substring(0,25),str.substring(0,14),
				str.substring(0,6),str.substring(0,15),str.substring(0,15));

	} catch (SQLException e) {
		System.out.println("List Flights "+ e);
	}
}

/**************************
 * Add user to our Site 
 * @param s
 * @param Fname
 * @param Lname
 * @param bDate
 * @param email
 * @param phone
 */
public void addusers(Statement s, String Fname, String Lname, String bDate,String email, String phone) {
	
try {
	s.executeUpdate("insert into users" + 
			" values('"+Fname+"','"+Lname+"','"+bDate+"','"+email+"','"+phone+"');" );
}
	catch (SQLException e) {
		System.out.println("add users "+e);
	}
}

/***
 * Add Website information
 * @param s
 */
public void addInfo(Statement s) {// String compName, String webaddress, String desc) {
	try {
		String compName, webaddress,desc;
		Scanner in = new Scanner(System.in);
		//Name
		System.out.println("Insert Company Name:");
		compName= "'"+in.nextLine()+"'";
		//Address
		System.out.println("Insert Company webAddress:");
		webaddress = "'"+in.nextLine()+"'";
		//Description
		System.out.println("Insert custom bulletin for "+compName+": ");
		desc = "'"+in.nextLine()+"'";
		
		in.close();
		s.executeUpdate("Insert into company_info"+" values("+compName+","+webaddress+","+desc+");" );
	}
			catch (SQLException e) {
			System.out.println("add info"+ e);
		}	
}

/********
 * FindUSer by Email
 * @param s
 * @param email
 */
public void findUser(Statement s,String email) {
	try {
		ResultSet r = s.executeQuery("Select * from users where Email="+email);
		String str="";
		for (int i =0; i < 113;i++ ) str+="_";
		
System.out.printf("|%-25s|%-25s|%-15s|%-30s|%-13s|\n","First Name",
		"Last Name","Birth Date","Email","Phone Number" );
		
		System.out.printf("|%-25s|%-25s|%-15s|%-30s|%-13s|\n",
				str.substring(0,25),str.substring(0,25),
				str.substring(0,15), str.substring(0,30),
				str.substring(0,13));
		
		while (r.next()) {
			System.out.printf("|%-25s|%-25s|%-15s|%-30s|%-13s|\n", 
					r.getString(1), r.getString(2),r.getDate(3),
					r.getString(4), r.getString(5));
			
			System.out.printf("|%-25s|%-25s|%-15s|%-30s|%-13s|\n"
					,"","","","","");
		}
		System.out.printf("|%-25s|%-25s|%-15s|%-30s|%-13s|\n",
				str.substring(0,25),str.substring(0,25),
				str.substring(0,15), str.substring(0,30),
				str.substring(0,13));
		
	} catch (SQLException e) {
		System.out.println("Find User "+ e);
	}
}	

/********
 * 
 * @param s
 * @param email
 */
public void removeUser(Statement s,String email) {
	try {
		s.executeUpdate("Delete from users where Email="+email);
	} catch (SQLException e) {
		System.out.println("removeUser " + e);
	}
}

public void removeCompany(Statement s,String name) {
	try {
		s.executeUpdate("Delete from company_info where Compnay_name="+name);
	} catch (SQLException e) {
		System.out.println("removeCompany "+e);
	}
}

/**********
 * Custom Update Function: Lets us use query of out own making
 * @param s
 * @param update
 */
public void customUpdate(Statement s,String update) {
	try {
		Scanner in = new Scanner(System.in);
		System.out.println("Insert custom Update:");
		update = in.nextLine();
		in.close();
		s.executeUpdate(update);
	} catch (SQLException e) {
		System.out.println("customUpdate "+e);
	}
}

public void listCompanies(Statement s) {
	String str="";
	for (int i =0; i < 73;i++ ) str+="_";
	try {
		ResultSet r = s.executeQuery("select * from company_info");
		
		System.out.printf("|%-25s|%-25s|%-20s|\n","Company Name","Website","Rating(Max 5 Stars)" );
		
		System.out.printf("|%-25s|%-25s|%-20s|\n",str.substring(0,25),str.substring(0,25),
				str.substring(0,20) );
		while (r.next()) {
			System.out.printf("|%-25s|%-25s|%-20s|\n", r.getString(1), r.getString(2),r.getString(3));
			System.out.printf("|%-25s|%-25s|%-20s|\n","","","");
		}
		System.out.printf("|%-25s|%-25s|%-20s|\n",str.substring(0,25),str.substring(0,25),
				str.substring(0,20) );
	} catch (SQLException e) {
		System.out.println("list Companies===="+ e);
	}	
}
}
