
package vn.com.splussoftware.sms.utils.dto;

import java.util.Date;

import org.dozer.Mapping;

import lombok.Data;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 * 
 * 3:00 PM 
 * 
 * 2/15/2016
 *
 */

@Data
public class LocationDto {
	@Mapping("id")
	private Integer id;
	
	@Mapping("name")
	private String name;
	
	@Mapping("description")
	private String description;
	
	@Mapping("providerId")
	private Integer providerId;
	
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
	public LocationDto setData(LocationDto data) {
		this.name= data.getName();
		this.description = data.getDescription();
		this.providerId = data.getProviderId();
		this.createTime = data.getCreateTime();
		this.createBy = data.getCreateBy();
		this.modifyBy = data.getModifyBy();
		this.modifyTime = data.getModifyTime();
		return this;
	}
}
