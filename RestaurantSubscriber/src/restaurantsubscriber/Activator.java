package restaurantsubscriber;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import restaurantpublisher.RestaurantPublish;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> publishRestaurantReference;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Restaurant Subscriber Start");
		
		publishRestaurantReference = context.getServiceReference(RestaurantPublish.class.getName());
		RestaurantPublish restaurantPublish = (RestaurantPublish) context.getService(publishRestaurantReference);
		
		RestaurantSubscriber currentSubscriber = new RestaurantSubscriber(restaurantPublish);
		
		registration = context.registerService(RestaurantSubscriber.class.getName(), currentSubscriber, null);
		
//		currentSubscriber.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Restaurant Subscriber Stop");
		Activator.context = null;
	}

}
