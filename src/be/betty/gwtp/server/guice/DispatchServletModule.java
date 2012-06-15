package be.betty.gwtp.server.guice;

//

import be.betty.gwtp.server.FileUpServlet;

import com.google.inject.servlet.ServletModule;
import com.gwtplatform.dispatch.server.guice.DispatchServiceImpl;
import com.gwtplatform.dispatch.shared.ActionImpl;

public class DispatchServletModule extends ServletModule {

	@Override
	public void configureServlets() {
		serve("/" + ActionImpl.DEFAULT_SERVICE_NAME)
				.with(DispatchServiceImpl.class);
		
		serve("/" + "upload")
			.with(FileUpServlet.class);
		
	}
}
