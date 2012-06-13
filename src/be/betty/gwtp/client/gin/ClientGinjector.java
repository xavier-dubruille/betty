package be.betty.gwtp.client.gin;

import be.betty.gwtp.client.presenters.LoginPresenter;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import be.betty.gwtp.client.presenters.SubscribePresenter;
import be.betty.gwtp.client.presenters.ProjectsPresenter;
import be.betty.gwtp.client.presenters.HeaderPresenter;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LoginPresenter> getLoginPresenter();

	AsyncProvider<MainPresenter> getMainPresenter();

	AsyncProvider<SubscribePresenter> getSubscribePresenter();

	AsyncProvider<ProjectsPresenter> getProjectsPresenter();

	AsyncProvider<HeaderPresenter> getHearderPresenter();
}
