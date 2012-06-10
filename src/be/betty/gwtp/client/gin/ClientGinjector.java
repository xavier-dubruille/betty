package be.betty.gwtp.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;
import be.betty.gwtp.client.gin.ClientModule;
import be.betty.gwtp.client.presenters.LoginPresenter;
import be.betty.gwtp.client.presenters.MainPresenter;

import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.event.shared.EventBus;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.google.gwt.inject.client.AsyncProvider;

@GinModules({ DispatchAsyncModule.class, ClientModule.class })
public interface ClientGinjector extends Ginjector {

	EventBus getEventBus();

	PlaceManager getPlaceManager();

	AsyncProvider<LoginPresenter> getLoginPresenter();

	AsyncProvider<MainPresenter> getMainPresenter();
}
