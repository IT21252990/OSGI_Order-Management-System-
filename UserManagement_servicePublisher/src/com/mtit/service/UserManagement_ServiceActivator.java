package com.mtit.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class UserManagement_ServiceActivator implements BundleActivator {

    ServiceRegistration publishServiceRegistraion;

    public void start(BundleContext context) throws Exception {
        System.out.println("--- user management publisher start ---");

        // Initialize the database connection
        DatabaseUtil.initializeDatabase();
        
//        Menu retrieval
        WelcomeMenu.displayWelcomeMenu();
        
      //Register service
        ServicePublishImpl servicePublish = new ServicePublishImpl();
        publishServiceRegistraion = context.registerService(ServicePublish.class.getName(), servicePublish, null);

        
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("--- user management publisher stop ---");

        // Unregister service
        publishServiceRegistraion.unregister();

        // Close Database Connection
        DatabaseUtil.closeConnection();
    }
}
