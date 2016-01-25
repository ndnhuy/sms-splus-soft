package vn.com.splussoftware.sms.utils.dto;


public class RestSuccessDto {
	private Integer status;
	private Object data;
	private String message;
	
	
	
	public RestSuccessDto(Integer status, Object data, String message) {
		super();
		this.status = status;
		this.data = data;
		this.message = message;
	}
	public RestSuccessDto(Integer status, Object data) {
		super();
		this.status = status;
		this.data = data;
		this.message = null;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}