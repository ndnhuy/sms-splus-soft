package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class MatrixValue implements Comparable<MatrixValue> {
	private int columnId;
	private int rowId;
	private String value;
	private Condition conditions;
	private String type;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(MatrixValue o) {
		return this.rowId * (-1) - o.rowId * (-1);
	}
}
