package restaurantpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceRegistration<?> publishRestaurantRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Restaurant Publisher Start");
		RestaurantPublish publisherService = new RestaurantPublishImpl();
		
		publishRestaurantRegistration = context.registerService(RestaurantPublish.class.getName(), publisherService, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Restaurant Publisher Stop");
		publishRestaurantRegistration.unregister();
	}

}
