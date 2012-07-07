package be.betty.gwtp.client;

import be.betty.gwtp.client.gin.ClientGinjector;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.DialogBox;
import com.gwtplatform.mvp.client.DelayedBindRegistry;

public class Betty_gwtp implements EntryPoint {

	private final ClientGinjector ginjector = GWT.create(ClientGinjector.class);

	@Override
	public void onModuleLoad() {

		// set uncaught exception handler
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable throwable) {
				//UmbrellaException u = (UmbrellaException) throwable;
				//System.out.println("************************ Get Cauz: "+u.getCause());
				String text = "Uncaught exception: ";
				while (throwable != null) {
					StackTraceElement[] stackTraceElements = throwable
							.getStackTrace();
					text += throwable.toString() + "\n";
					for (int i = 0; i < stackTraceElements.length; i++) {
						text += "    at " + stackTraceElements[i] + "\n";
					}
					throwable = throwable.getCause();
					if (throwable != null) {
						text += "Caused by: ";
					}
				}
				DialogBox dialogBox = new DialogBox(true, false);
				DOM.setStyleAttribute(dialogBox.getElement(),
						"backgroundColor", "#ABCDEF");
				System.err.print(text);
				text = text.replaceAll(" ", "&nbsp;");
				dialogBox.setHTML("<pre>" + text + "</pre>");
				dialogBox.center();
			}
		});

		// use a deferred command so that the handler catches 
		// onModuleLoad2()  exceptions
		Scheduler.get().scheduleDeferred(new Command() {
			public void execute() {
				onModuleLoad2();
			}

		});

	}

	private void onModuleLoad2() {

		// This is required for Gwt-Platform proxy's generator
		DelayedBindRegistry.bind(ginjector);

		ginjector.getPlaceManager().revealCurrentPlace();

	}
}
