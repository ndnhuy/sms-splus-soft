package vn.com.splussoftware.sms.utils.service.jsonhandler;

import java.util.ArrayList;
import java.util.List;

public class TableData {
	private List<TableColumn> columns = new ArrayList<TableColumn>();
	private List<TableValue> values = new ArrayList<TableValue>();

	public List<TableColumn> getColumns() {
		return columns;
	}

	public List<TableValue> getValues() {
		return values;
	}

	public void setColumns(List<TableColumn> columns) {
		this.columns = columns;
	}

	public void setValues(List<TableValue> values) {
		this.values = values;
	}

	public void change(TableValue mValue) {
		for (TableValue ma : values) {
			if (ma.getColumnId() == mValue.getColumnId() && ma.getRow() == mValue.getRow()) {
				ma.setValue(mValue.getValue());
				return;
			}
		}
		values.add(mValue);
	}

	// TuanHMA 1-29-2016 Return number of row.
	public int getMaxRow() {
		int max = 1;
		for (TableValue v : values) {
			if (max < v.getRow())
				max = v.getRow();
		}
		return max;
	}

	public TableColumn getTableColumn(int columnId) {
		for (int i = 0; i < columns.size(); i++) {
			if (columns.get(i).getId() == columnId) {
				return columns.get(i);
			}
		}
		return null;
	} // TuanHMA 1-29-2016 Create empty values so that every field have 1 value.

	public void fillData() {
		for (TableColumn column : columns) {
			boolean[] found = new boolean[getMaxRow()];
			for (TableValue value : values) {
				if (value.getColumnId() == column.getId()) {
					found[value.getRow() - 1] = true;
				}
			}
			for (int i = 0; i < found.length; i++) {
				if (!found[i]) {
					TableValue newValue = new TableValue();
					newValue.setColumnId(column.getId());
					newValue.setRow(i + 1);
					values.add(newValue);
				}
			}
		}
	}

	public static List<String> getColumnName(List<TableColumn> listColumn) {
		List<String> listColumnName = new ArrayList<>();
		for (int i = 0; i < listColumn.size(); i++) {
			listColumnName.add(listColumn.get(i).getName());
		}
		return listColumnName;
	}
	public TableValue getValueById(int column, int row){
		for (TableValue value : values){
			if (value.getColumnId() == column && value.getRow() == row){
				return value;
			}
		}
		return null;
	}
	public void shiftRow(int row, boolean isShiftUp){
		int shift = -1;
		if (!isShiftUp) shift = 1;
			for (TableValue value : values){
				if (value.getRow() == row){
					value.setRow(-1);
				}
			}
			for (TableValue value : values){
				if (value.getRow() == row + shift){
					value.setRow(row);
				}
			}
			for (TableValue value : values){
				if (value.getRow() == -1){
					value.setRow(row + shift);
				}
			}
	}
	
	public void dragRow(int fromRow, int toRow){
		if (fromRow < toRow){
			for (int i = fromRow;i< toRow; i++){
				shiftRow(i,false);
			}
		} else
			if (fromRow > toRow){
				for (int i = fromRow;i > toRow; i--){
					shiftRow(i,true);
				}
			}
	}
	public String getColumnType(int columnId){
		for (TableColumn column : columns){
			if (column.getId() == columnId){
				return column.getType();
			}
		}
		return null;
	}
}
