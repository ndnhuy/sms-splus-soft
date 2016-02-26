package vn.com.splussoftware.sms.utils.service.jsonhandler;

public class Relative {
	private long id;
	private long realid;
	private boolean autoCreate;
	private String type;
	private String summary;
	private String owner;
	private boolean fromInput;
	private boolean toOutput;
	private String status;
	private boolean isDone;
	private String phaseType;
	private RelativeRoles roles;
	private RelativeDisplay display;
	
	public boolean isAutoCreate() {
		return autoCreate;
	}
	public void setAutoCreate(boolean autoCreate) {
		this.autoCreate = autoCreate;
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public boolean isFromInput() {
		return fromInput;
	}
	public void setFromInput(boolean fromInput) {
		this.fromInput = fromInput;
	}
	public boolean isToOutput() {
		return toOutput;
	}
	public void setToOutput(boolean toOutput) {
		this.toOutput = toOutput;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public String getPhaseType() {
		return phaseType;
	}
	public void setPhaseType(String phaseType) {
		this.phaseType = phaseType;
	}
	public RelativeRoles getRoles() {
		return roles;
	}
	public void setRoles(RelativeRoles roles) {
		this.roles = roles;
	}
	public RelativeDisplay getDisplay() {
		return display;
	}
	public void setDisplay(RelativeDisplay display) {
		this.display = display;
	}
	public long getRealid() {
		return realid;
	}
	public void setRealid(long realid) {
		this.realid = realid;
	}
}
