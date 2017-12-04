 import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class driver extends DemoUtilities {
	public static void main (String [] args) throws InterruptedException {
		Functions demo = new Functions();
		try
		{
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}
			@SuppressWarnings("unused")
			Statement stmt = null;
			Connection con = null;
			try {
				con = DriverManager.getConnection(connectionURL+
						DBname,user,pswd);
				stmt=con.createStatement();
			} catch (SQLException e) {
				System.out.println(e);
			}
			demo.listFlights(stmt,con,"","");
			demo.listCompanies(stmt);
			//demo.customUpdate(stmt);
			//demo.addusers(stmt,"Prashant","Doshi","1975-11-15","pdoshi@cs.uga.edu","000-00-0000");
			//demo.addusers(stmt,"John","Wick","1970-10-23","jwick@random.com","111-11-1111");
			//demo.addusers(stmt,"John","Qick","1965-10-23","jqick@random.com","000-00-0000");
			//demo.addusers(stmt,"Adam","Jensen","1970-10-23","ajensen@random.com","000-00-0000");
			//demo.findUser(stmt, "Prashant","Doshi"); // by Name
			//demo.findUser(stmt, "ajensen@random.com""); //by Email
			//demo.ListAllUser(stmt);
			//demo.addInfo(stmt);
			//demo.removeCompany(stmt, "USAirways");
			//demo.removeUser(stmt, "pdoshi@cs.uga.edu");

	}
}
