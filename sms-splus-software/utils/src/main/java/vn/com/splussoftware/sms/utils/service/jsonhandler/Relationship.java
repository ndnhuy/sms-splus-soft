package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class Relationship {
	private long id;
	private long from;
	private long to;
	private long realid;
	private long fromreal;
	private long toreal;
	private boolean autoCreate;
	private String status;
	private DataObject detail;
	private String type;
	public long getRealid() {
		return realid;
	}
	public void setRealid(long realid) {
		this.realid = realid;
	}
	public long getFromreal() {
		return fromreal;
	}
	public void setFromreal(long fromreal) {
		this.fromreal = fromreal;
	}
	public long getToreal() {
		return toreal;
	}
	public void setToreal(long toreal) {
		this.toreal = toreal;
	}
	
	public long getFrom() {
		return from;
	}
	public void setFrom(long from) {
		this.from = from;
	}
	public long getTo() {
		return to;
	}
	public void setTo(long to) {
		this.to = to;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public DataObject getDetail() {
		return detail;
	}
	public void setDetail(DataObject detail) {
		this.detail = detail;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isAutoCreate() {
		return autoCreate;
	}
	public void setAutoCreate(boolean autoCreate) {
		this.autoCreate = autoCreate;
	}
	
}
