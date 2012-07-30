package be.betty.gwtp.client.action;

import com.gwtplatform.dispatch.shared.Result;

public class SolveItResult implements Result {

	private String soluceInfo;
	public SolveItResult() {
	}
	
	public SolveItResult(String soluceInfo) {
		super();
		this.soluceInfo = soluceInfo;
	}

	public String getSoluceInfo() {
		return soluceInfo;
	}
	public void setSoluceInfo(String soluceInfo) {
		this.soluceInfo = soluceInfo;
	}
}
