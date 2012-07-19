package be.betty.gwtp.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import be.betty.gwtp.client.action.GetCards;
import be.betty.gwtp.client.action.GetProjectsAction;
import be.betty.gwtp.client.action.LoginAction;
import be.betty.gwtp.server.LoginActionActionHandler;
import be.betty.gwtp.server.GetProjectsActionActionHandler;
import be.betty.gwtp.client.action.DeleteProjectAction;
import be.betty.gwtp.server.DeleteProjectActionActionHandler;
import be.betty.gwtp.server.GetCardsActionHandler;
import be.betty.gwtp.client.action.SubscribeAction;
import be.betty.gwtp.server.SubscribeActionActionHandler;
import be.betty.gwtp.client.action.SaveCardDropAction;
import be.betty.gwtp.server.SaveCardDropActionActionHandler;

public class ServerModule extends HandlerModule {

	@Override
	protected void configureHandlers() {

		bindHandler(LoginAction.class, LoginActionActionHandler.class);

		bindHandler(GetProjectsAction.class,
				GetProjectsActionActionHandler.class);

		bindHandler(DeleteProjectAction.class,
				DeleteProjectActionActionHandler.class);

		bindHandler(GetCards.class, GetCardsActionHandler.class);
	
		bindHandler(SubscribeAction.class, SubscribeActionActionHandler.class);

		bindHandler(SaveCardDropAction.class,
				SaveCardDropActionActionHandler.class);
}
}
