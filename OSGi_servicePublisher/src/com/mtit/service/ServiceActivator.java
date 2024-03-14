package com.mtit.service;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ServiceActivator implements BundleActivator {

    ServiceRegistration publishServiceRegistraion;

    public void start(BundleContext context) throws Exception {
        System.out.println("--- user management publisher start ---");

        // Initialize the database connection
        DatabaseUtil.initializeDatabase();

        ServicePublishImpl servicePublish = new ServicePublishImpl(DatabaseUtil.getConnection());
        publishServiceRegistraion = context.registerService(ServicePublish.class.getName(), servicePublish, null);

        WelcomeMenu.displayWelcomeMenu();
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("--- user management publisher stop ---");

        // Unregister service
        publishServiceRegistraion.unregister();

        // Close Database Connection
        DatabaseUtil.closeConnection();
    }
}
