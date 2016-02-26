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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import vn.com.splussoftware.sms.model.constant.CustomerEntityConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 *
 */
@Entity
@Table(name=CustomerEntityConstant.TABLE_NAME)
@Data
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = CustomerEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=CustomerEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =CustomerEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@DecimalMin(value="0",message="Not number")
	@Column (name = CustomerEntityConstant.COLUMN_CONTACT_POINT_ID)
	private Integer contactPointId;
	
	@NotNull
	@Column(name = CustomerEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = CustomerEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = CustomerEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = CustomerEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = CustomerEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;
}
