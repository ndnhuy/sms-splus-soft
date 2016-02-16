package vn.com.splussoftware.sms.model.entity.login;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="smsusertbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSUserEntity {
	
	public SMSUserEntity(SMSUserEntity user) {
		this.id = user.getId();
		this.userkey = user.getUserkey();
		this.password = user.getPassword();
		this.status = user.getStatus();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="smsusertbl_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="userkey")
	private String userkey;
	
	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name="login_method_id")
	private LoginMethodEntity loginMethodEntity;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private List<GlobalPermissionEntity> globalPermissions;
}
