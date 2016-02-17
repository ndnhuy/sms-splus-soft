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
import vn.com.splussoftware.sms.model.constant.ProviderEntityConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 *	12:02 PM 2/15/2016
 */
@Entity
@Table(name = ProviderEntityConstant.TABLE_NAME)
@Data
public class ProviderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = ProviderEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=ProviderEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =ProviderEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@Column (name = ProviderEntityConstant.COLUMN_CONTACT_POINT_ID)
	private Integer contactPointId;
	
	@NotNull
	@Column(name = ProviderEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = ProviderEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = ProviderEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = ProviderEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = ProviderEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;
}
