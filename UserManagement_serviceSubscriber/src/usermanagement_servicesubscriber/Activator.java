package usermanagement_servicesubscriber;

import ordermanagementservicepublisher.ServicePublish;
import ordermangementservicesubscriber.ServiceSubscriber;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import usermanagement_servicepublisher.UserServicePublish;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> serviceReference;
	private ServiceReference<?> orderReference;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("--- user management subscriber start ---");

        // Retrieve the service reference for the ServicePublish service
        serviceReference = context.getServiceReference(UserServicePublish.class);
        UserServicePublish servicePublish = (UserServicePublish) context.getService(serviceReference);
        
        WelcomeMenu currentSubscriber = new WelcomeMenu(servicePublish , Activator.context);
        
        registration = context.registerService(WelcomeMenu.class.getName(), currentSubscriber , null);
        
        currentSubscriber.displayWelcomeMenu();

        
        if (servicePublish != null) {
        	String menuResponse = servicePublish.publishService();
            System.out.println("Received Menu: " + menuResponse);
        }
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
