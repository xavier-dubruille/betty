package be.betty.gwtp.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HasHandlers;

public class ProjectListModifyEvent extends
		GwtEvent<ProjectListModifyEvent.ProjectListModifyHandler> {

	public static Type<ProjectListModifyHandler> TYPE = new Type<ProjectListModifyHandler>();

	public interface ProjectListModifyHandler extends EventHandler {
		void onProjectListModify(ProjectListModifyEvent event);
	}

	public ProjectListModifyEvent() {
	}

	@Override
	protected void dispatch(ProjectListModifyHandler handler) {
		handler.onProjectListModify(this);
	}

	@Override
	public Type<ProjectListModifyHandler> getAssociatedType() {
		return TYPE;
	}

	public static Type<ProjectListModifyHandler> getType() {
		return TYPE;
	}

	public static void fire(HasHandlers source) {
		source.fireEvent(new ProjectListModifyEvent());
	}
}
