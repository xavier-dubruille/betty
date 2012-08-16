package be.betty.gwtp.server;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.CreateNewCardAction;
import be.betty.gwtp.client.action.CreateNewCardActionResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class CreateNewCardActionActionHandler implements
		ActionHandler<CreateNewCardAction, CreateNewCardActionResult> {

	@Inject
	public CreateNewCardActionActionHandler() {
	}

	@Override
	public CreateNewCardActionResult execute(CreateNewCardAction action,
			ExecutionContext context) throws ActionException {
		return null;
	}

	@Override
	public void undo(CreateNewCardAction action, CreateNewCardActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<CreateNewCardAction> getActionType() {
		return CreateNewCardAction.class;
	}
}