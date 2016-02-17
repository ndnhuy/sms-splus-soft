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
import vn.com.splussoftware.sms.model.constant.ServicesConstant;

/**
 * @author KietLT
 * 11:18 AM 2/15/2016
 * Entity of category table
 *
 */
@Entity
@Table(name=CategoryEntityConstant.TABLE_NAME)
@Data
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = CategoryEntityConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name=CategoryEntityConstant.COLUMN_NAME)
	private String name;
	
	@NotNull
	@Column(name =CategoryEntityConstant.COLUMN_DESCRIPTION)
	private String description;
	
	@NotNull
	@Column (name = CategoryEntityConstant.COLUMN_CUSTOMER_ID)
	private Integer customerId;
	
	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_CREATE_TIME)
	private Date createTime;

	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_MODIFIED_TIME)
	private Date modifyTime;

	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_MODIFIED_BY)
	private String modifyBy;

	@NotNull
	@Column(name = CategoryEntityConstant.COLUMN_IS_ACTIVE)
	private Boolean isActive = ServicesConstant.NON_DELETE;
}
