package orderprocessingpublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class RestaurantPublishActivator implements BundleActivator {

	private ServiceRegistration<?> publishRestaurantRegistration;
	private static BundleContext context;
	
	static BundleContext getContext() {
		return context;
	}
	
	public void start(BundleContext bundleContext) throws Exception {
		RestaurantPublishActivator.context = bundleContext;
		System.out.println("Restaurant Publisher Start");
		RestaurantPublish publisherService = new RestaurantPublishImpl();
		
		publishRestaurantRegistration = context.registerService(RestaurantPublish.class.getName(), publisherService, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		RestaurantPublishActivator.context = bundleContext;
		System.out.println("Restaurant Publisher Stop");
		publishRestaurantRegistration.unregister();
	}

}
