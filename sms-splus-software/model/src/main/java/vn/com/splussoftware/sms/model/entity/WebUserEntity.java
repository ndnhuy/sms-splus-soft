package vn.com.splussoftware.sms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="web_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebUserEntity {
	
	public WebUserEntity(WebUserEntity user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.role = user.getRole();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="web_user_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name="role")
	private String role;
	
	@Column(name="user_login_type")
	private String userLoginType;
}
