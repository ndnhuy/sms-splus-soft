/**
 * 
 */
package vn.com.splussoftware.sms.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import vn.com.splussoftware.sms.model.constant.ProcessesEntityConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 *
 */
@Entity
@Table(name=ProcessesEntityConstant.TABLE_NAME)
@Data
public class ProcessesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = ProcessesEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=ProcessesEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =ProcessesEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@Column (name = ProcessesEntityConstant.COLUMN_SERVICE_ID)
	private Integer serviceId;
	
	@NotNull
	@Column (name = ProcessesEntityConstant.COLUMN_LOCATION_ID)
	private Integer locationId;
	
	@NotNull
	@Column (name = ProcessesEntityConstant.COLUMN_INPUT)
	private String input;
	
	@NotNull
	@Column (name = ProcessesEntityConstant.COLUMN_OUTPUT)
	private String output;
	
	@NotNull
	@Column (name = ProcessesEntityConstant.COLUMN_WORKFLOW)
	private String workflow;
	
	@NotNull
	@Column(name = ProcessesEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = ProcessesEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = ProcessesEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = ProcessesEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = ProcessesEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;

}
