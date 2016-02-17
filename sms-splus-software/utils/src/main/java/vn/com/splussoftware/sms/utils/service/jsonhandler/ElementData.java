package vn.com.splussoftware.sms.utils.service.jsonhandler;

public abstract class ElementData implements Comparable<ElementData> {
	protected String name;
	protected String dataType;
	protected int id;
	protected int position;
	protected Condition conditions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int compareTo(ElementData o) {
		return this.getPosition() - o.getPosition();
	}
	
}
