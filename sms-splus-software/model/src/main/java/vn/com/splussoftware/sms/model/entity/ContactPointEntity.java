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
import vn.com.splussoftware.sms.model.constant.ContactPointEntityConstant;
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 * 2:00 PM 2/15/2016
 *
 */
@Entity
@Table(name=ContactPointEntityConstant.TABLE_NAME)
@Data
public class ContactPointEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = ContactPointEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=ContactPointEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =ContactPointEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@Column (name = ContactPointEntityConstant.COLUMN_PHONE)
	private String phone;
	
	@NotNull
	@Column (name = ContactPointEntityConstant.COLUMN_EMAIL)
	private String email;
	
	@NotNull
	@Column (name = ContactPointEntityConstant.COLUMN_ADDRESS)
	private String address;
	
	@NotNull
	@Column(name = ContactPointEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = ContactPointEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = ContactPointEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = ContactPointEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = ContactPointEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;

}
