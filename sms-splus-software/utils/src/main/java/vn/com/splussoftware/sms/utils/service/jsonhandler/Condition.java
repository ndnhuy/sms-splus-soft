package vn.com.splussoftware.sms.utils.service.jsonhandler;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Condition {
	private boolean required;
	@SerializedName("min-length")
	private int min_length;
	@SerializedName("max-length")
	private int max_length;
	List<ConditionData> data = new ArrayList<ConditionData>();
	ConditionFormat format;

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getMin_length() {
		return min_length;
	}

	public void setMin_length(int min_length) {
		this.min_length = min_length;
	}

	public int getMax_length() {
		return max_length;
	}

	public void setMax_length(int max_length) {
		this.max_length = max_length;
	}

	public List<ConditionData> getData() {
		return data;
	}

	public void setData(List<ConditionData> data) {
		this.data = data;
	}

	public ConditionFormat getFormat() {
		return format;
	}

	public void setFormat(ConditionFormat format) {
		this.format = format;
	}

	public List<String> getSelectData() {
		List<String> listValue = new ArrayList<>();
		for (ConditionData data : this.data) {
			listValue.add(data.getValue());
		}
		return listValue;
	}

}
