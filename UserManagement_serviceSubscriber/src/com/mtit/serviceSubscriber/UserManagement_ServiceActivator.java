package com.mtit.serviceSubscriber;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.mtit.service.ServicePublish;

public class UserManagement_ServiceActivator implements BundleActivator {

    private ServiceReference serviceReference;

    public void start(BundleContext context) throws Exception {
        System.out.println("--- user management subscriber start ---");

        // Retrieve the service reference for the ServicePublish service
        serviceReference = context.getServiceReference(ServicePublish.class);
        ServicePublish servicePublish = (ServicePublish) context.getService(serviceReference);


//        WelcomeMenu.displayWelcomeMenu();
        
        if (servicePublish != null) {
        	String menuResponse = servicePublish.publishService();
            System.out.println("Received Menu: " + menuResponse);
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("--- user management subscriber stop ---");

        // Release the service reference
            context.ungetService(serviceReference);
    }
}
