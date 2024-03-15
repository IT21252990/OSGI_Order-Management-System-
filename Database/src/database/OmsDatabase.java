package database;

import java.sql.Connection;

public interface OmsDatabase {
	
	// Method signature for Create a connection to the Restaurant_DB.
	Connection connection();
	
	// Method signature for Create a connection to the restaurant_DB.
	Connection connection(int Number);

}
