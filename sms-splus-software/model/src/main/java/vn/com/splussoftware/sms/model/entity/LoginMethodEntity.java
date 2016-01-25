package vn.com.splussoftware.sms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="login_method")
@Data
public class LoginMethodEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="login_method_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="login_type")
	private String loginType;
	
	@Column(name="url")
	private String url;
	
	@Column(name="priority")
	private Integer priority;
}
