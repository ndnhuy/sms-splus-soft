/**
 * 
 */
package vn.com.splussoftware.sms.utils.dto;

import java.util.Date;

import org.dozer.Mapping;

import lombok.Data;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 *
 */
@Data
public class ProcessesDto {
	
	@Mapping("id")
	private Integer id;
	
	@Mapping("name")
	private String name;
	
	@Mapping("description")
	private String description;
	
	@Mapping("serviceId")
	private Integer serviceId;
	
	@Mapping("locationId")
	private Integer locationId;
	
	@Mapping("input")
	private String input;
	
	@Mapping("output")
	private String output;
	
	@Mapping("workflow")
	private String workflow;
	
	@Mapping("createTime")
	private Date createTime;
	
	@Mapping("createBy")
	private String createBy;
	
	@Mapping("modifyTime")
	private Date modifyTime;
	
	@Mapping("modifyBy")
	private String modifyBy;
	
	@Mapping("isActive")
	private Boolean isActive = ServicesConstant.NON_DELETE;

	/**
	 * kietlt 9:49 PM 2016/2/17
	 * 
	 * using set data 
	 * 
	 */
	public ProcessesDto setData(ProcessesDto data) {
		this.name= data.getName();
		this.description = data.getDescription();
		this.serviceId = data.getServiceId();
		this.locationId = data.getLocationId();
		this.input = data.getInput();
		this.output = data.getOutput();
		this.workflow =data.getWorkflow();
		this.createTime = data.getCreateTime();
		this.createBy = data.getCreateBy();
		this.modifyBy = data.getModifyBy();
		this.modifyTime = data.getModifyTime();
		return this;
	}	
}
