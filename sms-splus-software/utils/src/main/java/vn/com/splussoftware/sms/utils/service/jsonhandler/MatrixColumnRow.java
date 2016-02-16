package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class MatrixColumnRow implements Comparable<MatrixColumnRow> {
	private String name;
	private int id;
	private int position;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	public int compareTo(MatrixColumnRow o) {
		return this.getPosition() - o.getPosition();
	}
}
