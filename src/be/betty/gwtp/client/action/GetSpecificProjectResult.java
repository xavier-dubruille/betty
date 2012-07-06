package be.betty.gwtp.client.action;

import java.util.ArrayList;
import java.util.Collection;

import be.betty.gwtp.server.bdd.Activity;

import com.gwtplatform.dispatch.shared.Result;

public class GetSpecificProjectResult implements Result {

	private String name;
	private ArrayList<String> activities;

	public GetSpecificProjectResult() {
		activities = new ArrayList<String>();
	}

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getActivities() {
		return this.activities;
	}
}
