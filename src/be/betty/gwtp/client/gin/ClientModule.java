package be.betty.gwtp.client.gin;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import be.betty.gwtp.client.place.ClientPlaceManager;
import be.betty.gwtp.client.place.DefaultPlace;
import be.betty.gwtp.client.place.NameTokens;
import be.betty.gwtp.client.presenters.LoginPresenter;
import be.betty.gwtp.client.presenters.MainPresenter;
import be.betty.gwtp.client.views.LoginView;
import be.betty.gwtp.client.views.MainView;

public class ClientModule extends AbstractPresenterModule {

	@Override
	protected void configure() {
		install(new DefaultModule(ClientPlaceManager.class));

		bindPresenter(LoginPresenter.class, LoginPresenter.MyView.class,
				LoginView.class, LoginPresenter.MyProxy.class);

		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.login);

		bindPresenter(MainPresenter.class, MainPresenter.MyView.class,
				MainView.class, MainPresenter.MyProxy.class);
	}
}
