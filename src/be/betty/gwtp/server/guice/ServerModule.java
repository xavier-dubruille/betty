package be.betty.gwtp.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.server.LoginActionActionHandler;
import be.betty.gwtp.server.GetProjectsActionActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(LoginAction.class, LoginActionActionHandler.class);

		bindHandler(GetProjectsAction.class,
				GetProjectsActionActionHandler.class);
	}
}
