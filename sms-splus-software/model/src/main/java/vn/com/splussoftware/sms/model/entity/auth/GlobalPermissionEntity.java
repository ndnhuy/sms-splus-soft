package vn.com.splussoftware.sms.model.entity.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="globalpermissiontbl")
@Data
public class GlobalPermissionEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="globalpermissiontbl_id_seq", allocationSize=1)
	private Integer id;
	
//	@ManyToOne(fetch=FetchType.LAZY)
//	private SMSUserEntity user;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="permission")
	private String permission;
}
