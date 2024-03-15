package ordermanagementservicepublisher;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	ServiceRegistration<?> publishServiceRegistration; 

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println( "Order Management Publisher has Started" );
		
		//Register service
		ServicePublish publisherService = new ServicePublishImpl();
		publishServiceRegistration = context.registerService(ServicePublish.class.getName(), publisherService, null);
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println( "Order Management Publisher has Stopped" );
		
		// Unregister service
		publishServiceRegistration.unregister();
	}

}
