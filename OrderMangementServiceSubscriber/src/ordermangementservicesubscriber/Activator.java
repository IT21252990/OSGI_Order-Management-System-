package ordermangementservicesubscriber;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import ordermanagementservicepublisher.ServicePublish;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> serviceReference;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Menu Retrieval Subscriber has Started");

        // Retrieve the published service
		serviceReference = context.getServiceReference(ServicePublish.class.getName());
		ServicePublish orderPublish = (ServicePublish) context.getService(serviceReference);
        
        ServiceSubscriber currentSubscriber = new ServiceSubscriber(orderPublish);
        
        registration = context.registerService(ServiceSubscriber.class.getName(), currentSubscriber , null);
        
//        currentSubscriber.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Menu Retrieval Subscriber has Stopped");

        // Unget the published service
        context.ungetService(serviceReference);
	}

}
