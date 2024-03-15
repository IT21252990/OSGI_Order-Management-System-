package usermanagement_servicepublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	ServiceRegistration<?> publishServiceRegistraion;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("--- user management publisher start ---");
		
		//Register service
        ServicePublishImpl servicePublish = new ServicePublishImpl();
        publishServiceRegistraion = context.registerService(UserServicePublish.class.getName(), servicePublish, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("--- user management publisher stop ---");

        // Unregister service
        publishServiceRegistraion.unregister();
	}

}
