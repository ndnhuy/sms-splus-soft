package vn.com.splussoftware.sms.utils.service.jsonhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class MatrixData {
	List<MatrixColumnRow> columns = new ArrayList<MatrixColumnRow>();
	List<MatrixColumnRow> rows = new ArrayList<MatrixColumnRow>();
	List<MatrixValue> values = new ArrayList<MatrixValue>();

	public List<MatrixColumnRow> getColumns() {
		return columns;
	}

	public void setColumns(List<MatrixColumnRow> columns) {
		this.columns = columns;
	}

	public List<MatrixColumnRow> getRows() {
		return rows;
	}

	public void setRows(List<MatrixColumnRow> rows) {
		this.rows = rows;
	}

	public List<MatrixValue> getValues() {
		return values;
	}

	public void setValues(List<MatrixValue> values) {
		this.values = values;
	}

	// TuanHMA change or add new value to MatrixData
	public void change(MatrixValue mValue) {
		for (MatrixValue ma : values) {
			if (ma.getColumnId() == mValue.getColumnId() && ma.getRowId() == mValue.getRowId()) {
				ma.setValue(mValue.getValue());
				return;
			}
		}
		values.add(mValue);
	}

	public void addColumn(MatrixColumnRow matrixColumn) {
		this.getColumns().add(matrixColumn);
	}

	public void addRow(MatrixColumnRow matrixRow) {
		this.getRows().add(matrixRow);
	}

	public void addValue(MatrixValue matrixValue) {
		this.getValues().add(matrixValue);
	}

	public void fillData() {
		for (MatrixColumnRow column : columns) {
			HashMap<Integer, Boolean> foundByRow = new HashMap<Integer, Boolean>();
			for (MatrixColumnRow row : rows) {
				foundByRow.put(row.getId(), false);
			}
			for (MatrixValue value : values) {
				if (value.getColumnId() == column.getId()) {
					foundByRow.replace(value.getRowId(), true);
				}
			}
			for (Entry<Integer, Boolean> entry : foundByRow.entrySet()) {
				if (entry.getValue())
					continue;
				MatrixValue newValue = new MatrixValue();
				newValue.setColumnId(column.getId());
				newValue.setRowId(entry.getKey());
				values.add(newValue);
			}
		}
	}
}
