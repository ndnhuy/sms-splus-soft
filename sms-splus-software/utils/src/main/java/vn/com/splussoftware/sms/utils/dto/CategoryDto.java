package vn.com.splussoftware.sms.utils.dto;

import java.util.Date;

import org.dozer.Mapping;

import lombok.Data;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 * 
 *         2:46 PM
 * 
 *         2/15/2016
 */
@Data
public class CategoryDto {

	@Mapping("id")
	private Integer id;

	@Mapping("name")
	private String name;
	
	@Mapping("description")
	private String description;
	
	@Mapping("customerId")
	private Integer customerId;
	
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
}
