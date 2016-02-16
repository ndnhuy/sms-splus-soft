package vn.com.splussoftware.sms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "draft")
public class DraftEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "draftid")
	private int draftid;
 @Column(name = "draftcontent")
	private String draftcontent;
 @Column(name = "time")
	private long time;
	
	public DraftEntity setData(DraftEntity data) {
		this.draftcontent = data.draftcontent;
		this.time = data.time;
		return this;
	}
    @Override
    public String toString() {
        return String.format(
                "Draft[id=%d, content='%s', time=%d]",
                draftid, draftcontent, time);
    }
}
