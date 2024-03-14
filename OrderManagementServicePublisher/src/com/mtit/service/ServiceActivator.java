package com.mtit.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ServiceActivator implements BundleActivator {

	ServiceRegistration publishServiceRegistration; 
	
	public void start(BundleContext context) throws Exception {
		
		System.out.println( "Order Management Publisher has Started" );
		
		// Initialize the database connection
        DatabaseUtil.initializeDatabase();
		
		//DatabaseUtil.getConnection();
        System.out.println( "Connected to DB" );
       
        
        //Menu retrieval
       // MenuRetrieval.retrieveAndDisplayMenu();
        
		
		//Register service
		ServicePublish publisherService = new ServicePublishImpl();
		publishServiceRegistration = context.registerService(ServicePublish.class.getName(), publisherService, null);
	
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println( "Order Management Publisher has Stopped" );
		
		// Unregister service
		publishServiceRegistration.unregister();
		
		//Close Database Connection 
		DatabaseUtil.closeConnection();
	}

}
