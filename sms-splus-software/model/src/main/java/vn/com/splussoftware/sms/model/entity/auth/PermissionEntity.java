package vn.com.splussoftware.sms.model.entity.auth;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="permissiontbl")
@Data
public class PermissionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="permissiontbl_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="target_type")
	private String targetType;
	
	@Column(name="target_id")
	private Integer targetId;
	
	@Column(name="permission")
	private String permission;
	
//	@Column(name="user_id")
//	private Integer userId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private SMSUserEntity user;
	
//	@Column(name="group_id")
//	private Integer groupId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="group_id")
	private SMSGroupEntity group;
	
}
