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
import vn.com.splussoftware.sms.model.constant.CategoryEntityConstant;
import vn.com.splussoftware.sms.model.constant.LocationEntityConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 *
 */
@Entity
@Table(name=LocationEntityConstant.TABLE_NAME)
@Data
public class LocationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = LocationEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=LocationEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =LocationEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@Column (name = LocationEntityConstant.COLUMN_PROVIDER_ID)
	private Integer providerId;
	
	@NotNull
	@Column(name = LocationEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = LocationEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = LocationEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = LocationEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;
}
