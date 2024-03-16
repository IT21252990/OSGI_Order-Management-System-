package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OmsDatabaseImpl implements OmsDatabase{
	
	// create variable to store database connection parameters
	private String driverName;
	private String databaseConnectionURI;
	private String databaseUser;
	private String databasePassword;
	
	//Default Configurations
	private String defaltDatabasePassword;
	
	//Default Constructor
	public OmsDatabaseImpl(){
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.databaseConnectionURI = "jdbc:mysql://localhost:3306/Restaurant_DB" ;
		this.databaseUser = "root";
		this.databasePassword = "Root@kali" ;
		//
		this.defaltDatabasePassword = "root" ;
	}

	// Establish Connection to the Restaurant_DB
	@Override
	public Connection connection() {
		
		Connection connection = null ;
		
		try {
			
			Class.forName(driverName);
			connection = DriverManager.getConnection( databaseConnectionURI , databaseUser , databasePassword) ;
			System.out.println("** Connected to the Database **");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// return Established database connection
		return connection ;
	}

	@Override
	public Connection connection(int Number) {
		
		Connection connection = null ;
		
		try {
			
			Class.forName(driverName);
			connection = DriverManager.getConnection( databaseConnectionURI , databaseUser , defaltDatabasePassword) ;
			System.out.println("** Connected to the Database **");
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// return Established database connection
		return connection ;
	}

}
