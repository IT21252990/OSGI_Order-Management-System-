package database;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	// Bundle Start method
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
	}

	//Bundle Stop method
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
