package be.betty.gwtp.server.guice;

import com.gwtplatform.dispatch.server.guice.HandlerModule;

import be.betty.gwtp.client.action.GetActivityStateAction;
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
import be.betty.gwtp.server.GetActivityStateActionActionHandler;
import be.betty.gwtp.client.action.SolveIt;
import be.betty.gwtp.server.SolveItActionHandler;
import be.betty.gwtp.client.action.GetInstancesOnly;
import be.betty.gwtp.server.GetInstancesOnlyActionHandler;

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

		bindHandler(GetActivityStateAction.class,
				GetActivityStateActionActionHandler.class);

		bindHandler(SolveIt.class, SolveItActionHandler.class);

		bindHandler(GetInstancesOnly.class, GetInstancesOnlyActionHandler.class);
}
}
