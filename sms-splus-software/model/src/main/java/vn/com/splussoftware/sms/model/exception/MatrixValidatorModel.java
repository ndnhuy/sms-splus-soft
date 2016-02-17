package vn.com.splussoftware.sms.model.exception;

public class MatrixValidatorModel extends ExcelValidatorModel {
	private int columnId;
	private int rowId;

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

}
