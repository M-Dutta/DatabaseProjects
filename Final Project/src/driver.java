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
			//demo.listCompanies(stmt);
			//demo.customUpdate(stmt, "insert into company_info values('Delta Airlines','delta.com','*****');");
			//demo.addusers(stmt);
			//demo.addInfo(stmt);
			//demo.removeCompany(stmt, "");
			//demo.findUser(stmt, "");
			//demo.removeUser(stmt, "");
		
	
	}
}
