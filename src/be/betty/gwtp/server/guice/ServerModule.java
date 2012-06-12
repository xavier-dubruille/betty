package be.betty.gwtp.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.server.LoginActionActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(LoginAction.class, LoginActionActionHandler.class);
	}
}
