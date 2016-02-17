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
import vn.com.splussoftware.sms.model.constant.ServicesConstant;
import vn.com.splussoftware.sms.model.constant.ServicesModelConstant;
/**
 * 3:30 PM 2016/1/27
 * @author KietLT
 *
 */
@Entity
@Table(name = "servicestbl")
@Data
public class ServicesEntity {
	/**
	 * kietlt 3:30 PM 2016/1/27 entity of servicestbl Table
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = ServicesModelConstant.COLUMN_ID)
	private Integer id;
	
	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_TITLE)
	private String title;
	
	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_DESCRIPTION)
	private String description;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_PRIORITY)
	private Integer priority;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_PROVIDER_ID)
	private Integer providerId;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_SLA_RESPONSE_TIME)
	private Double slaResponseTime;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_SLA_IMPLEMENT_TIME)
	private Double slaImplementTime;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_SLA_FIX_TIME)
	private Double slaFixTime;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_CREATE_DATE)
	private Date createDate;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_CREATE_BY)
	private String createBy;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_MODIFY_DATE)
	private Date modifyDate;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_MODIFY_BY)
	private String modifyBy;

	@NotNull
	@Column(name = ServicesModelConstant.COLUMN_ACTIVE)
	private Boolean active = ServicesConstant.NON_DELETE;

	/**
	 * kietlt 3:30 PM 2016/1/27
	 * using set data for EService with new EService
	 * 
	 * @param ESerives
	 *
	 * @return: EServices
	 */
	public ServicesEntity setData(ServicesEntity data) {
		this.title = data.getTitle();
		this.description = data.getDescription();
		this.priority = data.getPriority();
		this.providerId = data.getProviderId();
		this.slaFixTime = data.getSlaFixTime();
		this.slaImplementTime = data.getSlaImplementTime();
		this.slaResponseTime = data.getSlaResponseTime();
		this.createDate = data.getCreateDate();
		this.createBy = data.getCreateBy();
		this.modifyBy = data.getModifyBy();
		this.modifyDate = data.getModifyDate();
		return this;
	}

}
