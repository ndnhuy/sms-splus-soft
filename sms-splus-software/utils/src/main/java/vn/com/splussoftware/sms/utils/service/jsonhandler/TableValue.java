package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class TableValue implements Comparable<TableValue> {
	private int columnId;
	private int row;
	private String value;
	private Condition conditions;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
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

	public void setConditions(Condition condition) {
		this.conditions = condition;
	}

	@Override
	public int compareTo(TableValue o) {
		int lvRow = this.row - o.row;
		if (lvRow!=0) return lvRow;
		int lvColumn = this.getColumnId() - o.getColumnId();
		return lvColumn;
	}

	@Override
	public boolean equals(Object obj) {
		TableValue comValue = (TableValue)obj;
		return comValue.getRow() == this.getRow() && comValue.getColumnId() == this.getColumnId();
	}
}
