package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class TableColumn implements Comparable<TableColumn> {
	private String name;
	private String type;
	private int position;
	private int id;
	private Condition conditions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public Condition getConditions() {
		return conditions;
	}

	public void setConditions(Condition conditions) {
		this.conditions = conditions;
	}

	@Override
	public int compareTo(TableColumn o) {
		return this.getPosition() - o.getPosition();
	}

	@Override
	public boolean equals(Object o) {
		boolean retVal = false;

		if (o instanceof TableColumn) {
			TableColumn ptr = (TableColumn) o;
			retVal = ptr.name == this.name;
		}

		return retVal;
	}

//	@Override
//	public int hashCode() {
//		int hash = 7;
//		hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
//		return hash;
//	}
}
