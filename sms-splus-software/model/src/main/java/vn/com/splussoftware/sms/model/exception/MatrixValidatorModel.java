package vn.com.splussoftware.sms.model.exception;

import java.util.List;

public class MatrixValidatorModel extends ExcelValidatorModel {
	private int id;
	private int columnSize;
	private int rowSize;
	private List<String> header;
	private List<String> rowHeader;

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

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String> getRowHeader() {
		return rowHeader;
	}

	public void setRowHeader(List<String> rowHeader) {
		this.rowHeader = rowHeader;
	}

}
