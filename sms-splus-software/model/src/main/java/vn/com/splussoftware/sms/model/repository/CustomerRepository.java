/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.CustomerEntity;
import vn.com.splussoftware.sms.model.entity.LocationEntity;

/**
 * @author KietLT
 *
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public CustomerEntity  findByName(String name);

}
