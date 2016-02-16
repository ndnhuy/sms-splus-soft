package vn.com.splussoftware.sms.model.exception;

public class ExcelValidatorModel {
	private int elementId;
	private String type;
	private String value;
	private int row;
	private String column;

	
	
	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setValue(String value) {
		this.value = value;
	}

	

}
