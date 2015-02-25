package eclipseserver;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

public class SummaryEclipseServerApplication implements IApplication {

	public static final boolean TEST = true;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		System.out.println("inside SummaryEclipseServerApplication");
		Server server = new Server(8844);
		Context root = new Context(server,"/",Context.SESSIONS);
		root.addServlet(new ServletHolder(new  SummaryEclipseSerlvet()), "/*");	 
		server.start();
		server.join();
		return null;
	}

	@Override
	public void stop() {
	}

}
