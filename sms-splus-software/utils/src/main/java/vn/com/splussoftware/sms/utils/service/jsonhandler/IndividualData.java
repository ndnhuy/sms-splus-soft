package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class IndividualData {
	private Condition conditions;
	private String value;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Condition getConditions() {
		return conditions;
	}

	public void setConditions(Condition conditions) {
		this.conditions = conditions;
	}

}
