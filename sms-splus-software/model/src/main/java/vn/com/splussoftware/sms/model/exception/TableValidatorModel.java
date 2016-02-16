package vn.com.splussoftware.sms.model.exception;

import java.util.List;

public class TableValidatorModel extends ExcelValidatorModel {
	private int id;
	private int columnSize;
	private List<String> header;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

}
