package be.betty.gwtp.server;

import com.gwtplatform.dispatch.server.actionhandler.ActionHandler;
import be.betty.gwtp.client.action.DeleteCardAction;
import be.betty.gwtp.client.action.DeleteCardActionResult;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.server.ExecutionContext;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteCardActionActionHandler implements
		ActionHandler<DeleteCardAction, DeleteCardActionResult> {

	@Inject
	public DeleteCardActionActionHandler() {
	}

	@Override
	public DeleteCardActionResult execute(DeleteCardAction action,
			ExecutionContext context) throws ActionException {
		return null;
	}

	@Override
	public void undo(DeleteCardAction action, DeleteCardActionResult result,
			ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<DeleteCardAction> getActionType() {
		return DeleteCardAction.class;
	}
}
