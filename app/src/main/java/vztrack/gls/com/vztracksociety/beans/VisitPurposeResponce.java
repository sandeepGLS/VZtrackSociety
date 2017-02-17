package vztrack.gls.com.vztracksociety.beans;

import java.util.ArrayList;

public class VisitPurposeResponce {
	String code;
	String message;
	ArrayList<VisitPurposeBean> visitPurposeBeans = new ArrayList<VisitPurposeBean>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList<VisitPurposeBean> getVisitPurposeBeans() {
		return visitPurposeBeans;
	}
	public void setVisitPurposeBeans(ArrayList<VisitPurposeBean> visitPurposeBeans) {
		this.visitPurposeBeans = visitPurposeBeans;
	}
	
}
