/**
 * 
 */
package vn.com.splussoftware.sms.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.splussoftware.sms.model.entity.CustomerEntity;
import vn.com.splussoftware.sms.model.entity.ProviderEntity;

/**
 * @author KietLT
 * 
 * 3:13 PM 
 * 
 * 2/15/2016
 *
 */
public interface ProviderRepository extends JpaRepository<ProviderEntity,Integer>{
	/**
	 * search record by name
	 * @param name
	 * @return list of record have name are input string
	 */
	public ProviderEntity  findByName(String name);

}
