/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.ContactPointEntity;
import vn.com.splussoftware.sms.model.entity.ProcessesEntity;

/**
 * @author KietLT
 * 
 * 4:00 PM
 * 
 * 2/15/2016
 *
 */
public interface ContactPointRepository extends JpaRepository<ContactPointEntity,Integer> {
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public ContactPointEntity  findByName(String name);

}
