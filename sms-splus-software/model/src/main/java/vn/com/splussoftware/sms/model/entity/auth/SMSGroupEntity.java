package vn.com.splussoftware.sms.model.entity.auth;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="smsgrouptbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SMSGroupEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	@SequenceGenerator(name="seq", sequenceName="smsgrouptbl_id_seq", allocationSize=1)
	private Integer id;
	
	@Column(name="group_name")
	private String groupName;
	
	@Column(name="description")
	private String description;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="groupusermappingtbl",
				joinColumns={@JoinColumn(name="group_id")},
				inverseJoinColumns={@JoinColumn(name="user_id")})
	private List<SMSUserEntity> users = new ArrayList<SMSUserEntity>();
}
