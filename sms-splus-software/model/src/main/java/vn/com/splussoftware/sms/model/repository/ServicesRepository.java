/**
 * @Author: @author KietLT
 *
 * @Time: 6:28:34 PM
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.com.splussoftware.sms.model.entity.ServicesEntity;

/**
 * @author KietLT
 * @Time: 6:28:34 PM
 *
 */
@Repository
public interface ServicesRepository extends JpaRepository<ServicesEntity, Integer>{
	
	/**
	 * search record by title
	 * @param title
	 * @return list of record have title are input string
	 */
	public ServicesEntity  findByTitle(String title);
}
